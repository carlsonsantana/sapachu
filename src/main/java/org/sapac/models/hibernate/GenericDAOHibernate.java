package org.sapac.models.hibernate;

import javax.inject.Inject;
import org.hibernate.Session;
import org.sapac.models.GenericDAO;
import org.sapac.utils.ConexaoHibernate;

public class GenericDAOHibernate implements GenericDAO {
	
	@Inject
	private ConexaoHibernate conexaoHibernate;
	
	@Override
	public Session getSession() {
		if (conexaoHibernate == null) {
			conexaoHibernate = new ConexaoHibernate();
		}
		Session session = conexaoHibernate.getSession();
		return session;
	}
	
	public void closeSession() {
		conexaoHibernate.closeSession();
	}
}
