/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sapac.entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 *
 * @author carlson
 */
@Entity
public class Usuario implements Serializable {

	/**
	 * O id do usuário no banco de dados.
	 */
	@Id
	private int id;

	/**
	 * O login do usuário.
	 */
	private String nomeUsuario;

	/**
	 * A senha do usuário.
	 */
	private String senha;
	
	private boolean administrador;

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
	 * @return the nomeUsuario
	 */
	public String getNomeUsuario() {
		return nomeUsuario;
	}

	/**
	 * @param nomeUsuario the nomeUsuario to set
	 */
	public void setNomeUsuario(String nomeUsuario) {
		this.nomeUsuario = nomeUsuario;
	}

	/**
	 * @return the senha
	 */
	public String getSenha() {
		return senha;
	}

	/**
	 * @param senha the senha to set
	 */
	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	public boolean isMedico() {
		return true;
	}
	
	public boolean isEnfermeiro() {
		return true;
	}

	/**
	 * @return the administrador
	 */
	public boolean isAdministrador() {
		return administrador;
	}

	/**
	 * @param administrador the administrador to set
	 */
	public void setAdministrador(boolean administrador) {
		this.administrador = administrador;
	}
}