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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

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
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_usuario")
	private int id;

	//private MembroEquipe membroEquipe;
	
	/**
	 * O login do usuário.
	 */
	@Column(name = "nome_usuario")
	@NotNull
	private String nomeUsuario;

	/**
	 * A senha do usuário.
	 */
	@NotNull
	private String senha;
	
	@NotNull
	private boolean coordenador;
	
	@NotNull
	private boolean ativo;
	
	@OneToOne(mappedBy = "usuario")
	private MembroEquipe membroEquipe;

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
		return membroEquipe.isMedico();
	}
	
	public boolean isEnfermeiro() {
		return membroEquipe.isEnfermeiro();
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

	/**
	 * @return the membroEquipe
	 */
	public MembroEquipe getMembroEquipe() {
		return membroEquipe;
	}

	/**
	 * @param membroEquipe the membroEquipe to set
	 */
	public void setMembroEquipe(MembroEquipe membroEquipe) {
		this.membroEquipe = membroEquipe;
	}
}