/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sapac.models;

import java.util.Collection;
import java.util.Map;
import org.sapac.entities.MembroEquipe;

/**
 *
 * @author carlson
 */
public interface RelatorioDAO extends GenericDAO {
	public Collection<Map<String, String>> gerarRelatorio(MembroEquipe membroEquipe);
}
