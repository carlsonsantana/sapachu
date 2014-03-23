package org.sapac.entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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

@Entity
@Table(name = "variaveis_clinicas")
public class VariaveisClinicas implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_variaveis_clinicas")
	private int id;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_consulta")
	@NotNull
	private Consulta consulta;
	
	@OneToMany(mappedBy = "variaveisClinicas", fetch = FetchType.LAZY)
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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Consulta getConsulta() {
		return consulta;
	}

	public void setConsulta(Consulta consulta) {
		this.consulta = consulta;
	}

	public Collection<MedicamentoUso> getMedicamentosUso() {
		return medicamentosUso;
	}

	public void setMedicamentosUso(Collection<MedicamentoUso> medicamentosUso) {
		this.medicamentosUso = medicamentosUso;
	}

	public float getPressaoArterial() {
		return pressaoArterial;
	}

	public void setPressaoArterial(float pressaoArterial) {
		this.pressaoArterial = pressaoArterial;
	}

	public float getIndiceMassaCorporal() {
		return indiceMassaCorporal;
	}

	public void setIndiceMassaCorporal(float indiceMassaCorporal) {
		this.indiceMassaCorporal = indiceMassaCorporal;
	}

	public float getNivelGlicemia() {
		return nivelGlicemia;
	}

	public void setNivelGlicemia(float nivelGlicemia) {
		this.nivelGlicemia = nivelGlicemia;
	}

	public float getNivelCreatina() {
		return nivelCreatina;
	}

	public void setNivelCreatina(float nivelCreatina) {
		this.nivelCreatina = nivelCreatina;
	}

	public float getColesterolHDL() {
		return colesterolHDL;
	}

	public void setColesterolHDL(float colesterolHDL) {
		this.colesterolHDL = colesterolHDL;
	}

	public float getVhs() {
		return vhs;
	}

	public void setVhs(float vhs) {
		this.vhs = vhs;
	}

	public float getCintura() {
		return cintura;
	}

	public void setCintura(float cintura) {
		this.cintura = cintura;
	}

	public float getQuadril() {
		return quadril;
	}

	public void setQuadril(float quadril) {
		this.quadril = quadril;
	}

	public float getIndiceTornozeloBraco() {
		return indiceTornozeloBraco;
	}

	public void setIndiceTornozeloBraco(float indiceTornozeloBraco) {
		this.indiceTornozeloBraco = indiceTornozeloBraco;
	}

	public float getVelocidadeHemossedimentacao() {
		return velocidadeHemossedimentacao;
	}

	public void setVelocidadeHemossedimentacao(float velocidadeHemossedimentacao) {
		this.velocidadeHemossedimentacao = velocidadeHemossedimentacao;
	}

	public char getMobilidade() {
		return mobilidade;
	}

	public void setMobilidade(char mobilidade) {
		this.mobilidade = mobilidade;
	}

	public boolean isDesbridamentoCirurgico() {
		return desbridamentoCirurgico;
	}

	public void setDesbridamentoCirurgico(boolean desbridamentoCirurgico) {
		this.desbridamentoCirurgico = desbridamentoCirurgico;
	}

	public boolean isCompressao() {
		return compressao;
	}

	public void setCompressao(boolean compressao) {
		this.compressao = compressao;
	}

	public boolean isRepouso() {
		return repouso;
	}

	public void setRepouso(boolean repouso) {
		this.repouso = repouso;
	}

	public boolean isCoopera() {
		return coopera;
	}

	public void setCoopera(boolean coopera) {
		this.coopera = coopera;
	}

	public boolean isDiabetes() {
		return diabetes;
	}

	public void setDiabetes(boolean diabetes) {
		this.diabetes = diabetes;
	}

	public boolean isCardiopatiaIsquemica() {
		return cardiopatiaIsquemica;
	}

	public void setCardiopatiaIsquemica(boolean cardiopatiaIsquemica) {
		this.cardiopatiaIsquemica = cardiopatiaIsquemica;
	}

	public boolean isDoencaRenal() {
		return doencaRenal;
	}

	public void setDoencaRenal(boolean doencaRenal) {
		this.doencaRenal = doencaRenal;
	}

	public boolean isAcidenteVascularCerebral() {
		return acidenteVascularCerebral;
	}

	public void setAcidenteVascularCerebral(boolean acidenteVascularCerebral) {
		this.acidenteVascularCerebral = acidenteVascularCerebral;
	}

	public boolean isArtrite() {
		return artrite;
	}

	public void setArtrite(boolean artrite) {
		this.artrite = artrite;
	}

	public boolean isAlteracoesVisao() {
		return alteracoesVisao;
	}

	public void setAlteracoesVisao(boolean alteracoesVisao) {
		this.alteracoesVisao = alteracoesVisao;
	}

	public boolean isTromboseVascularProfunda() {
		return tromboseVascularProfunda;
	}

	public void setTromboseVascularProfunda(boolean tromboseVascularProfunda) {
		this.tromboseVascularProfunda = tromboseVascularProfunda;
	}

	public boolean isFratura() {
		return fratura;
	}

	public void setFratura(boolean fratura) {
		this.fratura = fratura;
	}

	public boolean isCirurgia() {
		return cirurgia;
	}

	public void setCirurgia(boolean cirurgia) {
		this.cirurgia = cirurgia;
	}

	public boolean isDoencaArterialPeriferica() {
		return doencaArterialPeriferica;
	}

	public void setDoencaArterialPeriferica(boolean doencaArterialPeriferica) {
		this.doencaArterialPeriferica = doencaArterialPeriferica;
	}

	public boolean isAlcoolista() {
		return alcoolista;
	}

	public void setAlcoolista(boolean alcoolista) {
		this.alcoolista = alcoolista;
	}

	public boolean isDermatiteOcre() {
		return dermatiteOcre;
	}

	public void setDermatiteOcre(boolean dermatiteOcre) {
		this.dermatiteOcre = dermatiteOcre;
	}

	public boolean isLipodermatoesclerose() {
		return lipodermatoesclerose;
	}

	public void setLipodermatoesclerose(boolean lipodermatoesclerose) {
		this.lipodermatoesclerose = lipodermatoesclerose;
	}

	public boolean isAsma() {
		return asma;
	}

	public void setAsma(boolean asma) {
		this.asma = asma;
	}

	public boolean isAnemiaFalciforme() {
		return anemiaFalciforme;
	}

	public void setAnemiaFalciforme(boolean anemiaFalciforme) {
		this.anemiaFalciforme = anemiaFalciforme;
	}

	public int getMacosCigarroDiarios() {
		return macosCigarroDiarios;
	}

	public void setMacosCigarroDiarios(int macosCigarroDiarios) {
		this.macosCigarroDiarios = macosCigarroDiarios;
	}

	public int getCeap() {
		return ceap;
	}

	public void setCeap(int ceap) {
		this.ceap = ceap;
	}

	public String getOutrasDrogas() {
		return outrasDrogas;
	}

	public void setOutrasDrogas(String outrasDrogas) {
		this.outrasDrogas = outrasDrogas;
	}

	public Date getPeriodoInicialFerida() {
		return periodoInicialFerida;
	}

	public void setPeriodoInicialFerida(Date periodoInicialFerida) {
		this.periodoInicialFerida = periodoInicialFerida;
	}

	public Date getPeriodoFinalFerida() {
		return periodoFinalFerida;
	}

	public void setPeriodoFinalFerida(Date periodoFinalFerida) {
		this.periodoFinalFerida = periodoFinalFerida;
	}
}
