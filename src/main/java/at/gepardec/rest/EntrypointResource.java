package at.gepardec.rest;

import at.gepardec.service.OrderedCallService;
import at.gepardec.service.RandomCallService;
import at.gepardec.service.ServiceCollector;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.metrics.MetricUnits;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Timed;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.UUID;

@Path("/")
@ApplicationScoped
public class EntrypointResource {

    @Inject
    Logger Log;

    @ConfigProperty(name = "microservices.idletime")
    int idletime;

    @ConfigProperty(name = "microservices.sequence")
    String orderSequence;

    int count = 0;

    @Inject
    ServiceCollector serviceCollector;

    @GET
    @Path("/start/{sequence}")
    @Produces(MediaType.TEXT_PLAIN)
    @Counted(name = "performedCalls", description = "How often the service has been called.")
    @Timed(name = "callsTimer", description = "A measure of how long it takes to perform the complete call.", unit = MetricUnits.MILLISECONDS)
    public void startRandomCallService(String sequence) {

        UUID transactionID = UUID.randomUUID();
        Log.info("[" + transactionID.toString() + "]" + " Sleeping for " + idletime + " ms");
        callNextService(orderSequence, transactionID);
    }

    public void callNextService(String orderSequence, UUID transactionID) {
        if (orderSequence.length() > 0) {
            Log.info("TransactionID: " + transactionID.toString() + " - Calling Random service #" + ++count);
            OrderedCallService orderedCallService = new OrderedCallService(serviceCollector.getServiceURLs());
            orderedCallService.callNextService(orderSequence, transactionID);
        }
        Log.info("[" + transactionID.toString() + "]" + " Stopping RandomCallService...");
    }

}
