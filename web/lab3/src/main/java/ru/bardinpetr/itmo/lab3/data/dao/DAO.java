package ru.bardinpetr.itmo.lab3.data.dao;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.criteria.CriteriaBuilder;
import ru.bardinpetr.itmo.lab3.data.beans.EntityManagerProvider;

import java.util.List;
import java.util.Optional;

/**
 * DAO implementation for JPA
 *
 * @param <PK> Primary key type
 * @param <T>  Row type
 */
public abstract class DAO<PK, T> {

    private final EntityManager manager;
    private final CriteriaBuilder criteriaBuilder;
    private final Class<T> rowClass;

    public DAO(EntityManagerProvider entityManagerProvider, Class<T> rowClass) {
        this.rowClass = rowClass;
        manager = entityManagerProvider.getEntityManager();
        criteriaBuilder = manager.getCriteriaBuilder();
    }

    /**
     * Find entity with specified primary key
     *
     * @param id primary key value
     * @return optional of found row
     */
    public Optional<T> find(PK id) {
        return Optional.ofNullable(
                manager.find(rowClass, id)
        );
    }

    /**
     * Find entity with specified primary key and fetch related entities
     *
     * @param id           primary key value
     * @param fetchColumns list of column names to do fetch for
     * @return optional of found row
     */
    public Optional<T> fetch(PK id, List<String> fetchColumns) {
        var crit = criteriaBuilder.createQuery(rowClass);
        var base = crit.from(rowClass);
        crit.where(criteriaBuilder.equal(base.get("id"), id));
        fetchColumns.forEach(base::fetch);
        var query = manager.createQuery(crit);
        query.setMaxResults(1);
        try {
            return Optional.of(query.getSingleResult());
        } catch (NoResultException ex) {
            return Optional.empty();
        }
    }

    /**
     * Return all rows from table
     *
     * @return entities list
     */
    public <U> List<T> findMatching(String column, U value) {
        var crit = criteriaBuilder.createQuery(rowClass);
        var base = crit.from(rowClass);
        crit.where(criteriaBuilder.equal(base.get(column), value));
        var query = manager.createQuery(crit);
        return query.getResultList();
    }

    /**
     * Return all rows from table
     *
     * @return entities list
     */
    public List<T> findAll() {
        var crit = criteriaBuilder.createQuery(rowClass);
        crit.from(rowClass);
        var query = manager.createQuery(crit);
        return query.getResultList();
    }

    /**
     * Store entity to DB
     *
     * @param data entity to insert
     * @return true if inserted successfully else false
     */
    boolean insert(T data) {
        manager.getTransaction().begin();
        try {
            manager.persist(data);
        } catch (EntityExistsException ex) {
            return false;
        }

        manager.getTransaction().commit();
        return true;
    }

    /**
     * Update entity stored in DB
     *
     * @param data entity to update
     * @return true if updated successfully else false
     */
    boolean update(T data) {
        manager.getTransaction().begin();
        try {
            manager.merge(data);
        } catch (IllegalArgumentException ex) {
            return false;
        }

        manager.getTransaction().commit();
        return true;
    }

    /**
     * Remove entity from DB by primary key
     *
     * @param id primary key of entity
     * @return true if removed successfully else false
     */
    boolean delete(PK id) {
        var entity = find(id);
        if (entity.isEmpty())
            return false;

        manager.getTransaction().begin();
        try {
            manager.remove(entity.get());
        } catch (IllegalArgumentException ex) {
            return false;
        }
        manager.getTransaction().commit();
        return true;
    }
}
