package org.sapac.controllers.usuario;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.sapac.annotations.DAOQualifier;
import org.sapac.controllers.GenericController;
import org.sapac.controllers.PaginasNavegacao;
import org.sapac.entities.Usuario;
import org.sapac.models.UsuarioDAO;
import org.sapac.utils.HashGenerator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

@Named
@SessionScoped
public class PerfilController extends GenericController {

	private String senhaAtual;

	private String novaSenha;

	private String confirmacaoNovaSenha;
	@Inject
	@DAOQualifier(DAOQualifier.DAOType.HIBERNATE)
	private UsuarioDAO usuarioDAO;

	private Usuario usuario;

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public String getNovaSenha() {
		return novaSenha;
	}

	public void setNovaSenha(String novaSenha) {
		this.novaSenha = novaSenha;
	}

	public String getConfirmacaoNovaSenha() {
		return confirmacaoNovaSenha;
	}

	public void setConfirmacaoNovaSenha(String confirmacaoNovaSenha) {
		this.confirmacaoNovaSenha = confirmacaoNovaSenha;
	}
	
	public String getSenhaAtual() {
		return senhaAtual;
	}

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
		if (usuario == null) {
			String userName = null;
			SecurityContext context = SecurityContextHolder.getContext();
			if (context instanceof SecurityContext) {
				Authentication authentication = context.getAuthentication();
				if (authentication instanceof Authentication) {
					userName = (authentication.getPrincipal()).toString();
				}
			}
			usuario = usuarioDAO.carregarUsuario(userName);
		}
	}

	public String logoff() {
		clearSession();
		return PaginasNavegacao.LOGIN;
	}

	public String telaMudarSenha() {
		return PaginasNavegacao.USUARIO_MUDAR_SENHA;
	}

	public boolean isLogado() {
		return (usuarioDAO.isSenhaCorreta(usuario.getNomeUsuario(), usuario.getSenha()));
	}

	public String logar() {
		if (usuarioDAO.isSenhaCorreta(usuario.getNomeUsuario(), usuario.getSenha())) {
			usuario = usuarioDAO.carregarUsuario(usuario.getNomeUsuario());
			
			return PaginasNavegacao.PAGINA_INICIAL;
		} else {
			adicionarMensagemAlerta("Não existe um usuário com o nome e a senha informados.");
			
			return PaginasNavegacao.LOGIN;
		}
	}
	
	public String mudarSenha() {
		boolean erro = false;
		if (!usuario.getSenha().equals(HashGenerator.gerar(senhaAtual))) {
			adicionarMensagemErro("A senha digitada não corresponde a senha atual.");
			erro = true;
		}
		if (!novaSenha.equals(confirmacaoNovaSenha)) {
			adicionarMensagemErro("A nova senha digitada e sua confirmação estão diferentes.");
			erro = true;
		}
		
		if (erro) {
			return PaginasNavegacao.USUARIO_MUDAR_SENHA;
		} else {
			usuarioDAO.mudarSenha(usuario, novaSenha);
			
			adicionarMensagemAviso("Senha alterada com sucesso.");
			
			return PaginasNavegacao.PAGINA_INICIAL;
		}
	}
}