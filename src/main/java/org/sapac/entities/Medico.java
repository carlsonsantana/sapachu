package org.sapac.entities;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "medico")
@PrimaryKeyJoinColumn(name = "id_medico")
public class Medico extends MembroEquipe {
	
}