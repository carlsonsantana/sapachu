package org.sapac.models.hibernate;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.sapac.adapters.SistemaPacienteAdapter;
import org.sapac.annotations.DAOQualifier;
import org.sapac.entities.Consulta;
import org.sapac.entities.DiagnosticoEnfermagem;
import org.sapac.entities.MedicamentoUso;
import org.sapac.entities.Paciente;
import org.sapac.entities.SituacaoUlceraConsulta;
import org.sapac.entities.Ulcera;
import org.sapac.models.ConsultaDAO;

@ApplicationScoped
@DAOQualifier(DAOQualifier.DAOType.HIBERNATE)
public class ConsultaDAOHibernate extends GenericDAOHibernate implements ConsultaDAO {

	@Inject
	private SistemaPacienteAdapter sistemaPacienteAdapter;
	
	@Override
	public Consulta marcarConsulta(Consulta consulta) {
		consulta.setSituacao(Consulta.CONSULTA_MARCADA);

		Session session = getSession();

		session.save(consulta);
		
		closeSession();
		
		return consulta;
	}

	@Override
	public Collection<Consulta> remarcarConsultas(Collection<Consulta> consultas) {
		Session session = getSession();
		
		for (Consulta consulta : consultas) {
			consulta.setSituacao(Consulta.CONSULTA_REMARCADA);
			consulta.setMembrosEquipe(null);
			consulta.setIntervencaoEnfermagem(null);
			consulta.setSituacoesUlcera(null);
			consulta.setVariaveisClinicas(null);
			
			Consulta novaConsulta = consulta.getConsultaMarcada();
			novaConsulta.setSituacao(Consulta.CONSULTA_MARCADA);

			session.save(novaConsulta);
			session.update(consulta);
		}
		
		closeSession();
		
		return consultas;
	}

	@Override
	public Collection<Consulta> cancelarConsultas(Collection<Consulta> consultas) {
		Session session = getSession();
		
		for (Consulta consulta : consultas) {
			consulta.setSituacao(Consulta.CONSULTA_CANCELADA);
			
			session.update(consulta);
		}
		
		closeSession();
		
		return consultas;
	}

	@Override
	public Collection<Consulta> procurarConsultasMes(Date data, Paciente paciente) {
		Session session = getSession();
		
		StringBuilder hql = new StringBuilder();
		hql.append("SELECT consulta FROM Consulta AS consulta ")
				.append(" INNER JOIN FETCH consulta.paciente AS paciente ")
				.append(" WHERE 1 = 1 ")
				.append(" AND consulta.situacao IN (:situacoes) ");
		if (data != null) {
			hql.append(" AND month(consulta.data) = :mes ")
				.append(" AND year(consulta.data) = :ano ");
		}
		if (paciente != null) {
			if ((paciente.getNome() != null) && (!paciente.getNome().isEmpty())) {
				hql.append(" AND UPPER(consulta.paciente.nome) LIKE UPPER(:nome) ");
			}
			if ((paciente.getProntuario() != null) && (!paciente.getProntuario().isEmpty())) {
				hql.append(" AND consulta.paciente.prontuario LIKE :prontuario ");
			}
		}
		
		Query query = session.createQuery(hql.toString());
		query.setParameterList("situacoes", new Object[] {Consulta.CONSULTA_MARCADA, Consulta.CONSULTA_REALIZADA});
		if (data != null) {
			Calendar calendario = Calendar.getInstance();
			calendario.setTime(data);
		
			query.setInteger("mes", calendario.get(Calendar.MONTH) + 1);
			query.setInteger("ano", calendario.get(Calendar.YEAR));
		}
		if (paciente != null) {
			if ((paciente.getNome() != null) && (!paciente.getNome().isEmpty())) {
				query.setString("nome", "%" + paciente.getNome() + "%");
			}
			if ((paciente.getProntuario() != null) && (!paciente.getProntuario().isEmpty())) {
				query.setString("prontuario", paciente.getProntuario());
			}
		}
		
		Collection<Consulta> consultas = query.list();
		
		closeSession();
		
		return consultas;
	}

