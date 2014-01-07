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
import javax.enterprise.context.SessionScoped;
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
import org.sapac.entities.IntervencaoEnfermagem;
import org.sapac.entities.Paciente;
import org.sapac.models.ConsultaDAO;
import org.sapac.models.hibernate.ConsultaDAOHibernate;

/**
 *
 * @author carlson
 */
@Named
@SessionScoped
public class AgendaController extends GenericController {

	private Paciente paciente;
	private Paciente pacientePesquisa;
	private transient DataModel<Paciente> listaPacientes;
	private ScheduleModel calendario;
	private ScheduleEvent eventoCalendario;
	private Date data;
	private boolean consultaMarcada;
	private Collection<Consulta> remarcados;
	private Collection<Consulta> cancelados;

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

	@PostConstruct
	public void init() {
		pacientePesquisa = new Paciente();
		calendario = new DefaultScheduleModel();
		clear();
	}

	public void clear() {
		paciente = new Paciente();
		consultaMarcada = false;
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
	}

	public String telaMarcarConsulta() {
		ConsultaDAO dao = new ConsultaDAOHibernate();
		listaPacientes = new ListDataModel<Paciente>((List) dao.procurarPacientes(pacientePesquisa));

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
		ConsultaDAO dao = new ConsultaDAOHibernate();
		if ((eventoCalendario != null) && (eventoCalendario.getData() != null)) {
			Consulta consulta = (Consulta) eventoCalendario.getData();
			if (validaConsulta(consulta)) {
				dao.marcarConsulta(consulta);

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
			eventoCalendario = new DefaultScheduleEvent("Consultar Paciente: "
					+ paciente.getNome(), dataInicio, dataFim);

			((DefaultScheduleEvent) eventoCalendario).setStyleClass("marcada");
		}
	}

	public void adicionarConsulta() {
		Consulta consulta = new Consulta();
		consulta.setData(eventoCalendario.getStartDate());
		consulta.setPaciente(paciente);
		if (validaConsulta(consulta)) {
			getCalendario().addEvent(eventoCalendario);
			calendario.updateEvent(eventoCalendario);

			consultaMarcada = true;

			consulta.setSituacao(Consulta.CONSULTA_MARCADA);
			((DefaultScheduleEvent) eventoCalendario).setData(consulta);
		}
	}

	public void onDataConsultaRemarcada(ScheduleEntryMoveEvent event) {
		DefaultScheduleEvent scheduleEvent = (DefaultScheduleEvent) event.getScheduleEvent();
		Consulta consulta = (Consulta) scheduleEvent.getData();

		consulta.setData(scheduleEvent.getStartDate());

		if (validaConsulta(consulta)) {
			scheduleEvent.setStyleClass("remarcado");

			consulta.setSituacao(Consulta.CONSULTA_REMARCADA);

			cancelados.remove(consulta);
			remarcados.add(consulta);
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
		ConsultaDAO dao = new ConsultaDAOHibernate();

		dao.remarcarConsultas(remarcados);
		dao.cancelarConsultas(cancelados);

		remarcados.clear();
		cancelados.clear();

		adicionarMensagemAviso("Consultas Remarcadas", "Consultas remarcadas com sucesso.");

		return PaginasNavegacao.PAGINA_INICIAL;
	}

	private void carregarConsultas() {
		ConsultaDAO dao = new ConsultaDAOHibernate();

		Collection<Consulta> consultas = dao.procurarConsultasMes(getDataAtual());
		calendario = new DefaultScheduleModel();
		for (Consulta consulta : consultas) {
			String mensagem = "Consultar Paciente: " + consulta.getPaciente().getNome();

			Date dataInicio = new Date(consulta.getData().getTime());
			Date dataFim = new Date(consulta.getData().getTime());

			ScheduleEvent evento = new DefaultScheduleEvent(mensagem, dataInicio, dataFim);
			((DefaultScheduleEvent) evento).setData(consulta);

			calendario.addEvent(evento);
		}
	}

	private boolean validaConsulta(Consulta consulta) {
		ConsultaDAO dao = new ConsultaDAOHibernate();
		Date hoje = new Date();
		if ((consulta.getData().before(hoje)) && (!getDataFormatada(consulta.getData()).equals(getDataFormatada(hoje)))) {
			adicionarMensagemAlerta("Consulta Não marcada", "A data escolhida para a consulta não pode ser inferior a data atual.");
			return false;
		} else if (dao.temConsultaDia(consulta.getPaciente(), consulta.getData())) {
			adicionarMensagemAlerta("Consulta Não marcada", "O paciente \"" + paciente.getNome() + "\""
					+ "já possuí uma consulta marcada para o dia " + getDataFormatada(data));
			return false;
		}
		return true;
	}
}