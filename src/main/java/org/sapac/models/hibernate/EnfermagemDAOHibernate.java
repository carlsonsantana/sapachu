package org.sapac.models.hibernate;

import java.util.Collection;
import java.util.Date;
import javax.enterprise.context.ApplicationScoped;
import org.hibernate.Query;
import org.hibernate.Session;
import org.sapac.annotations.DAOQualifier;
import org.sapac.entities.Consulta;
import org.sapac.entities.DiagnosticoEnfermagem;
import org.sapac.entities.IntervencaoEnfermagem;
import org.sapac.entities.Paciente;
import org.sapac.models.EnfermagemDAO;

@ApplicationScoped
@DAOQualifier(DAOQualifier.DAOType.HIBERNATE)
public class EnfermagemDAOHibernate extends GenericDAOHibernate implements EnfermagemDAO {

	@Override
	public DiagnosticoEnfermagem alterarDiagnosticoEnfermagem(DiagnosticoEnfermagem diagnosticoEnfermagem) {
		Session session = getSession();
		
		int idDiagnostico = diagnosticoEnfermagem.getId();
		DiagnosticoEnfermagem diagnosticoEnfermagemAntigo = (DiagnosticoEnfermagem) session.get(DiagnosticoEnfermagem.class, idDiagnostico);
			
		closeSession();
		
		
		session = getSession();
		
		diagnosticoEnfermagem.setAtivo(true);
		
		if (diagnosticoEnfermagem.getData() == null) {
			diagnosticoEnfermagem.setData(new Date());
			session.update(diagnosticoEnfermagem);
		} else {
			DiagnosticoEnfermagem novoDiagnostico = diagnosticoEnfermagem;
			novoDiagnostico.setId(0);
			novoDiagnostico.setData(new Date());
			
			session.save(novoDiagnostico);
			
			diagnosticoEnfermagemAntigo.setAtivo(false);
			session.update(diagnosticoEnfermagemAntigo);
		}
		
		closeSession();
		
		return diagnosticoEnfermagem;
	}

	@Override
	public IntervencaoEnfermagem alterarIntervencaoEnfermagem(IntervencaoEnfermagem intervencaoEnfermagem) {
		Session session = getSession();
		
		session.update(intervencaoEnfermagem);
		
		closeSession();
		
		return intervencaoEnfermagem;
	}

	@Override
	public DiagnosticoEnfermagem procurarDiagnosticoEnfermagem(Paciente paciente) {
		Session session = getSession();
		
		StringBuilder hql = new StringBuilder();
		hql.append("SELECT diagnosticoEnfermagem FROM DiagnosticoEnfermagem AS diagnosticoEnfermagem ")
				.append(" INNER JOIN FETCH diagnosticoEnfermagem.paciente AS paciente ")
				.append(" WHERE 1 = 1 ")
				.append(" AND paciente.id = :idPaciente ")
				.append(" AND diagnosticoEnfermagem.ativo IS TRUE ");
		
		Query query = session.createQuery(hql.toString());
		query.setInteger("idPaciente", paciente.getId());
		
		DiagnosticoEnfermagem diagnosticoEnfermagem = (DiagnosticoEnfermagem) query.uniqueResult();
		
		closeSession();
		
		return diagnosticoEnfermagem;
	}

	@Override
	public Collection<IntervencaoEnfermagem> procurarIntervencoesEnfermagem(Paciente paciente) {
		Session session = getSession();
		
		StringBuilder hql = new StringBuilder();
		hql.append("SELECT intervencaoEnfermagem FROM IntervencaoEnfermagem AS intervencaoEnfermagem ")
				.append(" INNER JOIN FETCH intervencaoEnfermagem.consulta AS consulta ")
				.append(" INNER JOIN FETCH consulta.paciente AS paciente ")
				.append(" WHERE 1 = 1 ")
				.append(" AND paciente.id = :idPaciente ")
				.append(" ORDER BY consulta.data DESC ");
		
		Query query = session.createQuery(hql.toString());
		query.setInteger("idPaciente", paciente.getId());
		
		Collection<IntervencaoEnfermagem> intervencoesEnfermagem = query.list();
		
		closeSession();
		
		return intervencoesEnfermagem;
	}

	@Override
	public IntervencaoEnfermagem procurarIntervencaoEnfermagem(Consulta consulta) {
		Session session = getSession();
		
		StringBuilder hql = new StringBuilder();
		hql.append("SELECT intervencaoEnfermagem FROM IntervencaoEnfermagem AS intervencaoEnfermagem ")
				.append(" INNER JOIN FETCH intervencaoEnfermagem.consulta AS consulta ")
				.append(" INNER JOIN FETCH intervencaoEnfermagem.enfermeiro ")
				.append(" WHERE 1 = 1 ")
				.append(" AND consulta.id = :idConsulta ");
		
		Query query = session.createQuery(hql.toString());
		query.setInteger("idConsulta", consulta.getId());
		
		IntervencaoEnfermagem intervencaoEnfermagem = (IntervencaoEnfermagem) query.uniqueResult();
		
		closeSession();
		
		return intervencaoEnfermagem;
	}

	@Override
	public Collection<IntervencaoEnfermagem> procurarIntervencoesEnfermagemDia(Paciente paciente, Date data) {
		Session session = getSession();
		
		StringBuilder hql = new StringBuilder();
		hql.append("SELECT intervencaoEnfermagem FROM IntervencaoEnfermagem AS intervencaoEnfermagem ")
				.append(" INNER JOIN FETCH intervencaoEnfermagem.consulta AS consulta ")
				.append(" INNER JOIN FETCH consulta.paciente AS paciente ")
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
		
		closeSession();
		
		return intervencoesEnfermagem;
	}
	
}
