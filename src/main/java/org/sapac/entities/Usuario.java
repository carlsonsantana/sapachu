package org.sapac.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "usuario")
public class Usuario implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_usuario")
	private int id;

	@Column(name = "nome_usuario", unique = true)
	@NotNull
	private String nomeUsuario;

	@NotNull
	private String senha;

	@NotNull
	private boolean coordenador;

	@NotNull
	private boolean ativo;

	@OneToOne(mappedBy = "usuario", fetch = FetchType.LAZY)
	private MembroEquipe membroEquipe;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNomeUsuario() {
		return nomeUsuario;
	}

	public void setNomeUsuario(String nomeUsuario) {
		this.nomeUsuario = nomeUsuario;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public boolean isMedico() {
		return membroEquipe.isMedico();
	}

	public boolean isEnfermeiro() {
		return membroEquipe.isEnfermeiro();
	}

	public boolean isCoordenador() {
		return coordenador;
	}

	public void setCoordenador(boolean coordenador) {
		this.coordenador = coordenador;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	public MembroEquipe getMembroEquipe() {
		return membroEquipe;
	}

	public void setMembroEquipe(MembroEquipe membroEquipe) {
		this.membroEquipe = membroEquipe;
	}

	@Override
	public boolean equals(Object object) {
		if (this != object) {
			if (!(object instanceof Usuario)) {
				return false;
			}

			Usuario usuario = (Usuario) object;
			if ((this.getId() != 0) && (usuario.getId() != 0)) {
				if (this.getId() != usuario.getId()) {
					return false;
				}
			} else {
				if (!this.getNomeUsuario().equals(usuario.getNomeUsuario())) {
					return false;
				}
				if (!this.getSenha().equals(usuario.getSenha())) {
					return false;
				}
			}
		}
		return true;
	}
}
