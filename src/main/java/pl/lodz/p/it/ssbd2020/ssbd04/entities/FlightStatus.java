package pl.lodz.p.it.ssbd2020.ssbd04.entities;

/**
 * Opisuje status lotu.
 */
public enum FlightStatus {
    /**
     * ACTIVE - lot jest aktywny i można w nim uczestniczyć.
     */
    ACTIVE,
    /**
     * INACTIVE - lot został wyłączony przez menedżera zasobów.
     */
    INACTIVE,
    /**
     * CANCELLED - lot został anulowany i pasażerowie otrzymali zwrot środków.
     */
    CANCELLED
}
