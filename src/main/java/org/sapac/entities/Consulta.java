/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sapac.entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

/**
 *
 * @author carlson
 */
@Entity
@Table(name = "consulta")
public class Consulta implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_consulta")
	private int id;
	
	@ManyToOne
	@JoinColumn(name = "id_paciente")
	@NotNull
	private Paciente paciente;
	
	@OneToOne(mappedBy = "consulta")
	private VariaveisClinicas variaveisClinicas;
	
	@OneToMany(mappedBy = "consulta")
	private Collection<SituacaoUlceraConsulta> situacoesUlcera;
	
	@ManyToMany
	@JoinTable(joinColumns = {@JoinColumn(name = "id_consulta")},
			inverseJoinColumns = {@JoinColumn(name = "id_membro_equipe")})
	private Collection<MembroEquipe> membrosEquipe;
	
	@OneToOne(mappedBy = "consulta")
	private IntervencaoEnfermagem intervencaoEnfermagem;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "data_consulta")
	@NotNull
	private Date data;
	
	@NotNull
	private int situacao;
	
	public final static int CONSULTA_MARCADA = 0;
	public final static int CONSULTA_REALIZADA = 1;
	public final static int CONSULTA_REMARCADA = 2;
	public final static int CONSULTA_CANCELADA = 3;

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
	 * @return the situacoesUlcera
	 */
	public Collection<SituacaoUlceraConsulta> getSituacoesUlcera() {
		return situacoesUlcera;
	}

	/**
	 * @param situacoesUlcera the situacoesUlcera to set
	 */
	public void setSituacoesUlcera(Collection<SituacaoUlceraConsulta> situacoesUlcera) {
		this.situacoesUlcera = situacoesUlcera;
	}

	/**
	 * @return the membrosEquipe
	 */
	public Collection<MembroEquipe> getMembrosEquipe() {
		return membrosEquipe;
	}

	/**
	 * @param membrosEquipe the membrosEquipe to set
	 */
	public void setMembrosEquipe(Collection<MembroEquipe> membrosEquipe) {
		this.membrosEquipe = membrosEquipe;
	}

	/**
	 * @return the data
	 */
	public Date getData() {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(Date data) {
		this.data = data;
	}

	/**
	 * @return the situacao
	 */
	public int getSituacao() {
		return situacao;
	}

	/**
	 * @param situacao the situacao to set
	 */
	public void setSituacao(int situacao) {
		this.situacao = situacao;
	}

	/**
	 * @return the paciente
	 */
	public Paciente getPaciente() {
		return paciente;
	}

	/**
	 * @param paciente the paciente to set
	 */
	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}

	/**
	 * @return the intervencaoEnfermagem
	 */
	public IntervencaoEnfermagem getIntervencaoEnfermagem() {
		return intervencaoEnfermagem;
	}

	/**
	 * @param intervencaoEnfermagem the intervencaoEnfermagem to set
	 */
	public void setIntervencaoEnfermagem(IntervencaoEnfermagem intervencaoEnfermagem) {
		this.intervencaoEnfermagem = intervencaoEnfermagem;
	}
}