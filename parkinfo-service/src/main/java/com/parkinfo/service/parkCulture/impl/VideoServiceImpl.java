package com.parkinfo.service.parkCulture.impl;

import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.CreateUploadVideoResponse;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoResponse;
import com.aliyuncs.vod.model.v20170321.RefreshUploadVideoResponse;
import com.google.common.collect.Lists;
import com.parkinfo.common.Result;
import com.parkinfo.dto.ParkUserDTO;
import com.parkinfo.entity.parkCulture.Book;
import com.parkinfo.entity.parkCulture.Video;
import com.parkinfo.entity.parkCulture.VideoComment;
import com.parkinfo.entity.parkCulture.VideoRecord;
import com.parkinfo.entity.userConfig.ParkRole;
import com.parkinfo.entity.userConfig.ParkUser;
import com.parkinfo.enums.ParkRoleEnum;
import com.parkinfo.exception.NormalException;
import com.parkinfo.repository.parkCulture.VideoCommentRepository;
import com.parkinfo.repository.parkCulture.VideoRecordRepository;
import com.parkinfo.repository.parkCulture.VideoRepository;
import com.parkinfo.request.parkCulture.*;
import com.parkinfo.response.parkCulture.*;
import com.parkinfo.service.parkCulture.IVideoService;
import com.parkinfo.token.TokenUtils;
import com.parkinfo.tools.vod.IVodService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * When I wrote this, only God and I understood what I was doing
 * Now, God only knows
 *
 * @author cnyuchu@gmail.com
 * @create 2019-09-06 17:18
 **/
@Service
@Slf4j
public class VideoServiceImpl implements IVideoService {

    @Autowired
    private VideoRepository videoRepository;

    @Autowired
    private VideoCommentRepository videoCommentRepository;

    @Autowired
    private VideoRecordRepository videoRecordRepository;

    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private IVodService vodService;

    @Override
    public Result<Page<VideoListResponse>> search(QueryVideoListRequest request) {
        Pageable pageable = PageRequest.of(request.getPageNum(), request.getPageSize(), Sort.Direction.DESC, "createTime");
        Specification<Book> bookSpecification = (Specification<Book>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.isNotBlank(request.getName())) {
                predicates.add(criteriaBuilder.like(root.get("name").as(String.class), "%" + request.getName() + "%"));
            }
            if (StringUtils.isNotBlank(request.getSecondCategoryId())) {
                predicates.add(criteriaBuilder.equal(root.get("category").get("id").as(String.class), request.getSecondCategoryId()));
            } else if (StringUtils.isNotBlank(request.getFirstCategoryId())) {
                predicates.add(criteriaBuilder.equal(root.get("category").get("parent").get("id").as(String.class), request.getSecondCategoryId()));
            }
            predicates.add(criteriaBuilder.equal(root.get("available").as(Boolean.class), Boolean.TRUE));
            predicates.add(criteriaBuilder.equal(root.get("delete").as(Boolean.class), Boolean.FALSE));
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
        Page<Video> videoPage = videoRepository.findAll(bookSpecification, pageable);
        Page<VideoListResponse> responsePage = this.convertVideoPage(videoPage);
        return Result.<Page<VideoListResponse>>builder().success().data(responsePage).build();
    }

    @Override
    public Result<VideoDetailResponse> detail(String id) {
        Video video = this.checkVideo(id);
        VideoDetailResponse response = new VideoDetailResponse();
        BeanUtils.copyProperties(video, response);
        //添加观看记录
        VideoRecord videoRecord = new VideoRecord();
        ParkUser parkUser = tokenUtils.getLoginUser();
        videoRecord.setWatcher(parkUser);
        videoRecord.setVideo(video);
        videoRecord.setDelete(false);
        videoRecord.setAvailable(true);
        videoRecordRepository.save(videoRecord);
        return Result.<VideoDetailResponse>builder().success().data(response).build();
    }

