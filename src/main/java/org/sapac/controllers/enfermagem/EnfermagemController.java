/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sapac.controllers.enfermagem;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Named;
import org.sapac.controllers.GenericController;
import org.sapac.controllers.PaginasNavegacao;
import org.sapac.entities.Paciente;

/**
 *
 * @author carlson
 */
@ManagedBean
@SessionScoped
public class EnfermagemController extends GenericController {
	
	private Paciente paciente;
	
	private Paciente pacientePesquisa;
	
	@PostConstruct
	public void init() {
		
	}
	
	public String telaDianosticarEnfermagem(Paciente paciente) {
		this.paciente = paciente;
		return PaginasNavegacao.ENFERMAGEM_EDITAR_DIAGNOSTICO;
	}
	
	public String telaIntervencaoEnfermagem() {
		return PaginasNavegacao.ENFERMAGEM_EDITAR_INTERVENCAO;
	}
	
	public String visualizarDiagnostico(Paciente paciente) {
		return PaginasNavegacao.ENFERMAGEM_VISUALIZAR_DIAGNOSTICO;
	}
	
	public String visualizarIntervencao() {
		return PaginasNavegacao.ENFERMAGEM_VISUALIZAR_INTERVENCAO;
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
