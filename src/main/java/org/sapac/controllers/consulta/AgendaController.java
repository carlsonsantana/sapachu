/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sapac.controllers.consulta;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.event.ScheduleEntryMoveEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;
import org.sapac.annotations.DAOQualifier;
import org.sapac.controllers.GenericController;
import org.sapac.controllers.PaginasNavegacao;
import org.sapac.entities.Consulta;
import org.sapac.entities.Paciente;
import org.sapac.models.ConsultaDAO;

/**
 *
 * @author carlson
 */
@Named
@SessionScoped
public class AgendaController extends GenericController {
	
	private Paciente paciente;
	private Paciente pacientePesquisa;
	private Collection<Paciente> listaPacientes;
	private ScheduleModel calendario;
	private ScheduleEvent eventoCalendario;
	private Date data;
	private boolean consultaMarcada;
	private Collection<Consulta> remarcados;
	private Collection<Consulta> cancelados;
	private boolean podeMarcar;
	@Inject
	@DAOQualifier(DAOQualifier.DAOType.HIBERNATE)
	private ConsultaDAO consultaDAO;

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
	public Collection<Paciente> getListaPacientes() {
		return listaPacientes;
	}

	/**
	 * @param listaPacientes the listaPacientes to set
	 */
	public void setListaPacientes(Collection<Paciente> listaPacientes) {
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
	
	@PostConstruct
	public void init() {
		pacientePesquisa = new Paciente();
		calendario = new DefaultScheduleModel();
		clear();
	}
	
	public void clear() {
		paciente = new Paciente();
		if (remarcados == null) {
			remarcados = new ArrayList<Consulta>();
		} else {
			remarcados.clear();
		}
		if (cancelados == null) {
			cancelados = new ArrayList<Consulta>();
		} else {
			cancelados.clear();
		}
		eventoCalendario = new DefaultScheduleEvent();
		
		setPodeMarcar(false);
		consultaMarcada = false;
	}
	
	public String telaMarcarConsulta() {
		List<Paciente> pacientes = new ArrayList<Paciente>();
		pacientes.addAll(consultaDAO.procurarPacientes(pacientePesquisa));
		listaPacientes = pacientes;
		
		clear();
		
		return "/private/consulta/marcar";
	}
	
	public String telaRemarcarConsulta() {
		clear();
		
		carregarConsultas();
		
		return PaginasNavegacao.CONSULTA_REMARCAR;
	}
	
	public String selecionarPacienteMarcarConsulta(Paciente paciente) {
		clear();
		
		this.paciente = paciente;
		
		carregarConsultas();
		
		return PaginasNavegacao.CONSULTA_MARCAR;
	}
	
	public String marcarConsulta(Paciente paciente) {
		if ((eventoCalendario != null) && (eventoCalendario.getData() != null)) {
			Consulta consulta = (Consulta) eventoCalendario.getData();
			if (validaConsulta(consulta)) {
				consultaDAO.marcarConsulta(consulta);
				
				adicionarMensagemAviso("Consulta Marcada", "A consulta do paciente \""
						+ paciente.getNome() + "\", para o dia "
						+ getDataFormatada(data) + ", foi marcada com sucesso" + ".");
				
				return PaginasNavegacao.PAGINA_INICIAL;
			} else {
				return PaginasNavegacao.CONSULTA_MARCAR;
			}
		} else {
			adicionarMensagemErro("Nenhuma data selecionada", "Selecione a data no calendário da consulta do paciente.");
			
			return PaginasNavegacao.CONSULTA_MARCAR;
		}
	}
	
	public void onDataConsultaSelecionada(SelectEvent selectEvent) {
		if (!consultaMarcada) {
			data = (Date) selectEvent.getObject();
			Date dataInicio = new Date(data.getTime());
			Date dataFim = new Date(data.getTime());
			
			Consulta consulta = new Consulta();
			consulta.setData(data);
			consulta.setPaciente(paciente);
			if (validaConsulta(consulta)) {
				setPodeMarcar(true);
				
				eventoCalendario = new DefaultScheduleEvent("Consultar Paciente: "
						+ paciente.getNome(), dataInicio, dataFim);
				
				((DefaultScheduleEvent) eventoCalendario).setStyleClass("marcada");
			} else {
				setPodeMarcar(false);
			}
		} else {
			//TODO Escolher mensagem
			adicionarMensagemAlerta("", "Uma consulta já foi marcada");
		}
	}
	
	public void adicionarConsulta() {
		if (!consultaMarcada) {
			Consulta consulta = new Consulta();
			consulta.setData(eventoCalendario.getStartDate());
			consulta.setPaciente(paciente);
			if (validaConsulta(consulta)) {
				getCalendario().addEvent(eventoCalendario);
				calendario.updateEvent(eventoCalendario);
				
				consultaMarcada = true;
				setPodeMarcar(false);
				
				consulta.setSituacao(Consulta.CONSULTA_MARCADA);
				((DefaultScheduleEvent) eventoCalendario).setData(consulta);
			}
		}
	}
	
	public void onDataConsultaRemarcada(ScheduleEntryMoveEvent event) {
		DefaultScheduleEvent scheduleEvent = (DefaultScheduleEvent) event.getScheduleEvent();
		Consulta consulta = (Consulta) scheduleEvent.getData();
		
		Date dataAntiga = consulta.getData();
		
		consulta.setData(new Date(scheduleEvent.getStartDate().getTime()));
		
		if (validaConsulta(consulta)) {
			scheduleEvent.setStyleClass("remarcado");
			
			consulta.setSituacao(Consulta.CONSULTA_REMARCADA);
			
			cancelados.remove(consulta);
			remarcados.add(consulta);
		} else {
			scheduleEvent.setStartDate(new Date(dataAntiga.getTime()));
			scheduleEvent.setEndDate(new Date(dataAntiga.getTime()));
			consulta.setData(new Date(dataAntiga.getTime()));
		}
	}
	
	public void onConsultaSelecionada(SelectEvent selectEvent) {
		eventoCalendario = (ScheduleEvent) selectEvent.getObject();
	}
	
	public void cancelarConsulta() {
		DefaultScheduleEvent defaultScheduleEvent = (DefaultScheduleEvent) eventoCalendario;
		Consulta consulta = (Consulta) defaultScheduleEvent.getData();
		
		defaultScheduleEvent.setStyleClass("removido");
		consulta.setSituacao(Consulta.CONSULTA_CANCELADA);
		
		remarcados.remove(consulta);
		cancelados.add(consulta);
	}
	
	public String remarcarDatas() {
		consultaDAO.remarcarConsultas(remarcados);
		consultaDAO.cancelarConsultas(cancelados);
		
		remarcados.clear();
		cancelados.clear();
		
		adicionarMensagemAviso("Consultas Remarcadas", "Consultas remarcadas com sucesso.");
		
		return PaginasNavegacao.PAGINA_INICIAL;
	}
	
	private void carregarConsultas() {
		Collection<Consulta> consultas = consultaDAO.procurarConsultasMes(getDataAtual(), null);
		calendario = new DefaultScheduleModel();
		for (Consulta consulta : consultas) {
			String mensagem = "Consultar Paciente: " + consulta.getPaciente().getNome();
			
			Date dataInicio = new Date(consulta.getData().getTime());
			Date dataFim = new Date(consulta.getData().getTime());
			
			ScheduleEvent evento = new DefaultScheduleEvent(mensagem, dataInicio, dataFim);
			((DefaultScheduleEvent) evento).setData(consulta);
			
			if (consulta.isRealizada()) {
				((DefaultScheduleEvent) evento).setStyleClass("realizada");
			} else if (consulta.getData().before(new Date())) {
				((DefaultScheduleEvent) evento).setStyleClass("nao-realizada");
			}
			
			calendario.addEvent(evento);
		}
	}
	
	private boolean validaConsulta(Consulta consulta) {
		Date hoje = new Date();
		if (consulta.isRealizada()) {
			adicionarMensagemAlerta("A consulta já foi realizada", "A consulta selecionada já foi realizada.");
			return false;
		} else if ((consulta.getData().before(hoje)) && (!getDataFormatada(consulta.getData()).equals(getDataFormatada(hoje)))) {
			adicionarMensagemAlerta("Consulta Não marcada", "A data escolhida para a consulta não pode ser inferior a data atual.");
			return false;
		} else {
			Calendar calen = Calendar.getInstance();
			calen.setTime(consulta.getData());
			Calendar hojeCalendar = Calendar.getInstance();
			hojeCalendar.setTime(hoje);
			if ((calen.get(Calendar.YEAR) == hojeCalendar.get(Calendar.YEAR))
					&& (calen.get(Calendar.MONTH) == hojeCalendar.get(Calendar.MONTH))) {
				if (hasConsultaMarcadaMesmoDiaMes(consulta)) {
					adicionarMensagemAlerta("Consulta Não marcada", "O paciente \"" + consulta.getPaciente().getNome() + "\""
							+ " já possuí uma consulta marcada para o dia " + getDataFormatada(consulta.getData()));
					return false;
				}
			} else {
				if (consultaDAO.temConsultaDia(consulta.getPaciente(), consulta.getData())) {
					adicionarMensagemAlerta("Consulta Não marcada", "O paciente \"" + consulta.getPaciente().getNome() + "\""
							+ " já possuí uma consulta marcada para o dia " + getDataFormatada(consulta.getData()));
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * @return the podeMarcar
	 */
	public boolean isPodeMarcar() {
		return podeMarcar;
	}

	/**
	 * @param podeMarcar the podeMarcar to set
	 */
	public void setPodeMarcar(boolean podeMarcar) {
		this.podeMarcar = podeMarcar;
	}
	
	private boolean hasConsultaMarcadaMesmoDiaMes(Consulta consulta) {
		Calendar consultaCalendar = Calendar.getInstance();
		consultaCalendar.setTime(consulta.getData());
		
		List<ScheduleEvent> eventos = calendario.getEvents();
		for (ScheduleEvent scheduleEvent : eventos) {
			DefaultScheduleEvent evento = (DefaultScheduleEvent) scheduleEvent;
			Consulta c = (Consulta) evento.getData();
			if (!consulta.equals(c)) {
				Calendar eventoCalendar = Calendar.getInstance();
				eventoCalendar.setTime(c.getData());
				
				if ((eventoCalendar.get(Calendar.YEAR) == consultaCalendar.get(Calendar.YEAR))
						&& (eventoCalendar.get(Calendar.MONTH) == consultaCalendar.get(Calendar.MONTH))
						&& (eventoCalendar.get(Calendar.DAY_OF_MONTH) == consultaCalendar.get(Calendar.DAY_OF_MONTH))
						&& (consulta.getPaciente().equals(c.getPaciente()))) {
					return true;
				}
			}
		}
		return false;
	}
}