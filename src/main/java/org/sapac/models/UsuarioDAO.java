package org.sapac.models;

import java.util.Collection;
import org.sapac.entities.MembroEquipe;
import org.sapac.entities.Usuario;

public interface UsuarioDAO extends GenericDAO {
	public boolean isSenhaCorreta(String login, String senha);
	public Usuario carregarUsuario(String login);
	public Usuario mudarSenha(Usuario usuario, String senha);
	public Usuario cadastrarUsuario(Usuario usuario);
	public Usuario editarUsuario(Usuario usuario);
	public Usuario ativarUsuario(Usuario usuario);
	public Usuario inativarUsuario(Usuario usuario);
	public Collection<MembroEquipe> pesquisarUsuario(MembroEquipe membroEquipe);
	public Usuario getUsuarioExistente(Usuario usuario);
}
