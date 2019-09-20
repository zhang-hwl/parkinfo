package com.parkinfo.service.companyManage.impl;

import com.parkinfo.common.Result;
import com.parkinfo.dto.ParkUserDTO;
import com.parkinfo.entity.companyManage.CompanyDemand;
import com.parkinfo.entity.companyManage.CompanyDetail;
import com.parkinfo.entity.userConfig.ParkInfo;
import com.parkinfo.enums.CheckStatus;
import com.parkinfo.enums.ParkRoleEnum;
import com.parkinfo.exception.NormalException;
import com.parkinfo.repository.companyManage.CompanyDemandRepository;
import com.parkinfo.request.compayManage.AddCompanyInfoRequest;
import com.parkinfo.request.compayManage.QueryCompanyRequest;
import com.parkinfo.request.compayManage.SetCompanyInfoRequest;
import com.parkinfo.response.companyManage.CompanyDemandResponse;
import com.parkinfo.response.companyManage.CompanyResponse;
import com.parkinfo.service.companyManage.ICompanyDemandService;
import com.parkinfo.token.TokenUtils;
import com.parkinfo.util.ExcelUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CompanyDemandServiceImpl implements ICompanyDemandService {

    @Autowired
    private CompanyDemandRepository companyDemandRepository;

    @Autowired
    private TokenUtils tokenUtils;

    @Override
    public Result companyImport(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        if (!Objects.requireNonNull(fileName).matches("^.+\\.(?i)(xls)$") && !fileName.matches("^.+\\.(?i)(xlsx)$")) {
            throw new NormalException("上传文件格式不正确");
        }
        try {
            List<CompanyDemand> companyDemands = ExcelUtils.importExcel(file, CompanyDemand.class);
            if(companyDemands != null){
                companyDemands.forEach(companyDemand -> {
                    ParkInfo parkInfo = tokenUtils.getCurrentParkInfo();
                    companyDemand.setParkInfo(parkInfo);
                    companyDemand.setAvailable(true);
                    companyDemand.setDelete(false);
                    companyDemand.setCheckStatus(CheckStatus.APPLYING);
                    companyDemandRepository.save(companyDemand);
                });
            }
        } catch (Exception e) {
            throw new NormalException("上传失败");
        }
        return Result.builder().message("上传成功").build();
    }

    @Override
    public Result companyExport(HttpServletResponse response) {
        CompanyDemand companyDemand = new CompanyDemand();
        List<CompanyDemand> companyDemandList = new ArrayList<>();
        companyDemandList.add(companyDemand);
        try {
            ExcelUtils.exportExcel(companyDemandList, "需求信息模板", "需求信息模板", CompanyDetail.class, "company", response);
        } catch (Exception e) {
            throw new NormalException("模板下载失败");
        }
        return Result.builder().success().message("模板下载成功").build();
    }

    @Override
    public Result<Page<CompanyResponse>> findAll(QueryCompanyRequest request) {
        Pageable pageable = PageRequest.of(request.getPageNum(), request.getPageSize(), Sort.Direction.DESC, "createTime");
        Specification<CompanyDemand> specification = (Specification<CompanyDemand>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            ParkUserDTO currentUser = tokenUtils.getLoginUserDTO();
            if (StringUtils.isNotBlank(request.getCompanyName())) {
                predicates.add(criteriaBuilder.like(root.get("companyName").as(String.class), "%" + request.getCompanyName()+ "%"));
            }
            if (StringUtils.isNotBlank(request.getMainBusiness())) {
                predicates.add(criteriaBuilder.like(root.get("mainBusiness").as(String.class), "%" + request.getMainBusiness() + "%"));
            }
            if (null != request.getCheckStatus()) {
                predicates.add(criteriaBuilder.equal(root.get("checkStatus").as(Integer.class), request.getCheckStatus().ordinal()));
            }
            if (currentUser.getRole().contains(ParkRoleEnum.PARK_MANAGER.toString())
                    || currentUser.getRole().contains(ParkRoleEnum.PARK_USER.toString())
                    || currentUser.getRole().contains(ParkRoleEnum.OFFICER.toString())) {
                predicates.add(criteriaBuilder.equal(root.get("parkInfo").get("id").as(String.class), currentUser.getCurrentParkId()));
            }else {
                if (StringUtils.isNotBlank(request.getParkId())){
                    predicates.add(criteriaBuilder.equal(root.get("parkInfo").get("id").as(String.class), request.getParkId()));
                }
            }
            predicates.add(criteriaBuilder.equal(root.get("delete").as(Boolean.class), Boolean.FALSE));
            predicates.add(criteriaBuilder.equal(root.get("available").as(Boolean.class), Boolean.TRUE));
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
        Page<CompanyDemand> companyDemandPage = companyDemandRepository.findAll(specification, pageable);
        Page<CompanyResponse> responses = this.convertDemandPage(companyDemandPage);
        return Result.<Page<CompanyResponse>>builder().success().data(responses).build();
    }

    @Override
    public Result<CompanyDemandResponse> query(String id) {
        CompanyDemand companyDemand = this.checkCompany(id);
        CompanyDemandResponse response = new CompanyDemandResponse();
        BeanUtils.copyProperties(companyDemand,response);
        return Result.<CompanyDemandResponse>builder().success().data(response).build();
    }

    @Override
    public Result add(AddCompanyInfoRequest request) {
        CompanyDemand companyDemand = new CompanyDemand();
        ParkInfo parkInfo = tokenUtils.getCurrentParkInfo();
        BeanUtils.copyProperties(request,companyDemand);
        companyDemand.setParkInfo(parkInfo);
        companyDemand.setAvailable(true);
        companyDemand.setDelete(false);
        companyDemand.setCheckStatus(CheckStatus.APPLYING);
        companyDemandRepository.save(companyDemand);
        return Result.builder().success().message("添加成功").build();
    }

    @Override
    public Result set(SetCompanyInfoRequest request) {
        CompanyDemand companyDemand = this.checkCompany(request.getId());
        BeanUtils.copyProperties(request,companyDemand);
        companyDemandRepository.save(companyDemand);
        return Result.builder().success().message("修改成功").build();
    }

    @Override
    public Result delete(String id) {
        CompanyDemand companyDemand = this.checkCompany(id);
        companyDemand.setDelete(true);
        companyDemandRepository.save(companyDemand);
        return Result.builder().success().message("删除成功").build();
    }

    private Page<CompanyResponse> convertDemandPage(Page<CompanyDemand> companyDemandPage) {
        List<CompanyResponse> content = new ArrayList<>();
        companyDemandPage.getContent().forEach(companyDemand -> {
            CompanyResponse response = new CompanyResponse();
            BeanUtils.copyProperties(companyDemand,response);
            content.add(response);
        });
        return new PageImpl<>(content, companyDemandPage.getPageable(), companyDemandPage.getTotalElements());
    }

    private CompanyDemand checkCompany(String id) {
        Optional<CompanyDemand> companyDemandOptional = companyDemandRepository.findByIdAndDeleteIsFalseAndAvailableIsTrue(id);
        if (!companyDemandOptional.isPresent()) {
            throw new NormalException("需求信息不存在");
        }
        return companyDemandOptional.get();
    }
}
