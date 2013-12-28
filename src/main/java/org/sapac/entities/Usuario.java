/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sapac.entities;

import java.beans.Transient;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author carlson
 */
@Entity
@Table(name = "usuario")
public class Usuario implements Serializable {

	/**
	 * O id do usuário no banco de dados.
	 */
	@Id
	@GeneratedValue
	@Column(name = "id_usuario")
	private int id;

	//private MembroEquipe membroEquipe;
	
	/**
	 * O login do usuário.
	 */
	@Column(name = "nome_usuario")
	private String nomeUsuario;

	/**
	 * A senha do usuário.
	 */
	private String senha;
	
	private boolean coordenador;
	
	private boolean ativo;

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
	 * @return the coordenador
	 */
	public boolean isCoordenador() {
		return coordenador;
	}

	/**
	 * @param coordenador the coordenador to set
	 */
	public void setCoordenador(boolean coordenador) {
		this.coordenador = coordenador;
	}

	/**
	 * @return the ativo
	 */
	public boolean isAtivo() {
		return ativo;
	}

	/**
	 * @param ativo the ativo to set
	 */
	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}
}