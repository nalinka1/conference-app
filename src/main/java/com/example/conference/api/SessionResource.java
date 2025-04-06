package com.example.conference.api;

import com.example.conference.core.Session;
import com.example.conference.core.User;
import com.example.conference.db.SessionDAO;
import io.dropwizard.auth.Auth;
import jakarta.validation.Valid;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Path("/sessions")
@Produces(MediaType.APPLICATION_JSON)
public class SessionResource {
    private final SessionDAO sessionDAO;
    private static final Logger logger = LoggerFactory.getLogger(SessionResource.class);

    public SessionResource(SessionDAO sessionDAO) {
        this.sessionDAO = sessionDAO;
    }

    @POST
    public Response create(@Valid Session session,  @Auth User user) {
        logger.info("Received a request to create a session with title: {}", session.getTitle());

        try {
            int id = sessionDAO.insert(session);
            session.setId(id);
            logger.info("Successfully created session with ID: {}", id);
            return Response.status(Response.Status.CREATED).entity(session).build();
        } catch (Exception e) {
            logger.error("Error creating session", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error creating session")
                    .build();
        }
    }

    @GET
    @Path("/{id}")
    public Session getSession(@PathParam("id") int id,  @Auth User user) {
        logger.info("Received a request to get session with ID: {}", id);
        try {
            Session session = sessionDAO.findById(id);
            if (session == null) {
                logger.warn("Session with ID: {} not found", id);
                return null;
            }
            return session;
        } catch (Exception e) {
            logger.error("Error finding session with ID: {}", id, e);
            return null;
        }
    }

    @GET
    public List<Session> getAllSessions(@Auth User user) {
        logger.info("Received a request to get all sessions");
        try {
            List<Session> sessions = sessionDAO.findAll();
            logger.info("Successfully retrieved {} sessions", sessions.size());
            return sessions;
        } catch (Exception e) {
            logger.error("Error retrieving all sessions", e);
            return null;
        }
    }

    @PUT
    @Path("/{id}")
    public Response updateSession(@PathParam("id") int id, @Valid Session session,  @Auth User user) {
        logger.info("Received a request to update session with ID: {}", id);

        try {
            session.setId(id);
            sessionDAO.update(session);
            logger.info("Successfully updated session with ID: {}", id);
            return Response.ok(session).build();
        } catch (Exception e) {
            logger.error("Error updating session with ID: {}", id, e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error updating session")
                    .build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deleteSession(@PathParam("id") int id,  @Auth User user) {
        logger.info("Received a request to delete session with ID: {}", id);
        try {
            sessionDAO.delete(id);
            logger.info("Successfully deleted session with ID: {}", id);
            return Response.noContent().build();
        } catch (Exception e) {
            logger.error("Error finding session with ID: {}", id, e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error finding session")
                    .build();
        }
    }
}
