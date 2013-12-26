/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sapac.controllers;

/**
 *
 * @author carlson
 */
public abstract class PaginasNavegacao {
	
	public final static String PAGINA_INICIAL;
	
	public final static String LOGIN;
	
	public final static String USUARIO_PESQUISAR;
	public final static String USUARIO_EDITAR;
	public final static String USUARIO_CADASTRAR;
	public final static String USUARIO_MUDAR_SENHA;
	
	public final static String RELATORIO_PESQUISAR_USUARIO;
	public final static String RELATORIO_VISUALIZAR;
	
	public final static String PACIENTE_PESQUISAR;
	public final static String PACIENTE_VISUALIZAR;
	public final static String PACIENTE_CADASTRAR;
	public final static String PACIENTE_VISUALIZAR_CONSULTA;
	
	public final static String ENFERMAGEM_EDITAR_INTERVENCAO;
	public final static String ENFERMAGEM_EDITAR_DIAGNOSTICO;
	public final static String ENFERMAGEM_VISUALIZAR_INTERVENCAO;
	public final static String ENFERMAGEM_VISUALIZAR_DIAGNOSTICO;
	
	public final static String CONSULTA_PESQUISAR_PACIENTE;
	public final static String CONSULTA_MARCAR;
	public final static String CONSULTA_REMARCAR;
	public final static String CONSULTA_CONSULTAR_PACIENTE;
	static {
		PAGINA_INICIAL = "/private/apresentacao";
		LOGIN = "/public/login";
		
		USUARIO_PESQUISAR = "/private/usuario/pesquisar";
		USUARIO_EDITAR = "/private/usuario/editar";
		USUARIO_CADASTRAR = "/private/usuario/cadastrar";
		USUARIO_MUDAR_SENHA = "/private/usuario/mudar_senha";
		
		RELATORIO_PESQUISAR_USUARIO = "/private/relatorio/pesquisar";
		RELATORIO_VISUALIZAR = "/private/relatorio/relatorio_desempenho";
		
		PACIENTE_PESQUISAR = "/private/paciente/pesquisar";
		PACIENTE_VISUALIZAR = "/private/paciente/visualizar";
		PACIENTE_CADASTRAR = "/private/paciente/cadastrar";
		PACIENTE_VISUALIZAR_CONSULTA = "/private/paciente/visualizar_consulta";
		
		ENFERMAGEM_EDITAR_INTERVENCAO = "/private/enfermagem/intervencao";
		ENFERMAGEM_EDITAR_DIAGNOSTICO = "/private/enfermagem/diagnosticar";
		ENFERMAGEM_VISUALIZAR_INTERVENCAO = "/private/enfermagem/visualizar_diagnostico";
		ENFERMAGEM_VISUALIZAR_DIAGNOSTICO = "/private/enfermagem/visualizar_intervencao";
		
		CONSULTA_PESQUISAR_PACIENTE = "/private/consulta/pesquisar_paciente";
		CONSULTA_MARCAR = "/private/consulta/marcar_consulta";
		CONSULTA_REMARCAR = "/private/consulta/remarcar";
		CONSULTA_CONSULTAR_PACIENTE = "/private/consulta/consultar_paciente";
	}
}