/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sapac.controllers.relatorio;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.sapac.controllers.GenericController;
import org.sapac.entities.Enfermeiro;
import org.sapac.entities.Funcionario;
import org.sapac.entities.Medico;

/**
 *
 * @author carlson
 */
@ManagedBean
@SessionScoped
public class RelatorioController extends GenericController {
	private Funcionario funcionario;
	private Funcionario funcionarioPesquisa;
	private transient Collection<CartesianChartModel> graficos;
	private transient DataModel<Funcionario> listaFuncionaros;
	
	public RelatorioController() {
		super();
		List<Funcionario> funcionarios = new ArrayList<Funcionario>();
		Funcionario f = new Medico();
		f.setNome("Lia Chagas");
		funcionarios.add(f);
		f = new Medico();
		f.setNome("Fernando Brito");
		funcionarios.add(f);
		f = new Enfermeiro();
		f.setNome("Marta Jasmine");
		funcionarios.add(f);
		listaFuncionaros = new ListDataModel<Funcionario>(funcionarios);
		funcionario = new Medico();
		funcionarioPesquisa = new Medico();
	}

	/**
	 * @return the funcionario
	 */
	public Funcionario getFuncionario() {
		return funcionario;
	}

	/**
	 * @param funcionario the funcionario to set
	 */
	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}
	
	public String gerarRelatorio(Funcionario funcionario) {
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
		
		return "/private/relatorio/relatorio_desempenho.xhtml";
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
	public DataModel<Funcionario> getListaFuncionaros() {
		return listaFuncionaros;
	}

	/**
	 * @param listaFuncionaros the listaFuncionaros to set
	 */
	public void setListaFuncionaros(DataModel<Funcionario> listaFuncionaros) {
		this.listaFuncionaros = listaFuncionaros;
	}

	/**
	 * @return the funcionarioPesquisa
	 */
	public Funcionario getFuncionarioPesquisa() {
		return funcionarioPesquisa;
	}

	/**
	 * @param funcionarioPesquisa the funcionarioPesquisa to set
	 */
	public void setFuncionarioPesquisa(Funcionario funcionarioPesquisa) {
		this.funcionarioPesquisa = funcionarioPesquisa;
	}
}