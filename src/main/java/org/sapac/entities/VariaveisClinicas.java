/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sapac.entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

/**
 *
 * @author carlson
 */
@Entity
@Table(name = "variaveis_clinicas")
public class VariaveisClinicas implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_variaveis_clinicas")
	private int id;
	
	@OneToOne
	@JoinColumn(name = "id_consulta")
	@NotNull
	private Consulta consulta;
	
	@OneToMany(mappedBy = "variaveisClinicas")
	private Collection<MedicamentoUso> medicamentosUso;
	
	@Column(name = "pressao_arterial")
	@NotNull
	private float pressaoArterial;
	@Column(name = "indice_massa_corporal")
	@NotNull
	private float indiceMassaCorporal;
	@Column(name = "nivel_glicemia")
	@NotNull
	private float nivelGlicemia;
	@Column(name = "nivel_creatina")
	@NotNull
	private float nivelCreatina;
	@Column(name = "colesterol_hdl")
	@NotNull
	private float colesterolHDL;
	@NotNull
	private float vhs;
	@NotNull
	private float cintura;
	@NotNull
	private float quadril;
	@Column(name = "indice_tornozelo_braco")
	@NotNull
	private float indiceTornozeloBraco;
	@Column(name = "velocidade_hemossedimentacao")
	@NotNull
	private float velocidadeHemossedimentacao;
	@NotNull
	private char mobilidade;
	@Column(name = "desbridamento_cirurgico")
	@NotNull
	private boolean desbridamentoCirurgico;
	@NotNull
	private boolean compressao;
	@NotNull
	private boolean repouso;
	@NotNull
	private boolean coopera;
	@NotNull
	private boolean diabetes;
	@Column(name = "cardiopatia_isquemica")
	@NotNull
	private boolean cardiopatiaIsquemica;
	@Column(name = "doenca_renal")
	@NotNull
	private boolean doencaRenal;
	@Column(name = "acidente_vascular_cerebral")
	@NotNull
	private boolean acidenteVascularCerebral;
	@NotNull
	private boolean artrite;
	@Column(name = "alteracoes_visao")
	@NotNull
	private boolean alteracoesVisao;
	@Column(name = "trombose_vascular_profunda")
	@NotNull
	private boolean tromboseVascularProfunda;
	@NotNull
	private boolean fratura;
	@NotNull
	private boolean cirurgia;
	@Column(name = "doenca_arterial_periferica")
	@NotNull
	private boolean doencaArterialPeriferica;
	@NotNull
	private boolean alcoolista;
	@Column(name = "dermatite_ocre")
	@NotNull
	private boolean dermatiteOcre;
	@NotNull
	private boolean lipodermatoesclerose;
	@NotNull
	private boolean asma;
	@Column(name = "anemia_falciforme")
	@NotNull
	private boolean anemiaFalciforme;
	@Column(name = "macos_cigarro_diarios")
	@NotNull
	private int macosCigarroDiarios;
	@NotNull
	private int ceap;
	@Column(name = "outras_drogas")
	@NotNull
	private String outrasDrogas;
	@Temporal(TemporalType.DATE)
	@Column(name = "periodo_inicial_ferida")
	private Date periodoInicialFerida;
	@Temporal(TemporalType.DATE)
	@Column(name = "periodo_final_ferida")
	private Date periodoFinalFerida;

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
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
	 * @return the medicamentosUso
	 */
	public Collection<MedicamentoUso> getMedicamentosUso() {
		return medicamentosUso;
	}

	/**
	 * @param medicamentosUso the medicamentosUso to set
	 */
	public void setMedicamentosUso(Collection<MedicamentoUso> medicamentosUso) {
		this.medicamentosUso = medicamentosUso;
	}

	/**
	 * @return the pressaoArterial
	 */
	public float getPressaoArterial() {
		return pressaoArterial;
	}

	/**
	 * @param pressaoArterial the pressaoArterial to set
	 */
	public void setPressaoArterial(float pressaoArterial) {
		this.pressaoArterial = pressaoArterial;
	}

	/**
	 * @return the indiceMassaCorporal
	 */
	public float getIndiceMassaCorporal() {
		return indiceMassaCorporal;
	}

	/**
	 * @param indiceMassaCorporal the indiceMassaCorporal to set
	 */
	public void setIndiceMassaCorporal(float indiceMassaCorporal) {
		this.indiceMassaCorporal = indiceMassaCorporal;
	}

	/**
	 * @return the nivelGlicemia
	 */
	public float getNivelGlicemia() {
		return nivelGlicemia;
	}

	/**
	 * @param nivelGlicemia the nivelGlicemia to set
	 */
	public void setNivelGlicemia(float nivelGlicemia) {
		this.nivelGlicemia = nivelGlicemia;
	}

	/**
	 * @return the nivelCreatina
	 */
	public float getNivelCreatina() {
		return nivelCreatina;
	}

	/**
	 * @param nivelCreatina the nivelCreatina to set
	 */
	public void setNivelCreatina(float nivelCreatina) {
		this.nivelCreatina = nivelCreatina;
	}

	/**
	 * @return the colesterolHDL
	 */
	public float getColesterolHDL() {
		return colesterolHDL;
	}

	/**
	 * @param colesterolHDL the colesterolHDL to set
	 */
	public void setColesterolHDL(float colesterolHDL) {
		this.colesterolHDL = colesterolHDL;
	}

	/**
	 * @return the vhs
	 */
	public float getVhs() {
		return vhs;
	}

	/**
	 * @param vhs the vhs to set
	 */
	public void setVhs(float vhs) {
		this.vhs = vhs;
	}

	/**
	 * @return the cintura
	 */
	public float getCintura() {
		return cintura;
	}

	/**
	 * @param cintura the cintura to set
	 */
	public void setCintura(float cintura) {
		this.cintura = cintura;
	}

	/**
	 * @return the quadril
	 */
	public float getQuadril() {
		return quadril;
	}

	/**
	 * @param quadril the quadril to set
	 */
	public void setQuadril(float quadril) {
		this.quadril = quadril;
	}

	/**
	 * @return the indiceTornozeloBraco
	 */
	public float getIndiceTornozeloBraco() {
		return indiceTornozeloBraco;
	}

	/**
	 * @param indiceTornozeloBraco the indiceTornozeloBraco to set
	 */
	public void setIndiceTornozeloBraco(float indiceTornozeloBraco) {
		this.indiceTornozeloBraco = indiceTornozeloBraco;
	}

	/**
	 * @return the velocidadeHemossedimentacao
	 */
	public float getVelocidadeHemossedimentacao() {
		return velocidadeHemossedimentacao;
	}

	/**
	 * @param velocidadeHemossedimentacao the velocidadeHemossedimentacao to set
	 */
	public void setVelocidadeHemossedimentacao(float velocidadeHemossedimentacao) {
		this.velocidadeHemossedimentacao = velocidadeHemossedimentacao;
	}

	/**
	 * @return the mobilidade
	 */
	public char getMobilidade() {
		return mobilidade;
	}

	/**
	 * @param mobilidade the mobilidade to set
	 */
	public void setMobilidade(char mobilidade) {
		this.mobilidade = mobilidade;
	}

	/**
	 * @return the desbridamentoCirurgico
	 */
	public boolean isDesbridamentoCirurgico() {
		return desbridamentoCirurgico;
	}

	/**
	 * @param desbridamentoCirurgico the desbridamentoCirurgico to set
	 */
	public void setDesbridamentoCirurgico(boolean desbridamentoCirurgico) {
		this.desbridamentoCirurgico = desbridamentoCirurgico;
	}

	/**
	 * @return the compressao
	 */
	public boolean isCompressao() {
		return compressao;
	}

	/**
	 * @param compressao the compressao to set
	 */
	public void setCompressao(boolean compressao) {
		this.compressao = compressao;
	}

	/**
	 * @return the repouso
	 */
	public boolean isRepouso() {
		return repouso;
	}

	/**
	 * @param repouso the repouso to set
	 */
	public void setRepouso(boolean repouso) {
		this.repouso = repouso;
	}

	/**
	 * @return the coopera
	 */
	public boolean isCoopera() {
		return coopera;
	}

	/**
	 * @param coopera the coopera to set
	 */
	public void setCoopera(boolean coopera) {
		this.coopera = coopera;
	}

	/**
	 * @return the diabetes
	 */
	public boolean isDiabetes() {
		return diabetes;
	}

	/**
	 * @param diabetes the diabetes to set
	 */
	public void setDiabetes(boolean diabetes) {
		this.diabetes = diabetes;
	}

	/**
	 * @return the cardiopatiaIsquemica
	 */
	public boolean isCardiopatiaIsquemica() {
		return cardiopatiaIsquemica;
	}

	/**
	 * @param cardiopatiaIsquemica the cardiopatiaIsquemica to set
	 */
	public void setCardiopatiaIsquemica(boolean cardiopatiaIsquemica) {
		this.cardiopatiaIsquemica = cardiopatiaIsquemica;
	}

	/**
	 * @return the doencaRenal
	 */
	public boolean isDoencaRenal() {
		return doencaRenal;
	}

	/**
	 * @param doencaRenal the doencaRenal to set
	 */
	public void setDoencaRenal(boolean doencaRenal) {
		this.doencaRenal = doencaRenal;
	}

	/**
	 * @return the acidenteVascularCerebral
	 */
	public boolean isAcidenteVascularCerebral() {
		return acidenteVascularCerebral;
	}

	/**
	 * @param acidenteVascularCerebral the acidenteVascularCerebral to set
	 */
	public void setAcidenteVascularCerebral(boolean acidenteVascularCerebral) {
		this.acidenteVascularCerebral = acidenteVascularCerebral;
	}

	/**
	 * @return the artrite
	 */
	public boolean isArtrite() {
		return artrite;
	}

	/**
	 * @param artrite the artrite to set
	 */
	public void setArtrite(boolean artrite) {
		this.artrite = artrite;
	}

	/**
	 * @return the alteracoesVisao
	 */
	public boolean isAlteracoesVisao() {
		return alteracoesVisao;
	}

	/**
	 * @param alteracoesVisao the alteracoesVisao to set
	 */
	public void setAlteracoesVisao(boolean alteracoesVisao) {
		this.alteracoesVisao = alteracoesVisao;
	}

	/**
	 * @return the tromboseVascularProfunda
	 */
	public boolean isTromboseVascularProfunda() {
		return tromboseVascularProfunda;
	}

	/**
	 * @param tromboseVascularProfunda the tromboseVascularProfunda to set
	 */
	public void setTromboseVascularProfunda(boolean tromboseVascularProfunda) {
		this.tromboseVascularProfunda = tromboseVascularProfunda;
	}

	/**
	 * @return the fratura
	 */
	public boolean isFratura() {
		return fratura;
	}

	/**
	 * @param fratura the fratura to set
	 */
	public void setFratura(boolean fratura) {
		this.fratura = fratura;
	}

	/**
	 * @return the cirurgia
	 */
	public boolean isCirurgia() {
		return cirurgia;
	}

	/**
	 * @param cirurgia the cirurgia to set
	 */
	public void setCirurgia(boolean cirurgia) {
		this.cirurgia = cirurgia;
	}

	/**
	 * @return the doencaArterialPeriferica
	 */
	public boolean isDoencaArterialPeriferica() {
		return doencaArterialPeriferica;
	}

	/**
	 * @param doencaArterialPeriferica the doencaArterialPeriferica to set
	 */
	public void setDoencaArterialPeriferica(boolean doencaArterialPeriferica) {
		this.doencaArterialPeriferica = doencaArterialPeriferica;
	}

	/**
	 * @return the alcoolista
	 */
	public boolean isAlcoolista() {
		return alcoolista;
	}

	/**
	 * @param alcoolista the alcoolista to set
	 */
	public void setAlcoolista(boolean alcoolista) {
		this.alcoolista = alcoolista;
	}

	/**
	 * @return the dermatiteOcre
	 */
	public boolean isDermatiteOcre() {
		return dermatiteOcre;
	}

	/**
	 * @param dermatiteOcre the dermatiteOcre to set
	 */
	public void setDermatiteOcre(boolean dermatiteOcre) {
		this.dermatiteOcre = dermatiteOcre;
	}

	/**
	 * @return the lipodermatoesclerose
	 */
	public boolean isLipodermatoesclerose() {
		return lipodermatoesclerose;
	}

	/**
	 * @param lipodermatoesclerose the lipodermatoesclerose to set
	 */
	public void setLipodermatoesclerose(boolean lipodermatoesclerose) {
		this.lipodermatoesclerose = lipodermatoesclerose;
	}

	/**
	 * @return the asma
	 */
	public boolean isAsma() {
		return asma;
	}

	/**
	 * @param asma the asma to set
	 */
	public void setAsma(boolean asma) {
		this.asma = asma;
	}

	/**
	 * @return the anemiaFalciforme
	 */
	public boolean isAnemiaFalciforme() {
		return anemiaFalciforme;
	}

	/**
	 * @param anemiaFalciforme the anemiaFalciforme to set
	 */
	public void setAnemiaFalciforme(boolean anemiaFalciforme) {
		this.anemiaFalciforme = anemiaFalciforme;
	}

	/**
	 * @return the macosCigarroDiarios
	 */
	public int getMacosCigarroDiarios() {
		return macosCigarroDiarios;
	}

	/**
	 * @param macosCigarroDiarios the macosCigarroDiarios to set
	 */
	public void setMacosCigarroDiarios(int macosCigarroDiarios) {
		this.macosCigarroDiarios = macosCigarroDiarios;
	}

	/**
	 * @return the ceap
	 */
	public int getCeap() {
		return ceap;
	}

	/**
	 * @param ceap the ceap to set
	 */
	public void setCeap(int ceap) {
		this.ceap = ceap;
	}

	/**
	 * @return the outrasDrogas
	 */
	public String getOutrasDrogas() {
		return outrasDrogas;
	}

	/**
	 * @param outrasDrogas the outrasDrogas to set
	 */
	public void setOutrasDrogas(String outrasDrogas) {
		this.outrasDrogas = outrasDrogas;
	}

	/**
	 * @return the periodoInicialFerida
	 */
	public Date getPeriodoInicialFerida() {
		return periodoInicialFerida;
	}

	/**
	 * @param periodoInicialFerida the periodoInicialFerida to set
	 */
	public void setPeriodoInicialFerida(Date periodoInicialFerida) {
		this.periodoInicialFerida = periodoInicialFerida;
	}

	/**
	 * @return the periodoFinalFerida
	 */
	public Date getPeriodoFinalFerida() {
		return periodoFinalFerida;
	}

	/**
	 * @param periodoFinalFerida the periodoFinalFerida to set
	 */
	public void setPeriodoFinalFerida(Date periodoFinalFerida) {
		this.periodoFinalFerida = periodoFinalFerida;
	}
}
