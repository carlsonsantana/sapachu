/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sapac.entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

/**
 *
 * @author carlson
 */
@Entity
@Table(name="paciente")
public class Paciente implements Serializable {
	private static long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_paciente")
	private int id;
	
	@OneToOne(mappedBy = "paciente")
	private DiagnosticoEnfermagem diagnosticoEnfermagem;
	
	@OneToMany(mappedBy = "paciente")
	private Collection<Ulcera> ulceras;
	
	@NotNull
	private String nome;
	@Transient
	private Date dataNascimento;
	@Transient
	private String sexo;
	@Transient
	private String endereco;
	@Transient
	private String cidade;
	@Transient
	private String estado;
	@Transient
	private String telefone;
	@Transient
	private String estadoCivil;
	@Transient
	private String grauInstrucao;
	@Transient
	private String profissao;
	@NotNull
	private String prontuario;
	@Column(name = "numero_cartao_sus")
	@NotNull
	private String numeroCartaoSus;

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

	/**
	 * @return the dataNascimento
	 */
	public Date getDataNascimento() {
		return dataNascimento;
	}

	/**
	 * @param dataNascimento the dataNascimento to set
	 */
	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	/**
	 * @return the sexo
	 */
	public String getSexo() {
		return sexo;
	}

	/**
	 * @param sexo the sexo to set
	 */
	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	/**
	 * @return the endereco
	 */
	public String getEndereco() {
		return endereco;
	}

	/**
	 * @param endereco the endereco to set
	 */
	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	/**
	 * @return the cidade
	 */
	public String getCidade() {
		return cidade;
	}

	/**
	 * @param cidade the cidade to set
	 */
	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	/**
	 * @return the estado
	 */
	public String getEstado() {
		return estado;
	}

	/**
	 * @param estado the estado to set
	 */
	public void setEstado(String estado) {
		this.estado = estado;
	}

	/**
	 * @return the telefone
	 */
	public String getTelefone() {
		return telefone;
	}

	/**
	 * @param telefone the telefone to set
	 */
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	/**
	 * @return the estadoCivil
	 */
	public String getEstadoCivil() {
		return estadoCivil;
	}

	/**
	 * @param estadoCivil the estadoCivil to set
	 */
	public void setEstadoCivil(String estadoCivil) {
		this.estadoCivil = estadoCivil;
	}

	/**
	 * @return the grauInstrucao
	 */
	public String getGrauInstrucao() {
		return grauInstrucao;
	}

	/**
	 * @param grauInstrucao the grauInstrucao to set
	 */
	public void setGrauInstrucao(String grauInstrucao) {
		this.grauInstrucao = grauInstrucao;
	}

	/**
	 * @return the profissao
	 */
	public String getProfissao() {
		return profissao;
	}

	/**
	 * @param profissao the profissao to set
	 */
	public void setProfissao(String profissao) {
		this.profissao = profissao;
	}

	/**
	 * @return the diagnosticoEnfermagem
	 */
	public DiagnosticoEnfermagem getDiagnosticoEnfermagem() {
		return diagnosticoEnfermagem;
	}

	/**
	 * @param diagnosticoEnfermagem the diagnosticoEnfermagem to set
	 */
	public void setDiagnosticoEnfermagem(DiagnosticoEnfermagem diagnosticoEnfermagem) {
		this.diagnosticoEnfermagem = diagnosticoEnfermagem;
	}

	/**
	 * @return the numeroCartaoSus
	 */
	public String getNumeroCartaoSus() {
		return numeroCartaoSus;
	}

	/**
	 * @param numeroCartaoSus the numeroCartaoSus to set
	 */
	public void setNumeroCartaoSus(String numeroCartaoSus) {
		this.numeroCartaoSus = numeroCartaoSus;
	}
	
	
	@Override
	public boolean equals(Object object) {
		if (object != this) {
			if (!(object instanceof Paciente)) {
				return false;
			}
			Paciente paciente = (Paciente) object;
			if ((paciente.getId() != 0) && (this.getId() != 0)) {
				if (paciente.getId() != this.getId()) {
					return false;
				}
			} else if (!paciente.getProntuario().equals(this.getProntuario())) {
				return false;
			}
		}
		return true;
	}

	/**
	 * @return the ulceras
	 */
	public Collection<Ulcera> getUlceras() {
		return ulceras;
	}

	/**
	 * @param ulceras the ulceras to set
	 */
	public void setUlceras(Collection<Ulcera> ulceras) {
		this.ulceras = ulceras;
	}
}