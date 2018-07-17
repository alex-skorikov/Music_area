package ru.skorikov;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Logic interface.
 * @param <T> param extemds Entity.
 */
public interface ValidateService<T> {
    /**
     * Add entity.
     *
     * @param t param t extends Entity.
     * @return isAdded.
     */
    boolean add(T t);

    /**
     * Update.
     *
     * @param t param t extends Entity.
     * @return isUpdate.
     */
    boolean update(T t);

    /**
     * Validate and delete Entity.
     * @param id id.
     * @return isDelete.
     */
    boolean delete(Integer id);

    /**
     * Find all Entities.
     *
     * @return Entityes list.
     */
    CopyOnWriteArrayList<T> findAll();

    /**
     * Find Entity by ID.
     *
     * @param id id.
     * @return Entity.
     */
    T findById(Integer id);

    /**
     * Find Entity by name.
     *
     * @param name name.
     * @return Entity.
     */
    T findByName(String name);
}
