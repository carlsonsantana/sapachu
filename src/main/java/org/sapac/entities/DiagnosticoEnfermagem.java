/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sapac.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.Type;

/**
 *
 * @author carlson
 */
@Entity
@Table(name = "diagnostico_enfermagem")
public class DiagnosticoEnfermagem implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_diagnostico_enfermagem")
	private int id;
		
	@OneToOne
	@JoinColumn(name = "id_paciente")
	@NotNull
	private Paciente paciente;
	
	@Column(name = "risco_glicemia")
	@Type(type = "org.hibernate.type.TextType")
	private String riscoGlicemia;
	
	@Column(name = "risco_nutricao_desequilibrada")
	@Type(type = "org.hibernate.type.TextType")
	private String riscoNutricaoDesequilibrada;
	
	@Column(name = "tipo_risco_nutricao")
	@Type(type = "org.hibernate.type.TextType")
	private String tipoRiscoNutricao;
	
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
	private String tipoDor;
	
	@Column(name = "outros_diagnosticos")
	@Type(type = "org.hibernate.type.TextType")
	private String outrosDiagnosticos;

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
	 * @return the riscoGlicemia
	 */
	public String getRiscoGlicemia() {
		return riscoGlicemia;
	}

	/**
	 * @param riscoGlicemia the riscoGlicemia to set
	 */
	public void setRiscoGlicemia(String riscoGlicemia) {
		this.riscoGlicemia = riscoGlicemia;
	}

	/**
	 * @return the riscoNutricaoDesequilibrada
	 */
	public String getRiscoNutricaoDesequilibrada() {
		return riscoNutricaoDesequilibrada;
	}

	/**
	 * @param riscoNutricaoDesequilibrada the riscoNutricaoDesequilibrada to set
	 */
	public void setRiscoNutricaoDesequilibrada(String riscoNutricaoDesequilibrada) {
		this.riscoNutricaoDesequilibrada = riscoNutricaoDesequilibrada;
	}

	/**
	 * @return the tipoRiscoNutricao
	 */
	public String getTipoRiscoNutricao() {
		return tipoRiscoNutricao;
	}

	/**
	 * @param tipoRiscoNutricao the tipoRiscoNutricao to set
	 */
	public void setTipoRiscoNutricao(String tipoRiscoNutricao) {
		this.tipoRiscoNutricao = tipoRiscoNutricao;
	}

	/**
	 * @return the sonoPrejudicado
	 */
	public String getSonoPrejudicado() {
		return sonoPrejudicado;
	}

	/**
	 * @param sonoPrejudicado the sonoPrejudicado to set
	 */
	public void setSonoPrejudicado(String sonoPrejudicado) {
		this.sonoPrejudicado = sonoPrejudicado;
	}

	/**
	 * @return the mobilidadeFisicaPrejudicada
	 */
	public String getMobilidadeFisicaPrejudicada() {
		return mobilidadeFisicaPrejudicada;
	}

	/**
	 * @param mobilidadeFisicaPrejudicada the mobilidadeFisicaPrejudicada to set
	 */
	public void setMobilidadeFisicaPrejudicada(String mobilidadeFisicaPrejudicada) {
		this.mobilidadeFisicaPrejudicada = mobilidadeFisicaPrejudicada;
	}

	/**
	 * @return the perfusaoTissularPerifericaIneficaz
	 */
	public String getPerfusaoTissularPerifericaIneficaz() {
		return perfusaoTissularPerifericaIneficaz;
	}

	/**
	 * @param perfusaoTissularPerifericaIneficaz the perfusaoTissularPerifericaIneficaz to set
	 */
	public void setPerfusaoTissularPerifericaIneficaz(String perfusaoTissularPerifericaIneficaz) {
		this.perfusaoTissularPerifericaIneficaz = perfusaoTissularPerifericaIneficaz;
	}

	/**
	 * @return the interacaoSocialPrejudicada
	 */
	public String getInteracaoSocialPrejudicada() {
		return interacaoSocialPrejudicada;
	}

	/**
	 * @param interacaoSocialPrejudicada the interacaoSocialPrejudicada to set
	 */
	public void setInteracaoSocialPrejudicada(String interacaoSocialPrejudicada) {
		this.interacaoSocialPrejudicada = interacaoSocialPrejudicada;
	}

	/**
	 * @return the faltaAdesao
	 */
	public String getFaltaAdesao() {
		return faltaAdesao;
	}

	/**
	 * @param faltaAdesao the faltaAdesao to set
	 */
	public void setFaltaAdesao(String faltaAdesao) {
		this.faltaAdesao = faltaAdesao;
	}

	/**
	 * @return the riscoInfeccao
	 */
	public String getRiscoInfeccao() {
		return riscoInfeccao;
	}

	/**
	 * @param riscoInfeccao the riscoInfeccao to set
	 */
	public void setRiscoInfeccao(String riscoInfeccao) {
		this.riscoInfeccao = riscoInfeccao;
	}

	/**
	 * @return the integridadePelePrejudicada
	 */
	public String getIntegridadePelePrejudicada() {
		return integridadePelePrejudicada;
	}

	/**
	 * @param integridadePelePrejudicada the integridadePelePrejudicada to set
	 */
	public void setIntegridadePelePrejudicada(String integridadePelePrejudicada) {
		this.integridadePelePrejudicada = integridadePelePrejudicada;
	}

	/**
	 * @return the tipoDor
	 */
	public String getTipoDor() {
		return tipoDor;
	}

	/**
	 * @param tipoDor the tipoDor to set
	 */
	public void setTipoDor(String tipoDor) {
		this.tipoDor = tipoDor;
	}

	/**
	 * @return the outrosDiagnosticos
	 */
	public String getOutrosDiagnosticos() {
		return outrosDiagnosticos;
	}

	/**
	 * @param outrosDiagnosticos the outrosDiagnosticos to set
	 */
	public void setOutrosDiagnosticos(String outrosDiagnosticos) {
		this.outrosDiagnosticos = outrosDiagnosticos;
	}
}
