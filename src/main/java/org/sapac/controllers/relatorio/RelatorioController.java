package org.sapac.controllers.relatorio;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.sapac.annotations.DAOQualifier;
import org.sapac.controllers.GenericController;
import org.sapac.controllers.PaginasNavegacao;
import org.sapac.entities.Medico;
import org.sapac.entities.MembroEquipe;
import org.sapac.models.RelatorioDAO;
import org.sapac.models.UsuarioDAO;

@Named
@SessionScoped
public class RelatorioController extends GenericController {

	private MembroEquipe membroEquipe;
	private MembroEquipe membroEquipePesquisa;
	private transient Collection<Map<String, String>> dados;
	private transient Collection<Map<String, String>> dadosAnterioresVariaveisClinicas;
	private transient Collection<Map<String, String>> dadosPosterioresVariaveisClinicas;
	private transient Collection<Map<String, String>> dadosAnterioresSituacaoUlcera;
	private transient Collection<Map<String, String>> dadosPosterioresSituacaoUlcera;
	private Collection<MembroEquipe> listaMembros;
	
	@Inject
	@DAOQualifier(DAOQualifier.DAOType.HIBERNATE)
	private RelatorioDAO relatorioDAO;
	
	@Inject
	@DAOQualifier(DAOQualifier.DAOType.HIBERNATE)
	private UsuarioDAO usuarioDAO;

	public MembroEquipe getMembroEquipe() {
		return membroEquipe;
	}

	public void setMembroEquipe(MembroEquipe membroEquipe) {
		this.membroEquipe = membroEquipe;
	}
	
	public MembroEquipe getMembroEquipePesquisa() {
		return membroEquipePesquisa;
	}

	public void setMembroEquipePesquisa(MembroEquipe membroEquipePesquisa) {
		this.membroEquipePesquisa = membroEquipePesquisa;
	}
	
	public Collection<MembroEquipe> getListaMembros() {
		return listaMembros;
	}

	public void setListaMembros(Collection<MembroEquipe> listaMembros) {
		this.listaMembros = listaMembros;
	}
	
	public Collection<Map<String, String>> getDados() {
		return dados;
	}

	public void setDados(Collection<Map<String, String>> dados) {
		this.dados = dados;
	}
	
	public Collection<Map<String, String>> getDadosAnterioresVariaveisClinicas() {
		return dadosAnterioresVariaveisClinicas;
	}

	public void setDadosAnterioresVariaveisClinicas(Collection<Map<String, String>> dadosAnterioresVariaveisClinicas) {
		this.dadosAnterioresVariaveisClinicas = dadosAnterioresVariaveisClinicas;
	}

	public Collection<Map<String, String>> getDadosPosterioresVariaveisClinicas() {
		return dadosPosterioresVariaveisClinicas;
	}

	public void setDadosPosterioresVariaveisClinicas(Collection<Map<String, String>> dadosPosterioresVariaveisClinicas) {
		this.dadosPosterioresVariaveisClinicas = dadosPosterioresVariaveisClinicas;
	}

	public Collection<Map<String, String>> getDadosAnterioresSituacaoUlcera() {
		return dadosAnterioresSituacaoUlcera;
	}

	public void setDadosAnterioresSituacaoUlcera(Collection<Map<String, String>> dadosAnterioresSituacaoUlcera) {
		this.dadosAnterioresSituacaoUlcera = dadosAnterioresSituacaoUlcera;
	}

	public Collection<Map<String, String>> getDadosPosterioresSituacaoUlcera() {
		return dadosPosterioresSituacaoUlcera;
	}

	public void setDadosPosterioresSituacaoUlcera(Collection<Map<String, String>> dadosPosterioresSituacaoUlcera) {
		this.dadosPosterioresSituacaoUlcera = dadosPosterioresSituacaoUlcera;
	}
	
	@PostConstruct
	public void init() {
		listaMembros = new ArrayList<MembroEquipe>();
		membroEquipe = new Medico();
		membroEquipePesquisa = new Medico();
	}

	public String gerarRelatorio(MembroEquipe membroEquipe) {
		this.membroEquipe = membroEquipe;

		dados = relatorioDAO.gerarRelatorioDiferenca(membroEquipe);
		dadosAnterioresVariaveisClinicas = relatorioDAO.gerarRelatorioAnteriorVariaveisClinicas(membroEquipe);
		dadosPosterioresVariaveisClinicas = relatorioDAO.gerarRelatorioPosteriorSituacaoUlcera(membroEquipe);
		dadosAnterioresSituacaoUlcera = relatorioDAO.gerarRelatorioAnteriorSituacaoUlcera(membroEquipe);
		dadosPosterioresSituacaoUlcera = relatorioDAO.gerarRelatorioPosteriorSituacaoUlcera(membroEquipe);

		return PaginasNavegacao.RELATORIO_VISUALIZAR;
	}
	
	public String telaPesquisarMembro() {
		listaMembros.clear();
		
		return PaginasNavegacao.RELATORIO_PESQUISAR_USUARIO;
	}

	public void pesquisarUsuarioRelatorio() {
		listaMembros = usuarioDAO.pesquisarUsuario(membroEquipePesquisa);
	}
}