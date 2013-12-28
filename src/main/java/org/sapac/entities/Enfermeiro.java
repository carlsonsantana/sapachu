/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sapac.entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author carlson
 */
@Entity
@Table(name="enfermeiro")
public class Enfermeiro implements Serializable, MembroEquipe {
	@Id
	private int id;
	@OneToOne
	private Usuario usuario;
	private String nome;
	private String email;
	private String rg;
	private String cpf;
	private short vinculo;
	private long matricula;

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

	/**
	 * @return the usuario
	 */
	public Usuario getUsuario() {
		return usuario;
	}

	/**
	 * @param usuario the usuario to set
	 */
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the rg
	 */
	public String getRg() {
		return rg;
	}

	/**
	 * @param rg the rg to set
	 */
	public void setRg(String rg) {
		this.rg = rg;
	}

	/**
	 * @return the cpf
	 */
	public String getCpf() {
		return cpf;
	}

	/**
	 * @param cpf the cpf to set
	 */
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	/**
	 * @return the vinculo
	 */
	public short getVinculo() {
		return vinculo;
	}

	/**
	 * @param vinculo the vinculo to set
	 */
	public void setVinculo(short vinculo) {
		this.vinculo = vinculo;
	}

	/**
	 * @return the matricula
	 */
	public long getMatricula() {
		return matricula;
	}

	/**
	 * @param matricula the matricula to set
	 */
	public void setMatricula(long matricula) {
		this.matricula = matricula;
	}
}
