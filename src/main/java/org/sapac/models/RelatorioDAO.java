package org.sapac.models;

import java.util.Collection;
import java.util.Map;
import org.sapac.entities.MembroEquipe;

public interface RelatorioDAO extends GenericDAO {
	public Collection<Map<String, String>> gerarRelatorio(MembroEquipe membroEquipe);
}
