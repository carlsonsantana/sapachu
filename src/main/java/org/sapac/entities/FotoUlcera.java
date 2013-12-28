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
public class FotoUlcera implements Serializable {
	private int id;
	private SituacaoUlceraConsulta situacaoUlceraConsulta;
	private String enderecoImagem;
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
	 * @return the situacaoUlceraConsulta
	 */
	public SituacaoUlceraConsulta getSituacaoUlceraConsulta() {
		return situacaoUlceraConsulta;
	}

	/**
	 * @param situacaoUlceraConsulta the situacaoUlceraConsulta to set
	 */
	public void setSituacaoUlceraConsulta(SituacaoUlceraConsulta situacaoUlceraConsulta) {
		this.situacaoUlceraConsulta = situacaoUlceraConsulta;
	}

	/**
	 * @return the enderecoImagem
	 */
	public String getEnderecoImagem() {
		return enderecoImagem;
	}

	/**
	 * @param enderecoImagem the enderecoImagem to set
	 */
	public void setEnderecoImagem(String enderecoImagem) {
		this.enderecoImagem = enderecoImagem;
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
