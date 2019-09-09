package com.parkinfo.service.parkCulture.impl;

import com.google.common.collect.Lists;
import com.parkinfo.common.Result;
import com.parkinfo.entity.parkCulture.Book;
import com.parkinfo.entity.parkCulture.Video;
import com.parkinfo.repository.parkCulture.VideoRepository;
import com.parkinfo.request.parkCulture.QueryVideoListRequest;
import com.parkinfo.response.parkCulture.VideoListResponse;
import com.parkinfo.service.parkCulture.IVideoService;
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
        Page<Video> videoPage = videoRepository.findAll(bookSpecification,pageable);
        Page<VideoListResponse> responsePage = this.convertVideoPage(videoPage);
        return Result.<Page<VideoListResponse>>builder().success().data(responsePage).build();
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
        return new PageImpl<>(content,videoPage.getPageable(),videoPage.getTotalElements());
    }
}
