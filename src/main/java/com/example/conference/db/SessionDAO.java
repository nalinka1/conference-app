package com.example.conference.db;

import com.example.conference.core.Session;
import org.jdbi.v3.sqlobject.config.RegisterRowMapper;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

@RegisterRowMapper(SessionMapper.class)
public interface SessionDAO {

    @SqlUpdate("INSERT INTO sessions (title, description, speaker_name, session_date, time_slot_id) VALUES (:title, :description, :speakerName, :sessionDate, :timeSlotId)")
    @GetGeneratedKeys
    int insert(@BindBean Session session);
}
