package com.parkinfo.service.parkService.impl;

import com.google.common.collect.Lists;
import com.parkinfo.common.Result;
import com.parkinfo.dto.ParkUserDTO;
import com.parkinfo.entity.parkService.meetingRoom.MeetingRoom;
import com.parkinfo.entity.parkService.meetingRoom.MeetingRoomReserve;
import com.parkinfo.entity.userConfig.ParkInfo;
import com.parkinfo.entity.userConfig.ParkUser;
import com.parkinfo.exception.NormalException;
import com.parkinfo.repository.parkService.MeetingRoomRepository;
import com.parkinfo.repository.parkService.MeetingRoomReserveRepository;
import com.parkinfo.request.parkService.meetingRoom.*;
import com.parkinfo.response.parkService.MeetingRoomReserveResponse;
import com.parkinfo.response.parkService.MeetingRoomResponse;
import com.parkinfo.service.parkService.IMeetingRoomService;
import com.parkinfo.token.TokenUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class MeetingRoomServiceImpl implements IMeetingRoomService {
    @Autowired
    private MeetingRoomRepository meetingRoomRepository;
    @Autowired
    private MeetingRoomReserveRepository meetingRoomReserveRepository;
    @Autowired
    private TokenUtils tokenUtils;

    @Override
    public Result<Page<MeetingRoomResponse>> searchMeetingRoom(SearchMeetingRoomRequest request) {
        Pageable pageable = PageRequest.of(request.getPageNum(),request.getPageSize(), Sort.Direction.DESC,"createTime");
//        ParkUserDTO loginUser = tokenUtils.getLoginUserDTO();
//        MeetingRoom exampleData = new MeetingRoom();
//        if (StringUtils.isNotBlank(request.getMeetingRoomId())){
//            exampleData.setId(request.getMeetingRoomId());
//        }
//        ParkInfo currentParkInfo = tokenUtils.getCurrentParkInfo();
//        exampleData.setParkInfo(tokenUtils.getCurrentParkInfo());
//        Example<MeetingRoom> example = Example.of(exampleData);
//        Page<MeetingRoom> meetingRooms = meetingRoomRepository.findAll(example, pageable);
        Specification<MeetingRoom> specification = (Specification<MeetingRoom>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = Lists.newArrayList();
            if (StringUtils.isNotBlank(request.getMeetingRoomId())){
                predicates.add(criteriaBuilder.equal(root.get("id").as(String.class),request.getMeetingRoomId()));
            }
            predicates.add(criteriaBuilder.equal(root.get("delete").as(Boolean.class),Boolean.FALSE));
            predicates.add(criteriaBuilder.equal(root.get("available").as(Boolean.class),Boolean.TRUE));
            predicates.add(criteriaBuilder.equal(root.get("parkInfo").as(ParkInfo.class),tokenUtils.getCurrentParkInfo()));
          return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
        Page<MeetingRoom> meetingRooms = meetingRoomRepository.findAll(specification, pageable);
        Page<MeetingRoomResponse> responses = this.convertMeetingRoomResponse(meetingRooms);
        return Result.<Page<MeetingRoomResponse>>builder().success().data(responses).build();
    }

    @Override
    public Result<String> addMeetingRoom(AddMeetingRoomRequest request) {
        MeetingRoom meetingRoom = new MeetingRoom();
        BeanUtils.copyProperties(request,meetingRoom);
        meetingRoom.setDelete(Boolean.FALSE);
        meetingRoom.setAvailable(Boolean.TRUE);
        meetingRoom.setParkUser(tokenUtils.getLoginUser());
        meetingRoom.setParkInfo(tokenUtils.getCurrentParkInfo());
        meetingRoomRepository.save(meetingRoom);
        return Result.<String>builder().success().message("新增会议室成功").build();
    }

    @Override
    public Result<String> deleteMeetingRoom(String id) {
        MeetingRoom meetingRoom = this.checkMeetingRoom(id);
        meetingRoom.setDelete(Boolean.TRUE);
        meetingRoomRepository.save(meetingRoom);
        return Result.<String>builder().success().message("删除会议室成功").build();
    }

    @Override
    public Result<List<MeetingRoomReserveResponse>> searchMeetingRoomReserve(SearchMeetingRoomReverseRequest request) {
        Date today = request.getSearchTime();
        Calendar nextDay = new GregorianCalendar();
        nextDay.setTime(today);
        nextDay.add(Calendar.DATE,1);
        List<MeetingRoomReserve> meetingRoomReserveList = meetingRoomReserveRepository.findAllByDeleteIsFalseAndAvailableIsTrueAndStartTimeBetweenAndMeetingRoom_Id(today, nextDay.getTime(), request.getMeetingRoomId());
        List<MeetingRoomReserveResponse> responseList = Lists.newArrayList();
        meetingRoomReserveList.forEach(meetingRoomReserve -> {
            MeetingRoomReserveResponse meetingRoomReserveResponse = this.convertMeetingRoomReserveResponse(meetingRoomReserve);
            responseList.add(meetingRoomReserveResponse);
        });
        return Result.<List<MeetingRoomReserveResponse>>builder().success().data(responseList).build();
    }

    @Override
    public Result<MeetingRoomResponse> detailMeetingRoom(String id) {
        MeetingRoom meetingRoom = checkMeetingRoom(id);
        MeetingRoomResponse response = new MeetingRoomResponse();
        BeanUtils.copyProperties(meetingRoom, response);
        response.setUserName(meetingRoom.getParkUser().getNickname());
        return Result.<MeetingRoomResponse>builder().success().data(response).build();
    }

    @Override
    public Result<String> editMeetingRoom(EditMeetingRoomRequest request) {
        MeetingRoom meetingRoom = this.checkMeetingRoom(request.getRoomId());
        meetingRoom.setCapacity(request.getCapacity());
        meetingRoom.setPicture(request.getPicture());
        meetingRoom.setRemark(request.getRemark());
        meetingRoom.setRoomName(request.getRoomName());
        meetingRoomRepository.save(meetingRoom);
        return Result.<String>builder().success().message("编辑会议室成功").build();
    }

    @Override
    public Result<String> reverseMeetingRoom(ReverseMeetingRoomRequest request) {
        if (request.getStartTime().after(request.getEndTime())){
            throw new NormalException("开始时间不能高于结束时间");
        }
        ParkUser loginUser = tokenUtils.getLoginUser();
        MeetingRoom meetingRoom = this.checkMeetingRoom(request.getMeetingRoomId());
        Integer meetingRoomReserveNum = meetingRoomReserveRepository.findMeetingRoomReserve(request.getMeetingRoomId(), request.getStartTime(), request.getEndTime());
        if (meetingRoomReserveNum != 0){
            throw new NormalException("该时间段已被预订");
        }
        MeetingRoomReserve meetingRoomReserve = new MeetingRoomReserve();
        meetingRoomReserve.setReserveUser(loginUser);
        meetingRoomReserve.setMeetingRoomSections(request.getMeetingRoomSections());
        meetingRoomReserve.setStartTime(request.getStartTime());
        meetingRoomReserve.setEndTime(request.getEndTime());
        meetingRoomReserve.setMeetingRoom(meetingRoom);
        meetingRoomReserve.setAvailable(Boolean.TRUE);
        meetingRoomReserve.setDelete(Boolean.FALSE);
        meetingRoomReserveRepository.save(meetingRoomReserve);
        return Result.<String>builder().success().message("预定成功").build();
    }

    private MeetingRoom checkMeetingRoom(String meetingRoomId) {
        Optional<MeetingRoom> meetingRoomOptional = meetingRoomRepository.findFirstByDeleteIsFalseAndAvailableIsTrueAndId(meetingRoomId);
        if (!meetingRoomOptional.isPresent()){
            throw new NormalException("会议室不存在");
        }
        return meetingRoomOptional.get();
    }

    private Page<MeetingRoomResponse> convertMeetingRoomResponse(Page<MeetingRoom> meetingRooms) {
        List<MeetingRoomResponse> response = Lists.newArrayList();
        meetingRooms.getContent().forEach(meetingRoom -> {
            MeetingRoomResponse meetingRoomResponse = new MeetingRoomResponse();
            BeanUtils.copyProperties(meetingRoom,meetingRoomResponse);
            meetingRoomResponse.setUserName(meetingRoom.getParkUser().getNickname());
            if (meetingRoom.getMeetingRoomReserves() != null && meetingRoom.getMeetingRoomReserves().size() != 0){
                Date now = Date.from(LocalDate.now().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
                Date tomorrow = Date.from(LocalDate.now().plusDays(1).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
                List<MeetingRoomReserve> collect = meetingRoom.getMeetingRoomReserves().stream().filter(meetingRoomReserve -> meetingRoomReserve.getStartTime().after(now) && meetingRoomReserve.getEndTime().before(tomorrow)).collect(Collectors.toList());
                List<MeetingRoomReserveResponse> meetingRoomReserveResponseList = Lists.newArrayList();
                collect.forEach(meetingRoomReserve -> {
                    MeetingRoomReserveResponse meetingRoomReserveResponse = this.convertMeetingRoomReserveResponse(meetingRoomReserve);
                    meetingRoomReserveResponseList.add(meetingRoomReserveResponse);
                });
                meetingRoomResponse.setReserveResponse(meetingRoomReserveResponseList);
            }
            response.add(meetingRoomResponse);
        });
        return new PageImpl<>(response,meetingRooms.getPageable(),meetingRooms.getTotalElements());
    }

    private MeetingRoomReserveResponse convertMeetingRoomReserveResponse(MeetingRoomReserve meetingRoomReserve) {
        MeetingRoomReserveResponse meetingRoomReserveResponse = new MeetingRoomReserveResponse();
        BeanUtils.copyProperties(meetingRoomReserve,meetingRoomReserveResponse);
        if (meetingRoomReserve.getReserveUser() != null){
            meetingRoomReserveResponse.setUserId(meetingRoomReserve.getReserveUser().getId());
        }
        return meetingRoomReserveResponse;
    }
}
