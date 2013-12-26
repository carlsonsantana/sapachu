/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sapac.models;

import java.io.Serializable;
import java.util.List;

public interface GenericDao {

	public <T> T find(Serializable id, Class<T> type);

	public <T> T[] findBy();

	public <T> List<T> findAll(Class<T> type);

	public boolean save(Object entity);

	public boolean[] save(Object... entities);
	
	public boolean update(Object entity);
	
	public boolean update(Object... entities);
	
	//public boolean 

	public boolean remove(Object entity);

	public void remove(Object... entities);

/*
	public List search(ISearch search);

	public Object searchUnique(ISearch search);

	public int count(ISearch search);

	public SearchResult searchAndCount(ISearch search);

	public boolean isAttached(Object entity);

	public void refresh(Object... entities);

	public void flush();

	public Filter getFilterFromExample(Object example);

	public Filter getFilterFromExample(Object example, ExampleOptions options);*/
}