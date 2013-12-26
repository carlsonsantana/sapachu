/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sapac.controllers.relatorio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.inject.Named;
import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.sapac.controllers.GenericController;
import org.sapac.controllers.PaginasNavegacao;
import org.sapac.entities.Enfermeiro;
import org.sapac.entities.MembroEquipe;
import org.sapac.entities.Medico;

/**
 *
 * @author carlson
 */
@ManagedBean
@SessionScoped
public class RelatorioController extends GenericController {

	private MembroEquipe funcionario;
	private MembroEquipe funcionarioPesquisa;
	private transient Collection<CartesianChartModel> graficos;
	private transient DataModel<MembroEquipe> listaFuncionaros;

	@PostConstruct
	public void init() {
		List<MembroEquipe> funcionarios = new ArrayList<MembroEquipe>();
		MembroEquipe f = new Medico();
		f.setNome("Lia Chagas");
		funcionarios.add(f);
		f = new Medico();
		f.setNome("Fernando Brito");
		funcionarios.add(f);
		f = new Enfermeiro();
		f.setNome("Marta Jasmine");
		funcionarios.add(f);
		listaFuncionaros = new ListDataModel<MembroEquipe>(funcionarios);
		funcionario = new Medico();
		funcionarioPesquisa = new Medico();
	}

	/**
	 * @return the funcionario
	 */
	public MembroEquipe getFuncionario() {
		return funcionario;
	}

	/**
	 * @param funcionario the funcionario to set
	 */
	public void setFuncionario(MembroEquipe funcionario) {
		this.funcionario = funcionario;
	}

	public String gerarRelatorio(MembroEquipe funcionario) {
		this.funcionario = funcionario;

		setGraficos(new ArrayList<CartesianChartModel>());
		CartesianChartModel graficoAreaTotalUlceras = new CartesianChartModel();
		ChartSeries series = new ChartSeries("Área Média do Total de Úlceras dos Pacientes");
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
		series = new ChartSeries("Número Médio de Total de Úlceras dos Pacientes");
		series.set("20/01/2013", 3.2);
		series.set("25/01/2013", 3.4);
		series.set("30/01/2013", 3);
		series.set("01/02/2013", 3.1);
		series.set("05/02/2013", 2.8);
		series.set("10/02/2013", 2.6);
		series.set("15/02/2013", 2);
		graficoAreaTotalUlceras.addSeries(series);
		getGraficos().add(graficoNumeroTotalUlceras);

		return PaginasNavegacao.RELATORIO_VISUALIZAR;
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
	 * @return the listaFuncionaros
	 */
	public DataModel<MembroEquipe> getListaFuncionaros() {
		return listaFuncionaros;
	}

	/**
	 * @param listaFuncionaros the listaFuncionaros to set
	 */
	public void setListaFuncionaros(DataModel<MembroEquipe> listaFuncionaros) {
		this.listaFuncionaros = listaFuncionaros;
	}

	/**
	 * @return the funcionarioPesquisa
	 */
	public MembroEquipe getFuncionarioPesquisa() {
		return funcionarioPesquisa;
	}

	/**
	 * @param funcionarioPesquisa the funcionarioPesquisa to set
	 */
	public void setFuncionarioPesquisa(MembroEquipe funcionarioPesquisa) {
		this.funcionarioPesquisa = funcionarioPesquisa;
	}

	public String pesquisarUsuarioRelatorio() {
		return PaginasNavegacao.RELATORIO_PESQUISAR_USUARIO;
	}
}