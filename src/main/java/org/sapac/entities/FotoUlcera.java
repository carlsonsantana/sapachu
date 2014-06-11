package org.sapac.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "foto_ulcera")
public class FotoUlcera implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_foto_ulcera")
	private int id;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_situacao_ulcera_consulta")
	@NotNull
	private SituacaoUlceraConsulta situacaoUlceraConsulta;
	
	@Column(name = "endereco_imagem")
	@NotNull
	private String enderecoImagem;

	private String pontos;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public SituacaoUlceraConsulta getSituacaoUlceraConsulta() {
		return situacaoUlceraConsulta;
	}

	public void setSituacaoUlceraConsulta(SituacaoUlceraConsulta situacaoUlceraConsulta) {
		this.situacaoUlceraConsulta = situacaoUlceraConsulta;
	}

	public String getEnderecoImagem() {
		return enderecoImagem;
	}

	public void setEnderecoImagem(String enderecoImagem) {
		this.enderecoImagem = enderecoImagem;
	}

	public String getPontos() {
		return pontos;
	}

	public void setPontos(String pontos) {
		this.pontos = pontos;
	}
	
	@Override
	public boolean equals(Object object) {
		if (this != object) {
			if (!(object instanceof FotoUlcera)) {
				return false;
			}
			
			FotoUlcera fotoUlcera = (FotoUlcera) object;
			if (this.getId() != fotoUlcera.getId()) {
				return false;
			}
		}
		return true;
	}
}