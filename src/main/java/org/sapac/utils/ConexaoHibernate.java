package org.sapac.utils;

import javax.enterprise.context.RequestScoped;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

@RequestScoped
public class ConexaoHibernate {

	private static SessionFactory sessionFactory;

	private Session session;

	public static SessionFactory getSessionHibernate() {
		if (sessionFactory == null) {
			Configuration configuration = new Configuration();
			configuration.configure();

			ServiceRegistry serviceRegistry = (new ServiceRegistryBuilder()).applySettings(configuration.getProperties()).buildServiceRegistry();
			sessionFactory = configuration.buildSessionFactory(serviceRegistry);
		}
		return sessionFactory;
	}

	public Session getSession() {
		if ((session == null)
				|| (!session.isOpen())) {
			session = getSessionHibernate().openSession();
			session.setDefaultReadOnly(true);
			session.beginTransaction();
		}
		return session;
	}

	public void closeSession() {
		if ((session != null) && (session.isOpen())) {
			try {
				session.getTransaction().commit();
			} finally {
				session.close();
			}
		}
		session = null;
	}
}
