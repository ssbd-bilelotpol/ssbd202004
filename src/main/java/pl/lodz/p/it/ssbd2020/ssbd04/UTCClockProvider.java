package pl.lodz.p.it.ssbd2020.ssbd04;

import javax.enterprise.context.ApplicationScoped;
import javax.validation.ClockProvider;
import java.time.Clock;

@ApplicationScoped
public class UTCClockProvider implements ClockProvider {
    private Clock clock = Clock.systemUTC();

    @Override
    public Clock getClock() {
        return clock;
    }
}
