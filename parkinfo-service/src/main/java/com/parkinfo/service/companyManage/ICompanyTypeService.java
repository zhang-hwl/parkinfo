package com.parkinfo.service.companyManage;

import com.parkinfo.common.Result;
import com.parkinfo.request.compayManage.AddGeneralRequest;
import com.parkinfo.request.compayManage.AddKindTypeRequest;
import com.parkinfo.request.compayManage.EditTypeRequest;
import com.parkinfo.request.compayManage.SearchCompanyTypeRequest;
import com.parkinfo.response.companyManage.CompanyTypeDetailResponse;
import com.parkinfo.response.companyManage.CompanyTypeResponse;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;

/**
 * @author Li
 * description:
 * date: 2019-10-10 10:59
 */
public interface ICompanyTypeService {

   Result<Page<CompanyTypeResponse>> search(SearchCompanyTypeRequest request);

   Result<List<CompanyTypeResponse>> findAll();

   Result<List<CompanyTypeDetailResponse>> findAllKind(String id);

   Result<List<CompanyTypeDetailResponse>> findAllGeneral();

   Result<String> addGeneral(AddGeneralRequest request);

   Result<String> addKind(AddKindTypeRequest request);

   Result<String> editGeneral(EditTypeRequest request);

   Result<String> deleteGeneral(String id);

}
