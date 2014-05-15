package ru.springtest.app;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.jdbc.Work;
import org.hibernate.metadata.ClassMetadata;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;

public class HibernateDataProvider {

    private HibernateTemplate helper;

    public HibernateDataProvider(SessionFactory sessionFactory) {
        helper = new HibernateTemplate(sessionFactory);
    }

    public Map<String, ClassMetadata> getAllClassMetadata() {
        return (Map<String, ClassMetadata>) helper.getSessionFactory().getAllClassMetadata();
    }

    public Session openSession() {
        return helper.getSessionFactory().openSession();
    }

    public Session getCurrentSession() {
        return helper.getSessionFactory().getCurrentSession();
    }

    public <T extends Object> List<T> loadAll(Class<T> type) {
        return (List<T>) helper.loadAll(type);
    }

    public <T extends Object> T load(Class<T> type, Serializable id) {
        return (T) helper.load(type, id);
    }

    public <T extends Object> T get(Class<T> type, Serializable id) {
        return (T) helper.get(type, id);
    }

    public void evict(Object... entities) {
        for (Object entity : entities) {
            helper.evict(entity);
        }
    }

    public void refresh(Object... entities) {
        for (Object entity : entities) {
            helper.refresh(entity);
        }
    }

    public void save(Object... entities) {
        for (Object entity : entities) {
            helper.saveOrUpdate(entity);
        }
    }

    public void delete(Object... entities) {
        for (Object entity : entities) {
        	helper.delete(entity);
        }
    }

    public void saveAndDelete(List<Object> forSave, List<Object> forDelete) {
        for (Object entity : forSave) {
            helper.saveOrUpdate(entity);
        }
        for (Object entity : forDelete) {
        	helper.delete(entity);
        }
    }

    public int getResultCount(final DetachedCriteria criteria) {
        final HibernateCallback hc = new HibernateCallback() {

            @Override
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                Criteria ec = criteria.getExecutableCriteria(session);
                List list = ec.setProjection(Projections.rowCount()).list();
                if (!list.isEmpty()) {
                    return list.get(0);
                }
                return new Integer(0);
            }

        };

