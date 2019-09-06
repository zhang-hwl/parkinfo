package com.parkinfo.repository.parkService;

import com.parkinfo.entity.parkService.meetingRoom.MeetingRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeetingRoomRepository extends JpaRepository<MeetingRoom,String> {
}
