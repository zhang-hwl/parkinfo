package com.parkinfo.service.parkCulture.impl;

import com.google.common.collect.Lists;
import com.parkinfo.common.Result;
import com.parkinfo.entity.parkCulture.Book;
import com.parkinfo.entity.parkCulture.BookComment;
import com.parkinfo.entity.parkCulture.ReadProcess;
import com.parkinfo.exception.NormalException;
import com.parkinfo.repository.parkCulture.BookCommentRepository;
import com.parkinfo.repository.parkCulture.BookRepository;
import com.parkinfo.repository.parkCulture.ReadProcessRepository;
import com.parkinfo.request.parkCulture.AddBookCommentRequest;
import com.parkinfo.request.parkCulture.QueryBookCommentListRequest;
import com.parkinfo.request.parkCulture.QueryBookListRequest;
import com.parkinfo.request.parkCulture.SetReadProcessRequest;
import com.parkinfo.response.parkCulture.BookCommentListResponse;
import com.parkinfo.response.parkCulture.BookDetailResponse;
import com.parkinfo.response.parkCulture.BookListResponse;
import com.parkinfo.response.parkCulture.ReadProcessResponse;
import com.parkinfo.service.parkCulture.ILibraryService;
import com.parkinfo.token.TokenUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * When I wrote this, only God and I understood what I was doing
 * Now, God only knows
 *
 * @author cnyuchu@gmail.com
 * @create 2019-09-05 15:30
 **/
