package com.parkinfo.web.personalCloud;

import com.parkinfo.common.Result;
import com.parkinfo.request.personalCloud.AddPersonalCloudRequest;
import com.parkinfo.request.personalCloud.DeletePersonalCloudRequest;
import com.parkinfo.request.personalCloud.QueryPersonalCloudRequest;
import com.parkinfo.request.personalCloud.SetPersonalCloudRequest;
import com.parkinfo.response.personalCloud.DownloadResponse;
import com.parkinfo.response.personalCloud.PersonalCloudResponse;
import com.parkinfo.response.personalCloud.UploadFileResponse;
import com.parkinfo.service.personalCloud.IPersonalCloudService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/personalCloud/cloud")
@Api(value = "/personalCloud/cloud", tags = {"个人云盘-云盘管理"})
public class PersonalCloudController {

    @Autowired
    IPersonalCloudService personalCloudService;

    @PostMapping("/findAll")
    @ApiOperation("分页查询所有文件")
    public Result<Page<PersonalCloudResponse>> findAll(@RequestBody QueryPersonalCloudRequest request) {
        return personalCloudService.findAll(request);
    }

    @PostMapping("/uploadFile")
    @ApiOperation("上传文件")
    public Result<List<UploadFileResponse>> uploadFile(HttpServletRequest request) {
        return personalCloudService.uploadFile(request);
    }

    @PostMapping("/add")
    @ApiOperation("把上传文件信息添加到数据库")
    public Result add(@RequestBody AddPersonalCloudRequest request) {
        return personalCloudService.add(request);
    }

    @PostMapping("/delete/{id}")
    @ApiOperation("删除文件")
    public Result delete(@PathVariable("id") String id) {
        return personalCloudService.delete(id);
    }

    @PostMapping("/set")
    @ApiOperation("文件重命名")
    public Result set(@RequestBody SetPersonalCloudRequest request) {
        return personalCloudService.set(request);
    }

    @PostMapping("/download/{id}")
    @ApiOperation("获取下载文件url和文件名")
    public Result<DownloadResponse> download(@PathVariable("id") String id) {
        return personalCloudService.download(id);
    }

    @PostMapping("/delete")
    @ApiOperation("批量删除文件")
    public Result deleteAll(DeletePersonalCloudRequest request) {
        return personalCloudService.deleteAll(request);
    }
}
