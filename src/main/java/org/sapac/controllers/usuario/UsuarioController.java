package org.sapac.controllers.usuario;

import java.util.ArrayList;
import java.util.Collection;
import java.util.regex.Pattern;
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

	public String getConfirmacaoSenha() {
		return confirmacaoSenha;
	}

	public void setConfirmacaoSenha(String confirmacaoSenha) {
		this.confirmacaoSenha = confirmacaoSenha;
	}

	public MembroEquipe getMembroEquipe() {
		return membroEquipe;
	}

	public void setMembroEquipe(MembroEquipe membroEquipe) {
		this.membroEquipe = membroEquipe;
	}

	public MembroEquipe getMembroEquipePesquisa() {
		return membroEquipePesquisa;
	}

	public void setMembroEquipePesquisa(MembroEquipe membroEquipePesquisa) {
		this.membroEquipePesquisa = membroEquipePesquisa;
	}

	public Collection<MembroEquipe> getListaMembros() {
		return listaMembros;
	}

	public void setListaMembros(Collection<MembroEquipe> listaMembros) {
		this.listaMembros = listaMembros;
	}

	public int getTipo() {
		return tipo;
	}

	public void setTipo(int tipo) {
		this.tipo = tipo;
	}

	@PostConstruct
	public void init() {
		membroEquipePesquisa = new MembroEquipe();
		listaMembros = new ArrayList<MembroEquipe>();
	}

	public String telaPesquisaUsuario() {
		listaMembros.clear();
		
		return PaginasNavegacao.USUARIO_PESQUISAR;
	}

	public String telaCadastrarUsuario() {
		membroEquipe = new MembroEquipe();
		membroEquipe.setUsuario(new Usuario());
		membroEquipe.getUsuario().setMembroEquipe(membroEquipe);

		return PaginasNavegacao.USUARIO_CADASTRAR;
	}

	public String telaEditarUsuario(MembroEquipe membroEquipe) {
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

			adicionarMensagemAviso("Membro cadastrado com sucesso.");

			return PaginasNavegacao.PAGINA_INICIAL;
		} else {
			return PaginasNavegacao.USUARIO_CADASTRAR;
		}
	}

	public String editar() {
		if (validarUsuario(membroEquipe.getUsuario())) {
			usuarioDAO.editarUsuario(membroEquipe.getUsuario());

			adicionarMensagemAviso("Membro editado com sucesso.");

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
				adicionarMensagemErro("Já existe um usuário com este login.");
			}
			if (usuarioExistente.getMembroEquipe().getEmail().equals(membroEquipe.getEmail().toLowerCase())) {
				adicionarMensagemErro("Já existe um usuário com este e-mail.");
			}
			if (usuarioExistente.getMembroEquipe().getCpf().equals(membroEquipe.getCpf())) {
				adicionarMensagemErro("Já existe um usuário com este CPF.");
			}
			resultado = false;
		}
		if (!isCPFValido(usuario.getMembroEquipe().getCpf())) {
			adicionarMensagemErro("O CPF informado é inválido.");
			resultado = false;
		}
		if (!usuario.getSenha().equals(confirmacaoSenha)) {
			adicionarMensagemErro("A senha digitada e sua confirmação estão diferentes.");
			resultado = false;
		}
		return resultado;
	}

	private boolean isCPFValido(String cpf) {
		if (cpf.isEmpty()) {
			return false;
		}
		if (!Pattern.matches("[0-9][0-9][0-9]\\.[0-9][0-9][0-9]\\.[0-9][0-9][0-9]-[0-9][0-9]", cpf)) {
			return false;
		}
		cpf = cpf.replace(".", "").replace(".", "").replace("-", "");
		if ((cpf.equals("00000000000"))
				|| (cpf.equals("11111111111"))
				|| (cpf.equals("22222222222"))
				|| (cpf.equals("33333333333"))
				|| (cpf.equals("44444444444"))
				|| (cpf.equals("55555555555"))
				|| (cpf.equals("66666666666"))
				|| (cpf.equals("77777777777"))
				|| (cpf.equals("88888888888"))
				|| (cpf.equals("99999999999"))
				|| (cpf.length() != 11)) {
			return false;
		}
		int digitoVerificador1;
		int digitoVerificador2;
		int resto1 = 0;
		int resto2 = 0;
		
		String digitos = cpf.substring(0, cpf.length() - 2);
		String digitosVerificadores = cpf.substring(cpf.length() - 2, cpf.length());
		for (int i = 0, length = digitos.length(); i < length; i++) {
			int digito = Integer.parseInt(digitos.substring(i, i + 1));
			
			resto1 += (10 - i) * digito;
			resto2 += (11 - i) * digito;
		}
		
		resto1 = resto1 % 11;
		
		if (resto1 < 2) {
			resto1 = 0;
		} else {
			resto1 = 11 - resto1;
		}
		
		resto2 += resto1 * 2;
		
		resto2 = resto2 % 11;
		
		if (resto2 < 2) {
			resto2 = 0;
		} else {
			resto2 = 11 - resto2;
		}
		
		return digitosVerificadores.equals(String.valueOf(resto1) + String.valueOf(resto2));
	}
}