package com.parkinfo.repository.parkService;

import com.parkinfo.entity.parkService.meetingRoom.MeetingRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MeetingRoomRepository extends JpaRepository<MeetingRoom,String> {
    Optional<MeetingRoom> findFirstByDeleteIsFalseAndAvailableIsTrueAndId(String roomId);
}
