package org.sapac.controllers.enfermagem;

import java.util.ArrayList;
import java.util.Collection;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.sapac.annotations.DAOQualifier;
import org.sapac.controllers.GenericController;
import org.sapac.controllers.PaginasNavegacao;
import org.sapac.controllers.usuario.PerfilController;
import org.sapac.entities.Consulta;
import org.sapac.entities.Enfermeiro;
import org.sapac.entities.IntervencaoEnfermagem;
import org.sapac.entities.Paciente;
import org.sapac.models.EnfermagemDAO;

@Named
@SessionScoped
public class EnfermagemController extends GenericController {

	private Paciente paciente;
	private Paciente pacientePesquisa;
	private IntervencaoEnfermagem intervencaoEnfermagem;
	private Collection<IntervencaoEnfermagem> intervencoesEnfermagem;
	@Inject
	private PerfilController perfilController;
	@Inject
	@DAOQualifier(DAOQualifier.DAOType.HIBERNATE)
	private EnfermagemDAO enfermagemDAO;

	public Paciente getPaciente() {
		return paciente;
	}

	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}

	public IntervencaoEnfermagem getIntervencaoEnfermagem() {
		return intervencaoEnfermagem;
	}

	public void setIntervencaoEnfermagem(IntervencaoEnfermagem intervencaoEnfermagem) {
		this.intervencaoEnfermagem = intervencaoEnfermagem;
	}

	public Paciente getPacientePesquisa() {
		return pacientePesquisa;
	}

	public void setPacientePesquisa(Paciente pacientePesquisa) {
		this.pacientePesquisa = pacientePesquisa;
	}

	public Collection<IntervencaoEnfermagem> getIntervencoesEnfermagem() {
		return intervencoesEnfermagem;
	}

	public void setIntervencoesEnfermagem(Collection<IntervencaoEnfermagem> intervencoesEnfermagem) {
		this.intervencoesEnfermagem = intervencoesEnfermagem;
	}

	@PostConstruct
	public void init() {
		pacientePesquisa = new Paciente();
		intervencoesEnfermagem = new ArrayList<IntervencaoEnfermagem>();
	}

	public String telaDianosticarEnfermagem(Paciente paciente) {
		this.paciente = paciente;

		paciente.setDiagnosticoEnfermagem(enfermagemDAO.procurarDiagnosticoEnfermagem(paciente));

		return PaginasNavegacao.ENFERMAGEM_EDITAR_DIAGNOSTICO;
	}

	public String telaIntervencaoEnfermagem(IntervencaoEnfermagem intervencaoEnfermagem) {
		setIntervencaoEnfermagem(intervencaoEnfermagem);
		setPaciente(intervencaoEnfermagem.getConsulta().getPaciente());

		return PaginasNavegacao.ENFERMAGEM_EDITAR_INTERVENCAO;
	}

	public String visualizarDiagnostico(Paciente paciente) {
		paciente.setDiagnosticoEnfermagem(enfermagemDAO.procurarDiagnosticoEnfermagem(paciente));
		paciente.setConsultas(new ArrayList<Consulta>());

		Collection<IntervencaoEnfermagem> intervencoes = enfermagemDAO.procurarIntervencoesEnfermagem(paciente);
		for (IntervencaoEnfermagem intervencao : intervencoes) {
			paciente.getConsultas().add(intervencao.getConsulta());
		}

		setPaciente(paciente);
		setIntervencaoEnfermagem(null);

		return PaginasNavegacao.ENFERMAGEM_VISUALIZAR_DIAGNOSTICO;
	}

	public String visualizarIntervencao(IntervencaoEnfermagem intervencaoEnfermagem) {
		setIntervencaoEnfermagem(intervencaoEnfermagem);

		return PaginasNavegacao.ENFERMAGEM_VISUALIZAR_DIAGNOSTICO;
	}

	public String salvarDiagnosticoEnfermagem() {
		paciente.getDiagnosticoEnfermagem().setEnfermeiro((Enfermeiro) perfilController.getUsuario().getMembroEquipe());
		enfermagemDAO.alterarDiagnosticoEnfermagem(paciente.getDiagnosticoEnfermagem());
		
		adicionarMensagemAviso("", "Diagnóstico alterado com sucesso.");

		return PaginasNavegacao.PAGINA_INICIAL;
	}
	
	public String telaPesquisarPacientes() {
		intervencoesEnfermagem.clear();
		
		return PaginasNavegacao.ENFERMAGEM_PESQUISAR_INTERVENCAO;
	}

	public void pesquisarIntervencoes() {
		setIntervencoesEnfermagem(enfermagemDAO.procurarIntervencoesEnfermagemDia(pacientePesquisa, getDataAtual()));
	}

	public String salvarIntervencaoEnfermagem() {
		Enfermeiro enfermeiro = (Enfermeiro) perfilController.getUsuario().getMembroEquipe();
		intervencaoEnfermagem.setEnfermeiro(enfermeiro);

		enfermagemDAO.alterarIntervencaoEnfermagem(intervencaoEnfermagem);

		init();
		
		adicionarMensagemAviso("", "Intervenção alterada com sucesso.");

		return telaPesquisarPacientes();
	}
}
