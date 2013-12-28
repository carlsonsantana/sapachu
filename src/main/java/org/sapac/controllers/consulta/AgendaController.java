/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sapac.controllers.consulta;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.inject.Named;
import org.primefaces.event.ScheduleEntryMoveEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;
import org.sapac.controllers.GenericController;
import org.sapac.controllers.PaginasNavegacao;
import org.sapac.entities.Consulta;
import org.sapac.entities.Paciente;

/**
 *
 * @author carlson
 */
@Named
@javax.enterprise.context.SessionScoped
public class AgendaController extends GenericController {

	private Paciente paciente;
	private Paciente pacientePesquisa;
	private transient DataModel<Paciente> listaPacientes;
	private ScheduleModel calendario;
	private ScheduleEvent eventoCalendario;
	private Date data;
	private boolean consultaMarcada;
	
	@PostConstruct
	public void init() {
		paciente = new Paciente();
		pacientePesquisa = new Paciente();
		calendario = new DefaultScheduleModel();
		ScheduleEvent evento = new DefaultScheduleEvent("Primeiro e",
				new Date(), new Date());
		((DefaultScheduleEvent) evento).setStyleClass("");
		Consulta consulta = new Consulta();
		consulta.setSituacao(Consulta.CONSULTA_MARCADA);
		((DefaultScheduleEvent) evento).setData(consulta);
		calendario.addEvent(evento);
		consultaMarcada = false;

		List<Paciente> pacientes = new ArrayList<Paciente>();
		Paciente paciente1 = new Paciente();
		paciente1.setNome("Carlos Miguel Fonseca");
		paciente1.setProntuario("3435.1212");
		pacientes.add(paciente1);
		paciente1 = new Paciente();
		paciente1.setNome("Josefa Maria de Lurdes");
		paciente1.setProntuario("9864.6324");
		pacientes.add(paciente1);
		pacientes.add(paciente1);
		pacientes.add(paciente1);
		pacientes.add(paciente1);
		pacientes.add(paciente1);
		pacientes.add(paciente1);
		pacientes.add(paciente1);
		pacientes.add(paciente1);
		pacientes.add(paciente1);
		pacientes.add(paciente1);
		pacientes.add(paciente1);
		pacientes.add(paciente1);
		pacientes.add(paciente1);
		pacientes.add(paciente1);
		pacientes.add(paciente1);
		pacientes.add(paciente1);
		pacientes.add(paciente1);
		pacientes.add(paciente1);

		listaPacientes = new ListDataModel<Paciente>(pacientes);
	}

	/**
	 * @return the paciente
	 */
	public Paciente getPaciente() {
		return paciente;
	}

