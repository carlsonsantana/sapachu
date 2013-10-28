/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sapac.controllers.consulta;

import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import org.sapac.controllers.GenericController;
import org.sapac.entities.Paciente;

/**
 *
 * @author carlson
 */
@ManagedBean
@SessionScoped
public class PacienteController extends GenericController {
	private static final long serialVersionUID = 1L;
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
	
	private enum Operacao {
		PESQUISAR,
		ADICIONAR;
	}
	
	private Paciente paciente;
	private Paciente pacientePesquisa;
	private Operacao operacao;
	private transient DataModel<Paciente> listaPacientes;

	public PacienteController() {
		super();
		paciente = new Paciente();
		pacientePesquisa = new Paciente();
		
		List<Paciente> pacientes = new ArrayList<Paciente>();
		Paciente paciente1 = new Paciente();
		paciente1.setNome("Carlos Miguel Fonseca");
		paciente1.setProntuario("3435.1212");
		pacientes.add(paciente1);
		paciente1 = new Paciente();
		paciente1.setNome("Josefa Maria de Lurdes");
		paciente1.setProntuario("9864.6324");
		pacientes.add(paciente1);
		pacientes.add(paciente1);
		pacientes.add(paciente1);
		pacientes.add(paciente1);
		pacientes.add(paciente1);
		pacientes.add(paciente1);
		pacientes.add(paciente1);
		pacientes.add(paciente1);
		pacientes.add(paciente1);
		pacientes.add(paciente1);
		pacientes.add(paciente1);
		pacientes.add(paciente1);
		pacientes.add(paciente1);
		pacientes.add(paciente1);
		pacientes.add(paciente1);
		pacientes.add(paciente1);
		pacientes.add(paciente1);
		pacientes.add(paciente1);
		
		listaPacientes = new ListDataModel<Paciente>(pacientes);
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
	 * Retorna a tela para pesquisar pacientes.
	 * @return A tela para pesquisar pacientes.
	 */
	public String pesquisarPaciente() {
		operacao = Operacao.PESQUISAR;
		return "/private/paciente/pesquisar";
	}

	/**
	 * Retorna a tela para cadastrar pacientes.
	 * @return A tela para cadastrar pacientes.
	 */
	public String cadastrarPaciente() {
		operacao = Operacao.ADICIONAR;
		return "/private/paciente/pesquisar";
	}

	/**
	 * @return the listaPacientes
	 */
	public DataModel<Paciente> getListaPacientes() {
		return listaPacientes;
	}

	/**
	 * @param listaPacientes the listaPacientes to set
	 */
	public void setListaPacientes(DataModel<Paciente> listaPacientes) {
		this.listaPacientes = listaPacientes;
	}

	/**
	 * Retorna a tela para visualizar um paciente.
	 * @param paciente O paciente a ser visualizado.
	 * @return A tela para visualizar um paciente.
	 */
	public String visualizarPaciente(Paciente paciente) {
		setPaciente(paciente);
		return "/private/paciente/visualizar";
	}

	public boolean isPesquisar() {
		return operacao.equals(Operacao.PESQUISAR);
	}

	public boolean isAdicionar() {
		return operacao.equals(Operacao.ADICIONAR);
	}

	/**
	 * Retorna a tela para visualizar um paciente.
	 * @param paciente O paciente a ser visualizado.
	 * @return A tela para visualizar um paciente.
	 */
	public String adicionarPaciente(Paciente paciente) {
		setPaciente(paciente);
		return "/private/paciente/cadastrar";
	}

	/**
	 * 
	 * @return 
	 */
	public String cadastrar() {
		return "/private/paciente/visualizar";
	}
}