package at.gepardec.service;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.logging.Logger;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import java.util.*;

@Dependent
public class ServiceCollector {

    @Inject
    Logger Log;

    @ConfigProperty(name = "microservices.env.variables")
    List<String> serviceUrls;

    public ServiceCollector() {
    }

    public List<String> getServiceURLs() {
        return List.copyOf(serviceUrls);
    }

}
