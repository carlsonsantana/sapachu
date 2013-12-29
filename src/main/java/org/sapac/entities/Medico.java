/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sapac.entities;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

/**
 *
 * @author carlson
 */
@Entity
@Table(name = "medico")
@PrimaryKeyJoinColumn(name = "id_medico")
public class Medico extends MembroEquipe {
	
}