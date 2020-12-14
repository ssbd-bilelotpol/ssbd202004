package pl.lodz.p.it.ssbd2020.ssbd04.services.health;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import java.io.Serializable;

@ApplicationScoped
public class State implements Serializable {

    private boolean state;

    public boolean getState() {
        return state;
    }

    public void switchState() {
        state = !state;
    }

    @PostConstruct
    private void init(){
        state = true;
    }
}
