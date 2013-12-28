/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sapac.models;

import org.sapac.entities.Usuario;

/**
 *
 * @author carlson
 */
public interface UsuarioDAO extends GenericDAO {
	public boolean isSenhaCorreta(String login, String senha);
	public Usuario mudarSenha(Usuario usuario, String senha);
	public Usuario cadastrarUsuario(Usuario usuario);
	public Usuario editarUsuario(Usuario usuario);
	public Usuario inativarUsuario(Usuario usuario);
}
