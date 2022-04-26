package at.gepardec.service;

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

    Random rand = new Random();

    CollectServices serviceCollection;

    public RandomCallService(CollectServices serviceCollection) {
        this.serviceCollection = serviceCollection;
        initCallService();
    }

    public void initCallService() {
        countServices = serviceCollection.getServiceURLList().size();
    }

    public String callRandomService() {
        return getRandomService().getResource();
    }

    public MiddlemanService getRandomService() {
        String service = serviceCollection.getServiceURLList().get(getRandom());
        Log.info("getRandomService: service: " + service);
        return RestClientBuilder.newBuilder().baseUri(URI.create(service)).build(MiddlemanService.class);
    }

    public int getRandom() {
        Log.info("getRandom(): countServiices: " + countServices);
        return rand.nextInt(countServices -1) + 1;
    }   // Todo: Hash/Seed mitgeben, damit Random Durchlauf nochmal rekonstruiert werden kann.


}