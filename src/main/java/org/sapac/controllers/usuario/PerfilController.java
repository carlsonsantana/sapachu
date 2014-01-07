/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sapac.controllers.usuario;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import org.sapac.controllers.GenericController;
import org.sapac.controllers.PaginasNavegacao;
import org.sapac.entities.Usuario;
import org.sapac.models.UsuarioDAO;
import org.sapac.models.hibernate.UsuarioDAOHibernate;

/**
 *
 * @author carlson
 */
@Named
@SessionScoped
public class PerfilController extends GenericController {
	
	private String senhaAtual;
	
	private String novaSenha;
	
	private String confirmacaoNovaSenha;

	/**
	 * Usuário logado na sessão.
	 */
	private Usuario usuario;

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
	
	public boolean isMedico() {
		return usuario.isMedico();
	}
	
	public boolean isEnfermeiro() {
		return usuario.isEnfermeiro();
	}
	
	public boolean isCoordenador() {
		return usuario.isCoordenador();
	}
	
	@PostConstruct
	public void init() {
		usuario = new Usuario();
	}

	/**
	 * Fecha a sessão para o usuário.
	 *
	 * @return A tela de login.
	 */
	public String logoff() {
		clearSessions();
		return PaginasNavegacao.LOGIN;
	}

	/**
	 *
	 */
	public String telaMudarSenha() {
		return PaginasNavegacao.USUARIO_MUDAR_SENHA;
	}

	/**
	 *
	 */
	public boolean isLogado() {
		UsuarioDAO dao = new UsuarioDAOHibernate();
		if (dao.isSenhaCorreta(usuario.getNomeUsuario(), usuario.getSenha())) {
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
		UsuarioDAO dao = new UsuarioDAOHibernate();
		if (dao.isSenhaCorreta(usuario.getNomeUsuario(), usuario.getSenha())) {
			usuario = dao.carregarUsuario(usuario.getNomeUsuario(), usuario.getSenha());
			return PaginasNavegacao.PAGINA_INICIAL;
		} else {
			adicionarMensagemAlerta("Usuário não existe", "Não existe um usuário com o nome e a senha informados.");
			return PaginasNavegacao.LOGIN;
		}
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
			UsuarioDAO dao = new UsuarioDAOHibernate();
			dao.mudarSenha(usuario, novaSenha);
			
			adicionarMensagemAviso("Mudança de Senha", "Senha alterada com sucesso.");
			return PaginasNavegacao.PAGINA_INICIAL;
		}
	}
}