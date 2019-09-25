package com.parkinfo.web.parkCulture;

import com.parkinfo.common.Result;
import com.parkinfo.request.parkCulture.*;
import com.parkinfo.response.login.ParkInfoListResponse;
import com.parkinfo.response.login.ParkUserListResponse;
import com.parkinfo.response.parkCulture.*;
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
import java.util.List;

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

//    @PostMapping("/disable/{bookId}")
//    @ApiOperation(value = "禁用或启用图书")
//    @RequiresPermissions("parkCulture:library:book_disable")
//    public Result disableBook(@PathVariable("bookId") String bookId){
//        return libraryService.disableBook(bookId);
//    }

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

    @PostMapping("/category/list")
    @ApiOperation(value = "不分页查看图书的分类")
//    @RequiresPermissions("parkCulture:library:category_search")
    public Result<List<BookCategoryListResponse>> search( @RequestBody QueryCategoryListRequest request){
        return libraryService.search(request);
    }

    @PostMapping("/category/search")
    @ApiOperation(value = "管理员分页查看图书的分类")
    @RequiresPermissions("parkCulture:library:category_search")
    public Result<Page<BookCategoryListResponse>> search(@Valid @RequestBody QueryCategoryPageRequest request, BindingResult result){
        if (result.hasErrors()){
            for (ObjectError error:result.getAllErrors()) {
                return Result.<Page<BookCategoryListResponse>>builder().fail().code(500).message(error.getDefaultMessage()).build();
            }
        }
        return libraryService.search(request);
    }


    @PostMapping("/category/add")
    @ApiOperation(value = "管理员添加图书分类")
    @RequiresPermissions("parkCulture:library:category_add")
    public Result addBookCategory(@Valid @RequestBody AddBookCategoryRequest request,BindingResult result){
        if (result.hasErrors()){
            for (ObjectError error:result.getAllErrors()) {
                return Result.builder().fail().code(500).message(error.getDefaultMessage()).build();
            }
        }
        return libraryService.addBookCategory(request);
    }

    @PostMapping("/category/set")
    @ApiOperation(value = "管理员修改图书分类")
    @RequiresPermissions("parkCulture:library:category_set")
    public Result setBookCategory(@Valid @RequestBody SetBookCategoryRequest request,BindingResult result){
        if (result.hasErrors()){
            for (ObjectError error:result.getAllErrors()) {
                return Result.builder().fail().code(500).message(error.getDefaultMessage()).build();
            }
        }
        return libraryService.setBookCategory(request);
    }

    @PostMapping("/category/delete/{id}")
    @ApiOperation(value = "管理员删除图书分类")
    @RequiresPermissions("parkCulture:library:category_delete")
    public Result deleteBookCategory(@PathVariable("id")String id){
        return libraryService.deleteBookCategory(id);
    }

    @PostMapping("/manage")
    @ApiOperation(value = "管理员-管理员管理图书列表")
    @RequiresPermissions("parkCulture:library:book_manage")
    Result<Page<BookManageListResponse>> manageVideo(@Valid @RequestBody QueryBookListRequest request,BindingResult result){
        if (result.hasErrors()){
            for (ObjectError error:result.getAllErrors()) {
                return Result.<Page<BookManageListResponse>>builder().fail().code(500).message(error.getDefaultMessage()).build();
            }
        }
        return libraryService.manageBook(request);
    }

    @PostMapping("/add")
    @ApiOperation(value = "管理员添加图书")
    @RequiresPermissions("parkCulture:library:book_add")
    public Result addBook(@Valid @RequestBody AddBookRequest request,BindingResult result){
        if (result.hasErrors()){
            for (ObjectError error:result.getAllErrors()) {
                return Result.builder().fail().code(500).message(error.getDefaultMessage()).build();
            }
        }
        return libraryService.addBook(request);
    }

    @PostMapping("/process")
    @ApiOperation(value = "管理员分页查看某本书的阅读进度")
    @RequiresPermissions("parkCulture:library:process_search")
    public Result<Page<ReadProcessListResponse>> searchProcess(@Valid @RequestBody QueryReadProcessListRequest request,BindingResult result){
        if (result.hasErrors()){
            for (ObjectError error:result.getAllErrors()) {
                return Result.<Page<ReadProcessListResponse>>builder().fail().code(500).message(error.getDefaultMessage()).build();
            }
        }
        return libraryService.searchProcess(request);
    }

    @PostMapping("/setStatus/{bookId}")
    @ApiOperation(value = "管理员修改图书状态")
    @RequiresPermissions("parkCulture:library:book_setStatus")
    public Result setBookStatus(@PathVariable("bookId") String bookId){
        return libraryService.setBookStatus(bookId);
    }


    @PostMapping("/delete/{bookId}")
    @ApiOperation(value = "管理员删除图书")
    @RequiresPermissions("parkCulture:library:book_delete")
    public Result deleteBook(@PathVariable("bookId") String bookId){
        return libraryService.deleteBook(bookId);
    }

    @PostMapping("/set")
    @ApiOperation(value = "管理员修改图书")
    @RequiresPermissions("parkCulture:library:book_set")
    public Result setBook(@Valid @RequestBody SetBookRequest request,BindingResult result){
        if (result.hasErrors()){
            for (ObjectError error:result.getAllErrors()) {
                return Result.builder().fail().code(500).message(error.getDefaultMessage()).build();
            }
        }
        return libraryService.setBook(request);
    }

    @PostMapping("/park/list")
    @ApiOperation(value = "管理员获取园区列表")
    @RequiresPermissions("parkCulture:library:process_add")
    public Result<List<ParkInfoListResponse>> getParkList(){
        return libraryService.getParkList();
    }

    @PostMapping("/user/list/{parkId}")
    @ApiOperation(value = "管理员获取某个园区的人员列表")
    @RequiresPermissions("parkCulture:library:process_add")
    public Result<List<ParkUserListResponse>> getUserList(@PathVariable("parkId") String parkId){
        return libraryService.getUserList(parkId);
    }

    @PostMapping("/task/add")
    @ApiOperation(value = "管理员设置员工必读")
    @RequiresPermissions("parkCulture:library:process_add")
    public Result addReadProcess(@Valid @RequestBody AddReadProcessRequest request,BindingResult result){
        if (result.hasErrors()){
            for (ObjectError error:result.getAllErrors()) {
                return Result.builder().fail().code(500).message(error.getDefaultMessage()).build();
            }
        }
        return libraryService.addReadProcess(request);
    }

}
