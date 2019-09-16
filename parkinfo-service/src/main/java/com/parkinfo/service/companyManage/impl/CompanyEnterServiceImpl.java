package com.parkinfo.service.companyManage.impl;

import com.parkinfo.common.Result;
import com.parkinfo.entity.companyManage.CompanyDetail;
import com.parkinfo.entity.companyManage.EnclosureTotal;
import com.parkinfo.entity.companyManage.EnteredInfo;
import com.parkinfo.entity.userConfig.ParkInfo;
import com.parkinfo.enums.FileUploadType;
import com.parkinfo.exception.NormalException;
import com.parkinfo.repository.companyManage.CompanyDetailRepository;
import com.parkinfo.repository.companyManage.EnclosureTotalRepository;
import com.parkinfo.repository.companyManage.EnteredInfoRepository;
import com.parkinfo.request.compayManage.*;
import com.parkinfo.response.companyManage.EnterDetailResponse;
import com.parkinfo.response.companyManage.EnterResponse;
import com.parkinfo.service.companyManage.ICompanyEnterService;
import com.parkinfo.token.TokenUtils;
import com.parkinfo.tools.oss.IOssService;
import com.parkinfo.util.ExcelUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CompanyEnterServiceImpl implements ICompanyEnterService {

    @Autowired
    private CompanyDetailRepository companyDetailRepository;

    @Autowired
    private EnteredInfoRepository enteredInfoRepository;

    @Autowired
    private EnclosureTotalRepository enclosureTotalRepository;

    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private IOssService ossService;

    @Override
    public Result enterExport(HttpServletResponse response) {
        CompanyDetail enterDetail = new CompanyDetail();
        List<CompanyDetail> companyDetailList = new ArrayList<>();
        companyDetailList.add(enterDetail);
        try {
            ExcelUtils.exportExcel(companyDetailList, "入驻企业信息模板", "入驻企业信息模板", CompanyDetail.class, "company", response);
        } catch (Exception e) {
            throw new NormalException("模板下载失败");
        }
        return Result.builder().success().message("模板下载成功").build();
    }

    @Override
    public Result<Page<EnterResponse>> findAll(QueryEnterRequest request) {
        Pageable pageable = PageRequest.of(request.getPageNum(), request.getPageSize(), Sort.Direction.DESC, "createTime");
        Specification<CompanyDetail> specification = (Specification<CompanyDetail>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            ParkInfo parkInfo = tokenUtils.getCurrentParkInfo();
            if (StringUtils.isNotBlank(request.getCompanyName())) {
                predicates.add(criteriaBuilder.like(root.get("companyName").as(String.class), "%" + request.getCompanyName() + "%"));
            }
            if (null != request.getEnterStatus()) {
                predicates.add(criteriaBuilder.equal(root.get("enterStatus").as(Integer.class), request.getEnterStatus().ordinal()));
            }
            Join<CompanyDetail, ParkInfo> join = root.join(root.getModel().getSingularAttribute("parkInfo", ParkInfo.class), JoinType.LEFT);
            predicates.add(criteriaBuilder.equal(join.get("id").as(String.class), parkInfo.getId()));
            predicates.add(criteriaBuilder.equal(root.get("deleteEnter").as(Boolean.class), Boolean.FALSE));
            predicates.add(criteriaBuilder.equal(root.get("entered").as(Boolean.class), Boolean.TRUE));
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
        Page<CompanyDetail> companyDetailPage = companyDetailRepository.findAll(specification, pageable);
        Page<EnterResponse> responses = this.convertDetailPage(companyDetailPage);
        return Result.<Page<EnterResponse>>builder().success().data(responses).build();
    }

    @Override
    public Result modify(SetEnterRequest request) {
        CompanyDetail checkEnter = this.checkEnter(request.getId());
        checkEnter.setCompanyName(request.getCompanyName());
        checkEnter.setNumber(request.getNumber());
        checkEnter.setEnterStatus(request.getEnterStatus());
        companyDetailRepository.save(checkEnter);
        return Result.builder().success().message("修改成功").build();
    }

    @Override
    public Result setCompany(ModifyCompanyRequest request) {
        CompanyDetail companyDetail = this.checkEnter(request.getId());
        BeanUtils.copyProperties(request,companyDetail);
        companyDetailRepository.save(companyDetail);
        return Result.builder().success().message("修改成功").build();
    }

    @Override
    public Result addEnter(AddEnterDetailRequest request) {
        CompanyDetail companyDetail = this.checkEnter(request.getCompanyId());
        EnteredInfo enteredInfo = new EnteredInfo();
        BeanUtils.copyProperties(request,enteredInfo);
        enteredInfo.setCompanyDetail(companyDetail);
        enteredInfo.setDelete(false);
        enteredInfo.setAvailable(true);
        enteredInfoRepository.save(enteredInfo);
        return Result.builder().success().message("添加成功").build();
    }

    @Override
    public Result set(SetEnterDetailRequest request) {
        EnteredInfo enteredInfo = this.checkEnterInfo(request.getEnterId());
        BeanUtils.copyProperties(request,enteredInfo);
        enteredInfoRepository.save(enteredInfo);
        return Result.builder().success().message("修改成功").build();
    }

    @Override
    public Result deleteEnter(String enterId) {
        EnteredInfo enteredInfo = this.checkEnterInfo(enterId);
        enteredInfo.setDelete(true);
        enteredInfoRepository.save(enteredInfo);
        return Result.builder().success().message("删除成功").build();
    }

    @Override
    public Result<EnterDetailResponse> query(String id) {
        CompanyDetail companyDetail = this.checkEnter(id);
        EnterDetailResponse response = new EnterDetailResponse();
        BeanUtils.copyProperties(companyDetail,response);
        return Result.<EnterDetailResponse>builder().success().data(response).build();
    }

    @Override
    public Result<List<EnteredInfo>> queryEnter(String id) {
        List<EnteredInfo> totals = enteredInfoRepository.findAllByCompanyDetail_IdAndDeleteIsFalse(id);
        return Result.<List<EnteredInfo>>builder().success().data(totals).build();
    }

    @Override
    public Result<List<EnclosureTotal>> find(String id) {
        List<EnclosureTotal> all = enclosureTotalRepository.findAllByCompanyDetail_IdAndDeleteIsFalse(id);
        return Result.<List<EnclosureTotal>>builder().success().data(all).build();
    }

    @Override
    public Result delete(String id) {
        CompanyDetail checkEnter = this.checkEnter(id);
        checkEnter.setDeleteEnter(true);
        companyDetailRepository.save(checkEnter);
        return Result.builder().success().message("删除成功").build();
    }

    @Override
    public Result<String> uploadFile(HttpServletRequest request) {
        String fileUpload = ossService.MultipartFileUpload(request, FileUploadType.COMPANY_ENCLOSURE.toString());
        return Result.<String>builder().success().data(fileUpload).build();
    }

    @Override
    public Result addFile(AddFileRequest request) {
        CompanyDetail companyDetail = this.checkEnter(request.getCompanyId());
        EnclosureTotal enclosureTotal = new EnclosureTotal();
        enclosureTotal.setCompanyDetail(companyDetail);
        BeanUtils.copyProperties(request,enclosureTotal);
        enclosureTotal.setDelete(false);
        enclosureTotal.setAvailable(true);
        enclosureTotalRepository.save(enclosureTotal);
        return Result.builder().success().message("上传成功").build();
    }


    private Page<EnterResponse> convertDetailPage(Page<CompanyDetail> companyDetailPage) {
        List<EnterResponse> content = new ArrayList<>();
        companyDetailPage.getContent().forEach(companyDetail -> {
            EnterResponse response = new EnterResponse();
            BeanUtils.copyProperties(companyDetail, response);
            content.add(response);
        });
        return new PageImpl<>(content, companyDetailPage.getPageable(), companyDetailPage.getTotalElements());
    }

    private CompanyDetail checkEnter(String id) {
        Optional<CompanyDetail> companyDetailOptional = companyDetailRepository.findByIdAndDeleteEnterIsFalse(id);
        if (!companyDetailOptional.isPresent()) {
            throw new NormalException("入驻企业不存在");
        }
        return companyDetailOptional.get();
    }

    private EnteredInfo checkEnterInfo(String id) {
        Optional<EnteredInfo> enteredInfoOptional = enteredInfoRepository.findByIdAndDeleteIsFalse(id);
        if (!enteredInfoOptional.isPresent()) {
            throw new NormalException("入驻企业不存在");
        }
        return enteredInfoOptional.get();
    }
}
