/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sapac.models;

import java.util.Collection;
import org.sapac.entities.MembroEquipe;
import org.sapac.entities.Usuario;

/**
 *
 * @author carlson
 */
public interface UsuarioDAO extends GenericDAO {
	public boolean isSenhaCorreta(String login, String senha);
	//public Usuario carregarUsuario(String login, String senha); Desativado para permitir edição de outros usuários.
	public Usuario carregarUsuario(String login); //Novo carregar usuário
	public Usuario mudarSenha(Usuario usuario, String senha);
	public Usuario cadastrarUsuario(Usuario usuario);
	public Usuario editarUsuario(Usuario usuario);
	public Usuario inativarUsuario(Usuario usuario);
	public Collection<MembroEquipe> pesquisarUsuario(MembroEquipe membroEquipe);
}
