package org.sapac.models;

import java.util.Collection;
import java.util.Date;
import org.sapac.entities.Consulta;
import org.sapac.entities.DiagnosticoEnfermagem;
import org.sapac.entities.IntervencaoEnfermagem;
import org.sapac.entities.Paciente;

public interface EnfermagemDAO extends GenericDAO {
	public DiagnosticoEnfermagem alterarDiagnosticoEnfermagem(DiagnosticoEnfermagem diagnosticoEnfermagem);
	public IntervencaoEnfermagem alterarIntervencaoEnfermagem(IntervencaoEnfermagem intervencaoEnfermagem);
	public DiagnosticoEnfermagem procurarDiagnosticoEnfermagem(Paciente paciente);
	public Collection<IntervencaoEnfermagem> procurarIntervencoesEnfermagem(Paciente paciente);
	public IntervencaoEnfermagem procurarIntervencaoEnfermagem(Consulta consulta);
	public Collection<IntervencaoEnfermagem> procurarIntervencoesEnfermagemDia(Paciente paciente, Date data);
}