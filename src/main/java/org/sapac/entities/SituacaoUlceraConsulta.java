/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sapac.entities;

import java.io.Serializable;

/**
 *
 * @author carlson
 */
public class SituacaoUlceraConsulta implements Serializable {
	private int id;
	private Consulta consulta;
	private Ulcera ulcera;
	private FotoUlcera fotoUlcera;
	private int estadoUlcera;
	private int prurido;
	private int ecsema;
	private int odor;
	private int profundidade;
	private int fibrina;
	private int granula;
	private int reulcera;
	private int nivelDor;
	private int edema;
	private int secrecao;
	private int sinalCicatrizacao;
	private int circular;
	private int area;
	private int cicloLitico;
	private boolean cicatrizada;
	private boolean lado;
}
