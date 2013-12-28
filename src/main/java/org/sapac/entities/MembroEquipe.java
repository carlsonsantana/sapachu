/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sapac.entities;

/**
 *
 * @author carlson
 */
public interface MembroEquipe {
	public int getId();
	public void setId(int id);
	public Usuario getUsuario();
	public void setUsuario(Usuario usuario);
	public String getNome();
	public void setNome(String nome);
	public String getEmail();
	public void setEmail(String email);
	public String getRg();
	public void setRg(String rg);
	public String getCpf();
	public void setCpf(String cpf);
	public short getVinculo();
	public void setVinculo(short vinculo);
	public long getMatricula();
	public void setMatricula(long matricula);
	public boolean isMedico();
	public boolean isEnfermeiro();
}
