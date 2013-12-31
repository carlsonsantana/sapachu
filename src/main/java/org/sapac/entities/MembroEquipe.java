/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sapac.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 *
 * @author carlson
 */
@Entity
@Table(name = "membro_equipe")
@Inheritance(strategy = InheritanceType.JOINED)
public class MembroEquipe implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_membro_equipe")
	private int id;
	
	@OneToOne
	@JoinColumn(name = "id_usuario")
	@NotNull
	private Usuario usuario;
	
	@ManyToMany(mappedBy = "membrosEquipe")
	private List<Consulta> consultas;
	
	@NotNull
	private String nome;
	@NotNull
	private String email;
	@NotNull
	private String rg;
	@NotNull
	private String cpf;
	@NotNull
	private short vinculo;
	@NotNull
	private long matricula;


	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public boolean isMedico() {
		return true;
	}
	
	public boolean isEnfermeiro() {
		return false;
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

	/**
	 * @return the consultas
	 */
	public List<Consulta> getConsultas() {
		return consultas;
	}

	/**
	 * @param consultas the consultas to set
	 */
	public void setConsultas(List<Consulta> consultas) {
		this.consultas = consultas;
	}

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
}
