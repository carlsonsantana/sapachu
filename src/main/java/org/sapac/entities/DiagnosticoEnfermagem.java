package org.sapac.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "diagnostico_enfermagem")
public class DiagnosticoEnfermagem implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_diagnostico_enfermagem")
	private int id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_paciente")
	@NotNull
	private Paciente paciente;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_enfermeiro")
	private Enfermeiro enfermeiro;
	
	@NotNull
	private boolean ativo;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_diagnostico_enfermagem")
	private Date data;
	
	@Column(name = "risco_glicemia_instavel")
	@Type(type = "org.hibernate.type.TextType")
	private String riscoGlicemiaInstavel;
	
	@Column(name = "risco_nutricao_desequilibrada")
	@Type(type = "org.hibernate.type.TextType")
	private String riscoNutricaoDesequilibrada;
	
	@Column(name = "risco_nutricao_acima_necessidades_corporais")
	private Boolean riscoNutricaoAcimaNecessidadesCorporais;
	
	@Column(name = "sono_prejudicado")
	@Type(type = "org.hibernate.type.TextType")
	private String sonoPrejudicado;
	
	@Column(name = "mobilidade_fisica_prejudicada")
	@Type(type = "org.hibernate.type.TextType")
	private String mobilidadeFisicaPrejudicada;
	
	@Column(name = "perfusao_tissular_periferica_ineficaz")
	@Type(type = "org.hibernate.type.TextType")
	private String perfusaoTissularPerifericaIneficaz;
	
	@Column(name = "interacao_social_prejudicada")
	@Type(type = "org.hibernate.type.TextType")
	private String interacaoSocialPrejudicada;
	
	@Column(name = "falta_adesao")
	@Type(type = "org.hibernate.type.TextType")
	private String faltaAdesao;
	
	@Column(name = "risco_infeccao")
	@Type(type = "org.hibernate.type.TextType")
	private String riscoInfeccao;
	
	@Column(name = "integridade_pele_prejudicada")
	@Type(type = "org.hibernate.type.TextType")
	private String integridadePelePrejudicada;
	
	@Column(name = "dor")
	@Type(type = "org.hibernate.type.TextType")
	private String dor;
	
	@Column(name = "tipo_dor")
	private Integer tipoDor;
	
	@Column(name = "outros_diagnosticos")
	@Type(type = "org.hibernate.type.TextType")
	private String outrosDiagnosticos;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Paciente getPaciente() {
		return paciente;
	}

	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}

	public String getRiscoGlicemiaInstavel() {
		return riscoGlicemiaInstavel;
	}

	public void setRiscoGlicemiaInstavel(String riscoGlicemiaInstavel) {
		this.riscoGlicemiaInstavel = riscoGlicemiaInstavel;
	}

	public String getRiscoNutricaoDesequilibrada() {
		return riscoNutricaoDesequilibrada;
	}

	public void setRiscoNutricaoDesequilibrada(String riscoNutricaoDesequilibrada) {
		this.riscoNutricaoDesequilibrada = riscoNutricaoDesequilibrada;
	}

	public Boolean getRiscoNutricaoAcimaNecessidadesCorporais() {
		return riscoNutricaoAcimaNecessidadesCorporais;
	}

	public void setRiscoNutricaoAcimaNecessidadesCorporais(Boolean riscoNutricaoAcimaNecessidadesCorporais) {
		this.riscoNutricaoAcimaNecessidadesCorporais = riscoNutricaoAcimaNecessidadesCorporais;
	}

	public String getSonoPrejudicado() {
		return sonoPrejudicado;
	}

	public void setSonoPrejudicado(String sonoPrejudicado) {
		this.sonoPrejudicado = sonoPrejudicado;
	}

	public String getMobilidadeFisicaPrejudicada() {
		return mobilidadeFisicaPrejudicada;
	}

	public void setMobilidadeFisicaPrejudicada(String mobilidadeFisicaPrejudicada) {
		this.mobilidadeFisicaPrejudicada = mobilidadeFisicaPrejudicada;
	}

	public String getPerfusaoTissularPerifericaIneficaz() {
		return perfusaoTissularPerifericaIneficaz;
	}

	public void setPerfusaoTissularPerifericaIneficaz(String perfusaoTissularPerifericaIneficaz) {
		this.perfusaoTissularPerifericaIneficaz = perfusaoTissularPerifericaIneficaz;
	}

	public String getInteracaoSocialPrejudicada() {
		return interacaoSocialPrejudicada;
	}

	public void setInteracaoSocialPrejudicada(String interacaoSocialPrejudicada) {
		this.interacaoSocialPrejudicada = interacaoSocialPrejudicada;
	}

	public String getFaltaAdesao() {
		return faltaAdesao;
	}

	public void setFaltaAdesao(String faltaAdesao) {
		this.faltaAdesao = faltaAdesao;
	}

	public String getRiscoInfeccao() {
		return riscoInfeccao;
	}

	public void setRiscoInfeccao(String riscoInfeccao) {
		this.riscoInfeccao = riscoInfeccao;
	}

	public String getIntegridadePelePrejudicada() {
		return integridadePelePrejudicada;
	}

	public void setIntegridadePelePrejudicada(String integridadePelePrejudicada) {
		this.integridadePelePrejudicada = integridadePelePrejudicada;
	}

	public String getDor() {
		return dor;
	}

	public void setDor(String dor) {
		this.dor = dor;
	}

	public String getOutrosDiagnosticos() {
		return outrosDiagnosticos;
	}

	public void setOutrosDiagnosticos(String outrosDiagnosticos) {
		this.outrosDiagnosticos = outrosDiagnosticos;
	}

	public Integer getTipoDor() {
		return tipoDor;
	}
	
	public void setTipoDor(Integer tipoDor) {
		this.tipoDor = tipoDor;
	}

	public Enfermeiro getEnfermeiro() {
		return enfermeiro;
	}

	public void setEnfermeiro(Enfermeiro enfermeiro) {
		this.enfermeiro = enfermeiro;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}
	
	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}
	
	@Override
	public DiagnosticoEnfermagem clone() {
		DiagnosticoEnfermagem diagnosticoEnfermagem = new DiagnosticoEnfermagem();
		diagnosticoEnfermagem.setId(getId());
		diagnosticoEnfermagem.setPaciente(getPaciente());
		diagnosticoEnfermagem.setEnfermeiro(getEnfermeiro());
		diagnosticoEnfermagem.setAtivo(isAtivo());
		diagnosticoEnfermagem.setData(getData());
		diagnosticoEnfermagem.setRiscoGlicemiaInstavel(getRiscoGlicemiaInstavel());
		diagnosticoEnfermagem.setRiscoNutricaoDesequilibrada(getRiscoNutricaoDesequilibrada());
		diagnosticoEnfermagem.setRiscoNutricaoAcimaNecessidadesCorporais(getRiscoNutricaoAcimaNecessidadesCorporais());
		diagnosticoEnfermagem.setSonoPrejudicado(getSonoPrejudicado());
		diagnosticoEnfermagem.setMobilidadeFisicaPrejudicada(getMobilidadeFisicaPrejudicada());
		diagnosticoEnfermagem.setPerfusaoTissularPerifericaIneficaz(getPerfusaoTissularPerifericaIneficaz());
		diagnosticoEnfermagem.setInteracaoSocialPrejudicada(getInteracaoSocialPrejudicada());
		diagnosticoEnfermagem.setFaltaAdesao(getFaltaAdesao());
		diagnosticoEnfermagem.setRiscoInfeccao(getRiscoInfeccao());
		diagnosticoEnfermagem.setIntegridadePelePrejudicada(getIntegridadePelePrejudicada());
		diagnosticoEnfermagem.setDor(getDor());
		diagnosticoEnfermagem.setTipoDor(getTipoDor());
		diagnosticoEnfermagem.setOutrosDiagnosticos(getOutrosDiagnosticos());
		
		return diagnosticoEnfermagem;
	}
	
	@Override
	public boolean equals(Object object) {
		if (this != object) {
			if (!(object instanceof DiagnosticoEnfermagem)) {
				return false;
			}
			
			DiagnosticoEnfermagem diagnosticoEnfermagem = (DiagnosticoEnfermagem) object;
			if (this.getId() != diagnosticoEnfermagem.getId()) {
				return false;
			}
		}
		return true;
	}
}