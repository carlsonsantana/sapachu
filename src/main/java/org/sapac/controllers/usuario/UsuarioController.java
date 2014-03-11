/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sapac.controllers.usuario;

import java.util.Collection;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.sapac.annotations.DAOQualifier;
import org.sapac.controllers.GenericController;
import org.sapac.controllers.PaginasNavegacao;
import org.sapac.entities.Enfermeiro;
import org.sapac.entities.Medico;
import org.sapac.entities.MembroEquipe;
import org.sapac.entities.Usuario;
import org.sapac.models.UsuarioDAO;

/**
 *
 * @author carlson
 */
@Named
@SessionScoped
public class UsuarioController extends GenericController {

	private MembroEquipe membroEquipe;
	private MembroEquipe membroEquipePesquisa;
	private Collection<MembroEquipe> listaMembros;
	private String confirmacaoSenha;
	private int tipo;
	@Inject
	@DAOQualifier(DAOQualifier.DAOType.HIBERNATE)
	private UsuarioDAO usuarioDAO;

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

	/**
	 * @return the membroEquipe
	 */
	public MembroEquipe getMembroEquipe() {
		return membroEquipe;
	}

	/**
	 * @param membroEquipe the membroEquipe to set
	 */
	public void setMembroEquipe(MembroEquipe membroEquipe) {
		this.membroEquipe = membroEquipe;
	}

	/**
	 * @return the membroEquipePesquisa
	 */
	public MembroEquipe getMembroEquipePesquisa() {
		return membroEquipePesquisa;
	}

	/**
	 * @param membroEquipePesquisa the membroEquipePesquisa to set
	 */
	public void setMembroEquipePesquisa(MembroEquipe membroEquipePesquisa) {
		this.membroEquipePesquisa = membroEquipePesquisa;
	}

	/**
	 * @return the listaMembros
	 */
	public Collection<MembroEquipe> getListaMembros() {
		return listaMembros;
	}

	/**
	 * @param listaMembros the listaMembros to set
	 */
	public void setListaMembros(Collection<MembroEquipe> listaMembros) {
		this.listaMembros = listaMembros;
	}

	/**
	 * @return the tipo
	 */
	public int getTipo() {
		return tipo;
	}

	/**
	 * @param tipo the tipo to set
	 */
	public void setTipo(int tipo) {
		this.tipo = tipo;
	}

	@PostConstruct
	public void init() {
		membroEquipePesquisa = new MembroEquipe();
	}

	public String telaPesquisaUsuario() {
		pesquisarUsuario();

		return PaginasNavegacao.USUARIO_PESQUISAR;
	}

	public String telaCadastrarUsuario() {
		membroEquipe = new MembroEquipe();
		membroEquipe.setUsuario(new Usuario());
		membroEquipe.getUsuario().setMembroEquipe(membroEquipe);

		return PaginasNavegacao.USUARIO_CADASTRAR;
	}

	public String editarUsuario(MembroEquipe membroEquipe) {
		setMembroEquipe(membroEquipe);
		if (membroEquipe.isEnfermeiro()) {
			tipo = 1;
		} else if (membroEquipe.isMedico()) {
			tipo = 2;
		}

		return PaginasNavegacao.USUARIO_EDITAR;
	}

	public String inativarUsuario(MembroEquipe membroEquipe) {
		usuarioDAO.inativarUsuario(membroEquipe.getUsuario());

		return PaginasNavegacao.USUARIO_PESQUISAR;
	}

	public String ativarUsuario(MembroEquipe membroEquipe) {
		usuarioDAO.ativarUsuario(membroEquipe.getUsuario());

		return PaginasNavegacao.USUARIO_PESQUISAR;
	}

	public String cadastrar() {
		if (validarUsuario(membroEquipe.getUsuario())) {
			MembroEquipe membro;
			if (tipo == 1) {
				membro = new Enfermeiro();
			} else {
				membro = new Medico();
			}

			membro.setCpf(membroEquipe.getCpf());
			membro.setEmail(membroEquipe.getEmail());
			membro.setMatricula(membroEquipe.getMatricula());
			membro.setNome(membroEquipe.getNome());
			membro.setRg(membroEquipe.getRg());
			membro.setVinculo(membro.getVinculo());
			membro.setUsuario(membroEquipe.getUsuario());
			membro.getUsuario().setMembroEquipe(membro);

			usuarioDAO.cadastrarUsuario(membro.getUsuario());

			adicionarMensagemAviso("Membro cadastrado", "Membro cadastrado com Sucesso.");

			return PaginasNavegacao.USUARIO_PESQUISAR;
		} else {
			return PaginasNavegacao.USUARIO_CADASTRAR;
		}
	}

	public String editar() {
		if (validarUsuario(membroEquipe.getUsuario())) {
			usuarioDAO.editarUsuario(membroEquipe.getUsuario());

			adicionarMensagemAviso("Membro editado", "Membro editado com Sucesso.");

			return PaginasNavegacao.PAGINA_INICIAL;
		} else {
			return PaginasNavegacao.USUARIO_EDITAR;
		}
	}

	public void pesquisarUsuario() {
		listaMembros = usuarioDAO.pesquisarUsuario(membroEquipePesquisa);
	}

	private boolean validarUsuario(Usuario usuario) {
		boolean resultado = true;
		Usuario usuarioExistente = usuarioDAO.getUsuarioExistente(usuario);
		if (usuarioExistente != null) {
			if (usuarioExistente.getNomeUsuario().equals(membroEquipe.getUsuario().getNomeUsuario().toLowerCase())) {
				adicionarMensagemErro("Login já existe", "Já existe um usuário com este login.");
			}
			if (usuarioExistente.getMembroEquipe().getEmail().equals(membroEquipe.getEmail().toLowerCase())) {
				adicionarMensagemErro("E-mail já existe", "Já existe um usuário com este e-mail.");
			}
			if (usuarioExistente.getMembroEquipe().getCpf().equals(membroEquipe.getCpf())) {
				adicionarMensagemErro("CPF já existe", "Já existe um usuário com este CPF.");
			}
			resultado = false;
		}
		if (!isCPFValido(usuario.getMembroEquipe().getCpf())) {
			adicionarMensagemErro("CPF inválido", "O CPF informado é inválido.");
			resultado = false;
		}
		if (!usuario.getSenha().equals(confirmacaoSenha)) {
			adicionarMensagemErro("Senhas diferentes", "A senha digitada "
					+ "e sua confirmação estão diferentes.");
			resultado = false;
		}
		return resultado;
	}

	private boolean isCPFValido(String cpf) {
		//TODO Validação do CPF
		return true;
	}
}