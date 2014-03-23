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
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "medicamento_uso")
public class MedicamentoUso implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_medicamento_uso")
	private int id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_variaveis_clinicas")
	@NotNull
	private VariaveisClinicas variaveisClinicas;
	
	@NotNull
	private String nome;

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

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	@Override
	public boolean equals(Object object) {
		if (this != object) {
			if (!(object instanceof MedicamentoUso)) {
				return false;
			}
			
			MedicamentoUso medicamento = (MedicamentoUso) object;
			
			if ((this.getId() == 0) || (medicamento.getId() == 0)) {
				if (!this.getNome().equals(medicamento.getNome())) {
					return false;
				}
			} else if (this.getId() != medicamento.getId()) {
				return false;
			}
		}
		return true;
	}
}
