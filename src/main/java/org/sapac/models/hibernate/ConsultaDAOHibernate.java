/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sapac.models.hibernate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.sapac.entities.Consulta;
import org.sapac.entities.DiagnosticoEnfermagem;
import org.sapac.entities.Paciente;
import org.sapac.entities.SituacaoUlceraConsulta;
import org.sapac.entities.Ulcera;
import org.sapac.models.ConsultaDAO;

/**
 *
 * @author carlson
 */
public class ConsultaDAOHibernate extends GenericDAOHibernate implements ConsultaDAO {

	@Override
	public Consulta marcarConsulta(Consulta consulta) {
		consulta.setSituacao(Consulta.CONSULTA_MARCADA);

		Session session = getSession();
		
		Transaction transaction = session.beginTransaction();

		session.save(consulta);

		transaction.commit();
		
		return consulta;
	}

	@Override
	public Collection<Consulta> remarcarConsultas(Collection<Consulta> consultas) {
		Session session = getSession();
		
		Transaction transaction = session.beginTransaction();
		
		for (Consulta consulta : consultas) {
			consulta.setSituacao(Consulta.CONSULTA_REMARCADA);

			session.update(consulta);
		}
		
		transaction.commit();
		
		return consultas;
	}

	@Override
	public Collection<Consulta> cancelarConsultas(Collection<Consulta> consultas) {
		Session session = getSession();
		
		Transaction transaction = session.beginTransaction();
		
		for (Consulta consulta : consultas) {
			consulta.setSituacao(Consulta.CONSULTA_CANCELADA);
			
			session.update(consulta);
		}
		
		transaction.commit();
		
		return consultas;
	}

	@Override
	public Collection<Consulta> procurarConsultasMes(Date data) {
		Session session = getSession();
		
		Transaction transaction = session.beginTransaction();
		
		StringBuilder hql = new StringBuilder();
		hql.append("SELECT consulta FROM Consulta AS consulta ")
				.append(" WHERE 1 = 1 ")
				.append(" AND consulta.situacao IN (:situacoes) ");
		
		Query query = session.createQuery(hql.toString());
		query.setParameterList("situacoes", new Object[] {Consulta.CONSULTA_MARCADA, Consulta.CONSULTA_REALIZADA, Consulta.CONSULTA_REMARCADA});
		
		Collection<Consulta> consultas = query.list();
		
		transaction.commit();
		
		return consultas;
	}

	@Override
	public Collection<Consulta> procurarConsultasDia(Date data) {
		Session session = getSession();
		
		Transaction transaction = session.beginTransaction();
		
		StringBuilder hql = new StringBuilder();
		hql.append("SELECT consulta FROM Consulta AS consulta ")
				.append(" WHERE 1 = 1 ")
				.append(" AND consulta.situacao IN (:situacoes) ")
				.append(" AND consulta.data = current_date() ");
		
		Query query = session.createQuery(hql.toString());
		query.setParameterList("situacoes", new Object[] {Consulta.CONSULTA_MARCADA, Consulta.CONSULTA_REALIZADA, Consulta.CONSULTA_REMARCADA});
		
		Collection<Consulta> consultas = query.list();
		
		transaction.commit();
		
		return consultas;
	}

	@Override
	public Collection<Consulta> procurarConsultasPaciente(Paciente paciente) {
		Collection<Consulta> consultas = new ArrayList<Consulta>();
		Session session = getSession();
		
		Transaction transaction = session.beginTransaction();
		
		StringBuilder hql = new StringBuilder();
		hql.append("SELECT consulta FROM Consulta AS consulta ")
				.append(" LEFT JOIN consulta.paciente AS paciente ")
				.append(" WHERE 1 = 1 ")
				.append(" AND paciente.id = :idPaciente ")
				.append(" AND consulta.situacao IN (:situacoes) ");
		
		Query query = session.createQuery(hql.toString());
		query.setInteger("idPaciente", paciente.getId());
		query.setParameterList("situacoes", new Object[] {Consulta.CONSULTA_REALIZADA});
		
		consultas = query.list();
		
		transaction.commit();
		
		return consultas;
	}

	
	@Override
	public Collection<Paciente> procurarPacientes(Paciente paciente) {
		Session session = getSession();
		
		Transaction transaction = session.beginTransaction();
		
		StringBuilder hql = new StringBuilder();
		hql.append("SELECT paciente FROM Paciente AS paciente ")
				.append(" WHERE 1 = 1 ");
		if ((paciente.getNome() != null) && (!paciente.getNome().isEmpty())) {
			hql.append(" AND paciente.nome LIKE :nome ");
		}
		if ((paciente.getProntuario() != null) && (!paciente.getProntuario().isEmpty())) {
			hql.append(" AND paciente.prontuario = :prontuario ");
		}
		
		Query query = session.createQuery(hql.toString());
		if ((paciente.getNome() != null) && (!paciente.getNome().isEmpty())) {
			query.setString("nome", paciente.getNome());
		}
		if ((paciente.getProntuario() != null) && (!paciente.getProntuario().isEmpty())) {
			query.setString("prontuario", paciente.getProntuario());
		}
		
		Collection<Paciente> pacientes = query.list();
		
		transaction.commit();
		
		return pacientes;
	}
	
