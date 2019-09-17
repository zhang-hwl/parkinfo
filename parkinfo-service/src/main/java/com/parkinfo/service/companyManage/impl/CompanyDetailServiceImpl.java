package com.parkinfo.service.companyManage.impl;

import com.parkinfo.common.Result;
import com.parkinfo.entity.companyManage.CompanyDetail;
import com.parkinfo.entity.userConfig.ParkInfo;
import com.parkinfo.enums.CheckStatus;
import com.parkinfo.enums.EnterStatus;
import com.parkinfo.exception.NormalException;
import com.parkinfo.repository.companyManage.CompanyDetailRepository;
import com.parkinfo.request.compayManage.AddCompanyInfoRequest;
import com.parkinfo.request.compayManage.QueryCompanyRequest;
import com.parkinfo.request.compayManage.SetCompanyInfoRequest;
import com.parkinfo.response.companyManage.CompanyDetailResponse;
import com.parkinfo.response.companyManage.CompanyResponse;
import com.parkinfo.service.companyManage.ICompanyDetailService;
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
public class CompanyDetailServiceImpl implements ICompanyDetailService {

    @Autowired
    private CompanyDetailRepository companyDetailRepository;

    @Autowired
    private TokenUtils tokenUtils;

    @Override
    public Result companyImport(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        if (!Objects.requireNonNull(fileName).matches("^.+\\.(?i)(xls)$") && !fileName.matches("^.+\\.(?i)(xlsx)$")) {
            throw new NormalException("上传文件格式不正确");
        }
        try {
            List<CompanyDetail> companyDetails = ExcelUtils.importExcel(file, CompanyDetail.class);
            if(companyDetails != null){
                companyDetails.forEach(companyDetail -> {
                    ParkInfo parkInfo = tokenUtils.getCurrentParkInfo();
                    companyDetail.setParkInfo(parkInfo);
                    companyDetail.setAvailable(true);
                    companyDetail.setDelete(false);
                    companyDetail.setDeleteEnter(false);
                    companyDetail.setEnterStatus(EnterStatus.WAITING);
                    companyDetail.setEntered(false);
                    companyDetail.setCheckStatus(CheckStatus.APPLYING);
                    companyDetailRepository.save(companyDetail);
                });
            }
        } catch (Exception e) {
            throw new NormalException("上传失败");
        }
        return Result.builder().message("上传成功").build();
    }

    @Override
    public Result companyExport(HttpServletResponse response) {
        CompanyDetail companyDetail = new CompanyDetail();
        List<CompanyDetail> companyDetailList = new ArrayList<>();
        companyDetailList.add(companyDetail);
        try {
            ExcelUtils.exportExcel(companyDetailList, "企业信息模板", "企业信息模板", CompanyDetail.class, "company", response);
        } catch (Exception e) {
            throw new NormalException("模板下载失败");
        }
        return Result.builder().success().message("模板下载成功").build();
    }

    @Override
    public Result<Page<CompanyResponse>> findAll(QueryCompanyRequest request) {
        Pageable pageable = PageRequest.of(request.getPageNum(), request.getPageSize(), Sort.Direction.DESC, "createTime");
        Specification<CompanyDetail> specification = (Specification<CompanyDetail>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            ParkInfo parkInfo = tokenUtils.getCurrentParkInfo();
            if (StringUtils.isNotBlank(request.getCompanyName())) {
                predicates.add(criteriaBuilder.like(root.get("companyName").as(String.class), "%" + request.getCompanyName()+ "%"));
            }
            if (StringUtils.isNotBlank(request.getMainBusiness())) {
                predicates.add(criteriaBuilder.like(root.get("mainBusiness").as(String.class), "%" + request.getMainBusiness() + "%"));
            }
            if (null != request.getCheckStatus()) {
                predicates.add(criteriaBuilder.equal(root.get("checkStatus").as(Integer.class), request.getCheckStatus().ordinal()));
            }
            if (parkInfo == null) {
                throw new NormalException("请先登录");
            }
            Join<CompanyDetail, ParkInfo> join = root.join(root.getModel().getSingularAttribute("parkInfo", ParkInfo.class), JoinType.LEFT);
            predicates.add(criteriaBuilder.equal(join.get("id").as(String.class), parkInfo.getId()));
            predicates.add(criteriaBuilder.equal(root.get("delete").as(Boolean.class), Boolean.FALSE));
            predicates.add(criteriaBuilder.equal(root.get("entered").as(Boolean.class), Boolean.FALSE));
            predicates.add(criteriaBuilder.equal(root.get("enterStatus").as(Integer.class), EnterStatus.WAITING.ordinal()));
            //predicates.add(criteriaBuilder.equal(root.get("available").as(Boolean.class), Boolean.TRUE));
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
        Page<CompanyDetail> companyDetailPage = companyDetailRepository.findAll(specification, pageable);
        Page<CompanyResponse> responses = this.convertDetailPage(companyDetailPage);
        return Result.<Page<CompanyResponse>>builder().success().data(responses).build();
    }

    @Override
    public Result<CompanyDetailResponse> query(String id) {
        CompanyDetail companyDetail = this.checkCompany(id);
        CompanyDetailResponse response = new CompanyDetailResponse();
        BeanUtils.copyProperties(companyDetail,response);
        return Result.<CompanyDetailResponse>builder().success().data(response).build();
    }

    @Override
    public Result add(AddCompanyInfoRequest request) {
        CompanyDetail companyDetail = new CompanyDetail();
        ParkInfo parkInfo = tokenUtils.getCurrentParkInfo();
        BeanUtils.copyProperties(request,companyDetail);
        companyDetail.setParkInfo(parkInfo);
        companyDetail.setAvailable(true);
        companyDetail.setDelete(false);
        companyDetail.setDeleteEnter(false);
        companyDetail.setEnterStatus(EnterStatus.WAITING);
        companyDetail.setEntered(false);
        companyDetail.setCheckStatus(CheckStatus.APPLYING);
        companyDetailRepository.save(companyDetail);
        return Result.builder().success().message("添加成功").build();
    }

    @Override
    public Result set(SetCompanyInfoRequest request) {
        CompanyDetail company = this.checkCompany(request.getId());
        BeanUtils.copyProperties(request,company);
        companyDetailRepository.save(company);
        return Result.builder().success().message("修改成功").build();
    }

    @Override
    public Result delete(String id) {
        CompanyDetail company = this.checkCompany(id);
        company.setDelete(true);
        companyDetailRepository.save(company);
        return Result.builder().success().message("删除成功").build();
    }

    private Page<CompanyResponse> convertDetailPage(Page<CompanyDetail> companyDetailPage) {
        List<CompanyResponse> content = new ArrayList<>();
        companyDetailPage.getContent().forEach(companyDetail -> {
            CompanyResponse response = new CompanyResponse();
            BeanUtils.copyProperties(companyDetail,response);
            content.add(response);
        });
        return new PageImpl<>(content, companyDetailPage.getPageable(), companyDetailPage.getTotalElements());
    }

    private CompanyDetail checkCompany(String id) {
        Optional<CompanyDetail> companyDetailOptional = companyDetailRepository.findByIdAndDeleteIsFalse(id);
        if (!companyDetailOptional.isPresent()) {
            throw new NormalException("企业不存在");
        }
        return companyDetailOptional.get();
    }
}
