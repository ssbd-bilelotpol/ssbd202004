package pl.lodz.p.it.ssbd2020.ssbd04.mok.entities.access_levels;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.io.Serializable;

/**
 * Klasa encyjna reprezentująca poziom dostępu administratora
 */
@Entity
@DiscriminatorValue("admin")
public class AdminAccessLevel extends AccountAccessLevel implements Serializable { }
