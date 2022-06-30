package at.gepardec.rest;

import at.gepardec.service.RandomCallService;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.metrics.MetricUnits;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Timed;
import org.jboss.logging.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Random;

@Path("/")
@ApplicationScoped
public class EntrypointRandomResource {

    @Inject
    Logger Log;

    @ConfigProperty(name = "microservices.idletime")
    int idletime;

    @ConfigProperty(name = "microservices.seed")
    Long seed;

    @ConfigProperty(name = "microservices.urls")
    List<String> serviceUrls;

    Random random;

    int count = 0;

    @PostConstruct
    void initRandom() {
        random = new Random(seed);
    }

    @GET
    @Path("/startRandom/{ttl}")
    @Produces(MediaType.TEXT_PLAIN)
    @Counted(name = "performedCalls", description = "How often the service has been called.")
    @Timed(name = "callsTimer", description = "A measure of how long it takes to perform the complete call.", unit = MetricUnits.MILLISECONDS)
    public void startRandomCallService(int ttl)
            throws InterruptedException {

        Log.info(" Sleeping for " + idletime + " ms");
        Thread.sleep(idletime);
        callRandomService(ttl);
    }

    public void callRandomService(int ttl) {
        if (ttl > 0) {
            Log.info("Calling Random service #" + ++count);
            RandomCallService randomCallService = new RandomCallService(serviceUrls, random);
            randomCallService.callRandomService(ttl);
        }
        Log.info("Stopping RandomCallService...");
    }

}
