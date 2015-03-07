package org.sapac.entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "consulta")
public class Consulta implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_consulta")
	private int id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_paciente")
	@NotNull
	private Paciente paciente;
	
	@OneToOne(mappedBy = "consulta", fetch = FetchType.LAZY)
	private VariaveisClinicas variaveisClinicas;
	
	@OneToMany(mappedBy = "consulta", fetch = FetchType.LAZY)
	private Collection<SituacaoUlceraConsulta> situacoesUlcera;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "membro_participa_consulta",
			joinColumns = {@JoinColumn(name = "id_consulta")},
			inverseJoinColumns = {@JoinColumn(name = "id_membro_equipe")})
	private Collection<MembroEquipe> membrosEquipe;
	
	@OneToOne(mappedBy = "consulta", fetch = FetchType.LAZY)
	private IntervencaoEnfermagem intervencaoEnfermagem;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "data_consulta")
	@NotNull
	private Date data;
	
	@NotNull
	private int situacao;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_consulta_marcada")
	private Consulta consultaMarcada;
	
	public final static int CONSULTA_MARCADA = 0;
	public final static int CONSULTA_REALIZADA = 1;
	public final static int CONSULTA_REMARCADA = 2;
	public final static int CONSULTA_CANCELADA = 3;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public VariaveisClinicas getVariaveisClinicas() {
		return variaveisClinicas;
	}

	public void setVariaveisClinicas(VariaveisClinicas variaveisClinicas) {
		this.variaveisClinicas = variaveisClinicas;
	}

	public Collection<SituacaoUlceraConsulta> getSituacoesUlcera() {
		return situacoesUlcera;
	}

	public void setSituacoesUlcera(Collection<SituacaoUlceraConsulta> situacoesUlcera) {
		this.situacoesUlcera = situacoesUlcera;
	}

	public Collection<MembroEquipe> getMembrosEquipe() {
		return membrosEquipe;
	}

	public void setMembrosEquipe(Collection<MembroEquipe> membrosEquipe) {
		this.membrosEquipe = membrosEquipe;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public int getSituacao() {
		return situacao;
	}

	public void setSituacao(int situacao) {
		this.situacao = situacao;
	}

	public Paciente getPaciente() {
		return paciente;
	}

	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}

	public IntervencaoEnfermagem getIntervencaoEnfermagem() {
		return intervencaoEnfermagem;
	}

	public void setIntervencaoEnfermagem(IntervencaoEnfermagem intervencaoEnfermagem) {
		this.intervencaoEnfermagem = intervencaoEnfermagem;
	}
	
	public Consulta getConsultaMarcada() {
		if (consultaMarcada != null) {
			Consulta consulta = consultaMarcada.getConsultaMarcada();
			if (consulta != null) {
				return consulta;
			} else {
				return consultaMarcada;
			}
		}
		return consultaMarcada;
	}

	public void setConsultaMarcada(Consulta consultaMarcada) {
		this.consultaMarcada = consultaMarcada;
	}
	
	public boolean isMarcada() {
		return this.getSituacao() == Consulta.CONSULTA_REMARCADA;
	}
	
	public boolean isRemarcada() {
		return this.getSituacao() == Consulta.CONSULTA_REMARCADA;
	}
	
	public boolean isCancelada() {
		return this.getSituacao() == Consulta.CONSULTA_CANCELADA;
	}
	
	public boolean isRealizada() {
		return this.getSituacao() == Consulta.CONSULTA_REALIZADA;
	}
	
	public float getTotalAreasUlceras() {
		float areaTotal = 0;
		for (SituacaoUlceraConsulta situacaoUlceraConsulta : situacoesUlcera) {
			areaTotal += situacaoUlceraConsulta.getArea();
		}
		return areaTotal;
	}
	
	@Override
	public boolean equals(Object object) {
		if (this != object) {
			if (!(object instanceof Consulta)) {
				return false;
			}
			
			Consulta consulta = (Consulta) object;
			if ((this.getId() != 0) && (consulta.getId() != 0)) {
				if (this.getId() != consulta.getId()) {
					return false;
				}
			} else {
				if (!this.getPaciente().equals(consulta.getPaciente())) {
					return false;
				}
				if (!this.getData().equals(consulta.getData())) {
					return false;
				}
				if (this.getSituacao() != consulta.getSituacao()) {
					return false;
				}
			}
		}
		return true;
	}
	
	@Override
	public Consulta clone() {
		Consulta consulta = new Consulta();
		consulta.setConsultaMarcada(getConsultaMarcada());
		consulta.setData(getData());
		consulta.setId(getId());
		consulta.setIntervencaoEnfermagem(getIntervencaoEnfermagem());
		consulta.setMembrosEquipe(getMembrosEquipe());
		consulta.setPaciente(getPaciente());
		consulta.setSituacao(getSituacao());
		consulta.setSituacoesUlcera(getSituacoesUlcera());
		consulta.setVariaveisClinicas(getVariaveisClinicas());
		
		return consulta;
	}
}