@Service
public class LibraryServiceImpl implements ILibraryService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookCommentRepository bookCommentRepository;

    @Autowired
    private ReadProcessRepository readProcessRepository;

    @Autowired
    private TokenUtils tokenUtils;

    @Override
    public Result<Page<BookListResponse>> search(QueryBookListRequest request) {
        Pageable pageable = PageRequest.of(request.getPageNum(), request.getPageSize(), Sort.Direction.DESC, "createTime");
        Specification<Book> bookSpecification = (Specification<Book>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.isNotBlank(request.getName())) {
                predicates.add(criteriaBuilder.like(root.get("name").as(String.class), "%" + request.getName() + "%"));
            }
            if (StringUtils.isNotBlank(request.getThirdCategoryId())) {
                predicates.add(criteriaBuilder.equal(root.get("category").get("id").as(String.class), request.getThirdCategoryId()));
            } else if (StringUtils.isNotBlank(request.getSecondCategoryId())) {
                predicates.add(criteriaBuilder.equal(root.get("category").get("parent").get("id").as(String.class), request.getSecondCategoryId()));
            } else if (StringUtils.isNotBlank(request.getFirstCategoryId())) {
                predicates.add(criteriaBuilder.equal(root.get("category").get("parent").get("parent").get("id").as(String.class), request.getSecondCategoryId()));
            }
            predicates.add(criteriaBuilder.equal(root.get("available").as(Boolean.class), Boolean.TRUE));
            predicates.add(criteriaBuilder.equal(root.get("delete").as(Boolean.class), Boolean.FALSE));
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
        Page<Book> bookPage = bookRepository.findAll(bookSpecification, pageable);
        Page<BookListResponse> responsePage = this.convertBookPage(bookPage);
        return Result.<Page<BookListResponse>>builder().success().data(responsePage).build();
    }


    @Override
    public Result<BookDetailResponse> detail(String bookId) {
        Optional<Book> bookOptional = bookRepository.findByIdAndDeleteIsFalseAndAvailableIsTrue(bookId);
        if (!bookOptional.isPresent()) {
            throw new NormalException("该图书不存在");
        }
        Book book = bookOptional.get();
        BookDetailResponse response = this.convertBookDetail(book);
        return Result.<BookDetailResponse>builder().success().data(response).build();
    }

    @Override
    public Result<Page<BookCommentListResponse>> getCommentPage(QueryBookCommentListRequest request) {
        Pageable pageable = PageRequest.of(request.getPageNum(), request.getPageSize(), Sort.Direction.DESC, "createTime");
        Specification<BookComment> bookCommentSpecification = (Specification<BookComment>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.isNotBlank(request.getBookId())) {
                predicates.add(criteriaBuilder.equal(root.get("book").get("id").as(String.class), request.getBookId()));
            }
            predicates.add(criteriaBuilder.equal(root.get("available").as(Boolean.class), Boolean.TRUE));
            predicates.add(criteriaBuilder.equal(root.get("delete").as(Boolean.class), Boolean.FALSE));
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
        Page<BookComment> bookCommentPage = bookCommentRepository.findAll(bookCommentSpecification, pageable);
        Page<BookCommentListResponse> responsePage = this.convertBookCommentPage(bookCommentPage);
        return Result.<Page<BookCommentListResponse>>builder().success().data(responsePage).build();
    }

    @Override
    public Result addComment(AddBookCommentRequest request) {
        Book book = this.checkBook(request.getBookId());
        BookComment bookComment = new BookComment();
        bookComment.setBook(book);
        bookComment.setReader(tokenUtils.getLoginUser());
        bookComment.setComment(request.getComment());
        bookComment.setDelete(false);
        bookComment.setAvailable(true);
        bookCommentRepository.save(bookComment);
        return Result.builder().success().message("评论成功").build();
    }

    @Override
    public Result<ReadProcessResponse> getReadProcess(String bookId) {
        Book book = this.checkBook(bookId);
        Optional<ReadProcess> readProcessOptional = readProcessRepository.findByBook_IdAndDeleteIsTrueAndAvailableIsFalse(bookId);
        ReadProcess readProcess;
        if (!readProcessOptional.isPresent()) {
            readProcess = new ReadProcess();
            readProcess.setBook(book);
            readProcess.setReader(tokenUtils.getLoginUser());
            readProcess.setNecessary(false);
            readProcess.setProcess(BigDecimal.ZERO);
            readProcess.setDelete(false);
            readProcess.setAvailable(true);
            readProcessRepository.save(readProcess);
        } else {
            readProcess = readProcessOptional.get();
        }
        ReadProcessResponse response = this.convertReadProcess(readProcess);
        return Result.<ReadProcessResponse>builder().success().data(response).build();
    }

    @Override
    public Result setReadProcess(SetReadProcessRequest request) {
        Optional<ReadProcess> readProcessOptional = readProcessRepository.findById(request.getId());
        if (!readProcessOptional.isPresent()) {
            throw new NormalException("阅读进度不存在");
        }
        ReadProcess readProcess = readProcessOptional.get();
        readProcess.setProcess(request.getProcess());
        readProcessRepository.save(readProcess);
        return Result.builder().success().message("更新阅读进度成功").build();
    }

    /**
     * convert from Book's Page to BookListResponse's Page
     *
     * @param bookPage
     * @return
     */
    private Page<BookListResponse> convertBookPage(Page<Book> bookPage) {
        List<BookListResponse> content = Lists.newArrayList();
        bookPage.forEach(book -> {
            BookListResponse response = new BookListResponse();
            BeanUtils.copyProperties(book, response);
            content.add(response);
        });
        return new PageImpl<>(content, bookPage.getPageable(), bookPage.getTotalElements());
    }

    /**
     * convert from Book to BookDetailResponse
     *
     * @param book
     * @return
     */
    private BookDetailResponse convertBookDetail(Book book) {
        BookDetailResponse response = new BookDetailResponse();
        BeanUtils.copyProperties(book, response);
        return response;
    }

    /**
     * convert from BookComment's Page to BookCommentListResponse's Page
     *
     * @param bookCommentPage
     * @return
     */
    private Page<BookCommentListResponse> convertBookCommentPage(Page<BookComment> bookCommentPage) {
        List<BookCommentListResponse> content = Lists.newArrayList();
        bookCommentPage.forEach(bookComment -> {
            BookCommentListResponse response = new BookCommentListResponse();
            BeanUtils.copyProperties(bookComment, response);
            if (bookComment.getReader() != null) {
                response.setAvatar(bookComment.getReader().getAvatar());
                response.setNickname(bookComment.getReader().getNickname());
            }
            content.add(response);
        });
        return new PageImpl<>(content, bookCommentPage.getPageable(), bookCommentPage.getTotalElements());
    }

    /**
     * convert from ReadProcess to ReadProcessResponse
     * @param readProcess
     * @return
     */
    private ReadProcessResponse convertReadProcess(ReadProcess readProcess) {
        ReadProcessResponse response = new ReadProcessResponse();
        BeanUtils.copyProperties(readProcess,response);
        return response;
    }

    private Book checkBook(String bookId) {
        Optional<Book> bookOptional = bookRepository.findByIdAndDeleteIsFalseAndAvailableIsTrue(bookId);
        if (!bookOptional.isPresent()) {
            throw new NormalException("该图书不存在");
        }
        return bookOptional.get();
    }
}
