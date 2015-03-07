package org.sapac.adapters;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import org.sapac.annotations.DAOQualifier;
import org.sapac.entities.Consulta;
import org.sapac.entities.Paciente;
import org.sapac.models.ConsultaDAO;

@ApplicationScoped
public class ImotepAdapter implements SistemaPacienteAdapter, Serializable {

	private Connection connection;

	@Inject
	@DAOQualifier(DAOQualifier.DAOType.HIBERNATE)
	private ConsultaDAO consultaDAO;

	private void criarConexao() {
		connection = null;
		
		try {
			InputStream file = this.getClass().getClassLoader().getResourceAsStream("/sapac.properties");
			Properties properties = new Properties();
			properties.load(file);
			String serverHost = properties.getProperty("prontuario_server_host");
			String serverPort = properties.getProperty("prontuario_server_port");
			String database = properties.getProperty("prontuario_server_database");
			String user = properties.getProperty("prontuario_server_user");
			String password = properties.getProperty("prontuario_server_password");
			Class.forName("org.postgresql.Driver");
			connection = DriverManager.getConnection("jdbc:postgresql://" + serverHost + ":" + serverPort + "/" + database, user, password);
		} catch (ClassNotFoundException ex) {
			Logger.getLogger(ImotepAdapter.class.getName()).log(Level.SEVERE, null, ex);
		} catch (SQLException ex) {
			Logger.getLogger(ImotepAdapter.class.getName()).log(Level.SEVERE, null, ex);
		} catch (IOException ex) {
			Logger.getLogger(ImotepAdapter.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	private void fecharConexao() {
		if (connection != null) {
			try {
				if (!connection.isClosed()) {
					connection.close();
				}
			} catch (SQLException ex) {
				Logger.getLogger(ImotepAdapter.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	}
	
	private void carregarPaciente(ResultSet resultSet, Paciente paciente) throws SQLException {
		paciente.setNome(resultSet.getString("nome"));
		paciente.setDataNascimento(resultSet.getDate("data_nascimento"));
		paciente.setSexo(resultSet.getString("sexo"));
		paciente.setEndereco(resultSet.getString("endereco"));
		paciente.setCidade(resultSet.getString("cidade"));
		paciente.setEstado(resultSet.getString("estado"));
		paciente.setEstadoCivil(resultSet.getString("estado_civil"));
		paciente.setGrauInstrucao(resultSet.getString("grau_instrucao"));
		paciente.setProfissao(resultSet.getString("profissao"));
		paciente.setTelefone(resultSet.getString("telefone"));
		paciente.setNumeroCartaoSus(resultSet.getString("numero_cartao_sus"));
		paciente.setProntuario(resultSet.getString("prontuario"));
	}

	@Override
	public Collection<Paciente> procurarPaciente(Paciente paciente) {
		Collection<Paciente> pacientes = new ArrayList<Paciente>();

		criarConexao();
		if (connection != null) {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT paciente.cv_nome AS nome,")
				.append("paciente.dt_data_nascimento AS data_nascimento,")
				.append("case paciente.tp_sexo when 'F' then 'Feminino' else 'Masculino' end as sexo,")
				.append("paciente_info.cv_logradouro || paciente_info.cv_numero as endereco,")
				.append("cidade.cv_nome as cidade,")
				.append("estado.cv_nome as estado,")
				.append("paciente_info.tp_estado_civil as estado_civil,")
				.append("paciente_info.tp_escolaridade as grau_instrucao,")
				.append("paciente_info.cv_profissao_atual as profissao,")
				.append("paciente_info.cv_telefone_1 as telefone,")
				.append("paciente.cv_numero_sus as numero_cartao_sus,")
				.append("paciente.cv_prontuario as prontuario FROM tb_paciente AS paciente ")
				.append(" LEFT JOIN tb_paciente_entrada as paciente_info ON (paciente.id_paciente = paciente_info.id_paciente) ")
				.append(" INNER JOIN tb_cidade as cidade ON (paciente_info.id_cidade = cidade.id_cidade)")
				.append(" INNER JOIN tb_estado as estado ON (cidade.id_estado = estado.id_estado) ")
				.append(" WHERE 1 = 1 ");
			if ((paciente.getNome() != null) && (!paciente.getNome().isEmpty())) {
				sql.append(" and paciente.cv_nome ilike ? ");
			}
			if ((paciente.getProntuario() != null) && (!paciente.getProntuario().isEmpty())) {
				sql.append(" and paciente.cv_prontuario like ?");
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
						carregarPaciente(results, pacienteResult);
						pacientes.add(pacienteResult);
					}

					results.close();
					preparedStatement.close();
				} catch (SQLException ex) {
					Logger.getLogger(ImotepAdapter.class.getName()).log(Level.SEVERE, null, ex);
				}
			}

			removerPacientesCadastrados(pacientes, paciente);
			fecharConexao();
		}
		return pacientes;
	}

	@Override
	public boolean salvarInformacoesProntuario(Consulta consulta) {
		return true;
	}

	@Override
	public void carregarInformacoesPaciente(Paciente paciente) {
		criarConexao();
		if (connection != null) {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT paciente.cv_nome AS nome,")
				.append("paciente.dt_data_nascimento AS data_nascimento,")
				.append("case paciente.tp_sexo when 'F' then 'Feminino' else 'Masculino' end as sexo,")
				.append("paciente_info.cv_logradouro || paciente_info.cv_numero as endereco,")
				.append("cidade.cv_nome as cidade,")
				.append("estado.cv_nome as estado,")
				.append("paciente_info.tp_estado_civil as estado_civil,")
				.append("paciente_info.tp_escolaridade as grau_instrucao,")
				.append("paciente_info.cv_profissao_atual as profissao,")
				.append("paciente_info.cv_telefone_1 as telefone,")
				.append("paciente.cv_numero_sus as numero_cartao_sus,")
				.append("paciente.cv_prontuario as prontuario FROM tb_paciente AS paciente ")
				.append(" LEFT JOIN tb_paciente_entrada as paciente_info ON (paciente.id_paciente = paciente_info.id_paciente) ")
				.append(" INNER JOIN tb_cidade as cidade ON (paciente_info.id_cidade = cidade.id_cidade)")
				.append(" INNER JOIN tb_estado as estado ON (cidade.id_estado = estado.id_estado) ")
				.append(" WHERE 1 = 1 ")
				.append(" AND cv_prontuario like ? ");
			try {
				PreparedStatement preparedStatement = connection.prepareStatement(sql.toString());
				preparedStatement.setString(1, paciente.getProntuario());

				ResultSet results = preparedStatement.executeQuery();
				if (results.next()) {
					carregarPaciente(results, paciente);
				}

				results.close();
				preparedStatement.close();
			} catch (SQLException ex) {
				Logger.getLogger(ImotepAdapter.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
		fecharConexao();
	}

	private Collection<Paciente> getPacientesCadastrado(Paciente paciente) {
		return consultaDAO.procurarPacientes(paciente);
	}

	private void removerPacientesCadastrados(Collection<Paciente> pacientesPEP, Paciente paciente) {
		Collection<Paciente> pacientesCadastrados = getPacientesCadastrado(paciente);
		pacientesPEP.removeAll(pacientesCadastrados);
	}
}