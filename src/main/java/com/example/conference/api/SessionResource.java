package com.example.conference.api;

import com.example.conference.core.Session;
import com.example.conference.db.SessionDAO;
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

import java.util.List;

@Path("/sessions")
@Produces(MediaType.APPLICATION_JSON)
public class SessionResource {
    private final SessionDAO sessionDAO;

    public SessionResource(SessionDAO sessionDAO) {
        this.sessionDAO = sessionDAO;
    }

    @POST
    public Response create(@Valid Session session) {
        int id = sessionDAO.insert(session);
        session.setId(id);
        return Response.status(Response.Status.CREATED)
                .entity(session)
                .build();

    }

    @GET
    @Path("/{id}")
    public Session getSession(@PathParam("id") int id) {
        return sessionDAO.findById(id);
    }

    @GET
    public List<Session> getAllSessions() {
        return sessionDAO.findAll();
    }

    @PUT
    @Path("/{id}")
    public Response updateSession(@PathParam("id") int id, @Valid Session session) {
        session.setId(id);
        sessionDAO.update(session);
        return Response.ok(session).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteSession(@PathParam("id") int id) {
        sessionDAO.delete(id);
        return Response.noContent().build();
    }
}
