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
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 *
 * @author carlson
 */
@Entity
@Table(name = "medicamento_uso")
public class MedicamentoUso implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_medicamento_uso")
	private int id;
	
	@ManyToOne
	@JoinColumn(name = "id_variaveis_clinicas")
	@NotNull
	private VariaveisClinicas variaveisClinicas;
	
	@NotNull
	private String nome;

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
	 * @return the variaveisClinicas
	 */
	public VariaveisClinicas getVariaveisClinicas() {
		return variaveisClinicas;
	}

	/**
	 * @param variaveisClinicas the variaveisClinicas to set
	 */
	public void setVariaveisClinicas(VariaveisClinicas variaveisClinicas) {
		this.variaveisClinicas = variaveisClinicas;
	}

	/**
	 * @return the nome
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * @param nome the nome to set
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}
}