    @Override
    public Result<Page<VideoCommentListResponse>> getCommentPage(QueryVideoCommentListRequest request) {
        Pageable pageable = PageRequest.of(request.getPageNum(), request.getPageSize(), Sort.Direction.DESC, "createTime");
        Specification<VideoComment> videoCommentSpecification = (Specification<VideoComment>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.isNotBlank(request.getVideoId())) {
                predicates.add(criteriaBuilder.equal(root.get("video").get("id").as(String.class), request.getVideoId()));
            }
            predicates.add(criteriaBuilder.equal(root.get("available").as(Boolean.class), Boolean.TRUE));
            predicates.add(criteriaBuilder.equal(root.get("delete").as(Boolean.class), Boolean.FALSE));
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
        Page<VideoComment> videoCommentPage = videoCommentRepository.findAll(videoCommentSpecification, pageable);
        Page<VideoCommentListResponse> responsePage = this.convertVideoCommentPage(videoCommentPage);
        return Result.<Page<VideoCommentListResponse>>builder().success().data(responsePage).build();
    }

    @Override
    public Result addComment(AddVideoCommentRequest request) {
        Video video = this.checkVideo(request.getVideoId());
        VideoComment videoComment = new VideoComment();
        videoComment.setVideo(video);
        videoComment.setWatcher(tokenUtils.getLoginUser());
        videoComment.setComment(request.getComment());
        videoComment.setDelete(false);
        videoComment.setAvailable(true);
        videoCommentRepository.save(videoComment);
        return Result.builder().success().message("评论成功").build();
    }

    @Override
    public Result<GetPlayInfoResponse> getPlayInfo(String videoId) {
        try {
            GetPlayInfoResponse playInfoResponse = vodService.getPlayInfo(videoId);
            return Result.<GetPlayInfoResponse>builder().success().data(playInfoResponse).build();
        } catch (ClientException e) {
            throw new NormalException("获取视频播放地址失败，原因为:" + e.getMessage());
        }
    }

