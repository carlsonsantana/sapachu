package org.sapac.entities;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "ulcera")
public class Ulcera implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_ulcera")
	private int id;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "ulcera_transforma_ulcera",
			joinColumns = {@JoinColumn(name = "ulcera_original")},
			inverseJoinColumns = {@JoinColumn(name = "ulcera_resultado")})
	private Collection<Ulcera> ulcerasResultado;
	
	@OneToMany(mappedBy = "ulcera", fetch = FetchType.LAZY)
	private Collection<SituacaoUlceraConsulta> situacaoUlceraConsultas;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_paciente")
	@NotNull
	private Paciente paciente;
	
	@NotNull
	private int situacao;
	
	private String pontos;
	
	public static final int ULCERA_LIMPA = 1;
	public static final int ULCERA_LIMPA_CONTAMINADA = 2;
	public static final int ULCERA_CONTAINADA = 3;
	public static final int ULCERA_INFECTADA = 4;
	public static final int ULCERA_NECROSADA = 5;
	public static final int ULCERA_CICATRIZADA = 6;
	public static final int ULCERA_SEPARADA = 7;
	public static final int ULCERA_JUNTADA = 8;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Collection<Ulcera> getUlcerasResultado() {
		return ulcerasResultado;
	}

	public void setUlcerasResultado(Collection<Ulcera> ulcerasResultado) {
		this.ulcerasResultado = ulcerasResultado;
	}

	public int getSituacao() {
		return situacao;
	}

	public void setSituacao(int situacao) {
		this.situacao = situacao;
	}

	public String getPontos() {
		return pontos;
	}

	public void setPontos(String pontos) {
		this.pontos = pontos;
	}

	public Collection<SituacaoUlceraConsulta> getSituacaoUlceraConsultas() {
		return situacaoUlceraConsultas;
	}

	public void setSituacaoUlceraConsultas(Collection<SituacaoUlceraConsulta> situacaoUlceraConsultas) {
		this.situacaoUlceraConsultas = situacaoUlceraConsultas;
	}

	public Paciente getPaciente() {
		return paciente;
	}

	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}
	
	@Override
	public boolean equals(Object object) {
		if (this != object) {
			if (!(object instanceof Ulcera)) {
				return false;
			}
			
			Ulcera ulcera = (Ulcera) object;
			if (this.getId() != ulcera.getId()) {
				return false;
			}
		}
		return true;
	}
}
