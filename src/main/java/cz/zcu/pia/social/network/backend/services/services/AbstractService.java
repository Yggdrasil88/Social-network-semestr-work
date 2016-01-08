/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zcu.pia.social.network.backend.services.services;

import com.google.common.base.Preconditions;
import cz.zcu.pia.social.network.backend.services.dao.GenericDAOInterface;
import java.io.Serializable;
import java.util.List;

/**
 * 
 * Abstract Service
 * @author Frantisek Kolenak
 * @param <T> represents entity
 */
public abstract class AbstractService<T extends Serializable> implements GenericDAOInterface<T> {

    /**
     * Saves entity
     * @param entity entity to save
     * @return entity id
     */
    @Override
    public Long persist(T entity) {
       return getDao().persist(entity);
    }

    /**
     * Updates entity
     */
    @Override
    public void update(T entity) {
        getDao().update(entity);
    }

    /**
     * Finds entity by id
     */
    @Override
    public T findById(long id) {
        return getDao().findById(id);
    }

    /**
     * Delete entity
     */
    @Override
    public int delete(long id) {
        T entity = getDao().findById(id);
        Preconditions.checkNotNull(entity);

        return getDao().delete(entity);

    }

    /**
     * Delete entity
     */
    @Override
    public int delete(T entity) {
        Preconditions.checkNotNull(entity);

        return getDao().delete(entity);

    }

    /**
     * Finds all
     */
    @Override
    public List<T> findAll() {
        return getDao().findAll();
    }

    /**
     * Gets dao object
     *
     * @return dao object
     */
    protected abstract GenericDAOInterface<T> getDao();
}
