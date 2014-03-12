/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sapac.models.hibernate;

import java.util.Collection;
import javax.enterprise.context.ApplicationScoped;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.jasypt.digest.StringDigester;
import org.sapac.annotations.DAOQualifier;
import org.sapac.controllers.usuario.SpringAdapter;
import org.sapac.entities.MembroEquipe;
import org.sapac.entities.Usuario;
import org.sapac.models.UsuarioDAO;
import org.sapac.utils.HashGenerator;

/**
 *
 * @author carlson
 */
@ApplicationScoped
@DAOQualifier(DAOQualifier.DAOType.HIBERNATE)
public class UsuarioDAOHibernate extends GenericDAOHibernate implements UsuarioDAO {

	@Override
	public boolean isSenhaCorreta(String login, String senha) {
		Usuario usuario;
		if ((usuario = carregarUsuario(login)) != null) {
			if (usuario.getSenha().equals(gerarHash(senha))) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	/*  Essa versão apresenta somente carrega as informações de usuário mediante a apresentação da senha.
	 Isso não é interessante pois deste modo ele impossibilita o uso do DAO para manipular a configuração dos outros usuários.
	 Acrescenta-se a isso o uso de senhas configuradas com uso de checksum MD5, já considerado obsoleto, e a codificação é feita
	 sem qualque uso de sal.
	
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
		query.setString("senha", gerarHash(senha));
		
		Usuario usuario = (Usuario) query.uniqueResult();
		
		transaction.commit();
		
		if (usuario != null) {
			usuario.setSenha(senha);
		}
		
		return usuario;
	}
	*/

	@Override
	public Usuario carregarUsuario(String login) {
		Session session = getSession();

		Transaction transaction = session.beginTransaction();

		StringBuilder hql = new StringBuilder();
		hql.append("SELECT usuario FROM Usuario AS usuario ")
				.append(" INNER JOIN FETCH usuario.membroEquipe ")
				.append(" WHERE 1 = 1 ")
				.append(" AND usuario.ativo IS TRUE ")
				.append(" AND UPPER(usuario.nomeUsuario) = UPPER(:login) ");

		Query query = session.createQuery(hql.toString());
		query.setString("login", login);

		Usuario usuario = (Usuario) query.uniqueResult();

		transaction.commit();

		return usuario;
	}

	@Override
	public Usuario mudarSenha(Usuario usuario, String senha) {
		Session session = getSession();
		
		Transaction transaction = session.beginTransaction();
		
		usuario.setSenha(gerarHash(senha));
		session.update(usuario);
		
		transaction.commit();
		
		return usuario;
	}

	@Override
	public Usuario cadastrarUsuario(Usuario usuario) {
		Session session = getSession();
		
		Transaction transaction = session.beginTransaction();
		
		usuario.setAtivo(true);
		usuario.setSenha(gerarHash(usuario.getSenha()));
		lowerFields(usuario);
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
			usuario.setSenha(gerarHash(usuario.getSenha()));
		}
		lowerFields(usuario);
		session.merge(usuario);
		session.merge(usuario.getMembroEquipe());
		
		transaction.commit();
		
		return usuario;
	}
	
	@Override
	public Usuario ativarUsuario(Usuario usuario) {
		Session session = getSession();
		
		Transaction transaction = session.beginTransaction();
		
		usuario.setAtivo(true);
		session.update(usuario);
		
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
	
	private String gerarHash(String string) {
                StringDigester hasher = SpringAdapter.getHashGenerator();
		return hasher.digest(string);
	}

	@Override
	public Collection<MembroEquipe> pesquisarUsuario(MembroEquipe membroEquipe) {
		Session session = getSession();
		
		Transaction transaction = session.beginTransaction();
		
		StringBuilder hql = new StringBuilder();
		hql.append("SELECT membroEquipe FROM MembroEquipe AS membroEquipe ")
				.append(" WHERE 1 = 1 ");
		if ((membroEquipe.getNome() != null) && (!membroEquipe.getNome().isEmpty())) {
			hql.append(" AND UPPER(membroEquipe.nome) LIKE UPPER(:nome) ");
		}
		
		Query query = session.createQuery(hql.toString());
		if ((membroEquipe.getNome() != null) && (!membroEquipe.getNome().isEmpty())) {
			query.setString("nome", "%" + membroEquipe.getNome() + "%");
		}
		
		Collection<MembroEquipe> membros = (Collection<MembroEquipe>) query.list();
		
		transaction.commit();
		
		return membros;
	}

	@Override
	public Usuario getUsuarioExistente(Usuario usuario) {
		Session session = getSession();
		
		Transaction transaction = session.beginTransaction();
		
		StringBuilder hql = new StringBuilder();
		hql.append("SELECT usuario FROM Usuario AS usuario ")
				.append(" WHERE 1 = 1")
				.append(" AND usuario.id <> :idUsuario ")
				.append(" AND ( ")
				.append(" UPPER(usuario.nomeUsuario) = UPPER(:nomeUsuario) ")
				.append(" OR usuario.membroEquipe.cpf = :cpf ")
				.append(" OR UPPER(usuario.membroEquipe.email) = UPPER(:email) ")
				.append(" ) ");
		
		Query query = session.createQuery(hql.toString());
		query.setInteger("idUsuario", usuario.getId());
		query.setString("nomeUsuario", usuario.getNomeUsuario());
		query.setString("cpf", usuario.getMembroEquipe().getCpf());
		query.setString("email", usuario.getMembroEquipe().getEmail());
		query.setMaxResults(1);
		
		Usuario usuarioExistente = (Usuario) query.uniqueResult();
		
		transaction.commit();
		
		return usuarioExistente;
	}
	
	private void lowerFields(Usuario usuario) {
		usuario.setNomeUsuario(usuario.getNomeUsuario().toLowerCase());
		usuario.getMembroEquipe().setEmail(usuario.getMembroEquipe().getEmail().toLowerCase());
	}
}
