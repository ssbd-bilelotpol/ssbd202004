package pl.lodz.p.it.ssbd2020.ssbd04.mol.entities;

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
     * DELAYED - lot został opóźniony.
     */
    DELAYED,
    /**
     * CANCELLED - lot został anulowany i pasażerowie otrzymali zwrot środków.
     */
    CANCELLED
}
