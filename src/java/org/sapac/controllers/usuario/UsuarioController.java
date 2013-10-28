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

/**
 *
 * @author carlson
 */
@ManagedBean
@SessionScoped
public class UsuarioController extends GenericController {
	
	//@Inject
	//private Conversation conversation;

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
	private Funcionario funcionario;
	private transient DataModel<Funcionario> listaFuncionaros;

	/**
	 * Contrutor.
	 */
	public UsuarioController() {
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
	}

	public String telaPesquisaFuncionario() {
		operacao = Operacao.FUNCIONARIO;
		return "/private/usuario/pesquisar";
	}

	public String telaPesquisaUsuario() {
		operacao = Operacao.USUARIO;
		return "/private/usuario/pesquisar";
	}

	public boolean isPesquisarFuncionario() {
		return operacao.equals(Operacao.FUNCIONARIO);
	}

	public boolean isPesquisarUsuario() {
		return operacao.equals(Operacao.USUARIO);
	}

	public String cadastrarFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
		return "/private/usuario/cadastrar";
	}

	public String editarUsuario(Funcionario funcionario) {
		//conversation.begin();
		//System.out.println("Conversation Begin");
		this.funcionario = funcionario;
		return "/private/usuario/editar";
	}
	
	public String lan(Funcionario funcionario) {
		//conversation.end();
		//System.out.println("Conversation End");
		return "/private/apresentacao";
	}
}