package org.sapac.entities;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "membro_equipe")
@Inheritance(strategy = InheritanceType.JOINED)
public class MembroEquipe implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_membro_equipe")
	private int id;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_usuario", unique = true)
	@NotNull
	private Usuario usuario;
	
	@ManyToMany(mappedBy = "membrosEquipe", fetch = FetchType.LAZY)
	private Collection<Consulta> consultas;
	
	@NotNull
	private String nome;
	@NotNull
	@Column(unique = true)
	private String email;
	@NotNull
	private String rg;
	@NotNull
	@Column(unique = true)
	private String cpf;
	@NotNull
	private short vinculo;
	@NotNull
	private long matricula;


	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public boolean isMedico() {
		return (this instanceof Medico);
	}
	
	public boolean isEnfermeiro() {
		return (this instanceof Enfermeiro);
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRg() {
		return rg;
	}

	public void setRg(String rg) {
		this.rg = rg;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public short getVinculo() {
		return vinculo;
	}

	public void setVinculo(short vinculo) {
		this.vinculo = vinculo;
	}

	public long getMatricula() {
		return matricula;
	}

	public void setMatricula(long matricula) {
		this.matricula = matricula;
	}

	public Collection<Consulta> getConsultas() {
		return consultas;
	}

	public void setConsultas(Collection<Consulta> consultas) {
		this.consultas = consultas;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	@Override
	public boolean equals(Object object) {
		if (this != object) {
			if (!(object instanceof MembroEquipe)) {
				return false;
			}
			
			MembroEquipe membroEquipe = (MembroEquipe) object;
			
			if (this.getId() == membroEquipe.getId()) {
				return true;
			}
			
			if (this.getCpf().equals(membroEquipe.getCpf())) {
				return true;
			}
		} else {
			return true;
		}
		return false;
	}
}
