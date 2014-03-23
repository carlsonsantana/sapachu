package org.sapac.models;

import java.io.Serializable;
import org.hibernate.Session;

public interface GenericDAO extends Serializable {
	public Session getSession();
}
