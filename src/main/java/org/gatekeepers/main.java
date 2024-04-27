package org.gatekeepers;

import java.util.HashMap;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/")
public class main {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response _main() {
        var response = new HashMap<Object, Object>();
        response.put("status", "ok");
        return Response.ok(response).build();
    }
}
