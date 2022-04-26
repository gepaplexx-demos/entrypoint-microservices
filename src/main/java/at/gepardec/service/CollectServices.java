package at.gepardec.service;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.logging.Logger;

import javax.enterprise.context.Dependent;
import java.util.ArrayList;
import java.util.List;

@Dependent
public class CollectServices {

    Logger Log;

    private List<String> serviceURLList = new ArrayList<>();

    private String ms_env_base;
    private String ms_env_variables;

    public CollectServices(
            @ConfigProperty(name = "microservices.env.base")
                    String base,
            @ConfigProperty(name ="microservices.env.variables")
                    String variables,
            Logger Log) {
        this.ms_env_base = base;
        this.ms_env_variables = variables;
        this.Log = Log;
        initServices();
        Log.info(getEnvVariables());
        logServiceURLs();
    }

    public void initServices() {
        String env;
        for (String s : ms_env_variables.split(",")) {
            env = System.getenv(ms_env_base + s);       // not working; getting null;
            Log.info("result for " + ms_env_base + s + ": " + env);
            serviceURLList.add(env);
        }
    }

    public List<String> getServiceURLList() {
        return serviceURLList;
    }

    public String getEnvVariables() {
        return "Environment-Variable: " + ms_env_base + ms_env_variables;
    }

    public void logServiceURLs() {
        Log.info("ServiceURLs: ");
        for(String s : getServiceURLList()) {
            Log.info(s);
        }
    }
}
