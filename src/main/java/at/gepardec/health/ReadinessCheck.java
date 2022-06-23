package at.gepardec.health;


import com.sun.management.OperatingSystemMXBean;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Liveness;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.lang.management.ManagementFactory;

@Liveness
@ApplicationScoped
public class ReadinessCheck implements HealthCheck {

    @Inject
    Logger Log;

    OperatingSystemMXBean osBean = ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class);

    @Override
    public HealthCheckResponse call() {
        double cpuLoad = osBean.getProcessCpuLoad();
        Log.info("ReadinessCheck - CpuUsage: " + String.format("%.2f", cpuLoad));
        boolean ready = cpuLoad < 0.8;
        return HealthCheckResponse.named("EntrypointService").status(ready).build();
    }
}
