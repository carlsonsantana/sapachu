package org.sapac.controllers.consulta;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.FlowEvent;
import org.sapac.annotations.DAOQualifier;
import org.sapac.controllers.GenericController;
import org.sapac.controllers.PaginasNavegacao;
import org.sapac.controllers.usuario.PerfilController;
import org.sapac.entities.Consulta;
import org.sapac.entities.Enfermeiro;
import org.sapac.entities.FotoUlcera;
import org.sapac.entities.IntervencaoEnfermagem;
import org.sapac.entities.MedicamentoUso;
import org.sapac.entities.MembroEquipe;
import org.sapac.entities.Paciente;
import org.sapac.entities.SituacaoUlceraConsulta;
import org.sapac.entities.Ulcera;
import org.sapac.entities.VariaveisClinicas;
import org.sapac.models.ConsultaDAO;
import org.sapac.models.UsuarioDAO;
import org.sapac.utils.ConfiguracaoHelper;

@Named
@SessionScoped
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
	private String baseComparativoUlcera;
	private Float alturaRealComparativoUlcera;
	private Float larguraRealComparativoUlcera;
	private List<Boolean> ulcerasConferidas;
	private List<SituacaoUlceraConsulta> ulcerasJuntadas;
	private SituacaoUlceraConsulta ulceraDividida;
	private List<Integer> estadosUlceras;
	private Operacao operacao;
	@Inject
	private PerfilController perfilController;
	@Inject
	@DAOQualifier(DAOQualifier.DAOType.HIBERNATE)
	private UsuarioDAO usuarioDAO;
	@Inject
	@DAOQualifier(DAOQualifier.DAOType.HIBERNATE)
	private ConsultaDAO consultaDAO;
	@Inject
	private ConfiguracaoHelper configuracaoHelper;
	private MembroEquipe membroEquipe;
	private Collection<MembroEquipe> membrosEncontrados;

	public MembroEquipe getMembroEquipe() {
		return membroEquipe;
	}

	public void setMembroEquipe(MembroEquipe membroEquipe) {
		this.membroEquipe = membroEquipe;
	}

	private enum Operacao {
		EDITAR_ULCERA,
		REMOVER_ULCERA,
		JUNTAR_ULCERAS,
		DIVIDIR_ULCERA;
	}

	public Paciente getPacientePesquisa() {
		return pacientePesquisa;
	}

	public void setPacientePesquisa(Paciente pacientePesquisa) {
		this.pacientePesquisa = pacientePesquisa;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getImagem() {
		return imagem;
	}

	public void setImagem(String imagem) {
		this.imagem = imagem;
	}

	public Collection<Consulta> getListaConsultas() {
		return listaConsultas;
	}

	public void setListaConsultas(Collection<Consulta> listaConsultas) {
		this.listaConsultas = listaConsultas;
	}

	public Consulta getConsulta() {
		return consulta;
	}

	public void setConsulta(Consulta consulta) {
		this.consulta = consulta;
	}

	public SituacaoUlceraConsulta getSituacaoUlceraConsulta() {
		return situacaoUlceraConsulta;
	}

	public void setSituacaoUlceraConsulta(SituacaoUlceraConsulta situacaoUlceraConsulta) {
		this.situacaoUlceraConsulta = situacaoUlceraConsulta;
	}

	public String getPoligonoUlcera() {
		return poligonoUlcera;
	}

	public void setPoligonoUlcera(String poligonoUlcera) {
		this.poligonoUlcera = poligonoUlcera;
	}
	
	public String getBaseComparativoUlcera() {
		return baseComparativoUlcera;
	}
	
	public void setBaseComparativoUlcera(String baseComparativoUlcera) {
		this.baseComparativoUlcera = baseComparativoUlcera;
	}
	
	public Float getAlturaRealComparativoUlcera() {
		return alturaRealComparativoUlcera;
	}
	
	public void setAlturaRealComparativoUlcera(Float alturaRealComparativoUlcera) {
		this.alturaRealComparativoUlcera = alturaRealComparativoUlcera;
	}
	
	public Float getLarguraRealComparativoUlcera() {
		return larguraRealComparativoUlcera;
	}
	
	public void setLarguraRealComparativoUlcera(Float larguraRealComparativoUlcera) {
		this.larguraRealComparativoUlcera = larguraRealComparativoUlcera;
	}

	public Collection<SituacaoUlceraConsulta> getSituacoesUlcera() {
		Collection<SituacaoUlceraConsulta> situacoes = new ArrayList<SituacaoUlceraConsulta>();
		situacoes.addAll(consulta.getSituacoesUlcera());

		Collection<SituacaoUlceraConsulta> removerSituacaoUlceraConsultas = new ArrayList<SituacaoUlceraConsulta>();

		for (SituacaoUlceraConsulta situacaoUlcera : situacoes) {
			if ((situacaoUlcera.getEstadoUlcera() == Ulcera.ULCERA_JUNTADA)
					|| (situacaoUlcera.getEstadoUlcera() == Ulcera.ULCERA_SEPARADA)) {
				removerSituacaoUlceraConsultas.add(situacaoUlcera);
			}
		}

		situacoes.removeAll(removerSituacaoUlceraConsultas);

		return situacoes;
	}
	
	public boolean isModoEditarSituacaoUlcera() {
		return operacao.equals(Operacao.EDITAR_ULCERA);
	}

	public boolean isModoRemoverUlcera() {
		return operacao.equals(Operacao.REMOVER_ULCERA);
	}

	public boolean isModoJuntarUlceras() {
		return operacao.equals(Operacao.JUNTAR_ULCERAS);
	}

	public boolean isModoDividirUlcera() {
		return operacao.equals(Operacao.DIVIDIR_ULCERA);
	}

	@PostConstruct
	public void init() {
		setConsulta(new Consulta());
		getConsulta().setPaciente(new Paciente());
		pacientePesquisa = new Paciente();
		ulcerasJuntadas = new ArrayList<SituacaoUlceraConsulta>();
		estadosUlceras = new ArrayList<Integer>();
		ulceraDividida = null;
		listaConsultas = new ArrayList<Consulta>();
		membroEquipe = new MembroEquipe();
		membrosEncontrados = new ArrayList<MembroEquipe>();
	}

	public String telaPesquisarPaciente() {
		listaConsultas.clear();

		return PaginasNavegacao.CONSULTA_PESQUISAR_PACIENTE;
	}

	public void pesquisarPacienteConsulta() {
		listaConsultas = consultaDAO.procurarConsultasDia(getDataAtual(), pacientePesquisa);
	}

	public String consultarPaciente(Consulta consulta) {
		consulta = consultaDAO.carregarConsulta(consulta);

		setConsulta(consulta);

		initializateConsulta();

		return PaginasNavegacao.CONSULTA_CONSULTAR_PACIENTE;
	}

	public void uploadFotoUlcera(FileUploadEvent event) throws IOException {
		Long time = getDataAtual().getTime();
		imagem = "http://" + getRequest().getServerName() + ":"
				+ getRequest().getServerPort() + getRequest().getContextPath()
				+ "/uploads/" + time.toString() + event.getFile().getFileName();

		File result = new File(configuracaoHelper.getDiretorioFotosUpload()
				+ time.toString() + event.getFile().getFileName());

		if (!result.getParentFile().exists()) {
			result.getParentFile().mkdirs();
		}
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

		adicionarMensagemAviso("O arquivo \"" + event.getFile().getFileName() + "\" foi enviado com sucesso.");
	}

	public String confirmarConsulta() {
		limparMedicamentosEmUso();

		consultaDAO.editarConsulta(consulta);

		adicionarMensagemAviso("Consulta salva com sucesso.");

		return telaPesquisarPaciente();
	}

	public String visualizarConsulta(Consulta consulta) {
		operacao = Operacao.EDITAR_ULCERA;
		
		setConsulta(consultaDAO.carregarConsulta(consulta));

		setSituacaoUlceraConsulta(null);

		return PaginasNavegacao.PACIENTE_VISUALIZAR_CONSULTA;
	}

	public void adicionarMedicamentoUso() {
		MedicamentoUso medicamento = new MedicamentoUso();

		consulta.getVariaveisClinicas().getMedicamentosUso().add(medicamento);
	}

	public void confirmarSituacaoUlcera() {
		if (validarSituacaoUlcera(situacaoUlceraConsulta)) {
			FotoUlcera fotoUlcera = situacaoUlceraConsulta.getFotoUlcera();
			fotoUlcera.setPontos(poligonoUlcera);
			fotoUlcera.setPontosComparativos(baseComparativoUlcera);
			fotoUlcera.setAlturaRealComparativo(alturaRealComparativoUlcera);
			fotoUlcera.setLarguraRealComparativo(larguraRealComparativoUlcera);

			List<SituacaoUlceraConsulta> situacoes = new ArrayList<SituacaoUlceraConsulta>();
			situacoes.addAll(consulta.getSituacoesUlcera());
			int index = situacoes.indexOf(situacaoUlceraConsulta);
			ulcerasConferidas.set(index, true);

			situacaoUlceraConsulta = null;
			
			area = "";
		}
	}

	public void adicionarMembroEquipe(MembroEquipe membroEquipe) {
		if (!consulta.getMembrosEquipe().contains(membroEquipe)) {
			consulta.getMembrosEquipe().add(membroEquipe);
			membrosEncontrados.remove(membroEquipe);
		} else {
			adicionarMensagemAlerta("O membro da equipe médica já foi adicionado como um dos participantes desta consulta.");
		}

	}

	public void removerMembroEquipe(MembroEquipe membroEquipe) {
		if (!membroEquipe.equals(perfilController.getUsuario().getMembroEquipe())) {
			consulta.getMembrosEquipe().remove(membroEquipe);
		} else {
			adicionarMensagemAlerta("Você não pode se remover como participante da consulta.");
		}
	}

	public String onProximoPasso(FlowEvent event) {
		situacaoUlceraConsulta = null;
		limparDivisaoJuncao();

		if ((event.getOldStep().equals("ulceras"))
				&& ((event.getNewStep().equals("participantes"))
				|| (event.getNewStep().equals("intervencaoenfermagem")))) {
			if (!validarSituacoesUlceras()) {
				adicionarMensagemAviso("Todas as situações das úlceras precisam ser informadas.");

				return event.getOldStep();
			}
		}
		operacao = Operacao.EDITAR_ULCERA;

		return event.getNewStep();
	}

	public void adicionarUlcera() {
		if ((area != null) && (!area.isEmpty())) {
			boolean adicionar = true;

			if (operacao.equals(Operacao.DIVIDIR_ULCERA)) {
				adicionar = validarDivisaoAdicionar();
			} else if (operacao.equals(Operacao.JUNTAR_ULCERAS)) {
				adicionar = validarJuncaoAdicionar();
			}

			if (adicionar) {
				Ulcera ulcera = new Ulcera();
				ulcera.setPontos(area);
				ulcera.setPaciente(getConsulta().getPaciente());

				SituacaoUlceraConsulta situacaoUlcera = new SituacaoUlceraConsulta();
				situacaoUlcera.setUlcera(ulcera);

				consulta.getSituacoesUlcera().add(situacaoUlcera);

				ulcerasConferidas.add(false);

				if (operacao.equals(Operacao.JUNTAR_ULCERAS)) {
					ulcera.setUlcerasResultado(new ArrayList<Ulcera>());
					for (SituacaoUlceraConsulta ulceraJuntada : ulcerasJuntadas) {
						ulceraJuntada.getUlcera().setUlcerasResultado(new ArrayList<Ulcera>());
						ulceraJuntada.getUlcera().getUlcerasResultado().add(ulcera);
					}
					operacao = Operacao.EDITAR_ULCERA;
					
					ulcerasJuntadas.clear();
				} else if (operacao.equals(Operacao.DIVIDIR_ULCERA)) {
					if (ulceraDividida.getUlcera().getUlcerasResultado() == null) {
						ulceraDividida.getUlcera().setUlcerasResultado(new ArrayList<Ulcera>());
					}
					ulceraDividida.getUlcera().getUlcerasResultado().add(ulcera);
				} else {
					operacao = Operacao.EDITAR_ULCERA;
				}
			}
		} else {
			adicionarMensagemErro("É necessário selecionar uma área para adicionar.");
		}
		area = "";
	}

	public void mudarModoRemoverUlcera() {
		operacao = Operacao.REMOVER_ULCERA;
		limparDivisaoJuncao();
	}

	public void mudarModoJuntarUlceras() {
		operacao = Operacao.JUNTAR_ULCERAS;
		limparDivisaoJuncao();
	}

	public void mudarModoDividirUlcera() {
		operacao = Operacao.DIVIDIR_ULCERA;
		limparDivisaoJuncao();
	}

	public void finalizarDivisaoJuncao() {
		operacao = Operacao.EDITAR_ULCERA;
		limparDivisaoJuncao();
	}

	public void confirmarDivisaoJuncao() {
		if (validarDivisaoJuncao()) {
			ulcerasJuntadas.clear();
			ulceraDividida = null;
			operacao = Operacao.EDITAR_ULCERA;
		}
	}

	public void editarUlcera(SituacaoUlceraConsulta situacaoUlcera) {
		setSituacaoUlceraConsulta(situacaoUlcera);

		if (situacaoUlcera.getFotoUlcera() != null) {
			setImagem(situacaoUlcera.getFotoUlcera().getEnderecoImagem());

			FotoUlcera fotoUlcera = situacaoUlcera.getFotoUlcera();
			setPoligonoUlcera(fotoUlcera.getPontos());
			setBaseComparativoUlcera(fotoUlcera.getPontosComparativos());
			setAlturaRealComparativoUlcera(fotoUlcera.getAlturaRealComparativo());
			setLarguraRealComparativoUlcera(fotoUlcera.getLarguraRealComparativo());
		} else {
			setImagem("");
		}
	}

	public void removerUlcera(SituacaoUlceraConsulta situacaoUlcera) {
		limparDivisaoJuncao();

		if (situacaoUlcera.getUlcera().getId() > 0) {
			adicionarMensagemErro("Uma úlcera que já foi salva, não pode excluí-la.");
		} else {
			Iterator<SituacaoUlceraConsulta> iteratorSituacao = consulta.getSituacoesUlcera().iterator();
			int index = -1;
			for (int i = 0, length = consulta.getSituacoesUlcera().size(); i < length; i++) {
				if (situacaoUlcera.equals(iteratorSituacao.next())) {
					index = i;
					break;
				}
			}
			ulcerasJuntadas.remove(situacaoUlcera);
			ulceraDividida.getUlcera().getUlcerasResultado().remove(situacaoUlcera.getUlcera());

			ulcerasConferidas.remove(index);
			consulta.getSituacoesUlcera().remove(situacaoUlcera);
		}
		mudarModoRemoverUlcera();
	}

	public void juntarUlceras(SituacaoUlceraConsulta situacaoUlcera) {
		if ((situacaoUlcera.getFotoUlcera() == null)
				|| (situacaoUlcera.getFotoUlcera().getId() == 0)) {
			estadosUlceras.add(situacaoUlcera.getEstadoUlcera());
			ulcerasJuntadas.add(situacaoUlcera);
			situacaoUlcera.setEstadoUlcera(Ulcera.ULCERA_JUNTADA);
		} else if (situacaoUlcera.getUlcera().getId() == 0) {
			adicionarMensagemErro("Não é possível juntar uma úlcera que não foi salva anteriormente.");
		} else {
			adicionarMensagemErro("Não é possível juntar uma úlcera que já teve sua situação informada.");
		}
	}

	public void dividirUlcera(SituacaoUlceraConsulta situacaoUlcera) {
		if (ulceraDividida != null) {
			editarUlcera(situacaoUlcera);
		} else {
			if ((situacaoUlcera.getFotoUlcera() == null)
					|| (situacaoUlcera.getFotoUlcera().getId() == 0)) {
				estadosUlceras.add(situacaoUlcera.getEstadoUlcera());
				ulceraDividida = situacaoUlcera;
				situacaoUlcera.setEstadoUlcera(Ulcera.ULCERA_SEPARADA);
			} else if (situacaoUlcera.getUlcera().getId() == 0) {
				adicionarMensagemErro("Não é possível dividir uma úlcera que não foi salva anteriormente.");
			} else {
				adicionarMensagemErro("Não é possível dividir uma úlcera que já teve sua situação informada.");
			}
		}
	}

	public void selecionarSituacaoUlcera(SituacaoUlceraConsulta situacaoUlcera) {
		area = "";
		if (operacao.equals(Operacao.EDITAR_ULCERA)) {
			editarUlcera(situacaoUlcera);
		} else if (operacao.equals(Operacao.REMOVER_ULCERA)) {
			removerUlcera(situacaoUlcera);
		} else if (operacao.equals(Operacao.JUNTAR_ULCERAS)) {
			juntarUlceras(situacaoUlcera);
		} else if (operacao.equals(Operacao.DIVIDIR_ULCERA)) {
			dividirUlcera(situacaoUlcera);
		}
	}
	
	public void pesquisarMembrosNaoParticipantes() {
		Collection<MembroEquipe> membros = usuarioDAO.pesquisarUsuario(membroEquipe);
		membros.removeAll(consulta.getMembrosEquipe());
		membrosEncontrados = membros;
	}
	
	public Collection<MembroEquipe> getMembrosEncontrados() {
		return membrosEncontrados;
	}
	
	public void setMembrosEncontrados(Collection<MembroEquipe> membros) {
		membrosEncontrados = membros;
	}
	
	private boolean validarSituacaoUlcera(SituacaoUlceraConsulta situacaoUlcera) {
		if (situacaoUlcera.getFotoUlcera() == null) {
			adicionarMensagemAlerta("É necessário fazer o upload da foto da úlcera.");
			return false;
		} else if ((!situacaoUlcera.isCicatrizada()) && (situacaoUlcera.getArea() == 0.0)) {
			adicionarMensagemAlerta("É necessário selecionar a área da úlcera.");
			return false;
		}

		return true;
	}

	private void initializateConsulta() {
		if (consulta.getVariaveisClinicas() == null) {
			consulta.setVariaveisClinicas(new VariaveisClinicas());
		}
		if (consulta.getVariaveisClinicas().getMedicamentosUso() == null) {
			consulta.getVariaveisClinicas().setMedicamentosUso(new ArrayList<MedicamentoUso>());
		}
		if (consulta.getVariaveisClinicas().getMedicamentosUso().isEmpty()) {
			consulta.getVariaveisClinicas().getMedicamentosUso().add(new MedicamentoUso());
		}
		if (consulta.getIntervencaoEnfermagem() == null) {
			consulta.setIntervencaoEnfermagem(new IntervencaoEnfermagem());
		}
		if (perfilController.isEnfermeiro()) {
			consulta.getIntervencaoEnfermagem().setEnfermeiro((Enfermeiro) perfilController.getUsuario().getMembroEquipe());
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
				SituacaoUlceraConsulta situacaoUlcera = new SituacaoUlceraConsulta();
				situacaoUlcera.setUlcera(ulcera);
				situacaoUlcera.setConsulta(consulta);
				situacaoUlcera.setEstadoUlcera(ulcera.getSituacao());

				consulta.getSituacoesUlcera().add(situacaoUlcera);

				if (ulcera.getSituacao() == Ulcera.ULCERA_CICATRIZADA) {
					ulcerasConferidas.add(true);
				} else {
					ulcerasConferidas.add(false);
				}
			}
		} else {
			Collection<SituacaoUlceraConsulta> situacoesUlceras = consulta.getSituacoesUlcera();
			for (SituacaoUlceraConsulta situacaoUlcera : situacoesUlceras) {
				if (((situacaoUlcera.getFotoUlcera() != null)
						&& (situacaoUlcera.getFotoUlcera().getEnderecoImagem() != null)
						&& (!situacaoUlcera.getFotoUlcera().getEnderecoImagem().isEmpty()))
						|| (situacaoUlcera.getUlcera().getSituacao() == Ulcera.ULCERA_CICATRIZADA)) {
					ulcerasConferidas.add(true);
				} else {
					ulcerasConferidas.add(false);
				}
			}
		}
	}

	private void limparMedicamentosEmUso() {
		Collection<MedicamentoUso> medicamentosRemover = new ArrayList<MedicamentoUso>();
		Collection<MedicamentoUso> medicamentoUsos = consulta.getVariaveisClinicas().getMedicamentosUso();

		for (MedicamentoUso medicamentoUso : medicamentoUsos) {
			if ((medicamentoUso.getNome() == null) || (medicamentoUso.getNome().trim().isEmpty())) {
				medicamentosRemover.add(medicamentoUso);
			} else {
				medicamentoUso.setNome(medicamentoUso.getNome().trim());
			}
		}

		consulta.getVariaveisClinicas().getMedicamentosUso().removeAll(medicamentosRemover);

		medicamentosRemover.clear();
		for (MedicamentoUso medicamentoUso : medicamentoUsos) {
			for (MedicamentoUso medicamentoPrepara : medicamentoUsos) {
				if (medicamentoUso != medicamentoPrepara) {
					if (medicamentoUso.getNome().toLowerCase().equals(medicamentoPrepara.getNome().toLowerCase())) {
						medicamentosRemover.add(medicamentoPrepara);
					}
				}
			}
		}

		consulta.getVariaveisClinicas().getMedicamentosUso().removeAll(medicamentosRemover);
	}
	
	private boolean validarSituacoesUlceras() {
		List<SituacaoUlceraConsulta> situacoes = new ArrayList<SituacaoUlceraConsulta>();
		situacoes.addAll(consulta.getSituacoesUlcera());

		for (int i = 0; i < situacoes.size(); i++) {
			if ((situacoes.get(i).getEstadoUlcera() != Ulcera.ULCERA_JUNTADA)
					&& (situacoes.get(i).getEstadoUlcera() != Ulcera.ULCERA_SEPARADA)
					&& (!ulcerasConferidas.get(i).booleanValue())) {
				return false;
			}
		}

		return true;
	}
	
	
	private void limparDivisao() {
		if ((ulceraDividida != null)
				&& (ulceraDividida.getUlcera().getUlcerasResultado() != null)) {
			Collection<Ulcera> ulceras = ulceraDividida.getUlcera().getUlcerasResultado();
			Collection<SituacaoUlceraConsulta> situacoes = consulta.getSituacoesUlcera();
			Collection<SituacaoUlceraConsulta> situacoesRemover = new ArrayList<SituacaoUlceraConsulta>();

			int numeroEstadosRemovidos = 0;
			int i = 0;
			for (SituacaoUlceraConsulta situacao : situacoes) {
				for (Ulcera ulcera : ulceras) {
					if (situacao.getUlcera().equals(ulcera)) {
						situacoesRemover.add(situacao);
						ulcerasConferidas.remove(i + numeroEstadosRemovidos);
						numeroEstadosRemovidos--;
						break;
					}
				}
				i++;
			}

			ulceraDividida.getUlcera().getUlcerasResultado().clear();
			ulceraDividida.getUlcera().setUlcerasResultado(null);
			ulceraDividida.setEstadoUlcera(estadosUlceras.get(0));

			consulta.getSituacoesUlcera().removeAll(situacoesRemover);
		}

		ulceraDividida = null;
	}
	
	private void limparJuncao() {
		if (!ulcerasJuntadas.isEmpty()) {
			int length = ulcerasJuntadas.size();

			Collection<SituacaoUlceraConsulta> situacoes = consulta.getSituacoesUlcera();

			Collection<SituacaoUlceraConsulta> situacoesRemover = new ArrayList<SituacaoUlceraConsulta>();

			for (int i = 0; i < length; i++) {
				SituacaoUlceraConsulta situacao = ulcerasJuntadas.get(i);

				situacao.setEstadoUlcera(estadosUlceras.get(i));

				Collection<Ulcera> ulcerasResultado = situacao.getUlcera().getUlcerasResultado();
				for (SituacaoUlceraConsulta situacaoPesquisa : situacoes) {
					for (Ulcera ulcera : ulcerasResultado) {
						if (situacaoPesquisa.getUlcera().equals(ulcera)) {
							situacoesRemover.add(situacaoPesquisa);
							break;
						}
					}
				}

				situacao.getUlcera().setUlcerasResultado(null);
			}

			consulta.getSituacoesUlcera().removeAll(situacoesRemover);

			ulcerasJuntadas.clear();
		}
	}
	
	private void limparDivisaoJuncao() {
		limparDivisao();
		limparJuncao();
		estadosUlceras.clear();
	}
	
	private boolean validarDivisaoJuncao() {
		if (operacao.equals(Operacao.DIVIDIR_ULCERA)) {
			if ((ulceraDividida.getUlcera().getUlcerasResultado() != null)
					&& (ulceraDividida.getUlcera().getUlcerasResultado().size() >= 2)) {
				ulceraDividida = null;

				return true;
			} else {
				adicionarMensagemErro("Para dividir uma úlcera é necessário, pelo menos duas úlceras sejam adicionadas.");

				return false;
			}
		} else if (operacao.equals(Operacao.JUNTAR_ULCERAS)) {
			if (ulcerasJuntadas.size() >= 2) {
				ulcerasJuntadas.clear();

				return true;
			} else {
				adicionarMensagemErro("Para juntar uma úlcera é necessário, pelo menos duas úlceras sejam juntadas.");

				return false;
			}
		}

		return true;
	}

	private boolean validarDivisaoAdicionar() {
		if (ulceraDividida == null) {
			adicionarMensagemErro("Para adicionar uma úlcera no modo de divisão é necessário, primeiro selecionar uma úlcera.");

			return false;
		}

		return true;
	}

	private boolean validarJuncaoAdicionar() {
		if (ulcerasJuntadas.size() < 2) {
			adicionarMensagemErro("Para juntar uma úlcera é necessário, pelo menos duas úlceras sejam juntadas.");

			return false;
		}

		return true;
	}
}