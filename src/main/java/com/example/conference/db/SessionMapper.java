package com.example.conference.db;

import com.example.conference.core.Session;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SessionMapper implements RowMapper<Session> {
    @Override
    public Session map(ResultSet rs, StatementContext ctx) throws SQLException {
        Session session = new Session();
        session.setId(rs.getInt("id"));
        session.setTitle(rs.getString("title"));
        session.setDescription(rs.getString("description"));
        session.setSpeakerName(rs.getString("speaker_name"));
        session.setSessionDate(rs.getDate("session_date"));
        session.setTimeSlotId(rs.getInt("time_slot_id"));
        return session;
    }
}
