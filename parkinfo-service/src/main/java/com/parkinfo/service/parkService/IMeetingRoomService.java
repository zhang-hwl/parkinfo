package com.parkinfo.service.parkService;

import com.parkinfo.common.Result;
import com.parkinfo.request.parkService.*;
import com.parkinfo.response.parkService.MeetingRoomReserveResponse;
import com.parkinfo.response.parkService.MeetingRoomResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IMeetingRoomService {
    /**
     * 分页获取会议室
     * @param request
     * @return
     */
    Result<Page<MeetingRoomResponse>> searchMeetingRoom(SearchMeetingRoomRequest request);

    /**
     * 预约会议室
     * @param request
     * @return
     */
    Result<String> reverseMeetingRoom(ReverseMeetingRoomRequest request);

    /**
     *新增会议室
     * @param request
     * @return
     */
    Result<String> addMeetingRoom(AddMeetingRoomRequest request);

    /**
     * 编辑会议室
     * @param request
     * @return
     */
    Result<String> editMeetingRoom(EditMeetingRoomRequest request);

    /**
     * 删除园区会议室
     * @param id
     * @return
     */
    Result<String> deleteMeetingRoom(String id);

    /**
     * 查询会议室当天的预定记录
     * @param request
     * @return
     */
    Result<List<MeetingRoomReserveResponse>> searchMeetingRoomReserve(SearchMeetingRoomReverseRequest request);
}
