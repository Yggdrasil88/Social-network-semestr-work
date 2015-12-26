/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zcu.pia.social.network.backend.services.dao;

import java.io.Serializable;
import java.util.List;


/**
 * Interface
 * @author Frantisek Kolenak
 *
 * @param <T>
 */
public interface GenericDAOInterface<T extends Serializable> {
	/**
	 * Save entity
	 * @param entity entity
	 */
	public Long persist(T entity);
	/**
	 * Update entity
	 * @param entity entity
	 */
	public void update(T entity);
	/**
	 * Find by id
	 * @param id id
	 * @return entity
	 */
	public T findById(long id);
	/**
	 * Delete entity
	 * @param id id
	 * @return number if its ok
	 */
	public int delete(long id);
	/**
	 * Delete entity
         * @param entity entity to delete
	 * @return positive number if its ok
	 */
	public int delete(T entity);
	/**
	 * Finds all
	 * @return
	 */
	public List<T> findAll();
	





}

