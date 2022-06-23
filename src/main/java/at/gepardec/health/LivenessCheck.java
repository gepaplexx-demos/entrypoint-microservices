package at.gepardec.health;

import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Liveness;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@Liveness
@ApplicationScoped
public class LivenessCheck implements HealthCheck {

    @Inject
    Logger Log;

    @Override
    public HealthCheckResponse call() {
        Log.info("LivenessCheck - ");
        return HealthCheckResponse.named("EntrypointService").status(true).build();
    }
}
