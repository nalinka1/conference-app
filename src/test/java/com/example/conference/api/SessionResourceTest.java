package com.example.conference.api;

import com.example.conference.core.Session;
import com.example.conference.core.User;
import com.example.conference.db.SessionDAO;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class SessionResourceTest {

    private SessionDAO sessionDAO;
    private SessionResource sessionResource;
    private User mockUser;

    @BeforeEach
    void setUp() {
        sessionDAO = mock(SessionDAO.class);
        sessionResource = new SessionResource(sessionDAO);
        mockUser = new User("testuser", "test");
    }

    @Test
    void testCreateSession_Success() {
        Session session = new Session();
        session.setTitle("Test Session");

        when(sessionDAO.insert(any(Session.class))).thenReturn(1);

        Response response = sessionResource.create(session, mockUser);

        assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
        Session created = (Session) response.getEntity();
        assertEquals(1, created.getId());
        assertEquals("Test Session", created.getTitle());
    }

    @Test
    void testCreateSession_Failure() {
        Session session = new Session();
        session.setTitle("Fail Session");

        when(sessionDAO.insert(any(Session.class))).thenThrow(RuntimeException.class);

        Response response = sessionResource.create(session, mockUser);

        assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());
        assertEquals("Error creating session", response.getEntity());
    }

    @Test
    void testGetSession_Found() {
        Session session = new Session();
        session.setId(1);
        session.setTitle("Existing");

        when(sessionDAO.findById(1)).thenReturn(session);

        Session result = sessionResource.getSession(1, mockUser);

        assertNotNull(result);
        assertEquals(1, result.getId());
    }

    @Test
    void testGetSession_NotFound() {
        when(sessionDAO.findById(1)).thenReturn(null);

        Session result = sessionResource.getSession(1, mockUser);

        assertNull(result);
    }

    @Test
    void testGetSession_Exception() {
        when(sessionDAO.findById(1)).thenThrow(RuntimeException.class);

        Session result = sessionResource.getSession(1, mockUser);

        assertNull(result);
    }

    @Test
    void testGetAllSessions_Success() {
        List<Session> sessions = Arrays.asList(new Session(), new Session());

        when(sessionDAO.findAll()).thenReturn(sessions);

        List<Session> result = sessionResource.getAllSessions(mockUser);

        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    void testGetAllSessions_Failure() {
        when(sessionDAO.findAll()).thenThrow(RuntimeException.class);

        List<Session> result = sessionResource.getAllSessions(mockUser);

        assertNull(result);
    }

    @Test
    void testUpdateSession_Success() {
        Session session = new Session();
        session.setTitle("Updated Title");

        Response response = sessionResource.updateSession(1, session, mockUser);

        verify(sessionDAO).update(session);
        assertEquals(1, session.getId());
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals(session, response.getEntity());
    }

    @Test
    void testUpdateSession_Failure() {
        Session session = new Session();
        session.setTitle("Update Fail");

        doThrow(RuntimeException.class).when(sessionDAO).update(any(Session.class));

        Response response = sessionResource.updateSession(1, session, mockUser);

        assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());
        assertEquals("Error updating session", response.getEntity());
    }

    @Test
    void testDeleteSession_Success() {
        Response response = sessionResource.deleteSession(1, mockUser);

        verify(sessionDAO).delete(1);
        assertEquals(Response.Status.NO_CONTENT.getStatusCode(), response.getStatus());
    }

    @Test
    void testDeleteSession_Failure() {
        doThrow(RuntimeException.class).when(sessionDAO).delete(1);

        Response response = sessionResource.deleteSession(1, mockUser);

        assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());
        assertEquals("Error finding session", response.getEntity());
    }
}
