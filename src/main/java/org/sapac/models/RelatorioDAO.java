package org.sapac.models;

import java.util.Collection;
import java.util.Map;
import org.sapac.entities.MembroEquipe;

public interface RelatorioDAO extends GenericDAO {
	public Collection<Map<String, String>> gerarRelatorioDiferenca(MembroEquipe membroEquipe);
	public Collection<Map<String, String>> gerarRelatorioAnteriorVariaveisClinicas(MembroEquipe membroEquipe);
	public Collection<Map<String, String>> gerarRelatorioPosteriorVariaveisClinicas(MembroEquipe membroEquipe);
	public Collection<Map<String, String>> gerarRelatorioAnteriorSituacaoUlcera(MembroEquipe membroEquipe);
	public Collection<Map<String, String>> gerarRelatorioPosteriorSituacaoUlcera(MembroEquipe membroEquipe);
}