    @Override
    public Result<Page<VideoRecordListResponse>> getRecordList(QueryVideoRecordListRequest request) {
        Pageable pageable = PageRequest.of(request.getPageNum(), request.getPageSize(), Sort.Direction.DESC, "createTime");
        Specification<VideoRecord> videoRecordSpecification = (Specification<VideoRecord>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(criteriaBuilder.equal(root.get("video").get("id").as(String.class),request.getVideoId()));
            predicates.add(criteriaBuilder.equal(root.get("available").as(Boolean.class), Boolean.TRUE));
            predicates.add(criteriaBuilder.equal(root.get("delete").as(Boolean.class), Boolean.FALSE));
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
        Page<VideoRecord> videoRecordPage = videoRecordRepository.findAll(videoRecordSpecification, pageable);
        Page<VideoRecordListResponse> responsePage = this.convertVideoRecordPage(videoRecordPage);
        return Result.<Page<VideoRecordListResponse>>builder().success().data(responsePage).build();
    }


    @Override
    public Result addVideo(AddVideoRequest request) {
        Video video = new Video();
        BeanUtils.copyProperties(request,video);
        videoRepository.save(video);
        return Result.builder().success().message("保存视频成功").build();
    }

    @Override
    public Result setVideoStatus(String videoId) {
        Video video = this.checkVideo(videoId);
        video.setAvailable(video.getAvailable() != null && !video.getAvailable());
        videoRepository.save(video);
        return Result.builder().success().message("成功").build();
    }

    @Override
    public Result<Page<VideoManageListResponse>> manageVideo(QueryVideoManageRequest request) {
        ParkUserDTO currentUser = tokenUtils.getLoginUserDTO();
        Pageable pageable = PageRequest.of(request.getPageNum(), request.getPageSize(), Sort.Direction.DESC, "createTime");
        Specification<Book> bookSpecification = (Specification<Book>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.isNotBlank(request.getName())) {
                predicates.add(criteriaBuilder.like(root.get("name").as(String.class), "%" + request.getName() + "%"));
            }
           if (request.getCreateTimeFrom()!=null&&request.getCreateTimeTo()!=null){
               predicates.add(criteriaBuilder.between(root.get("createTime"), request.getCreateTimeFrom(),request.getCreateTimeTo()));
           }
            predicates.add(criteriaBuilder.equal(root.get("available").as(Boolean.class), Boolean.TRUE));
            predicates.add(criteriaBuilder.equal(root.get("delete").as(Boolean.class), Boolean.FALSE));
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
        Page<Video> videoPage = videoRepository.findAll(bookSpecification, pageable);
        Page<VideoManageListResponse> responsePage = this.convertVideoManagePage(videoPage);
        return Result.<Page<VideoManageListResponse>>builder().success().data(responsePage).build();
    }



    @Override
    public Result setVideo(SetVideoRequest request) {
        Video video = this.checkVideo(request.getVideoId());
        BeanUtils.copyProperties(request,video);
        videoRepository.save(video);
        return Result.builder().success().message("保存视频成功").build();
    }

    @Override
    public Result<CreateUploadVideoResponse> createUploadVideo(CreateUploadVideoRequest request)  {
        CreateUploadVideoResponse response = null;
        try {
            response = vodService.createUploadVideo(request.getTitle(), request.getFileName());
        } catch (ClientException e) {
            throw new NormalException(e.getErrMsg());
        }
        return Result.<CreateUploadVideoResponse>builder().success().data(response).build();
    }

    @Override
    public Result<RefreshUploadVideoResponse> refreshUploadVideo(String videoId) throws Exception {
        RefreshUploadVideoResponse response = vodService.refreshUploadVideo(videoId);
        return Result.<RefreshUploadVideoResponse>builder().success().data(response).build();
    }

    private Page<VideoListResponse> convertVideoPage(Page<Video> videoPage) {
        List<VideoListResponse> content = Lists.newArrayList();
        videoPage.forEach(video -> {
            VideoListResponse response = new VideoListResponse();
            BeanUtils.copyProperties(video, response);
            if (video.getUploader() != null) {
                response.setUploader(video.getUploader().getNickname());
            }
            content.add(response);
        });
        return new PageImpl<>(content, videoPage.getPageable(), videoPage.getTotalElements());
    }

    private Page<VideoCommentListResponse> convertVideoCommentPage(Page<VideoComment> videoCommentPage) {
        List<VideoCommentListResponse> content = Lists.newArrayList();
        videoCommentPage.forEach(videoComment -> {
            VideoCommentListResponse response = new VideoCommentListResponse();
            BeanUtils.copyProperties(videoComment, response);
            if (videoComment.getWatcher() != null) {
                response.setAvatar(videoComment.getWatcher().getAvatar());
                response.setNickname(videoComment.getWatcher().getNickname());
            }
            content.add(response);
        });
        return new PageImpl<>(content, videoCommentPage.getPageable(), videoCommentPage.getTotalElements());
    }

    private Page<VideoManageListResponse> convertVideoManagePage(Page<Video> videoPage) {
        List<VideoManageListResponse> content = Lists.newArrayList();
        videoPage.forEach(video -> {
            VideoManageListResponse response = new VideoManageListResponse();
            BeanUtils.copyProperties(video, response);
            if (video.getUploader() != null) {
                response.setUploader(video.getUploader().getNickname());
            }
            content.add(response);
        });
        return new PageImpl<>(content, videoPage.getPageable(), videoPage.getTotalElements());
    }

    private Page<VideoRecordListResponse> convertVideoRecordPage(Page<VideoRecord> videoRecordPage) {
        List<VideoRecordListResponse> content = Lists.newArrayList();
        videoRecordPage.forEach(videoRecord -> {
            VideoRecordListResponse response = new VideoRecordListResponse();
            BeanUtils.copyProperties(videoRecord,response);
            if (videoRecord.getWatcher()!=null){
                response.setAvatar(videoRecord.getWatcher().getAvatar());
                response.setNickname(videoRecord.getWatcher().getNickname());
            }
            content.add(response);
        });
        return new PageImpl<>(content,videoRecordPage.getPageable(),videoRecordPage.getTotalElements());
    }

    private Video checkVideo(String videoId) {
        Optional<Video> videoOptional = videoRepository.findById(videoId);
        if (!videoOptional.isPresent()) {
            throw new NormalException("该视频不存在");
        }
        return videoOptional.get();
    }
}
