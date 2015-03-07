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
import org.hibernate.annotations.Type;

@Entity
@Table(name = "intervencao_enfermagem")
public class IntervencaoEnfermagem implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_intervencao_enfermagem")
	private int id;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_consulta")
	@NotNull
	private Consulta consulta;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_enfermeiro")
	private Enfermeiro enfermeiro;
	
	@Type(type = "org.hibernate.type.TextType")
	private String descricao;

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

	public Enfermeiro getEnfermeiro() {
		return enfermeiro;
	}

	public void setEnfermeiro(Enfermeiro enfermeiro) {
		this.enfermeiro = enfermeiro;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	@Override
	public boolean equals(Object object) {
		if (this != object) {
			if (!(object instanceof IntervencaoEnfermagem)) {
				return false;
			}
			
			IntervencaoEnfermagem intervencaoEnfermagem = (IntervencaoEnfermagem) object;
			if ((this.getId() != 0) && (intervencaoEnfermagem.getId() != 0)) {
				if (this.getId() != intervencaoEnfermagem.getId()) {
					return false;
				}
			} else {
				if (!this.getConsulta().equals(intervencaoEnfermagem.getConsulta())) {
					return false;
				}
				if (!this.getEnfermeiro().equals(intervencaoEnfermagem.getEnfermeiro())) {
					return false;
				}
			}
		}
		return true;
	}
}