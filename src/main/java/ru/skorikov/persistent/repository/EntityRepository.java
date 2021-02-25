package ru.skorikov.persistent.repository;

import ru.skorikov.Entity;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

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
    ConcurrentHashMap<String, ArrayList<Entity>> getAllEntity(T t);
}
