/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sapac.entities;

import javax.persistence.Entity;

/**
 *
 * @author carlson
 */
public interface Funcionario {
	public String getNome();
	public void setNome(String nome);
	public boolean isMedico();
	public boolean isEnfermeiro();
}
