package com.example.conference.db;

import com.example.conference.core.Session;
import org.jdbi.v3.sqlobject.config.RegisterRowMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.List;

@RegisterRowMapper(SessionMapper.class)
public interface SessionDAO {

    @SqlUpdate("INSERT INTO sessions (title, description, speaker_name, session_date, time_slot_id) VALUES (:title, :description, :speakerName, :sessionDate, :timeSlotId)")
    @GetGeneratedKeys
    int insert(@BindBean Session session);

    @SqlQuery("SELECT * FROM sessions WHERE id = :id")
    Session findById(@Bind("id") int id);

    @SqlQuery("SELECT * FROM sessions")
    List<Session> findAll();

    @SqlUpdate("UPDATE sessions SET title = :title, description = :description, speaker_name = :speakerName, session_date = :sessionDate, time_slot_id = :timeSlotId WHERE id = :id")
    void update(@BindBean Session session);



    @SqlUpdate("DELETE FROM sessions WHERE id = :id")
    void delete(@Bind("id") int id);
}
