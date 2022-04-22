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

    private int countPorts;

    Random rand = new Random();

    CollectServicePorts portCollection;

    public RandomCallService(CollectServicePorts portCollection) {
        this.portCollection = portCollection;
        initCallService();
    }

    public void initCallService() {
        countPorts = portCollection.getPortList().size();
    }

    public String callRandomService() {
        return getRandomService().getResource();
    }

    public MiddlemanService getRandomService() {
        int port = portCollection.getPortList().get(getRandomPort());
        return RestClientBuilder.newBuilder().baseUri(URI.create("http://localhost:" + port)).build(MiddlemanService.class);
    }

    public int getRandomPort() {
        return rand.nextInt(countPorts);
    }   // Todo: Hash/Seed mitgeben, damit Random Durchlauf nochmal rekonstruiert werden kann.


}