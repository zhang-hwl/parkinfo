package com.parkinfo.service.companyManage.impl;

import com.google.common.collect.Lists;
import com.parkinfo.common.Result;
import com.parkinfo.dto.ParkUserDTO;
import com.parkinfo.entity.companyManage.CompanyDemand;
import com.parkinfo.entity.companyManage.CompanyDetail;
import com.parkinfo.entity.companyManage.CompanyType;
import com.parkinfo.entity.userConfig.ParkInfo;
import com.parkinfo.entity.userConfig.ParkUser;
import com.parkinfo.enums.DiscussStatus;
import com.parkinfo.enums.EnterStatus;
import com.parkinfo.enums.ParkRoleEnum;
import com.parkinfo.exception.NormalException;
import com.parkinfo.repository.companyManage.CompanyDetailRepository;
import com.parkinfo.repository.companyManage.CompanyTypeRepository;
import com.parkinfo.repository.userConfig.ParkUserRepository;
import com.parkinfo.request.compayManage.*;
import com.parkinfo.response.companyManage.CompanyDetailResponse;
import com.parkinfo.response.companyManage.ManageDetailResponse;
import com.parkinfo.response.companyManage.ManagementResponse;
import com.parkinfo.service.companyManage.IManagementService;
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
import java.io.IOException;
import java.util.*;

@Service
public class ManagementServiceImpl implements IManagementService {

    @Autowired
    private CompanyDetailRepository companyDetailRepository;

    @Autowired
    private ParkUserRepository parkUserRepository;

    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private CompanyTypeRepository companyTypeRepository;

    @Override
    public Result investImport(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        if (!Objects.requireNonNull(fileName).matches("^.+\\.(?i)(xls)$") && !fileName.matches("^.+\\.(?i)(xlsx)$")) {
            throw new NormalException("上传文件格式不正确");
        }
        try {
            List<CompanyDetail> companyDetails = ExcelUtils.importExcel(file, CompanyDetail.class);
            if (companyDetails != null) {
                companyDetails.forEach(companyDetail -> {
                    ParkInfo parkInfo = tokenUtils.getCurrentParkInfo();
                    companyDetail.setParkInfo(parkInfo);
                    companyDetail.setAvailable(true);
                    companyDetail.setDelete(false);
                    companyDetail.setDeleteEnter(false);
                    companyDetail.setEntered(false);
                    companyDetail.setEnterStatus(EnterStatus.WAITING);
                    companyDetail.setDiscussStatus(DiscussStatus.WAIT_LOOK);
                    companyDetailRepository.save(companyDetail);
                });
            }
        } catch (Exception e) {
            throw new NormalException("上传失败");
        }
        return Result.builder().message("上传成功").build();
    }

    @Override
    public Result<String> investExport(List<String> ids, HttpServletResponse httpServletResponse) {
        ParkUserDTO currentUser = tokenUtils.getLoginUserDTO();
        if(ids == null || ids.size() == 0){
            throw new NormalException("请选择文件");
        }
        List<CompanyDetail> allByIds = companyDetailRepository.findAllByDeleteIsFalseAndAvailableIsTrueAndIdIn(ids);
        List<ManagementResponse> content = new ArrayList<>();
        allByIds.forEach(companyDetail -> {
            ManagementResponse response = new ManagementResponse();
            BeanUtils.copyProperties(companyDetail, response);
            Optional<ParkUser> optionalParkUser = parkUserRepository.findByCompanyDetail_IdAndDeleteIsFalseAndAvailableIsTrue(companyDetail.getId());
            if (!optionalParkUser.isPresent()) {
                content.add(response);
            }else {
                ParkUser user = optionalParkUser.get();
                response.setManId(user.getId());
                response.setNickname(user.getNickname());
                content.add(response);
            }
        });
        try {
            ExcelUtils.exportExcel(content, "需求详情", "需求详情", ManagementResponse.class, "xuqiu", httpServletResponse);
        } catch (IOException e) {
            throw new NormalException("需求详情导出失败");
        }
        return Result.<String>builder().success().data("导出成功").build();
    }

    @Override
    public Result investExport(HttpServletResponse response) {
        CompanyDetail companyDetail = new CompanyDetail();
        List<CompanyDetail> companyDetailList = new ArrayList<>();
        companyDetailList.add(companyDetail);
        try {
            ExcelUtils.exportExcel(companyDetailList, "招商信息模板", "招商信息模板", CompanyDetail.class, "company", response);
        } catch (Exception e) {
            throw new NormalException("模板下载失败");
        }
        return Result.builder().success().message("模板下载成功").build();
    }

