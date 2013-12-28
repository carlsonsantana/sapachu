/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sapac.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author carlson
 */
@Entity
@Table(name = "ulcera")
public class Ulcera implements Serializable {
	
	@Id
	@GeneratedValue
	@Column(name = "id_ulcera")
	private int id;
	
	@OneToOne
	@JoinColumn(name = "id_ulcera_referencia")
	private Ulcera ulceraReferencia;
	
	@OneToMany(mappedBy = "ulcera")
	private List<SituacaoUlceraConsulta> situacaoUlceraConsultas;
	
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
