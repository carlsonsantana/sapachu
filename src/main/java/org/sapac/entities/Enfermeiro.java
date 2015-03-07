package org.sapac.entities;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "enfermeiro")
@PrimaryKeyJoinColumn(name = "id_enfermeiro")
public class Enfermeiro extends MembroEquipe {
	
}