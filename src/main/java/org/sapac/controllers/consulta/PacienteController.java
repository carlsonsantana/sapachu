/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sapac.controllers.consulta;

import java.util.ArrayList;
import java.util.Collection;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.sapac.annotations.DAOQualifier;
import org.sapac.controllers.GenericController;
import org.sapac.controllers.PaginasNavegacao;
import org.sapac.entities.Consulta;
import org.sapac.entities.Paciente;
import org.sapac.models.ConsultaDAO;

/**
 *
 * @author carlson
 */
@Named
@javax.enterprise.context.SessionScoped
public class PacienteController extends GenericController {

	private static final long serialVersionUID = 1L;

	private enum Operacao {
		PESQUISAR,
		ADICIONAR,
		PESQUISAR_ENFERMAGEM;
	}
	private Paciente paciente;
	private Paciente pacientePesquisa;
	private Operacao operacao;
	private Collection<Paciente> listaPacientes;
	private transient Collection<CartesianChartModel> graficos;
	@Inject
	@DAOQualifier(DAOQualifier.DAOType.HIBERNATE)
	private ConsultaDAO consultaDAO;

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

	/**
	 * @return the listaPacientes
	 */
	public Collection<Paciente> getListaPacientes() {
		return listaPacientes;
	}

	/**
	 * @param listaPacientes the listaPacientes to set
	 */
	public void setListaPacientes(Collection<Paciente> listaPacientes) {
		this.listaPacientes = listaPacientes;
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

	@PostConstruct
	public void init() {
		paciente = new Paciente();
		pacientePesquisa = new Paciente();

		listaPacientes = new ArrayList<Paciente>();
	}

	/**
	 * Retorna a tela para pesquisar pacientes.
	 *
	 * @return A tela para pesquisar pacientes.
	 */
	public String pesquisarPaciente() {
		operacao = Operacao.PESQUISAR;

		listaPacientes = consultaDAO.procurarPacientes(pacientePesquisa);

		return PaginasNavegacao.PACIENTE_PESQUISAR;
	}

	/**
	 * Retorna a tela para cadastrar pacientes.
	 *
	 * @return A tela para cadastrar pacientes.
	 */
	public String cadastrarPaciente() {
		operacao = Operacao.ADICIONAR;

		listaPacientes = consultaDAO.procurarPacientesNaoCadastrados(pacientePesquisa);

		return PaginasNavegacao.PACIENTE_PESQUISAR;
	}

	public String pesquisarPacienteEnfermagem() {
		operacao = Operacao.PESQUISAR_ENFERMAGEM;

		listaPacientes = consultaDAO.procurarPacientes(pacientePesquisa);

		return PaginasNavegacao.PACIENTE_PESQUISAR;
	}

	/**
	 * Retorna a tela para visualizar um paciente.
	 *
	 * @param paciente O paciente a ser visualizado.
	 * @return A tela para visualizar um paciente.
	 */
	public String visualizarPaciente(Paciente paciente) {
		consultaDAO.carregarPaciente(paciente);
		setPaciente(paciente);

		paciente.setConsultas(consultaDAO.procurarConsultasPaciente(paciente));

		Collection<Consulta> consultas = paciente.getConsultas();
		setGraficos(new ArrayList<CartesianChartModel>());
		
		CartesianChartModel graficoAreaTotalUlceras = new CartesianChartModel();
		CartesianChartModel graficoNumeroTotalUlceras = new CartesianChartModel();
		CartesianChartModel graficoPressaoArterial = new CartesianChartModel();
		
		ChartSeries seriesAreaTotalUlceras = new ChartSeries("Área Total das Úlceras do Paciente");
		ChartSeries seriesNumeroTotalUlceras = new ChartSeries("Número Total de Úlceras do Paciente");
		ChartSeries seriesPressaoArterial = new ChartSeries("Pressão Arterial");
		
		for (Consulta consulta : consultas) {
			String data = getDataFormatada(consulta.getData());
			consulta = consultaDAO.carregarConsulta(consulta);
			seriesAreaTotalUlceras.set(data, consulta.getTotalAreasUlceras());
			
			seriesNumeroTotalUlceras.set(data, consulta.getSituacoesUlcera().size());
			
			seriesPressaoArterial.set(data, consulta.getVariaveisClinicas().getPressaoArterial());
		}
		graficoAreaTotalUlceras.addSeries(seriesAreaTotalUlceras);
		graficoNumeroTotalUlceras.addSeries(seriesNumeroTotalUlceras);
		graficoPressaoArterial.addSeries(seriesPressaoArterial);
		
		getGraficos().add(graficoAreaTotalUlceras);
		getGraficos().add(graficoNumeroTotalUlceras);
		getGraficos().add(graficoPressaoArterial);

		return PaginasNavegacao.PACIENTE_VISUALIZAR;
	}

	/**
	 * Retorna a tela para visualizar um paciente.
	 *
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
		paciente = consultaDAO.cadastrarPaciente(paciente);

		return visualizarPaciente(paciente);
	}
}