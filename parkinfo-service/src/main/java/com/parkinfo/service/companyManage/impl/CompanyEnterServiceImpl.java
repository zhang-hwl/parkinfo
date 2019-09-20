package com.parkinfo.service.companyManage.impl;

import com.parkinfo.common.Result;
import com.parkinfo.dto.ParkUserDTO;
import com.parkinfo.entity.companyManage.CompanyDetail;
import com.parkinfo.entity.companyManage.EnclosureTotal;
import com.parkinfo.entity.companyManage.EnteredInfo;
import com.parkinfo.entity.userConfig.ParkInfo;
import com.parkinfo.enums.FileUploadType;
import com.parkinfo.enums.ParkRoleEnum;
import com.parkinfo.exception.NormalException;
import com.parkinfo.repository.companyManage.CompanyDetailRepository;
import com.parkinfo.repository.companyManage.EnclosureTotalRepository;
import com.parkinfo.repository.companyManage.EnteredInfoRepository;
import com.parkinfo.request.compayManage.*;
import com.parkinfo.response.companyManage.EnclosureTotalResponse;
import com.parkinfo.response.companyManage.EnterDetailResponse;
import com.parkinfo.response.companyManage.EnterResponse;
import com.parkinfo.response.companyManage.EnteredInfoResponse;
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
import org.springframework.transaction.annotation.Transactional;

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
            ParkUserDTO currentUser = tokenUtils.getLoginUserDTO();
            if (StringUtils.isNotBlank(request.getCompanyName())) {
                predicates.add(criteriaBuilder.like(root.get("companyName").as(String.class), "%" + request.getCompanyName() + "%"));
            }
            if (null != request.getEnterStatus()) {
                predicates.add(criteriaBuilder.equal(root.get("enterStatus").as(Integer.class), request.getEnterStatus().ordinal()));
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
            predicates.add(criteriaBuilder.equal(root.get("deleteEnter").as(Boolean.class), Boolean.FALSE));
            predicates.add(criteriaBuilder.equal(root.get("entered").as(Boolean.class), Boolean.TRUE));
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
        Page<CompanyDetail> companyDetailPage = companyDetailRepository.findAll(specification, pageable);
        Page<EnterResponse> responses = this.convertDetailPage(companyDetailPage);
        return Result.<Page<EnterResponse>>builder().success().data(responses).build();
    }

    @Transactional
    @Override
    public Result setCompany(ModifyCompanyRequest request) {
        CompanyDetail companyDetail = this.checkEnter(request.getId());
        BeanUtils.copyProperties(request, companyDetail);
        companyDetailRepository.save(companyDetail);
        enteredInfoRepository.deleteByCompanyDetail_Id(request.getId());
        request.getEnterDetailRequests().forEach(addEnterDetailRequest->{
            EnteredInfo enteredInfo = new EnteredInfo();
            BeanUtils.copyProperties(addEnterDetailRequest,enteredInfo);
            enteredInfo.setDelete(false);
            enteredInfo.setAvailable(true);
            enteredInfo.setCompanyDetail(companyDetail);
            enteredInfoRepository.save(enteredInfo);
        });
        request.getFileRequests().forEach(addFileRequest->{
            Optional<EnclosureTotal> byEnclosureType = enclosureTotalRepository.findByEnclosureTypeAndAndDeleteIsFalse(addFileRequest.getEnclosureType());
            if (!byEnclosureType.isPresent()) {
                EnclosureTotal enclosureTotal = new EnclosureTotal();
                BeanUtils.copyProperties(addFileRequest,enclosureTotal);
                enclosureTotal.setAvailable(true);
                enclosureTotal.setDelete(false);
                enclosureTotal.setCompanyDetail(companyDetail);
                enclosureTotalRepository.save(enclosureTotal);
            }else {
                EnclosureTotal enclosureTotal = byEnclosureType.get();
                BeanUtils.copyProperties(addFileRequest,enclosureTotal);
                enclosureTotalRepository.save(enclosureTotal);
            }
        });
        return Result.builder().success().message("修改成功").build();
    }

    @Override
    public Result<EnterDetailResponse> query(String id) {
        CompanyDetail companyDetail = this.checkEnter(id);
        EnterDetailResponse response = new EnterDetailResponse();
        BeanUtils.copyProperties(companyDetail, response);
        //添加返回入驻信息
        List<EnteredInfo> totals = enteredInfoRepository.findAllByCompanyDetail_IdAndDeleteIsFalse(id);
        List<EnteredInfoResponse> enteredInfoResponseList = new ArrayList<>();
        totals.forEach(enteredInfo -> {
            EnteredInfoResponse enteredInfoResponse = new EnteredInfoResponse();
            BeanUtils.copyProperties(enteredInfo, enteredInfoResponse);
            enteredInfoResponseList.add(enteredInfoResponse);
        });
        response.setInfoResponseList(enteredInfoResponseList);
        //添加返回附件
        List<EnclosureTotal> all = enclosureTotalRepository.findAllByCompanyDetail_IdAndDeleteIsFalse(id);
        List<EnclosureTotalResponse> enclosureTotalResponseList = new ArrayList<>();
        all.forEach(enclosureTotal -> {
            EnclosureTotalResponse enclosureTotalResponse = new EnclosureTotalResponse();
            BeanUtils.copyProperties(enclosureTotal, enclosureTotalResponse);
            enclosureTotalResponseList.add(enclosureTotalResponse);
        });
        response.setEnclosureTotalResponseList(enclosureTotalResponseList);
        return Result.<EnterDetailResponse>builder().success().data(response).build();
    }

    @Override
    public Result delete(String id) {
        CompanyDetail checkEnter = this.checkEnter(id);
        checkEnter.setDeleteEnter(true);
        companyDetailRepository.save(checkEnter);
        return Result.builder().success().message("删除成功").build();
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
}
