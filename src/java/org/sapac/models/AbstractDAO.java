/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sapac.models;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;
import org.hibernate.impl.SessionFactoryImpl;

/**
 *
 * @author carlson
 */
public abstract class AbstractDAO {
	private Session session;
	
	public Session getSession() {
		if (session == null) {
			
		} else if (!session.isOpen()) {
			
		}
		return session;
	}
}
