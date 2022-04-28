package at.gepardec.rest;

import at.gepardec.service.RandomCallService;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/")
@ApplicationScoped
public class EntrypointResource {

    @ConfigProperty(name = "microservices.idletime")
    int idletime;

    int count = 0;

    @Inject
    Logger Log;

    @Inject
    RandomCallService randomCallService;

    @GET
    @Path("/start/{ttl}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response callRandomServices(int ttl) throws InterruptedException {
        return makeRequest(ttl);
    }

    @GET
    @Path("/start")
    @Produces(MediaType.TEXT_PLAIN)
    public Response callRandomService(@QueryParam("ttl") int ttl) throws InterruptedException {
        return makeRequest(ttl);
    }

    public Response makeRequest(int ttl) throws InterruptedException {
        Thread.sleep(idletime);
        if (ttl > 0) {
            Log.info("Calling Random service #" + ++count);
            return randomCallService.callRandomService(ttl);
        }
        Log.info("Stopping RandomCallService...");
        return Response.status(200).entity("Random Call-Service stopped...").build();
    }

}
