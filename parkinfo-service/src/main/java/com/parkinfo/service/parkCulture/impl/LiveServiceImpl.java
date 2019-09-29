package com.parkinfo.service.parkCulture.impl;

import com.google.common.collect.Lists;
import com.parkinfo.common.Result;
import com.parkinfo.entity.parkCulture.Book;
import com.parkinfo.entity.parkCulture.BookComment;
import com.parkinfo.entity.parkCulture.Live;
import com.parkinfo.entity.parkCulture.LiveComment;
import com.parkinfo.enums.BroadcastType;
import com.parkinfo.exception.NormalException;
import com.parkinfo.repository.parkCulture.LiveCommentRepository;
import com.parkinfo.repository.parkCulture.LiveRepository;
import com.parkinfo.request.parkCulture.*;
import com.parkinfo.response.parkCulture.*;
import com.parkinfo.service.parkCulture.ILiveService;
import com.parkinfo.token.TokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;
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
 * @create 2019-09-12 10:54
 **/

@Slf4j
@Service
public class LiveServiceImpl implements ILiveService {

    @Autowired
    private LiveRepository liveRepository;

    @Autowired
    private LiveCommentRepository liveCommentRepository;

    @Autowired
    private TokenUtils tokenUtils;


    @Override
    public Result<Page<LiveListResponse>> search(QueryLiveListRequest request) {
        Pageable pageable = PageRequest.of(request.getPageNum(), request.getPageSize(), Sort.Direction.DESC, "createTime");
        //查询条件
        Specification<Live> liveSpecification = (Specification<Live>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.isNotBlank(request.getTitle())) {
                predicates.add(criteriaBuilder.like(root.get("title").as(String.class), "%" + request.getTitle() + "%"));
            }
            if (request.getLive() != null) {
                predicates.add(criteriaBuilder.equal(root.get("live"), request.getLive()));
            }
            if (request.getLiveStartTimeFrom() != null && request.getLiveStartTimeTo() != null) {
                predicates.add(criteriaBuilder.between(root.get("liveStartTime"), request.getLiveStartTimeFrom(), request.getLiveStartTimeTo()));
            }
            predicates.add(criteriaBuilder.equal(root.get("available").as(Boolean.class), Boolean.TRUE));
            predicates.add(criteriaBuilder.equal(root.get("delete").as(Boolean.class), Boolean.FALSE));
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
        Page<Live> livePage = liveRepository.findAll(liveSpecification, pageable);
        Page<LiveListResponse> responsePage = this.convertLiveList(livePage);
        return Result.<Page<LiveListResponse>>builder().success().data(responsePage).build();
    }

    @Override
    public Result addLiveBroadcast(AddLiveBroadcastRequest request) {
        Live newData = new Live();
        BeanUtils.copyProperties(request, newData);
        newData.setPullDomain("rtmp://getvideo.ehrsip.com");
        newData.setPullKey("Gp585rMFE0");
        newData.setPushDomain("rtmp://pushvideo.ehrsip.com");
        newData.setPushKey("h66xZE8qiA");
        newData.setAppName("ehrsip");
        newData.setLive(BroadcastType.WAIT);
        newData.setStreamName(RandomStringUtils.randomAlphanumeric(8));
        newData.setAvailable(Boolean.TRUE);
        newData.setDelete(Boolean.FALSE);
        liveRepository.save(newData);
        return Result.builder().success().message("添加直播成功").build();
    }

    @Override
    public Result setLiveBroadcast(SetLiveBroadcastRequest request) {
        Live live = this.checkLive(request.getId());
        BeanUtils.copyProperties(request, live);
        liveRepository.save(live);
        return Result.builder().success().message("修改直播成功").build();
    }

    @Override
    public Result<PushLiveUrlResponse> generatePushLiveUrl(String id) {
        Live live = this.checkLive(id);
        long timeStamp = System.currentTimeMillis() / 1000 + 30 * 60;//有效期30分钟
//        long timeStamp = 1569722617L;
        String appName = live.getAppName(); //获取appName
        String streamName = live.getStreamName();  //获取streamName
        String rand = "0";//随机数  目前没有用
        String uid = "0";//uid  目前没有用
        String pushKey = live.getPushKey();//推流鉴权key
        String sString = "/" + appName + "/" + streamName + "-" + timeStamp + "-" + rand + "-" + uid + "-" + pushKey;
        String hashValue = DigestUtils.md5Hex(sString);
//        String pushLiveUrl = liveBroadcast.getPushDomain() + "/" + appName + "/" + streamName + "?auth_key=" + timeStamp + "-" + rand + "-" + uid + "-" + hashValue;
        PushLiveUrlResponse pushLiveUrlResponse = PushLiveUrlResponse.builder()
                .url(live.getPushDomain() + "/" + appName + "/")  //服务器
                .authKey(streamName + "?auth_key=" + timeStamp + "-" + rand + "-" + uid + "-" + hashValue)  //串流密钥
                .build();
        return Result.<PushLiveUrlResponse>builder().success().data(pushLiveUrlResponse).build();
    }

