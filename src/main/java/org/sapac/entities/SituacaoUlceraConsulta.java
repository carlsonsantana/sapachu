package org.sapac.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "situacao_ulcera_consulta")
public class SituacaoUlceraConsulta implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_situacao_ulcera_consulta")
	private int id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_consulta")
	@NotNull
	private Consulta consulta;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_ulcera")
	@NotNull
	private Ulcera ulcera;
	
	@OneToOne(mappedBy = "situacaoUlceraConsulta", fetch = FetchType.LAZY)
	private FotoUlcera fotoUlcera;
	
	@NotNull
	private float area;
	@Column(name = "estado_ulcera")
	@NotNull
	private int estadoUlcera;
	@NotNull
	private int profundidade;
	@Column(name = "nivel_dor")
	@NotNull
	private int nivelDor;
	@NotNull
	private int edema;
	@NotNull
	private int secrecao;
	@Column(name = "ciclo_litico")
	@NotNull
	private int cicloLitico;
	@NotNull
	private boolean fibrina;
	@NotNull
	private boolean granula;
	
	private boolean reulcera;
	@NotNull
	private boolean prurido;
	@NotNull
	private boolean ecsema;
	@NotNull
	private boolean odor;
	@Column(name = "sinal_cicatrizacao")
	@NotNull
	private boolean sinalCicatrizacao;
	@NotNull
	private boolean circular;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Consulta getConsulta() {
		return consulta;
	}

	public void setConsulta(Consulta consulta) {
		this.consulta = consulta;
	}

	public Ulcera getUlcera() {
		return ulcera;
	}

	public void setUlcera(Ulcera ulcera) {
		this.ulcera = ulcera;
	}

	public FotoUlcera getFotoUlcera() {
		return fotoUlcera;
	}

	public void setFotoUlcera(FotoUlcera fotoUlcera) {
		this.fotoUlcera = fotoUlcera;
	}

	public float getArea() {
		return area;
	}

	public void setArea(float area) {
		this.area = area;
	}

	public int getEstadoUlcera() {
		return estadoUlcera;
	}

	public void setEstadoUlcera(int estadoUlcera) {
		this.estadoUlcera = estadoUlcera;
	}

	public int getProfundidade() {
		return profundidade;
	}

	public void setProfundidade(int profundidade) {
		this.profundidade = profundidade;
	}

	public int getNivelDor() {
		return nivelDor;
	}

	public void setNivelDor(int nivelDor) {
		this.nivelDor = nivelDor;
	}

	public int getEdema() {
		return edema;
	}

	public void setEdema(int edema) {
		this.edema = edema;
	}

	public int getSecrecao() {
		return secrecao;
	}

	public void setSecrecao(int secrecao) {
		this.secrecao = secrecao;
	}

	public int getCicloLitico() {
		return cicloLitico;
	}

	public void setCicloLitico(int cicloLitico) {
		this.cicloLitico = cicloLitico;
	}

	public boolean isFibrina() {
		return fibrina;
	}

	public void setFibrina(boolean fibrina) {
		this.fibrina = fibrina;
	}

	public boolean isGranula() {
		return granula;
	}

	public void setGranula(boolean granula) {
		this.granula = granula;
	}

	public boolean isReulcera() {
		return reulcera;
	}

	public void setReulcera(boolean reulcera) {
		this.reulcera = reulcera;
	}

	public boolean isPrurido() {
		return prurido;
	}

	public void setPrurido(boolean prurido) {
		this.prurido = prurido;
	}

	public boolean isEcsema() {
		return ecsema;
	}

	public void setEcsema(boolean ecsema) {
		this.ecsema = ecsema;
	}

	public boolean isOdor() {
		return odor;
	}

	public void setOdor(boolean odor) {
		this.odor = odor;
	}

	public boolean isSinalCicatrizacao() {
		return sinalCicatrizacao;
	}

	public void setSinalCicatrizacao(boolean sinalCicatrizacao) {
		this.sinalCicatrizacao = sinalCicatrizacao;
	}

	public boolean isCircular() {
		return circular;
	}

	public void setCircular(boolean circular) {
		this.circular = circular;
	}
	
	public boolean isLimpa() {
		return estadoUlcera == 1;
	}
	
	public boolean isLimpaContaminada() {
		return estadoUlcera == 2;
	}
	
	public boolean isContaminada() {
		return estadoUlcera == 3;
	}
	
	public boolean isInfectada() {
		return estadoUlcera == 4;
	}
	
	public boolean isNecrozada() {
		return estadoUlcera == 5;
	}
	
	public boolean isCicatrizada() {
		return estadoUlcera == 6;
	}
	
	@Override
	public boolean equals(Object object) {
		if (this != object) {
			if (!(object instanceof SituacaoUlceraConsulta)) {
				return false;
			}
			
			SituacaoUlceraConsulta situacaoUlceraConsulta = (SituacaoUlceraConsulta) object;
			if (this.getId() != situacaoUlceraConsulta.getId()) {
				return false;
			}
		}
		return true;
	}
}
