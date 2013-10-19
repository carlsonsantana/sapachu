/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sapac.entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author carlson
 */
@Entity
@Table(name="Paciente")
public class Paciente implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	private int id;
	private String nome;
	private String prontuario;

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

	/**
	 * @return the prontuario
	 */
	public String getProntuario() {
		return prontuario;
	}

	/**
	 * @param prontuario the prontuario to set
	 */
	public void setProntuario(String prontuario) {
		this.prontuario = prontuario;
	}
}