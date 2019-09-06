package com.parkinfo.web.parkCulture;

import com.parkinfo.common.Result;
import com.parkinfo.request.parkCulture.AddBookCommentRequest;
import com.parkinfo.request.parkCulture.QueryBookCommentListRequest;
import com.parkinfo.request.parkCulture.QueryBookListRequest;
import com.parkinfo.request.parkCulture.SetReadProcessRequest;
import com.parkinfo.response.parkCulture.BookCommentListResponse;
import com.parkinfo.response.parkCulture.BookDetailResponse;
import com.parkinfo.response.parkCulture.BookListResponse;
import com.parkinfo.response.parkCulture.ReadProcessResponse;
import com.parkinfo.service.parkCulture.ILibraryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * When I wrote this, only God and I understood what I was doing
 * Now, God only knows
 *
 * @author cnyuchu@gmail.com
 * @create 2019-09-05 17:28
 **/
@RestController
@RequestMapping("/parkCulture/library")
@Api(value = "/parkCulture/library", tags = {"园区文化-网上图书馆"})
public class LibraryController{

    @Autowired
    private ILibraryService libraryService;

    @PostMapping("/search")
    @ApiOperation(value = "搜索图书列表")
    @RequiresPermissions("parkCulture:library:book_search")
    public Result<Page<BookListResponse>> search(@Valid @RequestBody QueryBookListRequest request, BindingResult result){
        if (result.hasErrors()){
            for (ObjectError error:result.getAllErrors()) {
                return Result.<Page<BookListResponse>>builder().fail().code(500).message(error.getDefaultMessage()).build();
            }
        }
        return libraryService.search(request);
    }

    @PostMapping("/detail/{bookId}")
    @ApiOperation(value = "查看图书详情")
    @RequiresPermissions("parkCulture:library:book_detail")
    public Result<BookDetailResponse> detail(@PathVariable("bookId") String bookId){
        return libraryService.detail(bookId);
    }

    @PostMapping("/comment/search")
    @ApiOperation(value = "分页查看图书的评论")
    @RequiresPermissions("parkCulture:library:comment_search")
    public Result<Page<BookCommentListResponse>> getCommentPage(@Valid @RequestBody QueryBookCommentListRequest request, BindingResult result){
        if (result.hasErrors()){
            for (ObjectError error:result.getAllErrors()) {
                return Result.<Page<BookCommentListResponse>>builder().fail().code(500).message(error.getDefaultMessage()).build();
            }
        }
        return libraryService.getCommentPage(request);
    }

    @PostMapping("/comment/add")
    @ApiOperation(value = "添加图书评论")
    @RequiresPermissions("parkCulture:library:comment_add")
    public  Result addComment(@Valid @RequestBody AddBookCommentRequest request,BindingResult result){
        if (result.hasErrors()){
            for (ObjectError error:result.getAllErrors()) {
                return Result.builder().fail().code(500).message(error.getDefaultMessage()).build();
            }
        }
        return libraryService.addComment(request);
    }

    @PostMapping("/readProcess/get/{bookId}")
    @ApiOperation(value = "查看某本书的阅读进度")
    @RequiresPermissions("parkCulture:library:progress_get")
    public Result<ReadProcessResponse> getReadProcess(@PathVariable("bookId") String bookId){
        return libraryService.getReadProcess(bookId);
    }

    @PostMapping("/readProcess/set}")
    @ApiOperation(value = "设置图书阅读进度")
    @RequiresPermissions("parkCulture:library:progress_set")
    public Result setReadProcess(@Valid @RequestBody SetReadProcessRequest request,BindingResult result){
        return libraryService.setReadProcess(request);
    }
}
