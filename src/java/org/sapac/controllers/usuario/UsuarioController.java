/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sapac.controllers.usuario;

import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import org.sapac.controllers.GenericController;
import org.sapac.entities.Enfermeiro;
import org.sapac.entities.Funcionario;
import org.sapac.entities.Medico;
import org.sapac.entities.Usuario;

/**
 *
 * @author carlson
 */
@ManagedBean
@SessionScoped
public class UsuarioController extends GenericController {

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

	private enum Operacao {
		FUNCIONARIO,
		USUARIO;
	}
	
	private Operacao operacao;
	
	/**
	 * Usuário logado na sessão.
	 */
	private Usuario usuario;
	
	private Funcionario funcionario;
	
	private transient DataModel<Funcionario> listaFuncionaros;

	/**
	 * Contrutor.
	 */
	public UsuarioController() {
		super();
		usuario = new Usuario();
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
	}

	/**
	 * @return the usuario
	 */
	public Usuario getUsuario() {
		return usuario;
	}

	/**
	 * @param usuario the usuario to set
	 */
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	/**
	 * Abre uma sessão para o usuário.
	 * @return A página inicial.
	 */
	public String logar() {
		return "/private/apresentacao.xhtml";
	}

	/**
	 * Fecha a sessão para o usuário.
	 * @return A tela de login.
	 */
	public String logoff() {
		getRequest().getSession().invalidate();
		return "/public/login.xhtml";
	}
	
	/**
	 * 
	 */
	public String mudarSenha() {
		return "/private/perfil/mudar_senha.xhtml";
	}
	
	/**
	 * 
	 */
	public boolean isLogado() {
		if (((usuario.getNomeUsuario() != null) && (!usuario.getNomeUsuario().isEmpty()))
				&& ((usuario.getSenha() != null) && (!usuario.getSenha().isEmpty()))) {
			return true;
		} else {
			return false;
		}
	}
	
	public String telaPesquisaFuncionario() {
		operacao = Operacao.FUNCIONARIO;
		return "/private/usuario/pesquisar.xhtml";
	}
	
	public String telaPesquisaUsuario() {
		operacao = Operacao.USUARIO;
		return "/private/usuario/pesquisar.xhtml";
	}
	
	public boolean isPesquisarFuncionario() {
		return operacao.equals(Operacao.FUNCIONARIO);
	}
	
	public boolean isPesquisarUsuario() {
		return operacao.equals(Operacao.USUARIO);
	}
	
	public String cadastrarFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
		return "/private/usuario/cadastrar.xhtml";
	}
	
	public String editarUsuario(Funcionario funcionario) {
		this.funcionario = funcionario;
		return "/private/usuario/editar.xhtml";
	}
}