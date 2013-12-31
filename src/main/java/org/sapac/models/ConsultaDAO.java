/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sapac.models;

import java.util.Collection;
import java.util.Date;
import org.sapac.entities.Consulta;
import org.sapac.entities.Paciente;

/**
 *
 * @author carlson
 */
public interface ConsultaDAO extends GenericDAO {
	public Consulta marcarConsulta(Consulta consulta);
	public Collection<Consulta> remarcarConsultas(Collection<Consulta> consultas);
	public Collection<Consulta> cancelarConsultas(Collection<Consulta> consultas);
	public Collection<Consulta> procurarConsultasMes(Date data);
	public Collection<Consulta> procurarConsultasDia(Date data);
	public Collection<Consulta> procurarConsultasPaciente(Paciente paciente);
	public Paciente cadastrarPaciente(Paciente paciente);
	public Collection<Paciente> procurarPacientes(Paciente paciente);
	public boolean temConsultaDia(Paciente paciente, Date data);
	public Consulta carregarConsulta(Consulta consulta);
	public Consulta editarConsulta(Consulta consulta);
}
