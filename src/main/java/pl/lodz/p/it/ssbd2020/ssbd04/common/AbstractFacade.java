package pl.lodz.p.it.ssbd2020.ssbd04.common;

import org.hibernate.exception.ConstraintViolationException;
import pl.lodz.p.it.ssbd2020.ssbd04.exceptions.AppBaseException;

import javax.annotation.security.DenyAll;
import javax.persistence.EntityManager;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * Klasa abstrakcyjna definiująca główne operacje wykonywane na encjach
 * przez zarządcę encji w kontekście trwałości.
 * Wymaga zdefiniowania własnego zarządcy encji z definicją jednostki trwałości.
 *
 * @param <T> klasa encyjna
 */
@DenyAll
public abstract class AbstractFacade<T> {

    private final Class<T> entityClass;

    public AbstractFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected abstract EntityManager getEntityManager();

    /**
     * Utrwala encję w bazie danych.
     *
     * @param entity obiekt encji.
     * @throws AppBaseException gdy zostaną naruszone warunki integralności lub wystapi inny błąd związany z bazą danych.
     */
    public void create(T entity) throws AppBaseException {
        try {
            getEntityManager().persist(entity);
            getEntityManager().flush();
        } catch (PersistenceException e) {
            if (!(e.getCause() instanceof ConstraintViolationException)) {
                throw AppBaseException.databaseOperation(e);
            }
            throw (ConstraintViolationException) e.getCause();
        }
    }

    /**
     * Aktualizuje dane encji w bazie danych.
     *
     * @param entity obiekt encji.
     * @throws AppBaseException gdy zostaną naruszone warunki integralności, błąd blokady optymistycznej lub wystąpi inny błąd związany z bazą danych.
     */
    public void edit(T entity) throws AppBaseException {
        try {
            getEntityManager().merge(entity);
            getEntityManager().flush();
        } catch (OptimisticLockException e) {
            throw AppBaseException.optimisticLock(e);
        } catch (PersistenceException e) {
            if (!(e.getCause() instanceof ConstraintViolationException)) {
                throw AppBaseException.databaseOperation(e);
            }
            throw (ConstraintViolationException) e.getCause();
        }
    }

    /**
     * Usuwa encję z bazy danych.
     *
     * @param entity obiekt encji.
     * @throws AppBaseException gdy zostaną naruszone warunki integralności lub wystąpi inny błąd związany z bazą danych.
     */
    public void remove(T entity) throws AppBaseException {
        try {
            getEntityManager().remove(getEntityManager().merge(entity));
            getEntityManager().flush();
        } catch (PersistenceException e) {
            if (!(e.getCause() instanceof ConstraintViolationException)) {
                throw AppBaseException.databaseOperation(e);
            }
            throw (ConstraintViolationException) e.getCause();
        }
    }

    /**
     * Szuka encji w bazie danych na podstawie obiektu klucza głównego.
     *
     * @param id obiekt klucza głównego
     * @return obiekt encji
     */
    public T find(Object id) throws AppBaseException {
        try {
            return getEntityManager().find(entityClass, id);
        } catch (PersistenceException e) {
            throw AppBaseException.databaseOperation(e);
        }
    }

    /**
     * Pobiera listę wszystkich encji znajdujących się w bazie danych.
     *
     * @return lista wszystkich encji.
     * @throws AppBaseException gdy wystapi błąd związany z bazą danych.
     */
    public List<T> findAll() throws AppBaseException {
        try {
            CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
            cq.select(cq.from(entityClass));
            return getEntityManager().createQuery(cq).getResultList();
        } catch (PersistenceException e) {
            throw AppBaseException.databaseOperation(e);
        }
    }

    /**
     * Pobiera liczbę wszystkich encji danego typu znajdujących się w bazie danych
     *
     * @return liczba wszystkich encji danego typu.
     * @throws AppBaseException gdy wystapi błąd związany z bazą danych.
     */
    public int count() throws AppBaseException {
        try {
            CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
            Root<T> rt = cq.from(entityClass);
            cq.select(getEntityManager().getCriteriaBuilder().count(rt));
            Query q = getEntityManager().createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } catch (PersistenceException e) {
            throw AppBaseException.databaseOperation(e);
        }
    }

}
