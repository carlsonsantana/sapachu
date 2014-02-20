/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sapac.models.hibernate;

import java.util.Collection;
import java.util.Map;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.sapac.models.GenericDAO;
import org.sapac.utils.ConexaoHibernate;

/**
 *
 * @author carlson
 */
public class GenericDAOHibernate implements GenericDAO {
	
	/**
	public T findFirstResult(Map<String, Object> mapa, Class<T> classe) {
		Session session = getSession();
		
		Criteria criteria = session.createCriteria(classe);
		
		for (Map.Entry<String, Object> entry : mapa.entrySet()) {
			String string = entry.getKey();
			Object object = entry.getValue();
			criteria.add(Restrictions.eq(string, object));
		}
		
		return criteria.uniqueResult();
	}
	
	public Collection<Class> find(Map<String, Object> mapa, Class classe) {
		Session session = getSession();
		
		Criteria criteria = session.createCriteria(classe);
		
		for (Map.Entry<String, Object> entry : mapa.entrySet()) {
			String string = entry.getKey();
			Object object = entry.getValue();
			criteria.add(Restrictions.eq(string, object));
		}
		
		return criteria.list();
	}
	*/
	
	@Override
	public Session getSession() {
		return ConexaoHibernate.getSession();
	}
}
