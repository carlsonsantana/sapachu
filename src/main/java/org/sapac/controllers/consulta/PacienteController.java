/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sapac.controllers.consulta;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.inject.Named;
import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.sapac.adapters.PEPAdapter;
import org.sapac.adapters.SistemaPacienteAdapter;
import org.sapac.controllers.GenericController;
import org.sapac.controllers.PaginasNavegacao;
import org.sapac.entities.Paciente;
import org.sapac.models.ConsultaDAO;
import org.sapac.models.hibernate.ConsultaDAOHibernate;

/**
 *
 * @author carlson
 */
@Named
@javax.enterprise.context.SessionScoped
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

	/**
	 * @return the graficos
	 */
	public Collection<CartesianChartModel> getGraficos() {
		return graficos;
	}

	/**
	 * @param graficos the graficos to set
	 */
	public void setGraficos(Collection<CartesianChartModel> graficos) {
		this.graficos = graficos;
	}
	
	private enum Operacao {
		PESQUISAR,
		ADICIONAR,
		PESQUISAR_ENFERMAGEM;
	}
	
	private Paciente paciente;
	private Paciente pacientePesquisa;
	private Operacao operacao;
	private transient DataModel<Paciente> listaPacientes;
	private transient Collection<CartesianChartModel> graficos;

	@PostConstruct
	public void init() {
		paciente = new Paciente();
		pacientePesquisa = new Paciente();
		
		listaPacientes = new ListDataModel<Paciente>();
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
		
		ConsultaDAO dao = new ConsultaDAOHibernate();
		listaPacientes = new ListDataModel<Paciente>((List) dao.procurarPacientes(pacientePesquisa));
		
		return PaginasNavegacao.PACIENTE_PESQUISAR;
	}

	/**
	 * Retorna a tela para cadastrar pacientes.
	 * @return A tela para cadastrar pacientes.
	 */
	public String cadastrarPaciente() {
		operacao = Operacao.ADICIONAR;
		
		SistemaPacienteAdapter sistemaPacienteAdapter = new PEPAdapter();
		listaPacientes = new ListDataModel<Paciente>((List) sistemaPacienteAdapter.procurarPaciente(pacientePesquisa));
		
		return PaginasNavegacao.PACIENTE_PESQUISAR;
	}
	
	public String pesquisarPacienteEnfermagem() {
		operacao = Operacao.PESQUISAR_ENFERMAGEM;
		
		ConsultaDAOHibernate dao = new ConsultaDAOHibernate();
		listaPacientes = new ListDataModel<Paciente>((List) dao.procurarPacientes(pacientePesquisa));
		
		return PaginasNavegacao.PACIENTE_PESQUISAR;
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
		SistemaPacienteAdapter sistemaPacienteAdapter = new PEPAdapter();
		sistemaPacienteAdapter.carregarInformacoesPaciente(paciente);
		setPaciente(paciente);
		
		
		setGraficos(new ArrayList<CartesianChartModel>());
		CartesianChartModel graficoAreaTotalUlceras = new CartesianChartModel();
		ChartSeries series = new ChartSeries("Área Total das Úlceras do Paciente");
		series.set("20/01/2013", 50);
		series.set("25/01/2013", 61);
		series.set("30/01/2013", 55);
		series.set("01/02/2013", 50);
		series.set("05/02/2013", 45);
		series.set("10/02/2013", 65);
		series.set("15/02/2013", 30);
		graficoAreaTotalUlceras.addSeries(series);
		getGraficos().add(graficoAreaTotalUlceras);
		
		CartesianChartModel graficoNumeroTotalUlceras = new CartesianChartModel();
		series = new ChartSeries("Número Total de Úlceras do Paciente");
		series.set("20/01/2013", 3.2);
		series.set("25/01/2013", 3.4);
		series.set("30/01/2013", 3);
		series.set("01/02/2013", 3.1);
		series.set("05/02/2013", 2.8);
		series.set("10/02/2013", 2.6);
		series.set("15/02/2013", 2);
		graficoAreaTotalUlceras.addSeries(series);
		getGraficos().add(graficoNumeroTotalUlceras);
		
		return PaginasNavegacao.PACIENTE_VISUALIZAR;
	}

	public boolean isPesquisar() {
		return operacao.equals(Operacao.PESQUISAR);
	}

	public boolean isAdicionar() {
		return operacao.equals(Operacao.ADICIONAR);
	}
	
	public boolean isEnfermagem() {
		return operacao.equals(Operacao.PESQUISAR_ENFERMAGEM);
	}

	/**
	 * Retorna a tela para visualizar um paciente.
	 * @param paciente O paciente a ser visualizado.
	 * @return A tela para visualizar um paciente.
	 */
	public String adicionarPaciente(Paciente paciente) {
		setPaciente(paciente);
		return PaginasNavegacao.PACIENTE_CADASTRAR;
	}

	/**
	 * 
	 * @return 
	 */
	public String cadastrar() {
		ConsultaDAO dao = new ConsultaDAOHibernate();
		
		paciente = dao.cadastrarPaciente(paciente);
		
		return visualizarPaciente(paciente);
	}
}