    @Override
    public Result add(AddInvestmentRequest request) {
        CompanyType type = null;
        if(StringUtils.isNotBlank(request.getTypeId())){
            Optional<CompanyType> byId = companyTypeRepository.findByIdAndDeleteIsFalseAndAvailableIsTrue(request.getTypeId());
            if(!byId.isPresent()){
                throw new NormalException("类型不存在");
            }
            type = byId.get();
        }
        Optional<CompanyType> byId = companyTypeRepository.findByIdAndDeleteIsFalseAndAvailableIsTrue(request.getTypeId());
        CompanyDetail companyDetail = new CompanyDetail();
        ParkInfo parkInfo = tokenUtils.getCurrentParkInfo();
        BeanUtils.copyProperties(request, companyDetail);
        companyDetail.setParkInfo(parkInfo);
        companyDetail.setAvailable(true);
        companyDetail.setDelete(false);
        companyDetail.setDeleteEnter(false);
        companyDetail.setEntered(false);
        companyDetail.setCompanyType(type);
        companyDetail.setEnterStatus(EnterStatus.WAITING);
        companyDetail.setDiscussStatus(DiscussStatus.LOOKED);
        companyDetailRepository.save(companyDetail);
        return Result.builder().success().message("添加成功").build();
    }

    @Override
    public Result<Page<ManagementResponse>> findAll(QueryManagementRequest request) {
        Pageable pageable = PageRequest.of(request.getPageNum(), request.getPageSize(), Sort.Direction.DESC, "createTime");
        Specification<CompanyDetail> specification = (Specification<CompanyDetail>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.isNotBlank(request.getLinkMan())) {
                predicates.add(criteriaBuilder.like(root.get("linkMan").as(String.class), "%" + request.getLinkMan() + "%"));
            }
            if (StringUtils.isNotBlank(request.getConnectWay())) {
                predicates.add(criteriaBuilder.equal(root.get("connectWay").as(String.class), request.getConnectWay()));
            }
            if (null != request.getDiscussStatus()) {
                predicates.add(criteriaBuilder.equal(root.get("discussStatus").as(DiscussStatus.class), request.getDiscussStatus().ordinal()));
            }
            if(StringUtils.isNotBlank(request.getTypeId())){
                predicates.add(criteriaBuilder.equal(root.get("companyType").get("id").as(String.class), request.getTypeId()));
            }
            predicates.add(criteriaBuilder.equal(root.get("parkInfo").get("id").as(String.class), tokenUtils.getCurrentParkInfo().getId()));
            predicates.add(criteriaBuilder.equal(root.get("delete").as(Boolean.class), Boolean.FALSE));
            predicates.add(criteriaBuilder.equal(root.get("available").as(Boolean.class), Boolean.TRUE));
//            predicates.add(criteriaBuilder.equal(root.get("entered").as(Boolean.class), Boolean.FALSE));
//            predicates.add(criteriaBuilder.equal(root.get("enterStatus").as(Integer.class), EnterStatus.WAITING.ordinal()));
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
        Page<CompanyDetail> companyDetailPage = companyDetailRepository.findAll(specification, pageable);
        Page<ManagementResponse> responses = this.convertDetailPage(companyDetailPage);
        return Result.<Page<ManagementResponse>>builder().success().data(responses).build();
    }

    @Override
    public Result delete(String id) {
        CompanyDetail companyDetail = this.checkInvestment(id);
        companyDetail.setAvailable(false);
        companyDetailRepository.save(companyDetail);
        return Result.builder().success().message("删除成功").build();
    }

    @Override
    public Result<ManageDetailResponse> query(String id) {
        CompanyDetail investment = this.checkInvestment(id);
        ManageDetailResponse response = new ManageDetailResponse();
        BeanUtils.copyProperties(investment,response);
        if(investment.getCompanyType() != null){
            response.setTypeId(investment.getCompanyType().getId());
            response.setParentId(investment.getCompanyType().getParent().getId());
        }
        if(investment.getCompanyType() != null){
            response.setTypeId(investment.getCompanyType().getId());
        }
        Optional<ParkUser> optionalParkUser = parkUserRepository.findByCompanyDetail_IdAndDeleteIsFalseAndAvailableIsTrue(investment.getId());
        if (!optionalParkUser.isPresent()) {
            return Result.<ManageDetailResponse>builder().success().data(response).build();
        }else {
            ParkUser user = optionalParkUser.get();
            response.setManId(user.getId());
            response.setNickname(user.getNickname());
            return Result.<ManageDetailResponse>builder().success().data(response).build();
        }
    }

