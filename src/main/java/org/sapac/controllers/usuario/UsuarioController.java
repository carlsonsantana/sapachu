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
		
		return PaginasNavegacao.USUARIO_CADASTRAR;
	}

	public String editarUsuario(MembroEquipe membroEquipe) {
		setMembroEquipe(membroEquipe);
		
		return PaginasNavegacao.USUARIO_EDITAR;
	}

	public String cadastrar() {
		if (!membroEquipe.getUsuario().getSenha().equals(confirmacaoSenha)) {
			adicionarMensagemErro("Senhas diferentes", "A senha digitada "
					+ "e sua confirmação estão diferentes.");
			return PaginasNavegacao.USUARIO_CADASTRAR;
		} else {
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
			membro.getUsuario().setMembroEquipe(membroEquipe);
			
			usuarioDAO.cadastrarUsuario(membroEquipe.getUsuario());
			
			adicionarMensagemAviso("Membro cadastrado", "Membro cadastrado com Sucesso.");
			
			return PaginasNavegacao.USUARIO_PESQUISAR;
		}
	}

	public String editar() {
		if (!membroEquipe.getUsuario().getSenha().equals(confirmacaoSenha)) {
			adicionarMensagemErro("Senhas diferentes", "A senha digitada "
					+ "e sua confirmação estão diferentes.");
			return PaginasNavegacao.USUARIO_EDITAR;
		} else {
			usuarioDAO.editarUsuario(membroEquipe.getUsuario());
			
			adicionarMensagemAviso("Membro editado", "Membro editado com Sucesso.");
			
			return PaginasNavegacao.PAGINA_INICIAL;
		}
	}
	
	public void pesquisarUsuario() {
		listaMembros = usuarioDAO.pesquisarUsuario(membroEquipePesquisa);
	}
}