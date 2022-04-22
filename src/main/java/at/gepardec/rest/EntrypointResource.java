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
    @Path("/trace")
    @Produces(MediaType.TEXT_PLAIN)
    public String callRandomServices() throws InterruptedException {
        Thread.sleep(idletime);
        if(count < 10) {
            Log.info("Calling Random service #" + ++count);
            return randomCallService.callRandomService();
        }
        Log.info("Stopping RandomCallService...");
        return "Random CallService stopped...";



    }

}
