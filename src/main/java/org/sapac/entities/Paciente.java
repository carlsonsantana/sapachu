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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="paciente")
public class Paciente implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_paciente")
	private int id;
	
	@OneToMany(mappedBy = "paciente", fetch = FetchType.LAZY)
	private Collection<DiagnosticoEnfermagem> diagnosticosEnfermagem;
	
	private transient DiagnosticoEnfermagem diagnosticoEnfermagem;
	
	@OneToMany(mappedBy = "paciente", fetch = FetchType.LAZY)
	private Collection<Ulcera> ulceras;
	
	@OneToMany(mappedBy = "paciente", fetch = FetchType.LAZY)
	private Collection<Consulta> consultas;
	
	@NotNull
	private String nome;
	@Transient
	private Date dataNascimento;
	@Transient
	private String sexo;
	@Transient
	private String endereco;
	@Transient
	private String cidade;
	@Transient
	private String estado;
	@Transient
	private String telefone;
	@Transient
	private String estadoCivil;
	@Transient
	private String grauInstrucao;
	@Transient
	private String profissao;
	@NotNull
	private String prontuario;
	@Column(name = "numero_cartao_sus")
	@NotNull
	private String numeroCartaoSus;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getProntuario() {
		return prontuario;
	}

	public void setProntuario(String prontuario) {
		this.prontuario = prontuario;
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getEstadoCivil() {
		return estadoCivil;
	}

	public void setEstadoCivil(String estadoCivil) {
		this.estadoCivil = estadoCivil;
	}

	public String getGrauInstrucao() {
		return grauInstrucao;
	}

	public void setGrauInstrucao(String grauInstrucao) {
		this.grauInstrucao = grauInstrucao;
	}

	public String getProfissao() {
		return profissao;
	}

	public void setProfissao(String profissao) {
		this.profissao = profissao;
	}

	public Collection<DiagnosticoEnfermagem> getDiagnosticosEnfermagem() {
		return diagnosticosEnfermagem;
	}

	public void setDiagnosticosEnfermagem(Collection<DiagnosticoEnfermagem> diagnosticosEnfermagem) {
		this.diagnosticosEnfermagem = diagnosticosEnfermagem;
	}

	public String getNumeroCartaoSus() {
		return numeroCartaoSus;
	}

	public void setNumeroCartaoSus(String numeroCartaoSus) {
		this.numeroCartaoSus = numeroCartaoSus;
	}
	
	public Collection<Ulcera> getUlceras() {
		return ulceras;
	}

	public void setUlceras(Collection<Ulcera> ulceras) {
		this.ulceras = ulceras;
	}

	public Collection<Consulta> getConsultas() {
		return consultas;
	}

	public void setConsultas(Collection<Consulta> consultas) {
		this.consultas = consultas;
	}

	public DiagnosticoEnfermagem getDiagnosticoEnfermagem() {
		return diagnosticoEnfermagem;
	}

	public void setDiagnosticoEnfermagem(DiagnosticoEnfermagem diagnosticoEnfermagem) {
		this.diagnosticoEnfermagem = diagnosticoEnfermagem;
	}
	
	@Override
	public boolean equals(Object object) {
		if (object != this) {
			if (!(object instanceof Paciente)) {
				return false;
			}
			Paciente paciente = (Paciente) object;
			if ((paciente.getId() != 0) && (this.getId() != 0)) {
				if (paciente.getId() != this.getId()) {
					return false;
				}
			} else if (!paciente.getProntuario().equals(this.getProntuario())) {
				return false;
			}
		}
		return true;
	}
}