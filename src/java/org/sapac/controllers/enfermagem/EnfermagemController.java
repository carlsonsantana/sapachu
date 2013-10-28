/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sapac.controllers.enfermagem;

import org.sapac.controllers.GenericController;
import org.sapac.entities.Paciente;

/**
 *
 * @author carlson
 */
public class EnfermagemController extends GenericController {
	
	private Paciente paciente;
	
	private Paciente pacientePesquisa;
	
	public EnfermagemController() {
		super();
	}
	
	public String telaDianosticarEnfermagem() {
		return "/private/enfermagem/diagnosticar";
	}
	
	public String telaIntervencaoEnfermagem() {
		return "/private/enfermagem/intervencao";
	}
	
	public String visualizarDiagnostico(Paciente paciente) {
		return "/private/enfermagem/visualizar_diagnostico";
	}
	
	public String visualizarIntervencao() {
		return "/private/enfermagem/visualizar_intervencao";
	}

	/**
	 * @return the paciente
	 */
	public Paciente getPaciente() {
		return paciente;
	}

	/**
	 * @param paciente the paciente to set
	 */
	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}

	/**
	 * @return the pacientePesquisa
	 */
	public Paciente getPacientePesquisa() {
		return pacientePesquisa;
	}

	/**
	 * @param pacientePesquisa the pacientePesquisa to set
	 */
	public void setPacientePesquisa(Paciente pacientePesquisa) {
		this.pacientePesquisa = pacientePesquisa;
	}
}
