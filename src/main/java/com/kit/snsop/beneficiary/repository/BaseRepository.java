package com.kit.snsop.beneficiary.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

@Repository
public class BaseRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public <T> T persist(T entity) {
        entityManager.clear();
        entityManager.persist(entity);
        entityManager.flush();
        return entity;
    }

    @Transactional
    public <T> T merge(T entity) {
        entityManager.clear();
        entityManager.merge(entity);
        entityManager.flush();
        return entity;
    }

    public List<Object[]> findByQuery(String sql, Map<String, Object> params, Integer from, Integer size) {
        if (from == null) {
            from = 0;
        }
        if (size == null) {
            size = 10;
        }
        Query query = entityManager.createNativeQuery(sql);
        if (params != null && params.size() >0) {
            params.forEach(query::setParameter);
        }
        if (size >0 && from >=0) {
            query.setFirstResult(from);
            query.setMaxResults(size);
        }

        return query.getResultList();
    }

    public <T extends Object> List<T> findByQuery(String sql, Map<String, Object> params, Integer from, Integer size, Class<T> t) {
        if (from == null) {
            from = 0;
        }
        if (size == null) {
            size = 10;
        }
        Query query = entityManager.createNativeQuery(sql, t);
        if (params != null && params.size() >0) {
            params.forEach(query::setParameter);
        }
        if (size >0 && from >=0) {
            query.setFirstResult(from);
            query.setMaxResults(size);
        }

        return query.getResultList();
    }

    public <T> T findSingleResult(String sql,Class<T> entity, Map<String, Object> params) {
        Query query = entityManager.createNativeQuery(sql, entity);
        if (params != null && params.size() >0) {
            params.forEach(query::setParameter);
        }
        try {
            return (T) query.getSingleResult();
        } catch (Throwable t) {
            //t.printStackTrace();
            return null;
        }
    }

    public <T> List<T> findByQuery(String sql,Class<T> entity, Map<String, Object> params, Integer from, Integer size) {
        if (from == null || from <0) {
            from = 0;
        }
        if (size == null) {
            size = 10;
        }
        Query query = entityManager.createNativeQuery(sql, entity);
        if (params != null && params.size() >0) {
            params.forEach(query::setParameter);
        }

        if (size >0 && from >=0) {
            query.setFirstResult(from);
            query.setMaxResults(size);
        }

        return query.getResultList();
    }

    public <T> List<T> findByQuery(String sql, Map<String, Object> params) {

        Query query = entityManager.createNativeQuery(sql);
        if (params != null && params.size() >0) {
            params.forEach(query::setParameter);
        }

        return query.getResultList();
    }

    public int executeUpdate(String sql, Map<String, Object> params){
        Query query = entityManager.createNativeQuery(sql);
        if (params != null && params.size() >0) {
            params.forEach(query::setParameter);
        }
        return query.executeUpdate();
    }

    public long getCount(String sql, Map<String, Object> params) {
        Query query = entityManager.createNativeQuery(sql);
        if (params != null && params.size() >0) {
            params.forEach(query::setParameter);
        }

        Object count = query.getSingleResult();
        if (count instanceof Long) {
            return (Long) count;
        }
        if (count instanceof Integer) {
            return ((Integer) count).longValue();
        }
        if (count instanceof Double) {
            return ((Double) count).longValue();
        }
        if (count instanceof BigDecimal) {
            return ((BigDecimal) count).longValue();
        }
        if (count instanceof BigInteger) {
            return ((BigInteger) count).longValue();
        }
        return 0;
    }
}
