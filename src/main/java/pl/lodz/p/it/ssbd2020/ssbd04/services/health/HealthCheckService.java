package pl.lodz.p.it.ssbd2020.ssbd04.services.health;

import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Liveness;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;


@ApplicationScoped
@Liveness
public class HealthCheckService implements HealthCheck {

    @Inject
    private State state;

    public HealthCheckResponse call() {
        if (state.getState()) return HealthCheckResponse.named("ssbd04-tua").up().build();
        return HealthCheckResponse.named("ssbd04-tua").down().build();
    }
}
