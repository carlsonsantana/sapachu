/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sapac.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author carlson
 */
@Entity
@Table(name = "foto_ulcera")
public class FotoUlcera implements Serializable {
	
	@Id
	@GeneratedValue
	@Column(name = "id_foto_ulcera")
	private int id;
	
	@OneToOne
	@JoinColumn(name = "id_situacao_ulcera_consulta")
	private SituacaoUlceraConsulta situacaoUlceraConsulta;
	
	@Column(name = "endereco_imagem")
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
