package pl.lodz.p.it.ssbd2020.ssbd04.controllers;

import pl.lodz.p.it.ssbd2020.ssbd04.common.AbstractEndpoint;
import pl.lodz.p.it.ssbd2020.ssbd04.common.Config;
import pl.lodz.p.it.ssbd2020.ssbd04.exceptions.AppBaseException;

import javax.ejb.EJBTransactionRolledbackException;
import javax.inject.Inject;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class AbstractController {
    @Inject
    private Config config;

    private final Logger LOGGER = Logger.getLogger(getClass().getName());

    protected <T> T repeat(AbstractEndpoint endpoint, AppSupplier<T> supplier) throws AppBaseException {
        int retryCount = 0;
        boolean rollback;
        T result = null;
        do {
            try {
                if (retryCount > 0) {
                    LOGGER.log(Level.INFO, "Method from class '{0}' is being retried by thread {1}",
                            new Object[]{
                                    endpoint.getClass().getSimpleName(),
                                    Thread.currentThread().getName()
                            });
                }
                result = supplier.execute();
                rollback = endpoint.isLastTransactionRollback();
            } catch (EJBTransactionRolledbackException e) {
                rollback = true;
            }
        } while (rollback && ++retryCount <= config.getTransactionRetryThreshold());

        return result;
    }

    protected void repeat(AbstractEndpoint endpoint, AppProcedure procedure) throws AppBaseException {
        int retryCount = 0;
        boolean rollback;
        do {
            try {
                if (retryCount > 0) {
                    LOGGER.log(Level.WARNING, "Method from class '{0}' is being retried by thread {1}",
                            new Object[]{
                                    endpoint.getClass().getName(),
                                    Thread.currentThread().getName()
                            });
                }
                procedure.execute();
                rollback = endpoint.isLastTransactionRollback();
            } catch (EJBTransactionRolledbackException e) {
                rollback = true;
            }
        } while (rollback && ++retryCount <= config.getTransactionRetryThreshold());
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
