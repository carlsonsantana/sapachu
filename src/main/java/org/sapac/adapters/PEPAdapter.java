package org.sapac.adapters;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import org.sapac.annotations.DAOQualifier;
import org.sapac.entities.Consulta;
import org.sapac.entities.Paciente;
import org.sapac.models.ConsultaDAO;

@ApplicationScoped
public class PEPAdapter implements SistemaPacienteAdapter, Serializable {

	private Connection connection;

	@Inject
	@DAOQualifier(DAOQualifier.DAOType.HIBERNATE)
	private ConsultaDAO consultaDAO;

	@Override
	public Collection<Paciente> procurarPaciente(Paciente paciente) {
		Collection<Paciente> pacientes = new ArrayList<Paciente>();
		if (connection == null) {
			try {
				Class.forName("org.postgresql.Driver");
				connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/engenharia", "postgres", "postgres");
			} catch (ClassNotFoundException ex) {
				Logger.getLogger(PEPAdapter.class.getName()).log(Level.SEVERE, null, ex);
			} catch (SQLException ex) {
				Logger.getLogger(PEPAdapter.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
		StringBuilder sql = new StringBuilder();
		sql.append("select * from pepsimulator.paciente where 1 = 1 ");
		if ((paciente.getNome() != null) && (!paciente.getNome().isEmpty())) {
			sql.append(" and nome ilike ? ");
		}
		if ((paciente.getProntuario() != null) && (!paciente.getProntuario().isEmpty())) {
			sql.append(" and prontuario = ?");
		}
		if (connection != null) {
			try {
				PreparedStatement preparedStatement = connection.prepareStatement(sql.toString());
				int i = 1;
				if ((paciente.getNome() != null) && (!paciente.getNome().isEmpty())) {
					preparedStatement.setString(i++, "%" + paciente.getNome() + "%");
				}
				if ((paciente.getProntuario() != null) && (!paciente.getProntuario().isEmpty())) {
					preparedStatement.setString(i++, paciente.getProntuario());
				}
				ResultSet results = preparedStatement.executeQuery();
				while (results.next()) {
					Paciente pacienteResult = new Paciente();
					pacienteResult.setNome(results.getString("nome"));
					pacienteResult.setDataNascimento(results.getDate("data_nascimento"));
					pacienteResult.setSexo(results.getString("sexo"));
					pacienteResult.setEndereco(results.getString("endereco"));
					pacienteResult.setCidade(results.getString("cidade"));
					pacienteResult.setEstado(results.getString("estado"));
					pacienteResult.setEstadoCivil(results.getString("estado_civil"));
					pacienteResult.setGrauInstrucao(results.getString("grau_instrucao"));
					pacienteResult.setProfissao(results.getString("profissao"));
					pacienteResult.setTelefone(results.getString("telefone"));
					pacienteResult.setNumeroCartaoSus(results.getString("numero_cartao_sus"));
					pacienteResult.setProntuario(results.getString("prontuario"));

					pacientes.add(pacienteResult);
				}

				results.close();
				preparedStatement.close();
			} catch (SQLException ex) {
				Logger.getLogger(PEPAdapter.class.getName()).log(Level.SEVERE, null, ex);
			}
			try {
				connection.close();
			} catch (Exception ex) {
				Logger.getLogger(PEPAdapter.class.getName()).log(Level.SEVERE, null, ex);
			}
		}

		removerPacientesCadastrados(pacientes, paciente);

		connection = null;

		return pacientes;
	}

	@Override
	public boolean salvarInformacoesProntuario(Consulta consulta) {

		return true;
	}

	@Override
	public void carregarInformacoesPaciente(Paciente paciente) {
		if (connection == null) {
			try {
				Class.forName("org.postgresql.Driver");
				connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/engenharia", "postgres", "postgres");
			} catch (ClassNotFoundException ex) {
				Logger.getLogger(PEPAdapter.class.getName()).log(Level.SEVERE, null, ex);
			} catch (SQLException ex) {
				Logger.getLogger(PEPAdapter.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
		StringBuilder sql = new StringBuilder();
		sql.append("select * from pepsimulator.paciente where 1 = 1 ")
			.append(" and prontuario = ?");
		if (connection != null) {
			try {
				PreparedStatement preparedStatement = connection.prepareStatement(sql.toString());
				preparedStatement.setString(1, paciente.getProntuario());

				ResultSet results = preparedStatement.executeQuery();
				if (results.next()) {
					paciente.setNome(results.getString("nome"));
					paciente.setDataNascimento(results.getDate("data_nascimento"));
					paciente.setSexo(results.getString("sexo"));
					paciente.setEndereco(results.getString("endereco"));
					paciente.setCidade(results.getString("cidade"));
					paciente.setEstado(results.getString("estado"));
					paciente.setEstadoCivil(results.getString("estado_civil"));
					paciente.setGrauInstrucao(results.getString("grau_instrucao"));
					paciente.setProfissao(results.getString("profissao"));
					paciente.setTelefone(results.getString("telefone"));
					paciente.setNumeroCartaoSus(results.getString("numero_cartao_sus"));
					paciente.setProntuario(results.getString("prontuario"));
				}

				results.close();
				preparedStatement.close();
			} catch (SQLException ex) {
				Logger.getLogger(PEPAdapter.class.getName()).log(Level.SEVERE, null, ex);
			}
			try {
				connection.close();
			} catch (Exception ex) {
				Logger.getLogger(PEPAdapter.class.getName()).log(Level.SEVERE, null, ex);
			}
		}

		connection = null;
	}

	private Collection<Paciente> getPacientesCadastrado(Paciente paciente) {
		return consultaDAO.procurarPacientes(paciente);
	}

	private void removerPacientesCadastrados(Collection<Paciente> pacientesPEP, Paciente paciente) {
		Collection<Paciente> pacientesCadastrados = getPacientesCadastrado(paciente);
		pacientesPEP.removeAll(pacientesCadastrados);
	}
}
