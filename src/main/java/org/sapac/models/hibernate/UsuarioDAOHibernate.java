package org.sapac.models.hibernate;

import java.util.Collection;
import javax.enterprise.context.ApplicationScoped;
import org.hibernate.Query;
import org.hibernate.Session;
import org.jasypt.digest.StringDigester;
import org.sapac.annotations.DAOQualifier;
import org.sapac.controllers.usuario.SpringAdapter;
import org.sapac.entities.MembroEquipe;
import org.sapac.entities.Usuario;
import org.sapac.models.UsuarioDAO;
import org.sapac.utils.HashGenerator;

@ApplicationScoped
@DAOQualifier(DAOQualifier.DAOType.HIBERNATE)
public class UsuarioDAOHibernate extends GenericDAOHibernate implements UsuarioDAO {

	@Override
	public boolean isSenhaCorreta(String login, String senha) {
		Usuario usuario = carregarUsuario(login);
		return ((usuario != null) && (usuario.getSenha().equals(gerarHash(senha))));
	}

	@Override
	public Usuario carregarUsuario(String login) {
		Session session = getSession();

		StringBuilder hql = new StringBuilder();
		hql.append("SELECT usuario FROM Usuario AS usuario ")
				.append(" INNER JOIN FETCH usuario.membroEquipe ")
				.append(" WHERE 1 = 1 ")
				.append(" AND usuario.ativo IS TRUE ")
				.append(" AND UPPER(usuario.nomeUsuario) = UPPER(:login) ");

		Query query = session.createQuery(hql.toString());
		query.setString("login", login);

		Usuario usuario = (Usuario) query.uniqueResult();
		
		closeSession();

		return usuario;
	}

	@Override
	public Usuario mudarSenha(Usuario usuario, String senha) {
		Session session = getSession();
		
		usuario.setSenha(gerarHash(senha));
		session.update(usuario);
		
		closeSession();
		
		return usuario;
	}

	@Override
	public Usuario cadastrarUsuario(Usuario usuario) {
		Session session = getSession();
		
		usuario.setAtivo(true);
		usuario.setSenha(gerarHash(usuario.getSenha()));
		lowerFields(usuario);
		session.save(usuario);
		session.save(usuario.getMembroEquipe());
		
		closeSession();
		
		return usuario;
	}

	@Override
	public Usuario editarUsuario(Usuario usuario) {
		Session session = getSession();

		if ((usuario.getSenha() == null) || (usuario.getSenha().isEmpty())) {
			Usuario user = (Usuario) session.get(Usuario.class, usuario.getId());
			usuario.setSenha(user.getSenha());
		} else {
			usuario.setSenha(gerarHash(usuario.getSenha()));
		}
		
		closeSession();
		
		lowerFields(usuario);
		
		getSession().update(usuario);
		getSession().update(usuario.getMembroEquipe());
		
		closeSession();
		
		return usuario;
	}
	
	@Override
	public Usuario ativarUsuario(Usuario usuario) {
		Session session = getSession();
		
		usuario.setAtivo(true);
		session.update(usuario);
		
		closeSession();
		
		return usuario;
	}

	@Override
	public Usuario inativarUsuario(Usuario usuario) {
		Session session = getSession();
		
		usuario.setAtivo(false);
		session.update(usuario);
		
		closeSession();
		
		return usuario;
	}
	
	private String gerarHash(String string) {
		StringDigester hasher = SpringAdapter.getHashGenerator();
		return hasher.digest(string);
	}

	@Override
	public Collection<MembroEquipe> pesquisarUsuario(MembroEquipe membroEquipe) {
		Session session = getSession();
		
		StringBuilder hql = new StringBuilder();
		hql.append("SELECT membroEquipe FROM MembroEquipe AS membroEquipe ")
				.append(" INNER JOIN FETCH membroEquipe.usuario AS usuario ")
				.append(" WHERE 1 = 1 ");
		if ((membroEquipe.getNome() != null) && (!membroEquipe.getNome().isEmpty())) {
			hql.append(" AND UPPER(membroEquipe.nome) LIKE UPPER(:nome) ");
		}
		
		Query query = session.createQuery(hql.toString());
		if ((membroEquipe.getNome() != null) && (!membroEquipe.getNome().isEmpty())) {
			query.setString("nome", "%" + membroEquipe.getNome() + "%");
		}
		
		Collection<MembroEquipe> membros = (Collection<MembroEquipe>) query.list();
		
		closeSession();
		
		return membros;
	}

	@Override
	public Usuario getUsuarioExistente(Usuario usuario) {
		Session session = getSession();
		
		StringBuilder hql = new StringBuilder();
		hql.append("SELECT usuario FROM Usuario AS usuario ")
				.append(" INNER JOIN FETCH usuario.membroEquipe ")
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
		
		closeSession();
		
		return usuarioExistente;
	}
	
	private void lowerFields(Usuario usuario) {
		usuario.setNomeUsuario(usuario.getNomeUsuario().trim().toLowerCase());
		usuario.getMembroEquipe().setEmail(usuario.getMembroEquipe().getEmail().trim().toLowerCase());
	}
}
