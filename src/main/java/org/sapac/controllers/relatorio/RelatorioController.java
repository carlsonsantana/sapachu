/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sapac.controllers.relatorio;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import org.sapac.annotations.DAOQualifier;
import org.sapac.controllers.GenericController;
import org.sapac.controllers.PaginasNavegacao;
import org.sapac.entities.Medico;
import org.sapac.entities.MembroEquipe;
import org.sapac.models.RelatorioDAO;
import org.sapac.models.UsuarioDAO;

/**
 *
 * @author carlson
 */
@Named
@javax.enterprise.context.SessionScoped
public class RelatorioController extends GenericController {

	private MembroEquipe funcionario;
	private MembroEquipe funcionarioPesquisa;
	private transient Collection<Map<String, String>> dados;
	private Collection<MembroEquipe> listaFuncionaros;
	
	@Inject
	@DAOQualifier(DAOQualifier.DAOType.HIBERNATE)
	private RelatorioDAO relatorioDAO;
	
	@Inject
	@DAOQualifier(DAOQualifier.DAOType.HIBERNATE)
	private UsuarioDAO usuarioDAO;

	@PostConstruct
	public void init() {
		listaFuncionaros = new ArrayList<MembroEquipe>();
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

		dados = relatorioDAO.gerarRelatorio(funcionario);

		return PaginasNavegacao.RELATORIO_VISUALIZAR;
	}

	/**
	 * @return the graficos
	 */
	public Collection<Map<String, String>> getDados() {
		return dados;
	}

	/**
	 * @param graficos the graficos to set
	 */
	public void setGraficos(Collection<Map<String, String>> dados) {
		this.dados = dados;
	}

	/**
	 * @return the listaFuncionaros
	 */
	public Collection<MembroEquipe> getListaFuncionaros() {
		return listaFuncionaros;
	}

	/**
	 * @param listaFuncionaros the listaFuncionaros to set
	 */
	public void setListaFuncionaros(Collection<MembroEquipe> listaFuncionaros) {
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
		listaFuncionaros.clear();
		
		listaFuncionaros = usuarioDAO.pesquisarUsuario(funcionarioPesquisa);
		
		return PaginasNavegacao.RELATORIO_PESQUISAR_USUARIO;
	}
}