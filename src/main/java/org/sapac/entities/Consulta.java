/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sapac.entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Id;

/**
 *
 * @author carlson
 */
public class Consulta implements Serializable {
	@Id
	private int id;
	private Paciente paciente;
	private VariaveisClinicas variaveisClinicas;
	private Collection<SituacaoUlceraConsulta> situacoesUlcera;
	private Collection<MembroEquipe> membrosEquipe;
	private Date data;
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
}
