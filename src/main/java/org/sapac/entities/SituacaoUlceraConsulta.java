/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sapac.entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author carlson
 */
@Entity
@Table(name = "situacao_ulcera_consulta")
public class SituacaoUlceraConsulta implements Serializable {
	@Id
	@GeneratedValue
	@Column(name = "id_situacao_ulcera_consulta")
	private int id;
	
	@ManyToOne
	@JoinColumn(name = "id_consulta")
	private Consulta consulta;
	
	@ManyToOne
	@JoinColumn(name = "id_ulcera")
	private Ulcera ulcera;
	
	@OneToOne(mappedBy = "situacaoUlceraConsulta")
	private FotoUlcera fotoUlcera;
	
	private float area;
	private int estadoUlcera;
	private int profundidade;
	private int nivelDor;
	private int edema;
	private int secrecao;
	private int cicloLitico;
	private boolean fibrina;
	private boolean granula;
	private boolean reulcera;
	private boolean prurido;
	private boolean ecsema;
	private boolean odor;
	private boolean sinalCicatrizacao;
	private boolean circular;
	private boolean lado;

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
	 * @return the consulta
	 */
	public Consulta getConsulta() {
		return consulta;
	}

	/**
	 * @param consulta the consulta to set
	 */
	public void setConsulta(Consulta consulta) {
		this.consulta = consulta;
	}

	/**
	 * @return the ulcera
	 */
	public Ulcera getUlcera() {
		return ulcera;
	}

	/**
	 * @param ulcera the ulcera to set
	 */
	public void setUlcera(Ulcera ulcera) {
		this.ulcera = ulcera;
	}

	/**
	 * @return the fotoUlcera
	 */
	public FotoUlcera getFotoUlcera() {
		return fotoUlcera;
	}

	/**
	 * @param fotoUlcera the fotoUlcera to set
	 */
	public void setFotoUlcera(FotoUlcera fotoUlcera) {
		this.fotoUlcera = fotoUlcera;
	}

	/**
	 * @return the area
	 */
	public float getArea() {
		return area;
	}

	/**
	 * @param area the area to set
	 */
	public void setArea(float area) {
		this.area = area;
	}

	/**
	 * @return the estadoUlcera
	 */
	public int getEstadoUlcera() {
		return estadoUlcera;
	}

	/**
	 * @param estadoUlcera the estadoUlcera to set
	 */
	public void setEstadoUlcera(int estadoUlcera) {
		this.estadoUlcera = estadoUlcera;
	}

	/**
	 * @return the profundidade
	 */
	public int getProfundidade() {
		return profundidade;
	}

	/**
	 * @param profundidade the profundidade to set
	 */
	public void setProfundidade(int profundidade) {
		this.profundidade = profundidade;
	}

	/**
	 * @return the nivelDor
	 */
	public int getNivelDor() {
		return nivelDor;
	}

	/**
	 * @param nivelDor the nivelDor to set
	 */
	public void setNivelDor(int nivelDor) {
		this.nivelDor = nivelDor;
	}

	/**
	 * @return the edema
	 */
	public int getEdema() {
		return edema;
	}

	/**
	 * @param edema the edema to set
	 */
	public void setEdema(int edema) {
		this.edema = edema;
	}

	/**
	 * @return the secrecao
	 */
	public int getSecrecao() {
		return secrecao;
	}

	/**
	 * @param secrecao the secrecao to set
	 */
	public void setSecrecao(int secrecao) {
		this.secrecao = secrecao;
	}

	/**
	 * @return the cicloLitico
	 */
	public int getCicloLitico() {
		return cicloLitico;
	}

	/**
	 * @param cicloLitico the cicloLitico to set
	 */
	public void setCicloLitico(int cicloLitico) {
		this.cicloLitico = cicloLitico;
	}

	/**
	 * @return the fibrina
	 */
	public boolean isFibrina() {
		return fibrina;
	}

	/**
	 * @param fibrina the fibrina to set
	 */
	public void setFibrina(boolean fibrina) {
		this.fibrina = fibrina;
	}

	/**
	 * @return the granula
	 */
	public boolean isGranula() {
		return granula;
	}

	/**
	 * @param granula the granula to set
	 */
	public void setGranula(boolean granula) {
		this.granula = granula;
	}

	/**
	 * @return the reulcera
	 */
	public boolean isReulcera() {
		return reulcera;
	}

	/**
	 * @param reulcera the reulcera to set
	 */
	public void setReulcera(boolean reulcera) {
		this.reulcera = reulcera;
	}

	/**
	 * @return the prurido
	 */
	public boolean isPrurido() {
		return prurido;
	}

	/**
	 * @param prurido the prurido to set
	 */
	public void setPrurido(boolean prurido) {
		this.prurido = prurido;
	}

	/**
	 * @return the ecsema
	 */
	public boolean isEcsema() {
		return ecsema;
	}

	/**
	 * @param ecsema the ecsema to set
	 */
	public void setEcsema(boolean ecsema) {
		this.ecsema = ecsema;
	}

	/**
	 * @return the odor
	 */
	public boolean isOdor() {
		return odor;
	}

	/**
	 * @param odor the odor to set
	 */
	public void setOdor(boolean odor) {
		this.odor = odor;
	}

	/**
	 * @return the sinalCicatrizacao
	 */
	public boolean isSinalCicatrizacao() {
		return sinalCicatrizacao;
	}

	/**
	 * @param sinalCicatrizacao the sinalCicatrizacao to set
	 */
	public void setSinalCicatrizacao(boolean sinalCicatrizacao) {
		this.sinalCicatrizacao = sinalCicatrizacao;
	}

	/**
	 * @return the circular
	 */
	public boolean isCircular() {
		return circular;
	}

	/**
	 * @param circular the circular to set
	 */
	public void setCircular(boolean circular) {
		this.circular = circular;
	}

	/**
	 * @return the lado
	 */
	public boolean isLado() {
		return lado;
	}

	/**
	 * @param lado the lado to set
	 */
	public void setLado(boolean lado) {
		this.lado = lado;
	}
}
