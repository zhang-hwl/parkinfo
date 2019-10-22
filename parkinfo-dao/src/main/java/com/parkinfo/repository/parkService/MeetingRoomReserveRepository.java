package com.parkinfo.repository.parkService;

import com.parkinfo.entity.parkService.meetingRoom.MeetingRoomReserve;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface MeetingRoomReserveRepository extends JpaRepository<MeetingRoomReserve, String> {

    @Query(nativeQuery = true, value = "SELECT\n" +
            "\tcount(id)\n" +
            "FROM\n" +
            "\tc_ser_meeting_room_reserve re\n" +
            "WHERE\n" +
            "\tre.meeting_room_id = ?1\n" +
            "AND re.`delete` = 0\n" +
            "AND re.available = 1\n" +
            "AND (\n" +
            "\t(\n" +
            "\t\tdate_format(\n" +
            "\t\t\t?2,\n" +
            "\t\t\t'%Y-%m-%d %H:%i:%s'\n" +
            "\t\t) > date_format(\n" +
            "\t\t\tre.start_time,\n" +
            "\t\t\t'%Y-%m-%d %H:%i:%s'\n" +
            "\t\t)\n" +
            "\t\tAND date_format(\n" +
            "\t\t\t?2,\n" +
            "\t\t\t'%Y-%m-%d %H:%i:%s'\n" +
            "\t\t) < date_format(\n" +
            "\t\t\tre.end_time,\n" +
            "\t\t\t'%Y-%m-%d %H:%i:%s'\n" +
            "\t\t)\n" +
            "\t)\n" +
            "\tOR (\n" +
            "\t\tdate_format(\n" +
            "\t\t\t?3,\n" +
            "\t\t\t'%Y-%m-%d %H:%i:%s'\n" +
            "\t\t) > date_format(\n" +
            "\t\t\tre.start_time,\n" +
            "\t\t\t'%Y-%m-%d %H:%i:%s'\n" +
            "\t\t)\n" +
            "\t\tAND date_format(\n" +
            "\t\t\t?3,\n" +
            "\t\t\t'%Y-%m-%d %H:%i:%s'\n" +
            "\t\t) < date_format(\n" +
            "\t\t\tre.end_time,\n" +
            "\t\t\t'%Y-%m-%d %H:%i:%s'\n" +
            "\t\t)\n" +
            "\t)\n" +
            "\tOR (\n" +
            "\t\tdate_format(\n" +
            "\t\t\tre.end_time,\n" +
            "\t\t\t'%Y-%m-%d %H:%i:%s'\n" +
            "\t\t) > date_format(\n" +
            "\t\t\t?2,\n" +
            "\t\t\t'%Y-%m-%d %H:%i:%s'\n" +
            "\t\t)\n" +
            "\t\tAND date_format(\n" +
            "\t\t\tre.end_time,\n" +
            "\t\t\t'%Y-%m-%d %H:%i:%s'\n" +
            "\t\t) < date_format(\n" +
            "\t\t\t?3,\n" +
            "\t\t\t'%Y-%m-%d %H:%i:%s'\n" +
            "\t\t)\n" +
            "\t)\n" +
            ")")
    Integer findMeetingRoomReserve(String roomId, Date startTime, Date endTime);

    List<MeetingRoomReserve> findAllByDeleteIsFalseAndAvailableIsTrueAndStartTimeBetweenAndMeetingRoom_Id(Date nowDay, Date nextDay, String roomId);
}
