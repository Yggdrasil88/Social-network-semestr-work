/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zcu.pia.social.network.backend.services.dao;

/**
 *
 * @author Frantisek Kolenak
 */
import java.io.Serializable;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.GenericTypeResolver;

/**
 * Abstract DAO
 *
 * @author Frantisek Kolenak
 *
 * @param <T>
 */
@SuppressWarnings("unchecked")
public abstract class GenericDAO<T extends Serializable> implements
    GenericDAOInterface<T> {

    protected final Class<T> genericType;

    private final Logger logger = LoggerFactory.getLogger(GenericDAO.class);

    /**
     * Session factory
     */
    @Autowired
    private SessionFactory sessionFactory;
    /**
     * current transaction
     */
    protected Transaction currentTransaction;

    /**
     * Constructor
     */
    public GenericDAO() {
        this.genericType = (Class<T>) GenericTypeResolver.resolveTypeArgument(
            getClass(), GenericDAO.class);
    }

    /**
     * Gets current session
     *
     * @return
     */
    protected Session getCurrentSession() {
        return sessionFactory.openSession();
    }

    /**
     * Gets current session with transaction
     *
     * @return
     */
    protected Session getCurrentSessionWithTransaction() {
        Session s = sessionFactory.openSession();
        this.currentTransaction = s.beginTransaction();
        return s;
    }

    /**
     * Closes session with transaction
     *
     * @param s session
     */
    private void closeSessionWithTransaction(Session s) {
        s.flush();
        this.currentTransaction.commit();
        s.clear();
        s.close();
        
    }

    /**
     * Closes session
     *
     * @param s session
     */
    protected void closeSession(Session s) {
        s.close();
    }

    /**
     * Save entity
     * @return id of the saved object
     */
    @Override
    public Long persist(T entity) {
        Preconditions.checkNotNull(entity);
        Session session = getCurrentSessionWithTransaction();
        try {
            return (Long) session.save(entity);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            closeSessionWithTransaction(session);
        }
        return null;
    }

    @Override
    public void update(T entity) {
        Preconditions.checkNotNull(entity);
        Session session = getCurrentSessionWithTransaction();
        try {
            session.update(entity);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            closeSessionWithTransaction(session);
        }
    }

    @Override
    public T findById(long id) {
        Session session = getCurrentSession();
        try {
            T cc = (T) session.get(this.genericType, id);
            return cc;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            closeSession(session);
        }
        return null;

    }

    @Override
    public int delete(T entity) {
        Preconditions.checkNotNull(entity);
        Session session = getCurrentSessionWithTransaction();
        try {

            session.delete(entity);
            return 1;
        } catch (ConstraintViolationException e) {
            return -1;
        } finally {
            closeSessionWithTransaction(session);
        }
    }

    @Override
    public int delete(long id) {
        T entity = findById(id);
        Preconditions.checkState(entity != null);
        return delete(entity);

    }

    @Override
    public List<T> findAll() {
        Session session = getCurrentSession();
        try {
            List<T> ccs = (List<T>) session.createQuery(
                "from " + this.genericType.getName()).list();
            return ccs;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            closeSession(session);
        }
        return null;

    }

}
