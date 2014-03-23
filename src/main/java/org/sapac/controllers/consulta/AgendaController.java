package org.sapac.controllers.consulta;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
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

@Named
@SessionScoped
public class AgendaController extends GenericController {

	private Paciente paciente;
	private Paciente pacientePesquisa;
	private Collection<Paciente> listaPacientes;
	private ScheduleModel calendario;
	private DefaultScheduleEvent eventoCalendario;
	private Date data;
	private boolean consultaMarcada;
	private Collection<Consulta> remarcados;
	private Collection<Consulta> cancelados;
	private boolean podeMarcar;
	@Inject
	@DAOQualifier(DAOQualifier.DAOType.HIBERNATE)
	private ConsultaDAO consultaDAO;

	public Paciente getPaciente() {
		return paciente;
	}

	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}

	public Paciente getPacientePesquisa() {
		return pacientePesquisa;
	}

	public void setPacientePesquisa(Paciente pacientePesquisa) {
		this.pacientePesquisa = pacientePesquisa;
	}

	public Collection<Paciente> getListaPacientes() {
		return listaPacientes;
	}

	public void setListaPacientes(Collection<Paciente> listaPacientes) {
		this.listaPacientes = listaPacientes;
	}

	public ScheduleModel getCalendario() {
		return calendario;
	}

	public void setCalendario(ScheduleModel calendario) {
		this.calendario = calendario;
	}

	public ScheduleEvent getEventoCalendario() {
		return eventoCalendario;
	}

	public void setEventoCalendario(DefaultScheduleEvent eventoCalendario) {
		this.eventoCalendario = eventoCalendario;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public boolean isConsultaMarcada() {
		return consultaMarcada;
	}

	public void setConsultaMarcada(boolean consultaMarcada) {
		this.consultaMarcada = consultaMarcada;
	}
	
	public boolean isPodeMarcar() {
		return podeMarcar;
	}

	public void setPodeMarcar(boolean podeMarcar) {
		this.podeMarcar = podeMarcar;
	}

	@PostConstruct
	public void init() {
		pacientePesquisa = new Paciente();
		calendario = new DefaultScheduleModel();
		listaPacientes = new ArrayList<Paciente>();
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
		listaPacientes.clear();
		eventoCalendario = new DefaultScheduleEvent();

		setPodeMarcar(false);
		consultaMarcada = false;
	}

	public String telaMarcarConsulta() {
		clear();

		return PaginasNavegacao.CONSULTA_PESQUISAR_PACIENTE_MARCAR;
	}
	
	public void pesquisarPaciente() {
		listaPacientes.clear();
		listaPacientes.addAll(consultaDAO.procurarPacientes(pacientePesquisa));
	}

	public String telaRemarcarConsulta() {
		clear();

		carregarConsultas(getDataAtual());

		return PaginasNavegacao.CONSULTA_REMARCAR;
	}

	public String selecionarPacienteMarcarConsulta(Paciente paciente) {
		clear();

		this.paciente = paciente;

		carregarConsultas(getDataAtual());

		return PaginasNavegacao.CONSULTA_MARCAR;
	}

	public String marcarConsulta(Paciente paciente) {
		if ((eventoCalendario != null) && (eventoCalendario.getData() != null)) {
			Consulta consulta = (Consulta) eventoCalendario.getData();
			if (validaConsulta(consulta)) {
				consultaDAO.marcarConsulta(consulta);

				adicionarMensagemAviso("Consulta Marcada", "A consulta do paciente \""
						+ paciente.getNome() + "\", para o dia "
						+ getDataFormatada(data) + ", foi marcada com sucesso.");

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

				eventoCalendario.setStyleClass("marcada");
			} else {
				setPodeMarcar(false);
			}
		} else {
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
				eventoCalendario.setData(consulta);
			}
		}
	}

	public void onDataConsultaRemarcada(ScheduleEntryMoveEvent event) {
		DefaultScheduleEvent scheduleEvent = (DefaultScheduleEvent) event.getScheduleEvent();
		Consulta consultaRemarcada = (Consulta) scheduleEvent.getData();
		
		Date dataAntiga = consultaRemarcada.getData();
		if (!consultaRemarcada.isRealizada()) {
			Consulta novaConsulta = consultaRemarcada.clone();

			novaConsulta.setId(0);
			novaConsulta.setData(new Date(scheduleEvent.getStartDate().getTime()));
			novaConsulta.setConsultaMarcada(null);
			novaConsulta.setSituacao(Consulta.CONSULTA_MARCADA);

			if (validaConsulta(novaConsulta)) {
				scheduleEvent.setStyleClass("remarcado");

				consultaRemarcada.setConsultaMarcada(novaConsulta);
				consultaRemarcada.setSituacao(Consulta.CONSULTA_REMARCADA);

				cancelados.remove(consultaRemarcada);
				if (!remarcados.contains(consultaRemarcada)) {
					remarcados.add(consultaRemarcada);
				}
			} else {
				scheduleEvent.setStartDate(new Date(dataAntiga.getTime()));
				scheduleEvent.setEndDate(new Date(dataAntiga.getTime()));
				consultaRemarcada.setData(new Date(dataAntiga.getTime()));
			}
		} else {
			adicionarMensagemAlerta("A consulta já foi realizada", "A consulta selecionada já foi realizada.");
			scheduleEvent.setStartDate(new Date(dataAntiga.getTime()));
			scheduleEvent.setEndDate(new Date(dataAntiga.getTime()));
		}
	}

	public void onConsultaSelecionada(SelectEvent selectEvent) {
		eventoCalendario = (DefaultScheduleEvent) selectEvent.getObject();
	}

	public void cancelarConsulta() {
		Consulta consulta = (Consulta) eventoCalendario.getData();
		if (!consulta.isRealizada()) {
			eventoCalendario.setStyleClass("removido");
			consulta.setSituacao(Consulta.CONSULTA_CANCELADA);

			remarcados.remove(consulta);
			cancelados.add(consulta);
		} else {
			adicionarMensagemAlerta("A consulta já foi realizada", "A consulta selecionada já foi realizada.");
		}
	}

	public String remarcarDatas() {
		consultaDAO.remarcarConsultas(remarcados);
		consultaDAO.cancelarConsultas(cancelados);

		remarcados.clear();
		cancelados.clear();

		adicionarMensagemAviso("Consultas Remarcadas", "Consultas remarcadas com sucesso.");

		return PaginasNavegacao.PAGINA_INICIAL;
	}

	private void carregarConsultas(Date data) {
		Collection<Consulta> consultas = consultaDAO.procurarConsultasMes(null, null);
		calendario = new DefaultScheduleModel();
		for (Consulta consulta : consultas) {
			String mensagem = "Consultar Paciente: " + consulta.getPaciente().getNome();

			Date dataInicio = new Date(consulta.getData().getTime());
			Date dataFim = new Date(consulta.getData().getTime());

			DefaultScheduleEvent evento = new DefaultScheduleEvent(mensagem, dataInicio, dataFim);
			evento.setData(consulta);

			Date hoje = new Date();
			
			if (consulta.isRealizada()) {
				evento.setStyleClass("realizada");
			} else if ((consulta.getData().before(hoje))
					&& (!isMesmaData(consulta.getData(), hoje))) {
				evento.setStyleClass("nao-realizada");
			}

			calendario.addEvent(evento);
		}
	}

	private boolean validaConsulta(Consulta consulta) {
		Date hoje = new Date();
		if ((consulta.getData().before(hoje))
				&& (!getDataFormatada(consulta.getData()).equals(getDataFormatada(hoje)))) {
			adicionarMensagemAlerta("Consulta Não marcada", "A data escolhida para a consulta não pode ser inferior a data atual.");
			return false;
		} else {
			if (isMesmoMes(consulta.getData(), hoje)) {
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

	private boolean hasConsultaMarcadaMesmoDiaMes(Consulta consulta) {
		if (consulta.isRemarcada()) {
			consulta = consulta.getConsultaMarcada();
		}
		Date dataConsulta = consulta.getData();

		Collection<ScheduleEvent> eventos = calendario.getEvents();
		for (ScheduleEvent scheduleEvent : eventos) {
			DefaultScheduleEvent evento = (DefaultScheduleEvent) scheduleEvent;
			Consulta c = (Consulta) evento.getData();
			if (c.isRemarcada()) {
				c = c.getConsultaMarcada();
			}
			if (!consulta.equals(c)) {
				if ((isMesmaData(dataConsulta, c.getData()))
						&& (consulta.getPaciente().equals(c.getPaciente()))) {
					return true;
				}
			}
		}
		return false;
	}
	
	private boolean isMesmaData(Date data1, Date data2) {
		Calendar calendar1 = Calendar.getInstance();
		calendar1.setTime(data1);
		
		Calendar calendar2 = Calendar.getInstance();
		calendar2.setTime(data2);
		
		return ((calendar1.get(Calendar.DAY_OF_MONTH) == calendar2.get(Calendar.DAY_OF_MONTH))
				&& isMesmoMes(data1, data2));
	}
	
	private boolean isMesmoMes(Date data1, Date data2) {
		Calendar calendar1 = Calendar.getInstance();
		calendar1.setTime(data1);
		
		Calendar calendar2 = Calendar.getInstance();
		calendar2.setTime(data2);
		
		return ((calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR))
			&& (calendar1.get(Calendar.MONTH) == calendar2.get(Calendar.MONTH)));
	}
}