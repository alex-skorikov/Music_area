package ru.skorikov.persistent.repository;

import ru.skorikov.Entity;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Inteface ropositiry.
 * @param <T> param.
 */
public interface EntityRepository<T> {
    /**
     * Map elements.
     * @param t param Entity.
     * @return map elements entityes.
     */
    ConcurrentHashMap<String, CopyOnWriteArrayList<Entity>> getAllEntity(T t);
}
