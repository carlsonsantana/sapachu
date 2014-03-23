package org.sapac.adapters;

import java.util.Collection;
import org.sapac.entities.Consulta;
import org.sapac.entities.Paciente;

public interface SistemaPacienteAdapter {
	public void carregarInformacoesPaciente(Paciente paciente);
	public Collection<Paciente> procurarPaciente(Paciente paciente);
	public boolean salvarInformacoesProntuario(Consulta consulta);
}