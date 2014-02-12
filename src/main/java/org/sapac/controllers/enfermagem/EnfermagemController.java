/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
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

/**
 *
 * @author carlson
 */
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
	 * @return the intervencaoEnfermagem
	 */
	public IntervencaoEnfermagem getIntervencaoEnfermagem() {
		return intervencaoEnfermagem;
	}

	/**
	 * @param intervencaoEnfermagem the intervencaoEnfermagem to set
	 */
	public void setIntervencaoEnfermagem(IntervencaoEnfermagem intervencaoEnfermagem) {
		this.intervencaoEnfermagem = intervencaoEnfermagem;
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
	 * @return the intervencoesEnfermagem
	 */
	public Collection<IntervencaoEnfermagem> getIntervencoesEnfermagem() {
		return intervencoesEnfermagem;
	}

	/**
	 * @param intervencoesEnfermagem the intervencoesEnfermagem to set
	 */
	public void setIntervencoesEnfermagem(Collection<IntervencaoEnfermagem> intervencoesEnfermagem) {
		this.intervencoesEnfermagem = intervencoesEnfermagem;
	}

	@PostConstruct
	public void init() {
		pacientePesquisa = new Paciente();
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

		Collection<IntervencaoEnfermagem> intervencoesEnfermagem = enfermagemDAO.procurarIntervencoesEnfermagem(paciente);
		for (IntervencaoEnfermagem intervencaoEnfermagem : intervencoesEnfermagem) {
			paciente.getConsultas().add(intervencaoEnfermagem.getConsulta());
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
		enfermagemDAO.alterarDiagnosticoEnfermagem(paciente.getDiagnosticoEnfermagem());

		return PaginasNavegacao.PAGINA_INICIAL;
	}

	public String pesquisarIntervencoes() {
		Collection<IntervencaoEnfermagem> intervencoes = enfermagemDAO.procurarIntervencoesEnfermagemDia(pacientePesquisa, getDataAtual());
		setIntervencoesEnfermagem(intervencoes);

		return "/private/enfermagem/pesquisar_paciente";
	}

	public String salvarIntervencaoEnfermagem() {
		Enfermeiro enfermeiro = (Enfermeiro) perfilController.getUsuario().getMembroEquipe();
		intervencaoEnfermagem.setEnfermeiro(enfermeiro);

		enfermagemDAO.alterarIntervencaoEnfermagem(intervencaoEnfermagem);

		init();

		return pesquisarIntervencoes();
	}
}