	@Override
	public Paciente cadastrarPaciente(Paciente paciente) {
		paciente.setDiagnosticoEnfermagem(new DiagnosticoEnfermagem());
		paciente.getDiagnosticoEnfermagem().setPaciente(paciente);
		
		Session session = getSession();
		
		Transaction transaction = session.beginTransaction();
		
		session.save(paciente);
		session.save(paciente.getDiagnosticoEnfermagem());
		
		transaction.commit();

		return paciente;
	}
	
	@Override
	public boolean temConsultaDia(Paciente paciente, Date data) {
		Session session = getSession();
		
		Transaction transaction = session.beginTransaction();
		
		StringBuilder hql = new StringBuilder();
		hql.append(" SELECT 1 FROM Consulta AS consulta ")
				.append(" WHERE 1 = 1 ")
				.append(" AND consulta.paciente.id = :idPaciente ")
				.append(" AND consulta.data = :dataConsulta ")
				.append(" AND consulta.situacao IN (:situacoes) ");
		
		Query query = session.createQuery(hql.toString());
		query.setInteger("idPaciente", paciente.getId());
		query.setDate("dataConsulta", data);
		query.setParameterList("situacoes", new Object[] {Consulta.CONSULTA_MARCADA, Consulta.CONSULTA_REALIZADA, Consulta.CONSULTA_REMARCADA});
		
		boolean resultado = query.list().size() > 0;
		
		transaction.commit();
		
		return resultado;
	}
	
	@Override
	public Consulta carregarConsulta(Consulta consulta) {
		Session session = getSession();
		
		Transaction transaction = session.beginTransaction();
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT consulta FROM Consulta AS consulta ")
				.append(" LEFT JOIN FETCH consulta.paciente AS paciente ")
				.append(" LEFT JOIN FETCH consulta.intervencaoEnfermagem AS intervencaoEnfermagem ")
				.append(" LEFT JOIN FETCH consulta.variaveisClinicas AS variaveisClinicas ")
				.append(" LEFT JOIN FETCH consulta.situacoesUlcera AS situacoesUlcera ")
				.append(" LEFT JOIN FETCH situacoesUlcera.fotoUlcera ")
				.append(" WHERE 1 = 1 ")
				.append(" AND consulta.id = :idConsulta ");
		
		Query query = session.createQuery(sql.toString());
		query.setInteger("idConsulta", consulta.getId());
		
		consulta = (Consulta) query.uniqueResult();
		
		Hibernate.initialize(consulta.getPaciente().getUlceras());
		
		Hibernate.initialize(consulta.getMembrosEquipe());
		
		transaction.commit();
		
		return consulta;
	}

	@Override
	public Consulta editarConsulta(Consulta consulta) {
		Session session = getSession();
		
		Transaction transaction = session.beginTransaction();
		
		consulta.setSituacao(Consulta.CONSULTA_REALIZADA);
		consulta.getVariaveisClinicas().setConsulta(consulta);
		
		session.update(consulta);
		if (consulta.getVariaveisClinicas().getId() == 0) {
			session.save(consulta.getVariaveisClinicas());
		} else {
			session.update(consulta.getVariaveisClinicas());
		}
		
		consulta.getIntervencaoEnfermagem().setConsulta(consulta);
		if (consulta.getIntervencaoEnfermagem().getId() == 0) {
			session.save(consulta.getIntervencaoEnfermagem());
		} else {
			session.update(consulta.getIntervencaoEnfermagem());
		}
		for (SituacaoUlceraConsulta situacaoUlceraConsulta : consulta.getSituacoesUlcera()) {
			situacaoUlceraConsulta.setConsulta(consulta);
			situacaoUlceraConsulta.getUlcera().setPaciente(consulta.getPaciente());
			situacaoUlceraConsulta.getUlcera().setSituacao(situacaoUlceraConsulta.getEstadoUlcera());
			situacaoUlceraConsulta.getFotoUlcera().setSituacaoUlceraConsulta(situacaoUlceraConsulta);
			
			if (situacaoUlceraConsulta.getUlcera().getId() == 0) {
				session.save(situacaoUlceraConsulta.getUlcera());
			} else {
				session.update(situacaoUlceraConsulta.getUlcera());
			}
			if (situacaoUlceraConsulta.getId() == 0) {
				session.save(situacaoUlceraConsulta);
			} else {
				session.update(situacaoUlceraConsulta);
			}
			if (situacaoUlceraConsulta.getFotoUlcera().getId() == 0) {
				session.save(situacaoUlceraConsulta.getFotoUlcera());
			} else {
				session.update(situacaoUlceraConsulta.getFotoUlcera());
			}
		}
		//session.saveOrUpdate(consulta.get);
		
		transaction.commit();
		
		return consulta;
	}
}
