/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sapac.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author carlson
 */
@Entity
@Table(name = "medico")
public class Medico implements Serializable, MembroEquipe {
	@Id
	@GeneratedValue
	@Column(name = "id_medico")
	private int id;
	
	@OneToOne
	@JoinColumn(name = "id_usuario")
	private Usuario usuario;
	
	private String nome;
	private String email;
	private String rg;
	private String cpf;
	private short vinculo;
	private long matricula;

	@Override
	public int getId() {
		return id;
	}

	@Override
	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String getNome() {
		return nome;
	}
	
	@Override
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	@Override
	public boolean isMedico() {
		return true;
	}
	
	@Override
	public boolean isEnfermeiro() {
		return false;
	}

	/**
	 * @return the usuario
	 */
	@Override
	public Usuario getUsuario() {
		return usuario;
	}

	/**
	 * @param usuario the usuario to set
	 */
	@Override
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	/**
	 * @return the email
	 */
	@Override
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	@Override
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the rg
	 */
	@Override
	public String getRg() {
		return rg;
	}

	/**
	 * @param rg the rg to set
	 */
	@Override
	public void setRg(String rg) {
		this.rg = rg;
	}

	/**
	 * @return the cpf
	 */
	@Override
	public String getCpf() {
		return cpf;
	}

	/**
	 * @param cpf the cpf to set
	 */
	@Override
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	/**
	 * @return the vinculo
	 */
	@Override
	public short getVinculo() {
		return vinculo;
	}

	/**
	 * @param vinculo the vinculo to set
	 */
	@Override
	public void setVinculo(short vinculo) {
		this.vinculo = vinculo;
	}

	/**
	 * @return the matricula
	 */
	@Override
	public long getMatricula() {
		return matricula;
	}

	/**
	 * @param matricula the matricula to set
	 */
	@Override
	public void setMatricula(long matricula) {
		this.matricula = matricula;
	}
}
