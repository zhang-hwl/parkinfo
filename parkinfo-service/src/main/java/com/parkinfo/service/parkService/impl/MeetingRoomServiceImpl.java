package com.parkinfo.service.parkService.impl;

import com.google.common.collect.Lists;
import com.parkinfo.common.Result;
import com.parkinfo.dto.ParkUserDTO;
import com.parkinfo.entity.parkService.meetingRoom.MeetingRoom;
import com.parkinfo.entity.parkService.meetingRoom.MeetingRoomReserve;
import com.parkinfo.entity.userConfig.ParkInfo;
import com.parkinfo.repository.parkService.MeetingRoomRepository;
import com.parkinfo.request.parkService.SearchMeetingRoomRequest;
import com.parkinfo.response.parkService.MeetingRoomReserveResponse;
import com.parkinfo.response.parkService.MeetingRoomResponse;
import com.parkinfo.service.parkService.IMeetingRoomService;
import com.parkinfo.token.TokenUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import javax.xml.crypto.Data;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MeetingRoomServiceImpl implements IMeetingRoomService {
    @Autowired
    private MeetingRoomRepository meetingRoomRepository;
    @Autowired
    private TokenUtils tokenUtils;

    @Override
    public Result<Page<MeetingRoomResponse>> searchMeetingRoom(SearchMeetingRoomRequest request) {
        Pageable pageable = PageRequest.of(request.getPageNum(),request.getPageSize(), Sort.Direction.DESC,"createTime");
        //todo 登录未做
//        ParkUserDTO loginUser = tokenUtils.getLoginUser();
        MeetingRoom exampleData = new MeetingRoom();
        if (StringUtils.isNotBlank(request.getMeetingRoomId())){
            exampleData.setId(request.getMeetingRoomId());
        }
//        ParkInfo parkInfo = new ParkInfo();
//        parkInfo.setId(loginUser.getCurrentParkId());
//        exampleData.setParkInfo(parkInfo);
        Example<MeetingRoom> example = Example.of(exampleData);
        Page<MeetingRoom> meetingRooms = meetingRoomRepository.findAll(example, pageable);
        Page<MeetingRoomResponse> responses = this.convertMeetingRoomResponse(meetingRooms);
        return Result.<Page<MeetingRoomResponse>>builder().success().data(responses).build();
    }

    private Page<MeetingRoomResponse> convertMeetingRoomResponse(Page<MeetingRoom> meetingRooms) {
        List<MeetingRoomResponse> response = Lists.newArrayList();
        meetingRooms.getContent().forEach(meetingRoom -> {
            MeetingRoomResponse meetingRoomResponse = new MeetingRoomResponse();
            BeanUtils.copyProperties(meetingRoom,meetingRoomResponse);
            //todo 需要昵称
            meetingRoomResponse.setUserName(meetingRoom.getParkUser().getAccount());
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
