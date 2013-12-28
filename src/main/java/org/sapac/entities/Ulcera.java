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
public class Ulcera implements Serializable {
	private int id;
	private Ulcera ulceraReferencia;
	private int situacao;
	private String pontos;

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
	 * @return the ulceraReferencia
	 */
	public Ulcera getUlceraReferencia() {
		return ulceraReferencia;
	}

	/**
	 * @param ulceraReferencia the ulceraReferencia to set
	 */
	public void setUlceraReferencia(Ulcera ulceraReferencia) {
		this.ulceraReferencia = ulceraReferencia;
	}

	/**
	 * @return the situacao
	 */
	public int getSituacao() {
		return situacao;
	}

	/**
	 * @param situacao the situacao to set
	 */
	public void setSituacao(int situacao) {
		this.situacao = situacao;
	}

	/**
	 * @return the pontos
	 */
	public String getPontos() {
		return pontos;
	}

	/**
	 * @param pontos the pontos to set
	 */
	public void setPontos(String pontos) {
		this.pontos = pontos;
	}
}
