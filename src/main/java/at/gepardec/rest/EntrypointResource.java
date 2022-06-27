package at.gepardec.rest;

import at.gepardec.service.OrderedCallService;
import at.gepardec.service.ServiceCollector;
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

@Path("/")
@ApplicationScoped
public class EntrypointResource {

    @Inject
    Logger Log;

    @Inject
    ServiceCollector serviceCollector;

    @GET
    @Path("/start/{sequence}")
    @Produces(MediaType.TEXT_PLAIN)
    @Counted(name = "performedCalls", description = "How often the service has been called.")
    @Timed(name = "callsTimer", description = "A measure of how long it takes to perform the complete call.", unit = MetricUnits.MILLISECONDS)
    public void startOrderedCallService(String sequence) {
        processRequest(sequence);
    }

    public void processRequest(String orderSequence) {
        if (orderSequence.length() == 0) {
            Log.info("Abort starting of OrderedCallService due to empty sequence...");
            return;
        }
        Log.info("Starting CallService");
        OrderedCallService orderedCallService = new OrderedCallService(serviceCollector.getServiceURLs());
        orderedCallService.callNextService(orderSequence);
        Log.info("Stopping OrderedCallService...\n\n");
    }

}
