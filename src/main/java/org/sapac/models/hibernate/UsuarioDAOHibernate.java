/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sapac.models.hibernate;

import java.util.Collection;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.sapac.entities.MembroEquipe;
import org.sapac.entities.Usuario;
import org.sapac.models.UsuarioDAO;
import org.sapac.utils.MD5Generator;

/**
 *
 * @author carlson
 */
public class UsuarioDAOHibernate extends GenericDAOHibernate implements UsuarioDAO {

	@Override
	public boolean isSenhaCorreta(String login, String senha) {
		if (carregarUsuario(login, senha) != null) {
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public Usuario carregarUsuario(String login, String senha) {
		Session session = getSession();
		
		Transaction transaction = session.beginTransaction();
		
		StringBuilder hql = new StringBuilder();
		hql.append("SELECT usuario FROM Usuario AS usuario ")
				.append(" INNER JOIN FETCH usuario.membroEquipe ")
				.append(" WHERE 1 = 1 ")
				.append(" AND usuario.nomeUsuario = :login ")
				.append(" AND usuario.senha = :senha ");
		
		Query query = session.createQuery(hql.toString());
		query.setString("login", login);
		query.setString("senha", gerarMD5(senha));
		
		Usuario usuario = (Usuario) query.uniqueResult();
		
		transaction.commit();
		
		if (usuario != null) {
			usuario.setSenha(senha);
		}
		
		return usuario;
	}

	@Override
	public Usuario mudarSenha(Usuario usuario, String senha) {
		Session session = getSession();
		
		Transaction transaction = session.beginTransaction();
		
		usuario.setSenha(gerarMD5(senha));
		session.update(usuario);
		
		transaction.commit();
		
		return usuario;
	}

	@Override
	public Usuario cadastrarUsuario(Usuario usuario) {
		Session session = getSession();
		
		Transaction transaction = session.beginTransaction();
		
		usuario.setAtivo(true);
		usuario.setSenha(gerarMD5(usuario.getSenha()));
		session.save(usuario);
		session.save(usuario.getMembroEquipe());
		
		transaction.commit();
		
		return usuario;
	}

	@Override
	public Usuario editarUsuario(Usuario usuario) {
		Session session = getSession();
		
		Transaction transaction = session.beginTransaction();
		
		if ((usuario.getSenha() == null) || (usuario.getSenha().isEmpty())) {
			Usuario user = (Usuario) session.get(Usuario.class, usuario.getId());
			usuario.setSenha(user.getSenha());
		} else {
			usuario.setSenha(gerarMD5(usuario.getSenha()));
		}
		
		session.update(usuario);
		session.update(usuario.getMembroEquipe());
		
		transaction.commit();
		
		return usuario;
	}

	@Override
	public Usuario inativarUsuario(Usuario usuario) {
		Session session = getSession();
		
		Transaction transaction = session.beginTransaction();
		
		usuario.setAtivo(false);
		session.update(usuario);
		
		transaction.commit();
		
		return usuario;
	}
	
	private String gerarMD5(String string) {
		return MD5Generator.gerar(string);
	}

	@Override
	public Collection<MembroEquipe> pesquisarUsuario(MembroEquipe membroEquipe) {
		Session session = getSession();
		
		Transaction transaction = session.beginTransaction();
		
		StringBuilder hql = new StringBuilder();
		hql.append("SELECT membroEquipe FROM MembroEquipe AS membroEquipe ")
				.append(" WHERE 1 = 1 ");
		if ((membroEquipe.getNome() != null) && (!membroEquipe.getNome().isEmpty())) {
			hql.append(" AND membroEquipe.nome LIKE :nome ");
		}
		
		Query query = session.createQuery(hql.toString());
		if ((membroEquipe.getNome() != null) && (!membroEquipe.getNome().isEmpty())) {
			query.setString("nome", "%" + membroEquipe.getNome() + "%");
		}
		
		Collection<MembroEquipe> membros = (Collection<MembroEquipe>) query.list();
		
		transaction.commit();
		
		return membros;
	}
}
