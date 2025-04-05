package com.example.conference.api;

import com.example.conference.core.Session;
import com.example.conference.db.SessionDAO;
import jakarta.validation.Valid;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

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
    public Response getSession(@PathParam("id") int id) {
        return Response.ok().build();
    }
}