        return ((Number) helper.execute(hc)).intValue();
    }

    public List<?> findByCriteria(DetachedCriteria criteria, int firstResult, int maxResults) {
        if (maxResults > 0) {
            return helper.findByCriteria(criteria, firstResult, maxResults);
        } else {
            return helper.findByCriteria(criteria);
        }
    }

    public int getResultCount(final String hql, final Map<String, Object> params) {
        final HibernateCallback hc = new HibernateCallback() {

            @Override
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                Query query = createQuery(session, hql, params, 0, -1, true, true);
                return query.uniqueResult();
            }

        };

        return ((Number) helper.execute(hc)).intValue();
    }

    
    public List<?> findByHQL(final String hql, final Map<String, Object> params,
            final int firstResult, final int maxResults) {
        final HibernateCallback hc = new HibernateCallback() {

            @Override
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                Query query = createQuery(session, hql, params, firstResult, maxResults, false, true);
                List<?> result = query.list();
                return result;
            }

        };

        return (List<?>) helper.execute(hc);
    }

    
    @SuppressWarnings("unchecked")
    public <T extends Object> List<T> findByProperty(Class<T> type, String propertyName, Object propertyValue,
            int firstResult, int maxResults) {
        return findByProperty(type, propertyName, propertyValue, firstResult, maxResults, null, true);
    }

    
    public <T> List<T> findByProperty(Class<T> type, String propertyName, Object propertyValue,
            int firstResult, int maxResults, String orderByPropertyName, boolean ascending) {
        DetachedCriteria dc = DetachedCriteria.forClass(type);
        if (propertyName != null) {
            dc.add(Restrictions.eq(propertyName, propertyValue));
        }
        if (orderByPropertyName != null) {
            if (ascending) {
                dc.addOrder(Order.asc(orderByPropertyName));
            } else {
                dc.addOrder(Order.desc(orderByPropertyName));
            }
        }
        return (List<T>) findByCriteria(dc, firstResult, maxResults);
    }

    
    public List<?> findBySQL(final String sql, final Map<String, Object> params,
            final int firstResult, final int maxResults) {
        final HibernateCallback hc = new HibernateCallback() {

            @Override
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                Query query = createQuery(session, sql, params, firstResult, maxResults, false, false);
                List<?> result = query.list();
                return result;
            }

        };

        return (List<?>) helper.execute(hc);
    }

    
    public void doWork(final Work work) {
        final HibernateCallback hc = new HibernateCallback() {

            @Override
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                session.doWork(work);
                return null;
            }

        };

        helper.execute(hc);
    }

    
    public int executeHQL(final String hql, final Map<String, Object> params) {
        final HibernateCallback hc = new HibernateCallback() {

            @Override
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                Query query = createQuery(session, hql, params, 0, -1, false, true);
                return new Integer(query.executeUpdate());
            }

        };

        return ((Integer) helper.execute(hc)).intValue();
    }

    
    public int executeSQL(final String sql, final Map<String, Object> params) {
        final HibernateCallback hc = new HibernateCallback() {

            @Override
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                Query query = createQuery(session, sql, params, 0, -1, false, false);
                return new Integer(query.executeUpdate());
            }

        };

        return ((Integer) helper.execute(hc)).intValue();
    }

    private Query createQuery(Session session, String queryString, Map<String, Object> params,
            int firstResult, int maxResults, boolean isCountQuery, boolean isHQL) {
        StringBuilder sb = new StringBuilder(256);
        if (isCountQuery) {
            sb.append("select count(*) ");
            if (queryString.indexOf("select ") == 0 || queryString.indexOf("SELECT ") == 0) {
                int index = queryString.indexOf("from ");
                if (index < 0) {
                    index = queryString.indexOf("FROM ");
                }
                sb.append(queryString.substring(index));
            } else {
                sb.append(queryString);
            }
            int orderByIndex = sb.indexOf("order by ");
            if (orderByIndex < 0) {
                orderByIndex = sb.indexOf("ORDER BY ");
            }
            if (orderByIndex > -1) {
                sb.delete(orderByIndex, sb.length());
            }
        } else {
            sb.append(queryString);
        }
        if (params != null) {
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                if (entry.getValue() == null) {
                    replaceByNull(sb, entry.getKey());
                }
            }
        }
        Query query;
        if (isHQL) {
            query = session.createQuery(sb.toString());
        } else {
            query = session.createSQLQuery(sb.toString());
        }
        if (params != null) {
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                if (entry.getValue() == null) {
                    continue;
                }

                if (entry.getValue().getClass().isArray()) {
                    query.setParameterList(entry.getKey(), (Object[]) entry.getValue());
                } else if (entry.getValue() instanceof Collection) {
                    query.setParameterList(entry.getKey(), (Collection) entry.getValue());
                } else {
                    query.setParameter(entry.getKey(), entry.getValue());
                }
            }
        }
        if (maxResults > 0) {
            query.setFirstResult(firstResult);
            query.setMaxResults(maxResults);
        }
        return query;
    }

    private void replaceByNull(StringBuilder sb, String paramName) {
        int paramIndex = -1;
        while ((paramIndex = sb.indexOf(":" + paramName, Math.max(0, paramIndex))) > 0) {
            String replace = null;
            int operatorIndex = -1;
            boolean eqFound = false;
            boolean gtFound = false;
            for (int i = paramIndex - 1; i >= 0; i--) {
                char c = sb.charAt(i);
                if (c == '=') {
                    eqFound = true;
                    continue;
                }
                if (c == '>') {
                    gtFound = true;
                    continue;
                }
                if (c == ' ' && eqFound) {
                    operatorIndex = i + 1;
                    replace = "is null";
                    break;
                }
                if ((c == '<' && gtFound) || (c == '!' && eqFound)) {
                    operatorIndex = i;
                    replace = "is not null";
                    break;
                }
            }
            if (operatorIndex > -1) {
                sb.delete(operatorIndex, (paramIndex + paramName.length() + 1));
                sb.insert(operatorIndex, replace);
                paramIndex = -1;
            }
        }
    }

}
