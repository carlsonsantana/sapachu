/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sapac.adapters;

import java.util.Collection;
import org.sapac.entities.Consulta;
import org.sapac.entities.Paciente;

/**
 *
 * @author carlson
 */
public interface SistemaPacienteAdapter {
	public Collection<Paciente> procurarPaciente(Paciente paciente);
	public boolean salvarInformacoesProntuario(Consulta consulta);
}
