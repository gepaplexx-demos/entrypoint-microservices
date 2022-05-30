package at.gepardec.rest;

import at.gepardec.service.RandomCallService;
import at.gepardec.service.ServiceCollector;
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
import java.util.Random;
import java.util.UUID;

@Path("/")
@ApplicationScoped
public class EntrypointRandomResource {

    @Inject
    Logger Log;

    @ConfigProperty(name = "microservices.idletime")
    int idletime;

    @ConfigProperty(name = "microservices.seed")
    Long seed;

    Random random;

    int count = 0;

    @Inject
    ServiceCollector serviceCollector;

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


        UUID transactionID = UUID.randomUUID();
        Log.info("[" + transactionID.toString() + "]" + " Sleeping for " + idletime + " ms");
        Thread.sleep(idletime);
        callRandomService(ttl, transactionID);
    }

    public void callRandomService(int ttl, UUID transactionID) {
        if (ttl > 0) {
            Log.info("TransactionID: " + transactionID.toString() + " - Calling Random service #" + ++count);
            RandomCallService randomCallService = new RandomCallService(serviceCollector.getServiceURLs(), random);
            randomCallService.callRandomService(ttl, transactionID);
        }
        Log.info("["+transactionID.toString()+"]" + " Stopping RandomCallService...");
    }

}
