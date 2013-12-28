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
public class PEPAdapter implements SistemaPacienteAdapter {

	@Override
	public Collection<Paciente> procurarPaciente(Paciente paciente) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated 
	}

	@Override
	public boolean salvarInformacoesProntuario(Consulta consulta) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}
	
}
