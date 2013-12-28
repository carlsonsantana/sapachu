/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sapac.entities;

import java.io.Serializable;

/**
 *
 * @author carlson
 */
public class DiagnosticoEnfermagem implements Serializable {
	private int id;
	private Paciente paciente;
	private String riscoGlicemia;
	private String riscoNutricaoDesequilibrada;
	private String tipoRiscoNutricao;
	private String sonoPrejudicado;
	private String mobilidadeFisicaPrejudicada;
	private String perfusaoTissularPerifericaIneficaz;
	private String interacaoSocialPrejudicada;
	private String faltaAdesao;
	private String riscoInfeccao;
	private String integridadePelePrejudicada;
	private String tipoDor;
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
