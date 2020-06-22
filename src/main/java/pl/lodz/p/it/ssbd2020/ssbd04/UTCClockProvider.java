package pl.lodz.p.it.ssbd2020.ssbd04;

import javax.enterprise.context.ApplicationScoped;
import javax.validation.ClockProvider;
import java.time.Clock;

/**
 * Klasa dostarczająca zegar UTC dla walidatorów daty.
 */
@ApplicationScoped
public class UTCClockProvider implements ClockProvider {
    private Clock clock = Clock.systemUTC();

    @Override
    public Clock getClock() {
        return clock;
    }
}
