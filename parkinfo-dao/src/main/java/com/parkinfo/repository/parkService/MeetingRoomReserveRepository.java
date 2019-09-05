package com.parkinfo.repository.parkService;

import com.parkinfo.entity.parkService.meetingRoom.MeetingRoomReserve;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeetingRoomReserveRepository extends JpaRepository<MeetingRoomReserve,String> {
}
