/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sapac.utils;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;


/**
 *
 * @author carlson
 */
public final class ConexaoHibernate {
	
	private static SessionFactory sessionFactory;
	
	public static SessionFactory getSessionHibernate() {
		if (sessionFactory == null) {
			Configuration configuration = new Configuration();
			configuration.configure();
			
			ServiceRegistry serviceRegistry = (new ServiceRegistryBuilder()).applySettings(configuration.getProperties()).buildServiceRegistry();
			sessionFactory = configuration.buildSessionFactory(serviceRegistry);
		}
		return sessionFactory;
	}
	
	public static Session getSession() {
		getSessionHibernate().openSession();
		return getSessionHibernate().getCurrentSession();
	}
}