    @Override
    public Result set(SetInvestmentRequest request) {
        CompanyType type = null;
        if(StringUtils.isNotBlank(request.getTypeId())){
            Optional<CompanyType> byId = companyTypeRepository.findByIdAndDeleteIsFalseAndAvailableIsTrue(request.getTypeId());
            if(!byId.isPresent()){
                throw new NormalException("类型不存在");
            }
            type = byId.get();
        }
        CompanyDetail companyDetail = this.checkInvestment(request.getId());
        BeanUtils.copyProperties(request, companyDetail);
        companyDetail.setCompanyType(type);
        companyDetailRepository.save(companyDetail);
        return Result.builder().success().message("修改成功").build();
    }

    @Override
    public Result enter(String id) {
        CompanyDetail companyDetail = this.checkInvestment(id);
//        ParkUser parkUser = companyDetail.getParkUser();
//        if (parkUser == null) {
//            throw new NormalException("请先绑定负责人");
//        }
        companyDetail.setEntered(true);
        companyDetail.setEnterStatus(EnterStatus.ENTERED);
        companyDetailRepository.save(companyDetail);
        return Result.builder().success().message("入驻成功").build();
    }

    @Override
    public Result bind(BindCompanyRequest request) {
        ParkUser parkUser = this.checkUser(request.getUserId());
        CompanyDetail companyDetail = parkUser.getCompanyDetail();
        if (null != companyDetail) {
            throw new NormalException("该用户已绑定公司，请选择其他用户");
        }
        CompanyDetail investment = this.checkInvestment(request.getCompanyId());
        investment.setParkUser(parkUser);
        companyDetailRepository.save(investment);
        parkUser.setCompanyDetail(investment);
        parkUserRepository.save(parkUser);
        return Result.builder().success().message("绑定成功").build();
    }

    @Override
    public Result<List<CompanyDetailResponse>> findAllCompany() {
        List<CompanyDetail> all = companyDetailRepository.findAllByDeleteIsFalseAndAvailableIsTrueAndParkInfo_Id(tokenUtils.getCurrentParkInfo().getId());
        List<CompanyDetailResponse> result = Lists.newArrayList();
        all.forEach(temp -> {
            CompanyDetailResponse response = new CompanyDetailResponse();
            BeanUtils.copyProperties(temp, response);
            response.setName(temp.getCompanyName());
            result.add(response);
        });
        return Result.<List<CompanyDetailResponse>>builder().success().data(result).build();
    }

    private Page<ManagementResponse> convertDetailPage(Page<CompanyDetail> companyDetailPage) {
        List<ManagementResponse> content = new ArrayList<>();
        companyDetailPage.getContent().forEach(companyDetail -> {
            ManagementResponse response = new ManagementResponse();
            BeanUtils.copyProperties(companyDetail, response);
            Optional<ParkUser> optionalParkUser = parkUserRepository.findByCompanyDetail_IdAndDeleteIsFalseAndAvailableIsTrue(companyDetail.getId());
            if (!optionalParkUser.isPresent()) {
                content.add(response);
            }else {
                ParkUser user = optionalParkUser.get();
                response.setManId(user.getId());
                response.setNickname(user.getNickname());
                content.add(response);
            }
        });
        return new PageImpl<>(content, companyDetailPage.getPageable(), companyDetailPage.getTotalElements());
    }

    private CompanyDetail checkInvestment(String id) {
        Optional<CompanyDetail> companyDetailOptional = companyDetailRepository.findByIdAndDeleteIsFalseAndAvailableIsTrue(id);
        if (!companyDetailOptional.isPresent()) {
            throw new NormalException("招商信息不存在");
        }
        return companyDetailOptional.get();
    }

    private ParkUser checkUser(String id) {
        Optional<ParkUser> optionalParkUser = parkUserRepository.findByIdAndDeleteIsFalseAndAvailableIsTrue(id);
        if (!optionalParkUser.isPresent()) {
            throw new NormalException("用户不存在");
        }
        return optionalParkUser.get();
    }
}
