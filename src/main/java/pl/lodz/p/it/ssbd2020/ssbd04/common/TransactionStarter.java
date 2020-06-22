package pl.lodz.p.it.ssbd2020.ssbd04.common;

/**
 * Interfejs oznaczający komponent EJB rozpoczynający transakcje.
 */
public interface TransactionStarter {

    /**
     * Po zakończeniu transakcji, umożliwia odpytanie o jej status.
     *
     * @return true, jeśli transakcja została odwołana, w przeciwnym przypadku false.
     */
    boolean isLastTransactionRollback();
}