    @Override
    public Result<PullLiveUrlResponse> generatePullLiveUrl(String id) {
        Live live = this.checkLive(id);
        long timeStamp = System.currentTimeMillis() / 1000 + 360 * 60;//有效期360分钟
//        long timeStamp = 1569722982L;
        String appName = live.getAppName(); //获取appName
        String streamName = live.getStreamName();  //获取streamName
        String rand = "0";//随机数  目前没有用
        String uid = "0";//uid  目前没有用
        String pullKey = live.getPullKey();//推流鉴权key
        String sStringPrefix = "/" + appName + "/" + streamName;  //需加密字符串前缀
        String sStringSuffix = "-" + timeStamp + "-" + rand + "-" + uid + "-" + pullKey;//需加密字符串后缀
        String url = live.getPullDomain() + "/" + appName + "/" + streamName;
        String authKeyPrefix = "?auth_key=" + timeStamp + "-" + rand + "-" + uid + "-";
        PullLiveUrlResponse response = PullLiveUrlResponse.builder()
                .lldUrl(url + "_lld" + authKeyPrefix + DigestUtils.md5Hex(sStringPrefix + "_lld" + sStringSuffix))
                .lsdUrl(url + "_lsd" + authKeyPrefix + DigestUtils.md5Hex(sStringPrefix + "_lsd" + sStringSuffix))
                .lhdUrl(url + "_lhd" + authKeyPrefix + DigestUtils.md5Hex(sStringPrefix + "_lhd" + sStringSuffix))
                .ludUrl(url + "_lud" + authKeyPrefix + DigestUtils.md5Hex(sStringPrefix + "_lud" + sStringSuffix))
                .build();
        return Result.<PullLiveUrlResponse>builder().success().data(response).build();
    }

    @Override
    public Result disableLiveBroadcast(String id) {
        Live live = this.checkLive(id);
        live.setAvailable(live.getAvailable() != null && !live.getAvailable());
        liveRepository.save(live);
        return Result.builder().success().message("成功").build();
    }

    @Override
    public Result liveCallback(AliLiveCallBack aliLiveCallBack) {
        if (aliLiveCallBack != null) {
            Optional<Live> liveOptional = liveRepository.findFirstByAppNameAndStreamName(aliLiveCallBack.getAppname(), aliLiveCallBack.getId());
            if (liveOptional.isPresent()) {
                Live live = liveOptional.get();
                if (aliLiveCallBack.getAction().equals("publish")) //正在直播
                {
                    live.setLive(BroadcastType.LIVE);
                }
                if (aliLiveCallBack.getAction().equals("publish_done")) //直播结束
                {
                    live.setLive(BroadcastType.END);
                }
                liveRepository.save(live);
            }
        }
        return null;
    }

    @Override
    public Result deleteLiveBroadcast(String id) {
        Live live = this.checkLive(id);
        live.setDelete(Boolean.TRUE);
        liveRepository.save(live);
        return Result.builder().success().message("删除直播数据成功").build();
    }

    @Override
    public Result<Page<LiveCommentListResponse>> getCommentPage(QueryLiveCommentListRequest request) {
        Pageable pageable = PageRequest.of(request.getPageNum(), request.getPageSize(), Sort.Direction.DESC, "createTime");
        Specification<LiveComment> liveCommentSpecification = (Specification<LiveComment>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.isNotBlank(request.getLiveId())) {
                predicates.add(criteriaBuilder.equal(root.get("live").get("id").as(String.class), request.getLiveId()));
            }
            predicates.add(criteriaBuilder.equal(root.get("available").as(Boolean.class), Boolean.TRUE));
            predicates.add(criteriaBuilder.equal(root.get("delete").as(Boolean.class), Boolean.FALSE));
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
        Page<LiveComment> liveCommentPage = liveCommentRepository.findAll(liveCommentSpecification, pageable);
        Page<LiveCommentListResponse> responsePage = this.convertLiveCommentPage(liveCommentPage);
        return Result.<Page<LiveCommentListResponse>>builder().success().data(responsePage).build();
    }



    @Override
    public Result addComment(AddLiveCommentRequest request) {
        Live live = this.checkLive(request.getLiveId());
        LiveComment liveComment = new LiveComment();
        liveComment.setLive(live);
        liveComment.setReader(tokenUtils.getLoginUser());
        liveComment.setComment(request.getComment());
        liveComment.setDelete(false);
        liveComment.setAvailable(true);
        liveCommentRepository.save(liveComment);
        return Result.builder().success().message("评论成功").build();
    }

    private Page<LiveListResponse> convertLiveList(Page<Live> livePage) {
        List<LiveListResponse> content = Lists.newArrayList();
        livePage.forEach(live -> {
            LiveListResponse response = new LiveListResponse();
            BeanUtils.copyProperties(live, response);
            content.add(response);
        });
        return new PageImpl<>(content, livePage.getPageable(), livePage.getTotalElements());
    }

    private Page<LiveCommentListResponse> convertLiveCommentPage(Page<LiveComment> liveCommentPage) {
        List<LiveCommentListResponse> content = Lists.newArrayList();
        liveCommentPage.forEach(liveComment -> {
            LiveCommentListResponse response = new LiveCommentListResponse();
            BeanUtils.copyProperties(liveComment, response);
            if (liveComment.getReader() != null) {
                response.setAvatar(liveComment.getReader().getAvatar());
                response.setNickname(liveComment.getReader().getNickname());
            }
            content.add(response);
        });
        return new PageImpl<>(content, liveCommentPage.getPageable(), liveCommentPage.getTotalElements());
    }

    private Live checkLive(String id) {
        Optional<Live> liveBroadcastOptional = liveRepository.findById(id);
        if (!liveBroadcastOptional.isPresent()) {
            throw new NormalException("直播数据不存在");
        }
        return liveBroadcastOptional.get();
    }
}
