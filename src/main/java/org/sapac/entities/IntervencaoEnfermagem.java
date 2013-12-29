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
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.Type;

/**
 *
 * @author carlson
 */
@Entity
@Table(name = "intervencao_enfermagem")
public class IntervencaoEnfermagem implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_intervencao_enfermagem")
	private int id;
	
	@OneToOne
	@JoinColumn(name = "id_consulta")
	@NotNull
	private Consulta consulta;
	
	@ManyToOne
	@JoinColumn(name = "id_enfermeiro")
	@NotNull
	private Enfermeiro enfermeiro;
	
	@Type(type = "org.hibernate.type.TextType")
	@NotNull
	private String descricao;

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
	 * @return the enfermeiro
	 */
	public Enfermeiro getEnfermeiro() {
		return enfermeiro;
	}

	/**
	 * @param enfermeiro the enfermeiro to set
	 */
	public void setEnfermeiro(Enfermeiro enfermeiro) {
		this.enfermeiro = enfermeiro;
	}

	/**
	 * @return the descricao
	 */
	public String getDescricao() {
		return descricao;
	}

	/**
	 * @param descricao the descricao to set
	 */
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
}
