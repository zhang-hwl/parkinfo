package com.parkinfo.service.personalCloud.impl;

import com.parkinfo.common.Result;
import com.parkinfo.entity.personalCloud.CloudDisk;
import com.parkinfo.entity.userConfig.ParkUser;
import com.parkinfo.enums.FileUploadType;
import com.parkinfo.exception.NormalException;
import com.parkinfo.repository.personalCloud.CloudDiskRepository;
import com.parkinfo.request.personalCloud.AddPersonalCloudRequest;
import com.parkinfo.request.personalCloud.QueryPersonalCloudRequest;
import com.parkinfo.request.personalCloud.SetPersonalCloudRequest;
import com.parkinfo.response.personalCloud.DownloadResponse;
import com.parkinfo.response.personalCloud.PersonalCloudResponse;
import com.parkinfo.response.personalCloud.UploadFileResponse;
import com.parkinfo.service.personalCloud.IPersonalCloudService;
import com.parkinfo.token.TokenUtils;
import com.parkinfo.tools.oss.IOssService;
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
            ParkUser loginUser = tokenUtils.getLoginUser();
            Join<CloudDisk, ParkUser> join = root.join(root.getModel().getSingularAttribute("parkUser", ParkUser.class), JoinType.LEFT);
            predicates.add(criteriaBuilder.equal(join.get("id").as(String.class), loginUser.getId()));
            predicates.add(criteriaBuilder.equal(root.get("delete").as(Boolean.class), Boolean.FALSE));
            predicates.add(criteriaBuilder.equal(root.get("available").as(Boolean.class), Boolean.TRUE));
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
        Page<CloudDisk> cloudDiskPage = cloudDiskRepository.findAll(specification, pageable);
        Page<PersonalCloudResponse> responses = this.convertCloudPage(cloudDiskPage);
        return Result.<Page<PersonalCloudResponse>>builder().success().data(responses).build();
    }

    @Override
    public Result<List<UploadFileResponse>> uploadFile(HttpServletRequest request) {
        //创建一个通用的多部分解析器
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        //判断 request 是否有文件上传,即多部分请求
        if (multipartResolver.isMultipart(request)) {
            //转换成多部分request
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
            // 取得request中的所有文件名
            Iterator<String> iter = multiRequest.getFileNames();
            List<UploadFileResponse> responses = new ArrayList<>();
            while (iter.hasNext()) {
                UploadFileResponse response = new UploadFileResponse();
                // 取得上传文件
                MultipartFile file = multiRequest.getFile(iter.next());
                String fileName = file.getOriginalFilename();
                long size = file.getSize();
                String fileSize = this.getFileSize(size);
                String fileUrl;
                try {
                    InputStream inputStream = file.getInputStream();
                    fileUrl = ossService.upload(inputStream, FileUploadType.PERSONAL_CLOUD.toString(), fileName);
                    inputStream.close();
                } catch (IOException e) {
                    throw new NormalException("上传失败," + "文件格式不正确");
                }
                response.setFileName(fileName);
                response.setFileSize(fileSize);
                response.setFileUrl(fileUrl);
                response.setUpTime(new Date());
                responses.add(response);
            }
            return Result.<List<UploadFileResponse>>builder().success().data(responses).build();
        } else {
            throw new NormalException("请选择上传文件");
        }
    }

    @Override
    public Result add(AddPersonalCloudRequest request) {
        List<UploadFileResponse> uploadFileResponses = request.getUploadFileResponses();
        for (UploadFileResponse uploadFileResponse: uploadFileResponses) {
            CloudDisk cloudDisk = new CloudDisk();
            BeanUtils.copyProperties(uploadFileResponse,cloudDisk);
            ParkUser loginUser = tokenUtils.getLoginUser();
            cloudDisk.setParkUser(loginUser);
            cloudDisk.setDelete(false);
            cloudDisk.setAvailable(true);
            cloudDiskRepository.save(cloudDisk);
        }
        return Result.builder().success().message("上传成功").build();
    }

    @Override
    public Result delete(String id) {
        CloudDisk cloudDisk = this.checkCloudDisk(id);
        cloudDisk.setDelete(true);
        cloudDiskRepository.save(cloudDisk);
        return Result.builder().success().message("删除成功").build();
    }

    @Override
    public Result set(SetPersonalCloudRequest request) {
        CloudDisk cloudDisk = this.checkCloudDisk(request.getId());
        BeanUtils.copyProperties(request, cloudDisk);
        cloudDiskRepository.save(cloudDisk);
        return Result.builder().success().message("修改成功").build();
    }

    @Override
    public Result<DownloadResponse> download(String id) {
        CloudDisk cloudDisk = this.checkCloudDisk(id);
        DownloadResponse response = new DownloadResponse();
        BeanUtils.copyProperties(cloudDisk,response);
        return Result.<DownloadResponse>builder().success().data(response).build();
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

    private String getFileSize(long size) {
        // 如果字节数少于1024，则直接以B为单位，否则先除于1024，后3位因太少无意义
        double value = (double) size;
        if (value < 1024) {
            return String.valueOf(value) + "B";
        } else {
            value = new BigDecimal(value / 1024).setScale(2, BigDecimal.ROUND_DOWN).doubleValue();
        }
        // 如果原字节数除于1024之后，少于1024，则可以直接以KB作为单位
        // 因为还没有到达要使用另一个单位的时候
        // 接下去以此类推
        if (value < 1024) {
            return String.valueOf(value) + "KB";
        } else {
            value = new BigDecimal(value / 1024).setScale(2, BigDecimal.ROUND_DOWN).doubleValue();
        }
        if (value < 1024) {
            return String.valueOf(value) + "MB";
        } else {
            // 否则如果要以GB为单位的，先除于1024再作同样的处理
            value = new BigDecimal(value / 1024).setScale(2, BigDecimal.ROUND_DOWN).doubleValue();
            return String.valueOf(value) + "GB";
        }
    }

    private CloudDisk checkCloudDisk(String id){
        Optional<CloudDisk> cloudDiskOptional = cloudDiskRepository.findByIdAndDeleteIsFalseAndAvailableIsTrue(id);
        if (!cloudDiskOptional.isPresent()) {
            throw new NormalException("文件不存在");
        }
        return cloudDiskOptional.get();
    }
}
