package at.gepardec.service;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.RestClientBuilder;
import org.jboss.logging.Logger;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import java.net.URI;
import java.util.Random;

@Dependent
public class RandomCallService {

    @Inject
    Logger Log;

    private int countServices;

    Random rand;

    CollectServices serviceCollection;

    public RandomCallService(CollectServices serviceCollection, @ConfigProperty(name = "microservices.seed") Long seed) {
        this.serviceCollection = serviceCollection;
        initCallService();
        rand = new Random(seed);
    }

    public void initCallService() {
        countServices = serviceCollection.getServiceURLs().size();
    }

    public String callRandomService() {
        return getRandomService().getResource();
    }

    public MiddlemanService getRandomService() {
        String service = serviceCollection.getServiceURLs().get(getRandom());
        Log.info("Service: " + service);
        return RestClientBuilder.newBuilder().baseUri(URI.create(service)).build(MiddlemanService.class);
    }

    public int getRandom() {
        return rand.nextInt(countServices - 1) + 1;                                            // returns values of interval [1 ; #services-1]
    }


}