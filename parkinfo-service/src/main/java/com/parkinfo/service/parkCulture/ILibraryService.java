package com.parkinfo.service.parkCulture;

import com.parkinfo.common.Result;
import com.parkinfo.request.parkCulture.AddBookCommentRequest;
import com.parkinfo.request.parkCulture.QueryBookCommentListRequest;
import com.parkinfo.request.parkCulture.QueryBookListRequest;
import com.parkinfo.request.parkCulture.SetReadProcessRequest;
import com.parkinfo.response.parkCulture.BookCommentListResponse;
import com.parkinfo.response.parkCulture.BookDetailResponse;
import com.parkinfo.response.parkCulture.BookListResponse;
import com.parkinfo.response.parkCulture.ReadProcessResponse;
import org.springframework.data.domain.Page;

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
}
