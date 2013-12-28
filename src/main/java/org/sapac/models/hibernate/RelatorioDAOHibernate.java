/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sapac.models.hibernate;

import java.util.Collection;
import java.util.HashMap;
import org.sapac.entities.MembroEquipe;
import org.sapac.models.RelatorioDAO;

/**
 *
 * @author carlson
 */
public class RelatorioDAOHibernate extends GenericDAOHibernate implements RelatorioDAO {

	@Override
	public Collection<HashMap<String, String>> gerarRelatorio(MembroEquipe membroEquipe) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}
	
}
