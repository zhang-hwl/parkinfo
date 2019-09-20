package com.parkinfo.service.parkCulture;

import com.parkinfo.common.Result;
import com.parkinfo.request.parkCulture.*;
import com.parkinfo.response.parkCulture.*;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * When I wrote this, only God and I understood what I was doing
 * Now, God only knows
 * 网上图书馆
 * @author cnyuchu@gmail.com
 * @create 2019-09-05 15:29
 **/
public interface ILibraryService {

    /**
     * 搜索图书列表
     * @param request
     * @return
     */
    Result<Page<BookListResponse>> search(QueryBookListRequest request);

    /**
     * 查看图书详情
     * @param bookId
     * @return
     */
    Result<BookDetailResponse> detail(String bookId);

    /**
     * 图书上架、下架
     * @param bookId
     * @return
     */
    Result disableBook(String bookId);
    /**
     * 分页查看图书的评论
     * @param request
     * @return
     */
    Result<Page<BookCommentListResponse>> getCommentPage(QueryBookCommentListRequest request);

    /**
     * 添加图书评论
     * @param request
     * @return
     */
    Result addComment(AddBookCommentRequest request);

    /**
     * 查看某本书的阅读进度
     * @param bookId
     * @return
     */
    Result<ReadProcessResponse> getReadProcess(String bookId);

    /**
     * 设置图书阅读进度
     * @param request
     * @return
     */
    Result setReadProcess(SetReadProcessRequest request);

    /**
     * 分页查询图书分类
     */
    Result<Page<BookCategoryListResponse>> search(QueryCategoryListRequest request);

    /**
     * 添加图书分类
     * @param request
     * @return
     */
    Result addBookCategory(AddBookCategoryRequest request);

    /**
     * 修改图书分类
     * @param request
     * @return
     */
    Result setBookCategory(SetBookCategoryRequest request);

    /**
     * 删除图书分类
     * @param id
     * @return
     */
    Result deleteBookCategory(String id);
}
