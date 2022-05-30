package at.gepardec.service;

import org.eclipse.microprofile.rest.client.RestClientBuilder;
import org.jboss.logging.Logger;

import java.net.URI;
import java.util.List;
import java.util.UUID;


public class OrderedCallService {

    Logger Log = Logger.getLogger(RandomCallService.class);


    List<String> serviceCollection;

    public OrderedCallService(List<String> serviceCollection) {
        this.serviceCollection = serviceCollection;
    }

    public void callNextService(String orderSequence, UUID transactionID) {
        getService(getUrl()).getNextResourceBySequence(orderSequence, transactionID);
    }

    public MiddlemanService getService(String url) {
        Log.info("Service: " + url);
        return RestClientBuilder
                .newBuilder()
                .baseUri(URI.create(url))
                .build(MiddlemanService.class);
    }

    public String getUrl() {
        return serviceCollection.get(0);
    }

}