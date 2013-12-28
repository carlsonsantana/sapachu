/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sapac.entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

/**
 *
 * @author carlson
 */
public class VariaveisClinicas implements Serializable {

	private int id;
	private Consulta consulta;
	private Collection<MedicamentoUso> medicamentosUso;
	private float pressaoArterial;
	private float indiceMassaCorporal;
	private float nivelGlicemia;
	private float nivelCreatina;
	private float colesterolHDL;
	private float vhs;
	private float cintura;
	private float quadril;
	private float indiceTornozeloBraco;
	private float velocidadeHemossedimentacao;
	private char mobilidade;
	private boolean desbridamentoCirurgico;
	private boolean compressao;
	private boolean repouso;
	private boolean coopera;
	private boolean diabetes;
	private boolean cardiopatiaIsquemica;
	private boolean doencaRenal;
	private boolean acidenteVascularCerebral;
	private boolean artrite;
	private boolean alteracoesVisao;
	private boolean tromboseVascularProfunda;
	private boolean fratura;
	private boolean cirurgia;
	private boolean doencaArterialPeriferica;
	private boolean alcoolista;
	private boolean dermatiteOcre;
	private boolean lipodermatoesclerose;
	private boolean asma;
	private boolean anemiaFalciforme;
	private int macosCigarroDiarios;
	private int ceap;
	private String outrasDrogas;
	private Date periodoInicialFerida;
	private Date periodoFinalFerida;
}