	/**
	 * @param paciente the paciente to set
	 */
	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}

	/**
	 * @return the pacientePesquisa
	 */
	public Paciente getPacientePesquisa() {
		return pacientePesquisa;
	}

	/**
	 * @param pacientePesquisa the pacientePesquisa to set
	 */
	public void setPacientePesquisa(Paciente pacientePesquisa) {
		this.pacientePesquisa = pacientePesquisa;
	}

	/**
	 * @return the listaPacientes
	 */
	public DataModel<Paciente> getListaPacientes() {
		return listaPacientes;
	}

	/**
	 * @param listaPacientes the listaPacientes to set
	 */
	public void setListaPacientes(DataModel<Paciente> listaPacientes) {
		this.listaPacientes = listaPacientes;
	}

	/**
	 * @return the calendario
	 */
	public ScheduleModel getCalendario() {
		return calendario;
	}

	/**
	 * @param calendario the calendario to set
	 */
	public void setCalendario(ScheduleModel calendario) {
		this.calendario = calendario;
	}

	/**
	 * @return the eventoCalendario
	 */
	public ScheduleEvent getEventoCalendario() {
		return eventoCalendario;
	}

	/**
	 * @param eventoCalendario the eventoCalendario to set
	 */
	public void setEventoCalendario(ScheduleEvent eventoCalendario) {
		this.eventoCalendario = eventoCalendario;
	}

	/**
	 * @return the data
	 */
	public Date getData() {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(Date data) {
		this.data = data;
	}

	/**
	 * @return the consultaMarcada
	 */
	public boolean isConsultaMarcada() {
		return consultaMarcada;
	}

	/**
	 * @param consultaMarcada the consultaMarcada to set
	 */
	public void setConsultaMarcada(boolean consultaMarcada) {
		this.consultaMarcada = consultaMarcada;
	}
	
	public String telaMarcarConsulta() {
		return "/private/consulta/marcar";
	}

	public String telaRemarcarConsulta() {
		return PaginasNavegacao.CONSULTA_REMARCAR;
	}
	
	public String selecionarPacienteMarcarConsulta(Paciente paciente) {
		this.paciente = paciente;
		consultaMarcada = false;
		return PaginasNavegacao.CONSULTA_MARCAR;
	}

	public String marcarConsulta(Paciente paciente) {
		adicionarMensagemAviso("Consulta Marcada", "A consulta do paciente \""
				+ paciente.getNome() + "\", para o dia "
				+ getDataFormatada(data) + ", foi marcada com sucesso" + ".");
		return PaginasNavegacao.PAGINA_INICIAL;
	}
	
	
	public void onDataConsultaSelecionada(SelectEvent selectEvent) {
		if (!consultaMarcada) {
			data = (Date) selectEvent.getObject();
			Date dataInicio = new Date(data.getTime());
			Date dataFim = new Date(data.getTime());
			eventoCalendario = new DefaultScheduleEvent("Consultar Paciente: "
					+ paciente.getNome(), dataInicio, dataFim);
			((DefaultScheduleEvent) eventoCalendario).setStyleClass("");
		}
	}

	public void adicionarConsulta() {
		if ((!consultaMarcada) && (eventoCalendario != null)) {
			getCalendario().addEvent(eventoCalendario);
			calendario.updateEvent(eventoCalendario);
			consultaMarcada = true;
			Consulta consulta = new Consulta();
			consulta.setData(eventoCalendario.getStartDate());
			consulta.setPaciente(paciente);
			consulta.setSituacao(Consulta.CONSULTA_MARCADA);
			((DefaultScheduleEvent) eventoCalendario).setData(consulta);
		}
	}
	
	public void onDataConsultaRemarcada(ScheduleEntryMoveEvent event) {
		DefaultScheduleEvent scheduleEvent = (DefaultScheduleEvent) event.getScheduleEvent();
		scheduleEvent.setStyleClass("remarcado");
		((Consulta) scheduleEvent.getData()).setSituacao(Consulta.CONSULTA_REMARCADA);
	}
	
	public void onConsultaSelecionada(SelectEvent selectEvent) {
		eventoCalendario = (ScheduleEvent) selectEvent.getObject();
	}
	
	public void cancelarConsulta() {
		((DefaultScheduleEvent) eventoCalendario).setStyleClass("removido");
		((Consulta)((DefaultScheduleEvent) eventoCalendario).getData()).setSituacao(Consulta.CONSULTA_CANCELADA);
	}
	
	public String remarcarDatas() {
		Collection<ScheduleEvent> eventos = calendario.getEvents();
		Collection<ScheduleEvent> removidos = new ArrayList<ScheduleEvent>();
		for (ScheduleEvent evento : eventos) {
			if (evento.getStyleClass() != null) {
				if (evento.getStyleClass().contains("removido")) {
					removidos.add(evento);
				}
			}
			((DefaultScheduleEvent) evento).setStyleClass("");
		}
		eventos.removeAll(removidos);
		removidos.clear();
		removidos = null;
		
		adicionarMensagemAviso("Consultas Remarcadas", "Consultas remarcadas com sucesso.");
		return PaginasNavegacao.PAGINA_INICIAL;
	}
}