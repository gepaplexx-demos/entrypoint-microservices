package at.gepardec.service;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.logging.Logger;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@Dependent
public class CollectServicePorts {

    @Inject
    Logger Log;

    private List<Integer> portList = new ArrayList<>();

    private String ports;

    public CollectServicePorts(@ConfigProperty(name = "microservices.ports") String ports) {
        this.ports = ports;
        initPorts();
    }

    public List<Integer> getPortList() {
        return portList;
    }

    public void initPorts() {
        for (String s : ports.split(",")) {
            portList.add(Integer.parseInt(s));
        }
        printPorts();
    }

    public void printPorts() {
        System.out.println("Ports: ");
        for(int port : portList) {
            System.out.println(port);
        }
    }
}
