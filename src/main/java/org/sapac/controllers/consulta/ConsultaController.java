/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sapac.controllers.consulta;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.inject.Named;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.FlowEvent;
import org.primefaces.event.ScheduleEntryMoveEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;
import org.sapac.controllers.GenericController;
import org.sapac.controllers.PaginasNavegacao;
import org.sapac.entities.Paciente;

/**
 *
 * @author carlson
 */
@ManagedBean
@SessionScoped
public class ConsultaController extends GenericController {

	private static final long serialVersionUID = 1L;
	private Paciente paciente;
	private Paciente pacientePesquisa;
	private transient DataModel<Paciente> listaPacientes;
	private transient ScheduleModel calendario;
	private transient ScheduleEvent eventoCalendario;
	private String area;
	private List<String> areas;
	private Date date;
	private boolean consultaMarcada;
	private String imagem;

	@PostConstruct
	public void init() {
		paciente = new Paciente();
		pacientePesquisa = new Paciente();
		calendario = new DefaultScheduleModel();
		ScheduleEvent evento = new DefaultScheduleEvent("Primeiro e",
				new Date(), new Date());
		((DefaultScheduleEvent) evento).setStyleClass("");
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
				+ getDataFormatada(date) + ", foi marcada com sucesso" + ".");
		return PaginasNavegacao.PAGINA_INICIAL;
	}

	/**
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	public String pesquisarPacienteConsulta() {
		return PaginasNavegacao.CONSULTA_PESQUISAR_PACIENTE;
	}

	public String consultarPaciente(Paciente paciente) {
		this.paciente = paciente;
		return PaginasNavegacao.CONSULTA_CONSULTAR_PACIENTE;
	}

	public String onFlowProcess(FlowEvent event) {
		return event.getNewStep();
	}

	public void onDateSelect(SelectEvent selectEvent) {
		if (!consultaMarcada) {
			date = (Date) selectEvent.getObject();
			Date dataInicio = new Date(date.getTime());
			Date dataFim = new Date(date.getTime());
			eventoCalendario = new DefaultScheduleEvent("Consultar Paciente: "
					+ paciente.getNome(), dataInicio, dataFim);
			((DefaultScheduleEvent) eventoCalendario).setStyleClass("");
		}
	}

	public void addEvent() {
		if ((!consultaMarcada) && (eventoCalendario != null)) {
			getCalendario().addEvent(eventoCalendario);
			calendario.updateEvent(eventoCalendario);
			consultaMarcada = true;
		}
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
	 * @return the area
	 */
	public String getArea() {
		return area;
	}

	/**
	 * @param area the area to set
	 */
	public void setArea(String area) {
		this.area = area;
	}

	/**
	 * @return the areas
	 */
	public List<String> getAreas() {
		return areas;
	}

	/**
	 * @param areas the areas to set
	 */
	public void setAreas(List<String> areas) {
		this.areas = areas;
	}

	public void handleFileUpload(FileUploadEvent event) throws IOException {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();
		Long time = getDataAtual().getTime();
		imagem = "http://" + getRequest().getServerName() + ":"
				+ getRequest().getServerPort() + getRequest().getContextPath()
				+ "/faces/upload/" + time.toString() + event.getFile().getFileName();
		//File result = new File(externalContext.getRealPath("//WEB-INF//upload") + "//" + event.getFile().getFileName() );
		File result = new File(externalContext.getRealPath("//upload//") + "//"
				+ time.toString() + event.getFile().getFileName());
		try {
			FileOutputStream fileOutputStream = new FileOutputStream(result);
			byte[] buffer;
			buffer = new byte[(int) event.getFile().getSize()];
			int bulk;
			// Here you get uploaded picture bytes, while debugging you can see that 34818
			InputStream inputStream = event.getFile().getInputstream();
			while (true) {
				bulk = inputStream.read(buffer);
				if (bulk < 0) {
					break;
				}
				fileOutputStream.write(buffer, 0, bulk);
				fileOutputStream.flush();
			}

			fileOutputStream.close();
			inputStream.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
		//UploadedFile uploadedFile = event.getFile();

		/*imagem = new DefaultStreamedContent(uploadedFile.getInputstream(),
		 uploadedFile.getContentType());*/
		adicionarMensagemAviso("Arquivo enviado com sucesso",
				event.getFile().getFileName());
	}

	/**
	 * @return the imagem
	 */
	public String getImagem() {
		return imagem;
	}

	/**
	 * @param imagem the imagem to set
	 */
	public void setImagem(String imagem) {
		this.imagem = imagem;
	}

	public String confirmarConsulta() {
		adicionarMensagemAviso("Consulta realizada", "Consulta salva com sucesso.");
		return pesquisarPacienteConsulta();
	}

	public void onEventMove(ScheduleEntryMoveEvent event) {
		DefaultScheduleEvent scheduleEvent = (DefaultScheduleEvent) event.getScheduleEvent();
		scheduleEvent.setStyleClass("remarcado");
		System.out.println(scheduleEvent.getStartDate());
		System.out.println(scheduleEvent.getEndDate());
	}
	
	public void onEventSelect(SelectEvent selectEvent) {
		eventoCalendario = (ScheduleEvent) selectEvent.getObject();
	}
	
	public void cancelarConsulta() {
		((DefaultScheduleEvent) eventoCalendario).setStyleClass("removido");
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
	
	public String visualizarConsulta() {
		return PaginasNavegacao.PACIENTE_VISUALIZAR_CONSULTA;
	}
}