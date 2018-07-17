package ru.skorikov.persistent.dao;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Interface DAO.
 * @param <T> param dao.
 */
public interface EntityDAO<T> {
    /**
     * Add new element.
     *
     * @param entity new element.
     * @return is added.
     */
    boolean add(T entity);

    /**
     * Update element.
     *
     * @param id     element id.
     * @param entity element.
     * @return isUpdate.
     */
    boolean update(Integer id, T entity);

    /**
     * Delete element.
     *
     * @param id element id.
     * @return is delete.
     */
    boolean delete(Integer id);

    /**
     * Find element by id.
     *
     * @param id element id.
     * @return element.
     */
    T findById(Integer id);

    /**
     * Find all elements.
     *
     * @return elements list.
     */
    CopyOnWriteArrayList<T> findAll();
}
