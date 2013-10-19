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
@Table(name="enfermeiro")
public class Enfermeiro implements Serializable, Funcionario {
	@Id
	private int id;
	
	private String nome;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public boolean isMedico() {
		return false;
	}
	
	public boolean isEnfermeiro() {
		return true;
	}
}
