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
public class MedicamentoUso implements Serializable {
	private int id;
	private VariaveisClinicas variaveisClinicas;
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
