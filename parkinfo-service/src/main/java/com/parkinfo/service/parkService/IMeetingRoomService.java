package com.parkinfo.service.parkService;

import com.parkinfo.common.Result;
import com.parkinfo.request.parkService.SearchMeetingRoomRequest;
import com.parkinfo.response.parkService.MeetingRoomResponse;
import org.springframework.data.domain.Page;

public interface IMeetingRoomService {
    /**
     * 分页获取会议室
     * @param request
     * @return
     */
    Result<Page<MeetingRoomResponse>> searchMeetingRoom(SearchMeetingRoomRequest request);
}
