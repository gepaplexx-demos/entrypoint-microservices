package at.gepardec.rest;

import at.gepardec.service.RandomCallService;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/")
@ApplicationScoped
public class EntrypointResource {

    @Inject
    Logger Log;

    @ConfigProperty(name = "microservices.idletime")
    int idletime;

    int count = 0;

    @Inject
    RandomCallService randomCallService;

    @GET
    @Path("/start/{ttl}")
    @Produces(MediaType.TEXT_PLAIN)
    public void startRandomCallService(int ttl)     //Todo: annotation pathparameter?
            throws InterruptedException {

        Thread.sleep(idletime);
        callRandomService(ttl);
    }

    public void callRandomService(int ttl) {
        if (ttl > 0) {
            Log.info("Calling Random service #" + ++count);
            randomCallService.callRandomService(ttl);
        }
        Log.info("Stopping RandomCallService...");
        //return Response.status(200).entity("Random Call-Service stopped...").build();
    }

}
