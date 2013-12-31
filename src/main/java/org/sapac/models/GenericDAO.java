/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sapac.models;

import org.hibernate.Session;

/**
 *
 * @author carlson
 */
public interface GenericDAO {
	public Session getSession();
}
