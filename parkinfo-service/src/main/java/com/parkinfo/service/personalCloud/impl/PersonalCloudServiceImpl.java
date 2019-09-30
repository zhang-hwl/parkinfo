package com.parkinfo.service.personalCloud.impl;

import com.parkinfo.common.Result;
import com.parkinfo.entity.personalCloud.CloudDisk;
import com.parkinfo.entity.userConfig.ParkUser;
import com.parkinfo.enums.FileUploadType;
import com.parkinfo.exception.NormalException;
import com.parkinfo.repository.personalCloud.CloudDiskRepository;
import com.parkinfo.request.personalCloud.*;
import com.parkinfo.response.personalCloud.DownloadResponse;
import com.parkinfo.response.personalCloud.PersonalCloudResponse;
import com.parkinfo.response.personalCloud.UploadFileResponse;
import com.parkinfo.service.personalCloud.IPersonalCloudService;
import com.parkinfo.token.TokenUtils;
import com.parkinfo.tools.oss.IOssService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;

@Service
public class PersonalCloudServiceImpl implements IPersonalCloudService {

    @Autowired
    private CloudDiskRepository cloudDiskRepository;
    @Autowired
    private TokenUtils tokenUtils;
    @Autowired
    private IOssService ossService;

    @Override
    public Result<Page<PersonalCloudResponse>> findAll(QueryPersonalCloudRequest request) {
        Pageable pageable = PageRequest.of(request.getPageNum(), request.getPageSize(), Sort.Direction.DESC, "createTime");
        Specification<CloudDisk> specification = (Specification<CloudDisk>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.isNotBlank(request.getFileName())){
                predicates.add(criteriaBuilder.like(root.get("fileName").as(String.class), "%"+request.getFileName()+"%"));
            }
            ParkUser loginUser = tokenUtils.getLoginUser();
            predicates.add(criteriaBuilder.equal(root.get("parkUser").get("id").as(String.class), loginUser.getId()));
            predicates.add(criteriaBuilder.equal(root.get("delete").as(Boolean.class), Boolean.FALSE));
            predicates.add(criteriaBuilder.equal(root.get("available").as(Boolean.class), Boolean.TRUE));
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
        Page<CloudDisk> cloudDiskPage = cloudDiskRepository.findAll(specification, pageable);
        Page<PersonalCloudResponse> responses = this.convertCloudPage(cloudDiskPage);
        return Result.<Page<PersonalCloudResponse>>builder().success().data(responses).build();
    }

    @Override
    public Result<String> uploadFile(HttpServletRequest servletRequest, UploadFileRequest request) {
        ParkUser loginUser = tokenUtils.getLoginUser();
        if(loginUser == null){
            throw new NormalException("token不存在或已过期");
        }
        MultipartFile file = request.getMultipartFile();
        String filename = file.getOriginalFilename();
        CloudDisk cloudDisk = new CloudDisk();
        cloudDisk.setUploadTime(new Date());
        cloudDisk.setRemark(request.getRemark());
        cloudDisk.setParkUser(loginUser);
        cloudDisk.setDelete(false);
        cloudDisk.setAvailable(true);
        cloudDisk.setFileName(filename);
        String size_S = String.valueOf(file.getSize());
        double size = Double.parseDouble(size_S);
        DecimalFormat df = new DecimalFormat("0.0");
        if(size < 1024){
            cloudDisk.setFileSize(size_S+"B");
        }
        else if(size >= 1024 && size < 1024*1024){
            cloudDisk.setFileSize(new Double(df.format(size/1024))+"K");
        }
        else if(size >= 1024*1024 && size < 1024*1024*1024){
            cloudDisk.setFileSize(new Double(df.format(size/1024/1024))+"M");
        }
        else if(size >= 1024*1024*1024 && size < 1024*1024*1024*1024){
            cloudDisk.setFileSize(new Double(df.format(size/1014/1024/1024))+"G");
        }
        else{
            throw new NormalException("文件过大，请重新选择");
        }
        String path = "parkInfo/"+tokenUtils.getCurrentParkInfo().getId()+"/cloud/"+loginUser.getId();
        String url = ossService.MultipartFileUpload(servletRequest, path);
        cloudDisk.setFileUrl(url);
        cloudDiskRepository.save(cloudDisk);
        return Result.<String>builder().success().data("上传成功").build();
    }

    @Override
    public Result<String> delete(String id) {
        CloudDisk cloudDisk = this.checkCloudDisk(id);
        cloudDisk.setDelete(true);
        cloudDiskRepository.save(cloudDisk);
        return Result.<String>builder().success().message("删除成功").build();
    }

    @Override
    public Result set(SetPersonalCloudRequest request) {
        CloudDisk cloudDisk = this.checkCloudDisk(request.getId());
        cloudDisk.setFileName(request.getFileName());
        cloudDiskRepository.save(cloudDisk);
        return Result.builder().success().message("修改成功").build();
    }

    @Override
    public Result deleteAll(DeletePersonalCloudRequest request) {
        List<CloudDisk> cloudDiskList = cloudDiskRepository.findAllById(request.getIds());
        cloudDiskList.forEach(cloudDisk -> {
            cloudDisk.setDelete(true);
        });
        cloudDiskRepository.saveAll(cloudDiskList);
        return Result.builder().success().message("删除成功").build();
    }

    private Page<PersonalCloudResponse> convertCloudPage(Page<CloudDisk> cloudDiskPage) {
        List<PersonalCloudResponse> content = new ArrayList<>();
        cloudDiskPage.getContent().forEach(cloudDisk -> {
            PersonalCloudResponse response = new PersonalCloudResponse();
            BeanUtils.copyProperties(cloudDisk, response);
            content.add(response);
        });
        return new PageImpl<>(content, cloudDiskPage.getPageable(), cloudDiskPage.getTotalElements());
    }

    private CloudDisk checkCloudDisk(String id){
        Optional<CloudDisk> cloudDiskOptional = cloudDiskRepository.findByIdAndDeleteIsFalseAndAvailableIsTrue(id);
        if (!cloudDiskOptional.isPresent()) {
            throw new NormalException("文件不存在");
        }
        return cloudDiskOptional.get();
    }
}
