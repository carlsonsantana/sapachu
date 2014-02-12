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
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.FlowEvent;
import org.sapac.annotations.DAOQualifier;
import org.sapac.controllers.GenericController;
import org.sapac.controllers.PaginasNavegacao;
import org.sapac.controllers.usuario.PerfilController;
import org.sapac.entities.Consulta;
import org.sapac.entities.FotoUlcera;
import org.sapac.entities.IntervencaoEnfermagem;
import org.sapac.entities.MembroEquipe;
import org.sapac.entities.Paciente;
import org.sapac.entities.SituacaoUlceraConsulta;
import org.sapac.entities.Ulcera;
import org.sapac.entities.VariaveisClinicas;
import org.sapac.models.ConsultaDAO;

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
	private Collection<Consulta> listaConsultas;
	private String area;
	private Date date;
	private String imagem;
	private SituacaoUlceraConsulta situacaoUlceraConsulta;
	private String poligonoUlcera;
	private Collection<Boolean> ulcerasConferidas;
	@Inject
	private PerfilController perfilController;
	@Inject
	@DAOQualifier(DAOQualifier.DAOType.HIBERNATE)
	private ConsultaDAO consultaDAO;

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

	/**
	 * @return the listaConsultas
	 */
	public Collection<Consulta> getListaConsultas() {
		return listaConsultas;
	}

	/**
	 * @param listaConsultas the listaConsultas to set
	 */
	public void setListaConsultas(Collection<Consulta> listaConsultas) {
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

	/**
	 * @return the situacaoUlceraConsulta
	 */
	public SituacaoUlceraConsulta getSituacaoUlceraConsulta() {
		return situacaoUlceraConsulta;
	}

	/**
	 * @param situacaoUlceraConsulta the situacaoUlceraConsulta to set
	 */
	public void setSituacaoUlceraConsulta(SituacaoUlceraConsulta situacaoUlceraConsulta) {
		this.situacaoUlceraConsulta = situacaoUlceraConsulta;
	}

	/**
	 * @return the poligonoUlcera
	 */
	public String getPoligonoUlcera() {
		return poligonoUlcera;
	}

	/**
	 * @param poligonoUlcera the poligonoUlcera to set
	 */
	public void setPoligonoUlcera(String poligonoUlcera) {
		this.poligonoUlcera = poligonoUlcera;
	}

	@PostConstruct
	public void init() {
		setConsulta(new Consulta());
		getConsulta().setPaciente(new Paciente());
		pacientePesquisa = new Paciente();
	}

	public String pesquisarPacienteConsulta() {
		listaConsultas = consultaDAO.procurarConsultasDia(getDataAtual(), pacientePesquisa);

		return PaginasNavegacao.CONSULTA_PESQUISAR_PACIENTE;
	}

	public String consultarPaciente(Consulta consulta) {
		consulta = consultaDAO.carregarConsulta(consulta);

		setConsulta(consulta);

		initializateConsulta();

		return PaginasNavegacao.CONSULTA_CONSULTAR_PACIENTE;
	}

	public String onFlowProcess(FlowEvent event) {
		situacaoUlceraConsulta = null;

		if ((event.getOldStep().equals("ulceras"))
				&& (event.getNewStep().equals("participantes"))) {
			if (!validarSituacoesUlceras()) {
				adicionarMensagemAviso("Situações de Úlceras Não Informadas",
						"Algumas situações das úlceras não foram informadas.");
				
				return event.getOldStep();
			}
		}

		return event.getNewStep();
	}

	public void handleFileUpload(FileUploadEvent event) throws IOException {
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		Long time = getDataAtual().getTime();
		imagem = "http://" + getRequest().getServerName() + ":"
				+ getRequest().getServerPort() + getRequest().getContextPath()
				+ "/faces/upload/" + time.toString() + event.getFile().getFileName();
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

		if (situacaoUlceraConsulta.getFotoUlcera() == null) {
			situacaoUlceraConsulta.setFotoUlcera(new FotoUlcera());
		}
		situacaoUlceraConsulta.getFotoUlcera().setEnderecoImagem(imagem);

		adicionarMensagemAviso("Arquivo enviado com sucesso",
				event.getFile().getFileName());
	}

	public String confirmarConsulta() {
		consultaDAO.editarConsulta(consulta);

		adicionarMensagemAviso("Consulta realizada",
				"Consulta salva com sucesso.");

		return pesquisarPacienteConsulta();
	}

	public String visualizarConsulta(Consulta consulta1) {
		consulta = consultaDAO.carregarConsulta(consulta1);

		setSituacaoUlceraConsulta(null);

		return PaginasNavegacao.PACIENTE_VISUALIZAR_CONSULTA;
	}

	public void adicionarUlcera() {
		Ulcera ulcera = new Ulcera();
		ulcera.setPontos(area);

		SituacaoUlceraConsulta situacaoUlcera = new SituacaoUlceraConsulta();
		situacaoUlcera.setUlcera(ulcera);

		consulta.getSituacoesUlcera().add(situacaoUlcera);

		ulcerasConferidas.add(false);
	}

	public void editarSituacaoUlcera(SituacaoUlceraConsulta situacaoUlcera) {
		setSituacaoUlceraConsulta(situacaoUlcera);

		if (situacaoUlcera.getFotoUlcera() != null) {
			setImagem(situacaoUlcera.getFotoUlcera().getEnderecoImagem());

			setPoligonoUlcera(situacaoUlcera.getFotoUlcera().getPontos());
		} else {
			setImagem("");
		}
	}

	public void finalizarSituacaoUlcera() {
		if (validarSituacaoUlcera(situacaoUlceraConsulta)) {
			situacaoUlceraConsulta.getFotoUlcera().setPontos(poligonoUlcera);

			List<SituacaoUlceraConsulta> situacoes;
			situacoes = (List<SituacaoUlceraConsulta>) consulta.getSituacoesUlcera();
			int index = situacoes.indexOf(situacaoUlceraConsulta);
			((List<Boolean>) ulcerasConferidas).set(index, true);

			situacaoUlceraConsulta = null;
		}
	}

	public void adicionarMembroEquipe(MembroEquipe membroEquipe) {
		if (!consulta.getMembrosEquipe().contains(membroEquipe)) {
			consulta.getMembrosEquipe().add(membroEquipe);
		} else {
			adicionarMensagemAlerta("Membro já adicionado",
					"O membro da equipe médica já foi adicionado como um dos participantes desta consulta.");
		}

	}

	public void removerMembroEquipe(MembroEquipe membroEquipe) {
		if (membroEquipe.equals(perfilController.getUsuario().getMembroEquipe())) {
			adicionarMensagemAlerta("Membro não pode ser removido",
					"Este membro não pode ser removido.");
		} else {
			consulta.getMembrosEquipe().remove(membroEquipe);
		}
	}

	private boolean validarSituacaoUlcera(SituacaoUlceraConsulta situacaoUlceraConsulta1) {
		if (situacaoUlceraConsulta1.getFotoUlcera() == null) {
			adicionarMensagemAlerta("Faltou a imagem",
					"Para cada úlcera é necessário uma foto.");
			return false;
		}

		return true;
	}

	private boolean validarSituacoesUlceras() {
		for (Boolean boolean1 : ulcerasConferidas) {
			if (!boolean1.booleanValue()) {
				adicionarMensagemAlerta("Informações faltando",
						"As informações de algumas úlceras não foram informadas.");

				return false;
			}
		}
		return true;
	}

	private boolean validator(Consulta consulta) {
		return true;
	}

	private void initializateConsulta() {
		if (consulta.getVariaveisClinicas() == null) {
			consulta.setVariaveisClinicas(new VariaveisClinicas());
		}
		if (consulta.getIntervencaoEnfermagem() == null) {
			consulta.setIntervencaoEnfermagem(new IntervencaoEnfermagem());
		}
		if (consulta.getMembrosEquipe() == null) {
			consulta.setMembrosEquipe(new ArrayList<MembroEquipe>());
		}

		MembroEquipe membroEquipe = perfilController.getUsuario().getMembroEquipe();
		if (!consulta.getMembrosEquipe().contains(membroEquipe)) {
			consulta.getMembrosEquipe().add(membroEquipe);
		}

		ulcerasConferidas = new ArrayList<Boolean>();

		if (!((consulta.getSituacoesUlcera() != null)
				&& (!consulta.getSituacoesUlcera().isEmpty()))) {
			consulta.setSituacoesUlcera(new ArrayList<SituacaoUlceraConsulta>());
			Collection<Ulcera> ulceras = consulta.getPaciente().getUlceras();

			for (Ulcera ulcera : ulceras) {
				SituacaoUlceraConsulta situacaoUlceraConsulta2 = new SituacaoUlceraConsulta();
				situacaoUlceraConsulta2.setUlcera(ulcera);
				situacaoUlceraConsulta2.setConsulta(consulta);

				consulta.getSituacoesUlcera().add(situacaoUlceraConsulta2);

				if (ulcera.getSituacao() == 6) {
					ulcerasConferidas.add(true);
				} else {
					ulcerasConferidas.add(false);
				}
			}
		}
	}
}