	@Override
	public Collection<Consulta> procurarConsultasDia(Date data, Paciente paciente) {
		Session session = getSession();
		
		StringBuilder hql = new StringBuilder();
		hql.append("SELECT consulta FROM Consulta AS consulta ")
				.append(" INNER JOIN FETCH consulta.paciente ")
				.append(" WHERE 1 = 1 ")
				.append(" AND consulta.situacao IN (:situacoes) ")
				.append(" AND consulta.data = current_date() ");
		if (paciente != null) {
			if ((paciente.getNome() != null) && (!paciente.getNome().isEmpty())) {
				hql.append(" AND UPPER(consulta.paciente.nome) LIKE UPPER(:nome) ");
			}
			if ((paciente.getProntuario() != null) && (!paciente.getProntuario().isEmpty())) {
				hql.append(" AND consulta.paciente.prontuario LIKE :prontuario ");
			}
		}
		
		Query query = session.createQuery(hql.toString());
		query.setParameterList("situacoes", new Object[] {Consulta.CONSULTA_MARCADA, Consulta.CONSULTA_REALIZADA});
		if (paciente != null) {
			if ((paciente.getNome() != null) && (!paciente.getNome().isEmpty())) {
				query.setString("nome", "%" + paciente.getNome() + "%");
			}
			if ((paciente.getProntuario() != null) && (!paciente.getProntuario().isEmpty())) {
				query.setString("prontuario", paciente.getProntuario());
			}
		}
		
		Collection<Consulta> consultas = query.list();
		
		closeSession();
		
		return consultas;
	}

	@Override
	public Collection<Consulta> procurarConsultasPaciente(Paciente paciente) {
		Collection<Consulta> consultas = new ArrayList<Consulta>();
		Session session = getSession();
		
		StringBuilder hql = new StringBuilder();
		hql.append("SELECT consulta FROM Consulta AS consulta ")
				.append(" LEFT JOIN consulta.paciente AS paciente ")
				.append(" WHERE 1 = 1 ")
				.append(" AND paciente.id = :idPaciente ")
				.append(" AND consulta.situacao IN (:situacoes) ")
				.append(" ORDER BY consulta.data ASC ");
		
		Query query = session.createQuery(hql.toString());
		query.setInteger("idPaciente", paciente.getId());
		query.setParameterList("situacoes", new Object[] {Consulta.CONSULTA_REALIZADA});
		
		consultas = query.list();
		
		closeSession();
		
		return consultas;
	}

	
	@Override
	public Collection<Paciente> procurarPacientes(Paciente paciente) {
		Session session = getSession();
		
		StringBuilder hql = new StringBuilder();
		hql.append("SELECT paciente FROM Paciente AS paciente ")
				.append(" WHERE 1 = 1 ");
		if ((paciente.getNome() != null) && (!paciente.getNome().isEmpty())) {
			hql.append(" AND UPPER(paciente.nome) LIKE UPPER(:nome) ");
		}
		if ((paciente.getProntuario() != null) && (!paciente.getProntuario().isEmpty())) {
			hql.append(" AND paciente.prontuario = :prontuario ");
		}
		
		Query query = session.createQuery(hql.toString());
		if ((paciente.getNome() != null) && (!paciente.getNome().isEmpty())) {
			query.setString("nome", "%" + paciente.getNome() + "%");
		}
		if ((paciente.getProntuario() != null) && (!paciente.getProntuario().isEmpty())) {
			query.setString("prontuario", paciente.getProntuario());
		}
		
		Collection<Paciente> pacientes = query.list();
		
		closeSession();
		
		return pacientes;
	}
	
	@Override
	public Paciente cadastrarPaciente(Paciente paciente) {
		DiagnosticoEnfermagem diagnosticoEnfermagem = new DiagnosticoEnfermagem();
		diagnosticoEnfermagem.setPaciente(paciente);
		diagnosticoEnfermagem.setRiscoGlicemiaInstavel("");
		diagnosticoEnfermagem.setRiscoNutricaoDesequilibrada("");
		diagnosticoEnfermagem.setRiscoNutricaoAcimaNecessidadesCorporais(null);
		diagnosticoEnfermagem.setSonoPrejudicado("");
		diagnosticoEnfermagem.setMobilidadeFisicaPrejudicada("");
		diagnosticoEnfermagem.setPerfusaoTissularPerifericaIneficaz("");
		diagnosticoEnfermagem.setInteracaoSocialPrejudicada("");
		diagnosticoEnfermagem.setFaltaAdesao("");
		diagnosticoEnfermagem.setRiscoInfeccao("");
		diagnosticoEnfermagem.setIntegridadePelePrejudicada("");
		diagnosticoEnfermagem.setDor("");
		diagnosticoEnfermagem.setTipoDor(null);
		diagnosticoEnfermagem.setOutrosDiagnosticos("");
		diagnosticoEnfermagem.setAtivo(true);
		paciente.setDiagnosticoEnfermagem(diagnosticoEnfermagem);
		
		
		Session session = getSession();
		
		session.save(paciente);
		session.save(paciente.getDiagnosticoEnfermagem());
		
		closeSession();

		return paciente;
	}
	
