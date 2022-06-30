package at.gepardec.service;

import org.eclipse.microprofile.rest.client.RestClientBuilder;
import org.jboss.logging.Logger;

import java.net.URI;
import java.util.List;


public class OrderedCallService {

    Logger Log = Logger.getLogger(OrderedCallService.class);

    List<String> serviceCollection;

    int idletime;

    public OrderedCallService(List<String> serviceCollection, int idletime) {
        this.serviceCollection = serviceCollection;
        this.idletime = idletime;
    }

    public void callNextService(String orderSequence) {
        getService(getUrl()).getNextResourceBySequence(orderSequence);
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