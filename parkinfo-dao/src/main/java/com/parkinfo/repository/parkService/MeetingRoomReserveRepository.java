package com.parkinfo.repository.parkService;

import com.parkinfo.entity.parkService.meetingRoom.MeetingRoomReserve;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface MeetingRoomReserveRepository extends JpaRepository<MeetingRoomReserve, String> {

    @Query(nativeQuery = true, value = "SELECT count(*) FROM c_ser_meeting_room_reserve re WHERE re.meeting_room_id = ? 1 AND re.`delete` = 0 AND re.available = 1\n" +
            "AND (\n" +
            "\t(date_format(? 2, '%Y-%m-%d %H:%i:%s') BETWEEN date_format(re.start_time,'%Y-%m-%d %H:%i:%s')\n" +
            "\t\tAND date_format(re.end_time,'%Y-%m-%d %H:%i:%s'))\n" +
            "\tOR (\n" +
            "\t\tdate_format(? 3, '%Y-%m-%d %H:%i:%s') BETWEEN date_format(re.start_time,'%Y-%m-%d %H:%i:%s')\n" +
            "\t\tAND date_format(re.end_time,'%Y-%m-%d %H:%i:%s')\n" +
            "\t)\n" +
            "\tOR (\n" +
            "\t\tdate_format(re.end_time,'%Y-%m-%d %H:%i:%s') BETWEEN date_format(? 2, '%Y-%m-%d %H:%i:%s')\n" +
            "\t\tAND date_format(? 3, '%Y-%m-%d %H:%i:%s')\n" +
            "\t)\n" +
            ")")
    Integer findMeetingRoomReserve(String roomId, Date startTime, Date endTime);

    List<MeetingRoomReserve> findAllByDeleteIsFalseAndAvailableIsTrueAndStartTimeBetweenAndMeetingRoom_Id(Date nowDay, Date nextDay, String roomId);
}