	@Override
	public boolean temConsultaDia(Paciente paciente, Date data) {
		Session session = getSession();
		
		StringBuilder hql = new StringBuilder();
		hql.append(" SELECT 1 FROM Consulta AS consulta ")
				.append(" WHERE 1 = 1 ")
				.append(" AND consulta.paciente.id = :idPaciente ")
				.append(" AND consulta.data = :dataConsulta ")
				.append(" AND consulta.situacao IN (:situacoes) ");
		
		Query query = session.createQuery(hql.toString());
		query.setInteger("idPaciente", paciente.getId());
		query.setDate("dataConsulta", data);
		query.setParameterList("situacoes", new Object[] {Consulta.CONSULTA_MARCADA, Consulta.CONSULTA_REALIZADA});
		
		boolean resultado = !query.list().isEmpty();
		
		closeSession();
		
		return resultado;
	}
	
	@Override
	public Consulta carregarConsulta(Consulta consulta) {
		Session session = getSession();
		
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
		if (consulta.getVariaveisClinicas() != null) {
			Hibernate.initialize(consulta.getVariaveisClinicas().getMedicamentosUso());
		}
		Collection<Ulcera> ulceras = consulta.getPaciente().getUlceras();
		for (Ulcera ulcera : ulceras) {
			Hibernate.initialize(ulcera.getUlcerasResultado());
		}
		
		closeSession();
		
		return consulta;
	}

	@Override
	public Consulta editarConsulta(Consulta consulta) {
		Session session = getSession();
		
		consulta.setSituacao(Consulta.CONSULTA_REALIZADA);
		consulta.getVariaveisClinicas().setConsulta(consulta);
		
		session.update(consulta);
		session.saveOrUpdate(consulta.getVariaveisClinicas());

		Collection<MedicamentoUso> medicamentos = consulta.getVariaveisClinicas().getMedicamentosUso();
		for (MedicamentoUso medicamento : medicamentos) {
			medicamento.setVariaveisClinicas(consulta.getVariaveisClinicas());
			
			session.saveOrUpdate(medicamento);
		}
		
		consulta.getIntervencaoEnfermagem().setConsulta(consulta);
		session.saveOrUpdate(consulta.getIntervencaoEnfermagem());
		
		for (SituacaoUlceraConsulta situacaoUlceraConsulta : consulta.getSituacoesUlcera()) {
			situacaoUlceraConsulta.setConsulta(consulta);
			situacaoUlceraConsulta.getUlcera().setPaciente(consulta.getPaciente());
			situacaoUlceraConsulta.getUlcera().setSituacao(situacaoUlceraConsulta.getEstadoUlcera());
			if (situacaoUlceraConsulta.getFotoUlcera() != null) {
				situacaoUlceraConsulta.getFotoUlcera().setSituacaoUlceraConsulta(situacaoUlceraConsulta);
			}
			
			if ((situacaoUlceraConsulta.getUlcera().getSituacao() == Ulcera.ULCERA_JUNTADA)
					|| (situacaoUlceraConsulta.getUlcera().getSituacao() == Ulcera.ULCERA_SEPARADA)) {
				Collection<Ulcera> ulceras = situacaoUlceraConsulta.getUlcera().getUlcerasResultado();
				if (ulceras != null) {
					for (Ulcera ulcera : ulceras) {
						session.saveOrUpdate(ulcera);
					}
				}
			}
			
			session.saveOrUpdate(situacaoUlceraConsulta.getUlcera());
			session.saveOrUpdate(situacaoUlceraConsulta);
			if (situacaoUlceraConsulta.getFotoUlcera() != null) {
				session.saveOrUpdate(situacaoUlceraConsulta.getFotoUlcera());
			}
		}
		
		sistemaPacienteAdapter.salvarInformacoesProntuario(consulta);
		
		closeSession();
		
		return consulta;
	}

	@Override
	public Collection<Paciente> procurarPacientesNaoCadastrados(Paciente paciente) {
		return sistemaPacienteAdapter.procurarPaciente(paciente);
	}

	@Override
	public Paciente carregarPaciente(Paciente paciente) {
		sistemaPacienteAdapter.carregarInformacoesPaciente(paciente);
		return paciente;
	}
}