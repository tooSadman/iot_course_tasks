package com.ua.lviv.iot.server;

import com.ua.lviv.iot.spares.Spares;
import com.ua.lviv.iot.persistence.dao.SparesDao;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.Serializable;

@Path("/spares")
@SessionScoped
public class SparesService implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private SparesDao sparesDao;

  //  private static Map<Integer, Spares> spares = new HashMap<>();

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Spares getSpare(@PathParam("id") Integer id) {
        return sparesDao.findById(id);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateSpare(Spares spare) {
        sparesDao.update(spare);
        return Response.ok().build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createSpare(Spares spare) {
        sparesDao.persist(spare);
        return Response.ok().build();
    }

    @DELETE
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteSpare(@PathParam("id") Integer id) {
        sparesDao.delete(id);
        return Response.ok().build();
    }
}