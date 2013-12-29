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
@Table(name = "enfermeiro")
@PrimaryKeyJoinColumn(name = "id_enfermeiro")
public class Enfermeiro extends MembroEquipe {
	
}