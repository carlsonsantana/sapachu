/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sapac.models.hibernate;

import org.sapac.entities.Usuario;
import org.sapac.models.UsuarioDAO;

/**
 *
 * @author carlson
 */
public class UsuarioDAOHibernate extends GenericDAOHibernate implements UsuarioDAO {

	@Override
	public boolean isSenhaCorreta(String login, String senha) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public Usuario mudarSenha(Usuario usuario, String senha) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public Usuario cadastrarUsuario(Usuario usuario) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public Usuario editarUsuario(Usuario usuario) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public Usuario inativarUsuario(Usuario usuario) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}
	
}
