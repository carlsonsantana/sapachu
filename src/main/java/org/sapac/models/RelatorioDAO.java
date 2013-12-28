/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sapac.models;

import java.util.Collection;
import java.util.HashMap;
import org.sapac.entities.MembroEquipe;

/**
 *
 * @author carlson
 */
public interface RelatorioDAO extends GenericDAO {
	public Collection<HashMap<String, String>> gerarRelatorio(MembroEquipe membroEquipe);
}
