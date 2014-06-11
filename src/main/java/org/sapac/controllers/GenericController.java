package org.sapac.controllers;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

public abstract class GenericController implements Serializable {
	
	private static final SimpleDateFormat dateFormatBrasil;

	static {
		dateFormatBrasil = new SimpleDateFormat("dd/MM/yyyy");
	}

	public HttpServletRequest getRequest() {
		return (HttpServletRequest) FacesContext.getCurrentInstance().
				getExternalContext().getRequest();
	}

	public Date getDataAtual() {
		return new Date();
	}

	public String getDataFormatada() {
		return dateFormatBrasil.format(getDataAtual());
	}

	private void adicionarMensagem(FacesMessage.Severity tipo, String titulo,
			String mensagem) {
		FacesContext.getCurrentInstance().
				addMessage(null, new FacesMessage(tipo, titulo, mensagem));
	}
	
	public void adicionarMensagemAviso(String titulo, String mensagem) {
		adicionarMensagem(FacesMessage.SEVERITY_INFO, titulo, mensagem);
	}
	
	public void adicionarMensagemAlerta(String titulo, String mensagem) {
		adicionarMensagem(FacesMessage.SEVERITY_WARN, titulo, mensagem);
	}
	
	public void adicionarMensagemErro(String titulo, String mensagem) {
		adicionarMensagem(FacesMessage.SEVERITY_ERROR, titulo, mensagem);
	}
	
	public void adicionarMensagemFatal(String titulo, String mensagem) {
		adicionarMensagem(FacesMessage.SEVERITY_FATAL, titulo, mensagem);
	}
	
	public void adicionarMensagemAviso(String mensagem) {
		adicionarMensagemAviso("", mensagem);
	}
	
	public void adicionarMensagemAlerta(String mensagem) {
		adicionarMensagemAlerta("", mensagem);
	}
	
	public void adicionarMensagemErro(String mensagem) {
		adicionarMensagemErro("", mensagem);
	}
	
	public void adicionarMensagemFatal(String mensagem) {
		adicionarMensagemFatal("", mensagem);
	}
	
	public String getDataFormatada(Date date) {
		return dateFormatBrasil.format(date);
	}
	
	public void clearSession() {
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
	}
	
	public String cancelar() {
		return PaginasNavegacao.PAGINA_INICIAL;
	}
}