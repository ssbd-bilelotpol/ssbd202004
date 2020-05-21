package pl.lodz.p.it.ssbd2020.ssbd04.controllers;

import pl.lodz.p.it.ssbd2020.ssbd04.common.Config;
import pl.lodz.p.it.ssbd2020.ssbd04.common.TransactionStarter;
import pl.lodz.p.it.ssbd2020.ssbd04.exceptions.AppBaseException;

import javax.ejb.EJBTransactionRolledbackException;
import javax.inject.Inject;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Definiuje wspólne operacje dla wszystkich kontrolerów, zajmujących się obsługą usługi REST.
 * Umożliwai ponawianie transakcji.
 */
public abstract class AbstractController {
    @Inject
    private Config config;

    private final Logger LOGGER = Logger.getLogger(getClass().getName());

    protected <T> T repeat(TransactionStarter transactionStarter, AppSupplier<T> supplier) throws AppBaseException {
        int retryCount = 0;
        boolean rollback;
        T result = null;
        do {
            try {
                if (retryCount > 0) {
                    LOGGER.log(Level.WARNING, "Method from class: {0} is being retried by thread: {1}",
                            new Object[]{
                                    transactionStarter.getClass().getSimpleName(),
                                    Thread.currentThread().getName()
                            });
                }
                result = supplier.execute();
                rollback = transactionStarter.isLastTransactionRollback();
            } catch (EJBTransactionRolledbackException e) {
                rollback = true;
            }
        } while (rollback && ++retryCount <= config.getTransactionRetryThreshold());

        if (retryCount > config.getTransactionRetryThreshold()) {
            throw AppBaseException.actionFailed();
        }

        return result;
    }

    protected void repeat(TransactionStarter transactionStarter, AppProcedure procedure) throws AppBaseException {
        int retryCount = 0;
        boolean rollback;
        do {
            try {
                if (retryCount > 0) {
                    LOGGER.log(Level.WARNING, "Method from class: {0} is being retried by thread: {1}",
                            new Object[]{
                                    transactionStarter.getClass().getName(),
                                    Thread.currentThread().getName()
                            });
                }
                procedure.execute();
                rollback = transactionStarter.isLastTransactionRollback();
            } catch (EJBTransactionRolledbackException e) {
                rollback = true;
            }
        } while (rollback && ++retryCount <= config.getTransactionRetryThreshold());

        if (retryCount > config.getTransactionRetryThreshold()) {
            throw AppBaseException.actionFailed();
        }
    }

    @FunctionalInterface
    public interface AppProcedure {
        void execute() throws AppBaseException;
    }

    @FunctionalInterface
    public interface AppSupplier<T> {
        T execute() throws AppBaseException;
    }
}
