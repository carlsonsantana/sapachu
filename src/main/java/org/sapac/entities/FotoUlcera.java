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
	
	@Column(name = "pontos_comparativos")
	private String pontosComparativos;
	
	@Column(name = "altura_real_comparativo")
	private Float alturaRealComparativo;
	
	@Column(name = "largura_real_comparativo")
	private Float larguraRealComparativo;

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
	
	public String getPontosComparativos() {
		return pontosComparativos;
	}

	public void setPontosComparativos(String pontosComparativos) {
		this.pontosComparativos = pontosComparativos;
	}
	
	public Float getAlturaRealComparativo() {
		return alturaRealComparativo;
	}

	public void setAlturaRealComparativo(Float alturaRealComparativo) {
		this.alturaRealComparativo = alturaRealComparativo;
	}
	
	public Float getLarguraRealComparativo() {
		return larguraRealComparativo;
	}

	public void setLarguraRealComparativo(Float larguraRealComparativo) {
		this.larguraRealComparativo = larguraRealComparativo;
	}
	
	@Override
	public boolean equals(Object object) {
		if (this != object) {
			if (!(object instanceof FotoUlcera)) {
				return false;
			}
			
			FotoUlcera fotoUlcera = (FotoUlcera) object;
			if ((this.getId() != 0) && (fotoUlcera.getId() != 0)) {
				if (this.getId() != fotoUlcera.getId()) {
					return false;
				}
			} else {
				if (!this.getEnderecoImagem().equals(fotoUlcera.getEnderecoImagem())) {
					return false;
				}
				if (!this.getPontos().equals(fotoUlcera.getPontos())) {
					return false;
				}
			}
		}
		return true;
	}
}