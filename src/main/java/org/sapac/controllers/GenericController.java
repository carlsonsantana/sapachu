/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sapac.controllers;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author carlson
 */
public abstract class GenericController implements Serializable {
	
	private static SimpleDateFormat dateFormatBrasil;

	static {
		dateFormatBrasil = new SimpleDateFormat("dd/MM/yyyy");
	}

	/**
	 * Método para retornar o request atual.
	 *
	 * @return O request atual.
	 */
	public HttpServletRequest getRequest() {
		return (HttpServletRequest) FacesContext.getCurrentInstance().
				getExternalContext().getRequest();
	}

	public Object getController(String managedBean) {
		Object requestObject = getRequest().getAttribute(managedBean);
		if (requestObject != null) {
			return requestObject;
		} else {
			Object sessionObject = getRequest().getSession().
					getAttribute(managedBean);
			if (sessionObject != null) {
				return sessionObject;
			} else {
				Object applicationObject = FacesContext.
						getCurrentInstance().getApplication().
						evaluateExpressionGet(FacesContext.getCurrentInstance(),
						"#{" + managedBean + "}", Object.class);
				if (applicationObject != null) {
					return applicationObject;
				}
			}
		}
		return null;
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
	
	public String getDataFormatada(Date date) {
		return dateFormatBrasil.format(date);
	}
	
	public void clearSessions() {
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
	}
	
	public String cancelar() {
		return PaginasNavegacao.PAGINA_INICIAL;
	}
}