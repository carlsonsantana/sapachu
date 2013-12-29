/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sapac.controllers.consulta;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.inject.Named;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.FlowEvent;
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
public class ConsultaController extends GenericController {

	private static final long serialVersionUID = 1L;
	private Consulta consulta;
	private Paciente pacientePesquisa;
	private transient DataModel<Consulta> listaConsultas;
	private String area;
	private List<String> areas;
	private Date date;
	private String imagem;

	@PostConstruct
	public void init() {
		setConsulta(new Consulta());
		getConsulta().setPaciente(new Paciente());
		pacientePesquisa = new Paciente();

		List<Consulta> consultas = new ArrayList<Consulta>();
		Paciente paciente1 = new Paciente();
		paciente1.setNome("Carlos Miguel Fonseca");
		paciente1.setProntuario("3435.1212");
		Consulta consulta1 = new Consulta();
		consulta1.setPaciente(paciente1);
		consulta1.setSituacao(Consulta.CONSULTA_MARCADA);
		consultas.add(consulta1);
		paciente1 = new Paciente();
		paciente1.setNome("Josefa Maria de Lurdes");
		paciente1.setProntuario("9864.6324");
		Consulta consulta2 = new Consulta();
		consulta2.setPaciente(paciente1);
		consulta2.setSituacao(Consulta.CONSULTA_MARCADA);
		consultas.add(consulta2);
		consultas.add(consulta2);
		consultas.add(consulta2);
		consultas.add(consulta2);
		consultas.add(consulta2);
		consultas.add(consulta2);
		consultas.add(consulta2);
		consultas.add(consulta2);
		consultas.add(consulta2);
		consultas.add(consulta2);
		consultas.add(consulta2);
		consultas.add(consulta2);
		consultas.add(consulta2);
		consultas.add(consulta2);
		consultas.add(consulta2);
		consultas.add(consulta2);
		consultas.add(consulta2);
		consultas.add(consulta2);

		listaConsultas = new ListDataModel<Consulta>(consultas);
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

	public String consultarPaciente(Consulta consulta) {
		setConsulta(consulta);
		return PaginasNavegacao.CONSULTA_CONSULTAR_PACIENTE;
	}

	public String onFlowProcess(FlowEvent event) {
		return event.getNewStep();
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
	
	public String visualizarConsulta() {
		return PaginasNavegacao.PACIENTE_VISUALIZAR_CONSULTA;
	}

	/**
	 * @return the listaConsultas
	 */
	public DataModel<Consulta> getListaConsultas() {
		return listaConsultas;
	}

	/**
	 * @param listaConsultas the listaConsultas to set
	 */
	public void setListaConsultas(DataModel<Consulta> listaConsultas) {
		this.listaConsultas = listaConsultas;
	}

	/**
	 * @return the consulta
	 */
	public Consulta getConsulta() {
		return consulta;
	}

	/**
	 * @param consulta the consulta to set
	 */
	public void setConsulta(Consulta consulta) {
		this.consulta = consulta;
	}
}