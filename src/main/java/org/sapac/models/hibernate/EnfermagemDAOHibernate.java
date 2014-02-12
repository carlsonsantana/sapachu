/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sapac.models.hibernate;

import java.util.Collection;
import java.util.Date;
import javax.enterprise.context.ApplicationScoped;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.sapac.annotations.DAOQualifier;
import org.sapac.entities.Consulta;
import org.sapac.entities.DiagnosticoEnfermagem;
import org.sapac.entities.IntervencaoEnfermagem;
import org.sapac.entities.Paciente;
import org.sapac.models.EnfermagemDAO;

/**
 *
 * @author carlson
 */
@ApplicationScoped
@DAOQualifier(DAOQualifier.DAOType.HIBERNATE)
public class EnfermagemDAOHibernate extends GenericDAOHibernate implements EnfermagemDAO {

	@Override
	public DiagnosticoEnfermagem alterarDiagnosticoEnfermagem(DiagnosticoEnfermagem diagnosticoEnfermagem) {
		Session session = getSession();
		
		Transaction transaction = session.beginTransaction();
		
		session.update(diagnosticoEnfermagem);
		
		transaction.commit();
		
		return diagnosticoEnfermagem;
	}

	@Override
	public IntervencaoEnfermagem alterarIntervencaoEnfermagem(IntervencaoEnfermagem intervencaoEnfermagem) {
		Session session = getSession();
		
		Transaction transaction = session.beginTransaction();
		
		session.update(intervencaoEnfermagem);
		
		transaction.commit();
		
		return intervencaoEnfermagem;
	}

	@Override
	public DiagnosticoEnfermagem procurarDiagnosticoEnfermagem(Paciente paciente) {
		Session session = getSession();
		
		Transaction transaction = session.beginTransaction();
		
		StringBuilder hql = new StringBuilder();
		hql.append("SELECT diagnosticoEnfermagem FROM DiagnosticoEnfermagem AS diagnosticoEnfermagem ")
				.append(" INNER JOIN diagnosticoEnfermagem.paciente AS paciente ")
				.append(" WHERE 1 = 1 ")
				.append(" AND paciente.id = :idPaciente ");
		
		Query query = session.createQuery(hql.toString());
		query.setInteger("idPaciente", paciente.getId());
		
		DiagnosticoEnfermagem diagnosticoEnfermagem = (DiagnosticoEnfermagem) query.uniqueResult();
		
		transaction.commit();
		
		return diagnosticoEnfermagem;
	}

	@Override
	public Collection<IntervencaoEnfermagem> procurarIntervencoesEnfermagem(Paciente paciente) {
		Session session = getSession();
		
		Transaction transaction = session.beginTransaction();
		
		StringBuilder hql = new StringBuilder();
		hql.append("SELECT intervencaoEnfermagem FROM IntervencaoEnfermagem AS intervencaoEnfermagem ")
				.append(" INNER JOIN FETCH intervencaoEnfermagem.consulta AS consulta ")
				.append(" INNER JOIN consulta.paciente AS paciente ")
				.append(" WHERE 1 = 1 ")
				.append(" AND paciente.id = :idPaciente ");
		
		Query query = session.createQuery(hql.toString());
		query.setInteger("idPaciente", paciente.getId());
		
		Collection<IntervencaoEnfermagem> intervencoesEnfermagem = query.list();
		
		transaction.commit();
		
		return intervencoesEnfermagem;
	}

	@Override
	public IntervencaoEnfermagem procurarIntervencaoEnfermagem(Consulta consulta) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public Collection<IntervencaoEnfermagem> procurarIntervencoesEnfermagemDia(Paciente paciente, Date data) {
		Session session = getSession();
		
		Transaction transaction = session.beginTransaction();
		
		StringBuilder hql = new StringBuilder();
		hql.append("SELECT intervencaoEnfermagem FROM IntervencaoEnfermagem AS intervencaoEnfermagem ")
				.append(" INNER JOIN intervencaoEnfermagem.consulta AS consulta ")
				.append(" INNER JOIN consulta.paciente AS paciente ")
				.append(" WHERE 1 = 1 ")
				.append(" AND consulta.data = :data ")
				.append(" AND consulta.situacao IN (:situacoes) ");
		if ((paciente.getNome() != null) && (!paciente.getNome().isEmpty())) {
			hql.append(" AND UPPER(paciente.nome) = UPPER(:nome) ");
		}
		if ((paciente.getProntuario() != null) && (!paciente.getProntuario().isEmpty())) {
			hql.append(" AND paciente.prontuario = :prontuario ");
		}
		
		Query query = session.createQuery(hql.toString());
		query.setDate("data", data);
		query.setParameterList("situacoes", new Object[] {Consulta.CONSULTA_REALIZADA});
		if ((paciente.getNome() != null) && (!paciente.getNome().isEmpty())) {
			query.setString("nome", "%" + paciente.getNome() + "%");
		}
		if ((paciente.getProntuario() != null) && (!paciente.getProntuario().isEmpty())) {
			query.setString("prontuario", paciente.getProntuario());
		}
		
		Collection<IntervencaoEnfermagem> intervencoesEnfermagem = query.list();
		
		transaction.commit();
		
		return intervencoesEnfermagem;
	}
	
}
