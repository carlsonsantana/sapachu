/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sapac.controllers.usuario;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.inject.Named;
import org.sapac.controllers.GenericController;
import org.sapac.controllers.PaginasNavegacao;
import org.sapac.entities.Enfermeiro;
import org.sapac.entities.MembroEquipe;
import org.sapac.entities.Medico;

/**
 *
 * @author carlson
 */
@Named
@javax.enterprise.context.SessionScoped
public class UsuarioController extends GenericController {
	
	//@Inject
	//private Conversation conversation;

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
	 * @return the senha
	 */
	public String getSenha() {
		return senha;
	}

	/**
	 * @param senha the senha to set
	 */
	public void setSenha(String senha) {
		this.senha = senha;
	}

	/**
	 * @return the confirmacaoSenha
	 */
	public String getConfirmacaoSenha() {
		return confirmacaoSenha;
	}

	/**
	 * @param confirmacaoSenha the confirmacaoSenha to set
	 */
	public void setConfirmacaoSenha(String confirmacaoSenha) {
		this.confirmacaoSenha = confirmacaoSenha;
	}

	private enum Operacao {
		FUNCIONARIO,
		USUARIO;
	}
	private Operacao operacao;
	private MembroEquipe funcionario;
	private transient DataModel<MembroEquipe> listaFuncionaros;
	private String senha;
	private String confirmacaoSenha;
	
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
	}

	public String telaPesquisaFuncionario() {
		operacao = Operacao.FUNCIONARIO;
		return PaginasNavegacao.USUARIO_PESQUISAR;
	}

	public String telaPesquisaUsuario() {
		operacao = Operacao.USUARIO;
		return PaginasNavegacao.USUARIO_PESQUISAR;
	}

	public boolean isPesquisarFuncionario() {
		return operacao.equals(Operacao.FUNCIONARIO);
	}

	public boolean isPesquisarUsuario() {
		return operacao.equals(Operacao.USUARIO);
	}

	public String cadastrarFuncionario() {
		//this.funcionario = funcionario;
		return PaginasNavegacao.USUARIO_CADASTRAR;
	}

	public String editarUsuario(MembroEquipe funcionario) {
		//conversation.begin();
		//System.out.println("Conversation Begin");
		this.funcionario = funcionario;
		return PaginasNavegacao.USUARIO_EDITAR;
	}
	
	public String cadastrar(MembroEquipe membroEquipe) {
		if (!senha.equals(confirmacaoSenha)) {
			adicionarMensagemErro("Senhas diferentes", "A senha digitada "
					+ "e sua confirmação estão diferentes.");
			return null;
		} else {
			adicionarMensagemAviso("Membro cadastrado", "Membro cadastrado com Sucesso.");
			return PaginasNavegacao.USUARIO_PESQUISAR;
		}
	}
	
	public String editar(MembroEquipe funcionario) {
		if (!senha.equals(confirmacaoSenha)) {
			adicionarMensagemErro("Senhas diferentes", "A senha digitada "
					+ "e sua confirmação estão diferentes.");
			return null;
		} else {
			adicionarMensagemAviso("Membro editado", "Membro editado com Sucesso.");
			return PaginasNavegacao.PAGINA_INICIAL;
		}
	}
}