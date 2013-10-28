/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sapac.controllers.usuario;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.sapac.controllers.GenericController;
import org.sapac.entities.Usuario;

/**
 *
 * @author carlson
 */
@ManagedBean
@SessionScoped
public class PerfilController extends GenericController {
	
	private String senhaAtual;
	
	private String novaSenha;
	
	private String confirmacaoNovaSenha;

	/**
	 * Usuário logado na sessão.
	 */
	private Usuario usuario;

	public PerfilController() {
		usuario = new Usuario();
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
	 * Fecha a sessão para o usuário.
	 *
	 * @return A tela de login.
	 */
	public String logoff() {
		clearSessions();
		return "/public/login";
	}

	/**
	 *
	 */
	public String telaMudarSenha() {
		return "/private/usuario/mudar_senha";
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

	/**
	 * Abre uma sessão para o usuário.
	 *
	 * @return A página inicial.
	 */
	public String logar() {
		return "/private/apresentacao";
	}

	/**
	 * @return the novaSenha
	 */
	public String getNovaSenha() {
		return novaSenha;
	}

	/**
	 * @param novaSenha the novaSenha to set
	 */
	public void setNovaSenha(String novaSenha) {
		this.novaSenha = novaSenha;
	}

	/**
	 * @return the confirmacaoNovaSenha
	 */
	public String getConfirmacaoNovaSenha() {
		return confirmacaoNovaSenha;
	}

	/**
	 * @param confirmacaoNovaSenha the confirmacaoNovaSenha to set
	 */
	public void setConfirmacaoNovaSenha(String confirmacaoNovaSenha) {
		this.confirmacaoNovaSenha = confirmacaoNovaSenha;
	}
	
	public String mudarSenha() {
		boolean erro = false;
		if (!usuario.getSenha().equals(senhaAtual)) {
			adicionarMensagemErro("Senha incorreta", "A senha digitada não "
					+ "corresponde a senha atual.");
			erro = true;
		}
		if (!novaSenha.equals(confirmacaoNovaSenha)) {
			adicionarMensagemErro("Senhas diferentes", "A nova senha digitada "
					+ "e sua confirmação estão diferentes.");
			erro = true;
		}
		if (erro) {
			return null;
		} else {
			adicionarMensagemAviso("Mudança de Senha", "Senha alterada com sucesso.");
			return "/private/apresentacao";
		}
	}

	/**
	 * @return the senhaAtual
	 */
	public String getSenhaAtual() {
		return senhaAtual;
	}

	/**
	 * @param senhaAtual the senhaAtual to set
	 */
	public void setSenhaAtual(String senhaAtual) {
		this.senhaAtual = senhaAtual;
	}
}