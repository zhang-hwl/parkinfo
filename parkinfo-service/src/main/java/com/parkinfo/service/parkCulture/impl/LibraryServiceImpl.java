package com.parkinfo.service.parkCulture.impl;

import com.google.common.collect.Lists;
import com.parkinfo.common.Result;
import com.parkinfo.dto.ParkUserDTO;
import com.parkinfo.entity.parkCulture.Book;
import com.parkinfo.entity.parkCulture.BookCategory;
import com.parkinfo.entity.parkCulture.BookComment;
import com.parkinfo.entity.parkCulture.ReadProcess;
import com.parkinfo.entity.userConfig.ParkInfo;
import com.parkinfo.entity.userConfig.ParkUser;
import com.parkinfo.enums.ParkRoleEnum;
import com.parkinfo.exception.NormalException;
import com.parkinfo.repository.parkCulture.BookCategoryRepository;
import com.parkinfo.repository.parkCulture.BookCommentRepository;
import com.parkinfo.repository.parkCulture.BookRepository;
import com.parkinfo.repository.parkCulture.ReadProcessRepository;
import com.parkinfo.repository.userConfig.ParkInfoRepository;
import com.parkinfo.repository.userConfig.ParkUserRepository;
import com.parkinfo.request.parkCulture.*;
import com.parkinfo.response.login.ParkInfoListResponse;
import com.parkinfo.response.login.ParkUserListResponse;
import com.parkinfo.response.parkCulture.*;
import com.parkinfo.service.parkCulture.ILibraryService;
import com.parkinfo.token.TokenUtils;
import org.apache.commons.io.FilenameUtils;
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
import java.util.stream.Collectors;

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
    private BookCategoryRepository bookCategoryRepository;

    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private ParkInfoRepository parkInfoRepository;

    @Autowired
    private ParkUserRepository parkUserRepository;

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
    public Result disableBook(String bookId) {
        Book book = this.checkBook(bookId);
        book.setAvailable(book.getAvailable() != null && !book.getAvailable());
        bookRepository.save(book);
        return Result.builder().success().message("成功").build();
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

    @Override
    public Result<Page<BookManageListResponse>> manageBook(QueryBookListRequest request) {
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
//            predicates.add(criteriaBuilder.equal(root.get("available").as(Boolean.class), Boolean.TRUE));
            predicates.add(criteriaBuilder.equal(root.get("delete").as(Boolean.class), Boolean.FALSE));
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
        Page<Book> bookPage = bookRepository.findAll(bookSpecification, pageable);
        Page<BookManageListResponse> responsePage = this.convertBookManagePage(bookPage);
        return Result.<Page<BookManageListResponse>>builder().success().data(responsePage).build();
    }

    @Override
    public Result addBook(AddBookRequest request) {
        ParkUserDTO currentUser = tokenUtils.getLoginUserDTO();
        Book book = new Book();
        BeanUtils.copyProperties(request, book);
        book.setAvailable(true);
        book.setDelete(false);
        book.setUploader(currentUser.getAccount());
        book.setReadNum(0);
        BookCategory bookCategory = this.checkBookCategory(request.getCategoryId());
        book.setCategory(bookCategory);
        bookRepository.save(book);
        return Result.builder().success().message("保存图书成功").build();
    }

    @Override
    public Result<Page<ReadProcessListResponse>> searchProcess(QueryReadProcessListRequest request) {
        Pageable pageable = PageRequest.of(request.getPageNum(), request.getPageSize(), Sort.Direction.DESC, "createTime");
        Specification<ReadProcess> readProcessSpecification = (Specification<ReadProcess>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(criteriaBuilder.like(root.get("book").get("id").as(String.class), request.getBookId()));
            predicates.add(criteriaBuilder.equal(root.get("available").as(Boolean.class), Boolean.TRUE));
            predicates.add(criteriaBuilder.equal(root.get("delete").as(Boolean.class), Boolean.FALSE));
            if (request.getNecessary() != null) {
                predicates.add(criteriaBuilder.equal(root.get("necessary").as(Boolean.class), request.getNecessary()));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
        Page<ReadProcess> readProcessPage = readProcessRepository.findAll(readProcessSpecification, pageable);
        Page<ReadProcessListResponse> responsePage = this.convertReadProcessPage(readProcessPage);
        return Result.<Page<ReadProcessListResponse>>builder().success().data(responsePage).build();
    }

    @Override
    public Result setBookStatus(String bookId) {
        Book book = this.getBook(bookId);
        book.setAvailable(book.getAvailable() != null && !book.getAvailable());
        bookRepository.save(book);
        return Result.builder().success().message("成功").build();
    }

    @Override
    public Result deleteBook(String bookId) {
        Book book = this.checkBook(bookId);
        book.setDelete(true);
        bookRepository.save(book);
        return Result.builder().success().message("成功").build();
    }

    @Override
    public Result setBook(SetBookRequest request) {
        Book book = this.checkBook(request.getId());
        BeanUtils.copyProperties(request, book);
        BookCategory bookCategory = this.checkBookCategory(request.getCategoryId());
        book.setCategory(bookCategory);
        bookRepository.save(book);
        return Result.builder().success().message("保存图书成功").build();
    }

    @Override
    public Result<Page<BookCategoryListResponse>> search(QueryCategoryPageRequest request) {
        Pageable pageable = PageRequest.of(request.getPageNum(), request.getPageSize(), Sort.Direction.DESC, "createTime");
        Specification<BookCategory> bookCategorySpecification = (Specification<BookCategory>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(criteriaBuilder.isNull(root.get("parent")));
            predicates.add(criteriaBuilder.equal(root.get("available").as(Boolean.class), Boolean.TRUE));
            predicates.add(criteriaBuilder.equal(root.get("delete").as(Boolean.class), Boolean.FALSE));
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
        Page<BookCategory> bookCategoryPage = bookCategoryRepository.findAll(bookCategorySpecification, pageable);
        Page<BookCategoryListResponse> responsePage = this.convertBookCategoryPage(bookCategoryPage);
        return Result.<Page<BookCategoryListResponse>>builder().success().data(responsePage).build();
    }

    @Override
    public Result<List<BookCategoryListResponse>> search(QueryCategoryListRequest request) {
        Specification<BookCategory> bookCategorySpecification = (Specification<BookCategory>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.isNotBlank(request.getParentId())) {
                predicates.add(criteriaBuilder.equal(root.get("parent").get("id"), request.getParentId()));
            } else {
                predicates.add(criteriaBuilder.isNull(root.get("parent")));
            }
            predicates.add(criteriaBuilder.equal(root.get("available").as(Boolean.class), Boolean.TRUE));
            predicates.add(criteriaBuilder.equal(root.get("delete").as(Boolean.class), Boolean.FALSE));
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
        List<BookCategory> bookCategoryList = bookCategoryRepository.findAll(bookCategorySpecification);
        List<BookCategoryListResponse> responseList = this.convertBookCategoryList(bookCategoryList);
        return Result.<List<BookCategoryListResponse>>builder().success().data(responseList).build();
    }

    @Override
    public Result addBookCategory(AddBookCategoryRequest request) {
        BookCategory bookCategory = new BookCategory();
        BeanUtils.copyProperties(request, bookCategory);
        if (StringUtils.isNotBlank(request.getParentId())) {
            BookCategory parent = this.checkBookCategory(request.getParentId());
            bookCategory.setParent(parent);
        }
        bookCategory.setDelete(false);
        bookCategory.setAvailable(true);
        bookCategoryRepository.save(bookCategory);
        return Result.builder().success().message("添加图书分类成功").build();
    }

    @Override
    public Result setBookCategory(SetBookCategoryRequest request) {
        BookCategory bookCategory = this.checkBookCategory(request.getId());
        BeanUtils.copyProperties(request, bookCategory);
        if (StringUtils.isNotBlank(request.getParentId())) {
            BookCategory parent = this.checkBookCategory(request.getParentId());
            bookCategory.setParent(parent);
        }
        bookCategoryRepository.save(bookCategory);
        return Result.builder().success().message("修改图书分类成功").build();
    }

    @Override
    public Result deleteBookCategory(String id) {
        BookCategory bookCategory = this.checkBookCategory(id);
        bookCategory.setDelete(true);
        bookCategoryRepository.save(bookCategory);
        return Result.builder().success().message("删除图书分类成功").build();
    }

    @Override
    public Result<List<ParkInfoListResponse>> getParkList() {
        List<ParkInfoListResponse> responseList;
        ParkUserDTO currentUser = tokenUtils.getLoginUserDTO();
        if (currentUser.getRole().contains(ParkRoleEnum.PARK_MANAGER.toString())) {
            ParkInfo parkInfo = tokenUtils.getCurrentParkInfo();
            ParkInfoListResponse response = this.convertParkInfo(parkInfo);
            responseList = Lists.newArrayList(response);
        } else if (currentUser.getRole().contains(ParkRoleEnum.PRESIDENT.toString()) ||
                currentUser.getRole().contains(ParkRoleEnum.GENERAL_MANAGER.toString()) ||
                currentUser.getRole().contains(ParkRoleEnum.ADMIN.toString())) {
            List<ParkInfo> parkInfoList = parkInfoRepository.findAllByDeleteIsFalseAndAvailableIsTrue();
            responseList = this.convertParkInfoList(parkInfoList);
        } else {
            responseList = Lists.newArrayList();
        }
        return Result.<List<ParkInfoListResponse>>builder().success().data(responseList).build();
    }

    @Override
    public Result<List<ParkUserListResponse>> getUserList(String parkId) {
//        List<ParkUserListResponse> responseList;
        Optional<ParkInfo> parkInfoOptional = parkInfoRepository.findByIdAndDeleteIsFalse(parkId);
        if (!parkInfoOptional.isPresent()) {
            throw new NormalException("该园区不存在");
        }
        ParkInfo parkInfo = parkInfoOptional.get();
        String managerId = null;
        if (parkInfo.getManager() != null) {
            managerId = parkInfo.getManager().getId();
        }
        List<ParkUser> parkUserList = parkInfo.getUsers().stream().filter(ParkUser::getAvailable).filter(parkUser -> !parkUser.getDelete()).collect(Collectors.toList());
        List<ParkUserListResponse> responseList = this.convertParkUserList(parkUserList, managerId);
        return Result.<List<ParkUserListResponse>>builder().success().data(responseList).build();
    }


    @Override
    public Result addReadProcess(AddReadProcessRequest request) {
        Book book = this.checkBook(request.getBookId());
        request.getUserIds().forEach(id -> {
            ParkUser parkUser = this.checkUser(id);
            ReadProcess readProcess = new ReadProcess();
            readProcess.setBook(book);
            readProcess.setReader(parkUser);
            readProcess.setNecessary(true);
            readProcess.setProcess(BigDecimal.ZERO);
            readProcess.setAvailable(true);
            readProcess.setDelete(false);
            readProcessRepository.save(readProcess);
        });
        return Result.builder().success().message("添加成功").build();
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

    private Page<BookManageListResponse> convertBookManagePage(Page<Book> bookPage) {
        List<BookManageListResponse> content = Lists.newArrayList();
        bookPage.forEach(book -> {
            BookManageListResponse response = new BookManageListResponse();
            BeanUtils.copyProperties(book, response);
            content.add(response);
        });
        return new PageImpl<>(content, bookPage.getPageable(), bookPage.getTotalElements());
    }

    private Page<ReadProcessListResponse> convertReadProcessPage(Page<ReadProcess> readProcessPage) {
        List<ReadProcessListResponse> content = Lists.newArrayList();
        readProcessPage.forEach(readProcess -> {
            ReadProcessListResponse response = new ReadProcessListResponse();
            BeanUtils.copyProperties(readProcess, response);
            if (readProcess.getBook() != null) {
                response.setBookName(readProcess.getBook().getName());
            }
            ParkUser reader = readProcess.getReader();
            if (reader != null) {
                response.setAvatar(reader.getAvatar());
                response.setNickname(reader.getNickname());
            }
            content.add(response);
        });
        return new PageImpl<>(content, readProcessPage.getPageable(), readProcessPage.getTotalElements());
    }

    private Page<BookCategoryListResponse> convertBookCategoryPage(Page<BookCategory> bookCategoryPage) {
        List<BookCategoryListResponse> content = Lists.newArrayList();
        bookCategoryPage.forEach(bookCategory -> {
            BookCategoryListResponse response = new BookCategoryListResponse();
            BeanUtils.copyProperties(bookCategory, response);
            List<BookCategoryListResponse> children = Lists.newArrayList();
            bookCategory.getChildren().forEach(childrenCategory -> {
                if (childrenCategory.getAvailable() && !childrenCategory.getDelete()) {
                    BookCategoryListResponse childrenResponse = new BookCategoryListResponse();
                    BeanUtils.copyProperties(childrenCategory, childrenResponse);
                    List<BookCategoryListResponse> grandSon = Lists.newArrayList();
                    childrenCategory.getChildren().forEach(grandSonCategory -> {
                        if (grandSonCategory.getAvailable() && !grandSonCategory.getDelete()) {
                            BookCategoryListResponse grandSonResponse = new BookCategoryListResponse();
                            BeanUtils.copyProperties(grandSonCategory, grandSonResponse);
                            grandSon.add(grandSonResponse);
                        }
                    });
                    childrenResponse.setChildren(grandSon);
                    children.add(childrenResponse);
                }

            });
            response.setChildren(children);
            content.add(response);
        });
        return new PageImpl<>(content, bookCategoryPage.getPageable(), bookCategoryPage.getTotalElements());
    }

    private List<BookCategoryListResponse> convertBookCategoryList(List<BookCategory> bookCategoryList) {
        List<BookCategoryListResponse> content = Lists.newArrayList();
        bookCategoryList.forEach(bookCategory -> {
            BookCategoryListResponse response = new BookCategoryListResponse();
            BeanUtils.copyProperties(bookCategory, response);
            content.add(response);
        });
        return content;

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
        BookCategory thirdCategory = book.getCategory();
        if (thirdCategory != null) {
            response.setThirdCategoryId(thirdCategory.getId());
            BookCategory secondCategory = thirdCategory.getParent();
            if (secondCategory != null) {
                response.setSecondCategoryId(secondCategory.getId());
                BookCategory firstCategory = secondCategory.getParent();
                if (firstCategory != null) {
                    response.setFirstCategoryId(firstCategory.getId());
                }
            }
        }
        if (StringUtils.isBlank(book.getFileName()) && book.getSource() != null) {
            response.setFileName(FilenameUtils.getName(book.getSource()));
        }
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

    private List<ParkInfoListResponse> convertParkInfoList(List<ParkInfo> parkInfoList) {
        List<ParkInfoListResponse> responseList = Lists.newArrayList();
        parkInfoList.forEach(parkInfo -> {
            ParkInfoListResponse response = new ParkInfoListResponse();
            BeanUtils.copyProperties(parkInfo, response);
            responseList.add(response);
        });
        return responseList;
    }

    private ParkInfoListResponse convertParkInfo(ParkInfo parkInfo) {
        ParkInfoListResponse response = new ParkInfoListResponse();
        BeanUtils.copyProperties(parkInfo, response);
        return response;
    }

    /**
     * convert from ReadProcess to ReadProcessResponse
     *
     * @param readProcess
     * @return
     */
    private ReadProcessResponse convertReadProcess(ReadProcess readProcess) {
        ReadProcessResponse response = new ReadProcessResponse();
        BeanUtils.copyProperties(readProcess, response);
        return response;
    }

    private List<ParkUserListResponse> convertParkUserList(List<ParkUser> parkUserList, String managerId) {
        List<ParkUserListResponse> responseList = Lists.newArrayList();
        parkUserList.forEach(parkUser -> {
            if (managerId.equals(parkUser.getId())) {
                ParkUserListResponse response = new ParkUserListResponse();
                BeanUtils.copyProperties(parkUser, response);
//                response.setName(parkUser.getNickname());
                response.setName(parkUser.getNickname() + "(园区管理员)");
                responseList.add(response);
            }
        });
        parkUserList.forEach(parkUser -> {
            if (!managerId.equals(parkUser.getId())) {
                ParkUserListResponse response = new ParkUserListResponse();
                BeanUtils.copyProperties(parkUser, response);
                response.setName(parkUser.getNickname());
//                response.setName(parkUser.getNickname()+"(园区管理员)");
                responseList.add(response);
            }
        });
        return responseList;
    }

    private Book checkBook(String bookId) {
        Optional<Book> bookOptional = bookRepository.findByIdAndDeleteIsFalseAndAvailableIsTrue(bookId);
        if (!bookOptional.isPresent()) {
            throw new NormalException("该图书不存在");
        }
        return bookOptional.get();
    }

    private Book getBook(String bookId) {
        Optional<Book> bookOptional = bookRepository.findById(bookId);
        if (!bookOptional.isPresent()) {
            throw new NormalException("该图书不存在");
        }
        return bookOptional.get();
    }

    private BookCategory checkBookCategory(String id) {
        Optional<BookCategory> bookCategoryOptional = bookCategoryRepository.findByIdAndDeleteIsFalseAndAvailableIsTrue(id);
        if (!bookCategoryOptional.isPresent()) {
            throw new NormalException("该图书分类不存在");
        }
        return bookCategoryOptional.get();

    }


    private ParkUser checkUser(String id) {
        Optional<ParkUser> parkUserOptional = parkUserRepository.findByIdAndAvailableIsTrueAndDeleteIsFalse(id);
        if (!parkUserOptional.isPresent()) {
            throw new NormalException("id为" + id + "的用户不存在");
        }
        return parkUserOptional.get();
    }

}
