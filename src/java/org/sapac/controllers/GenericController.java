/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sapac.controllers;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author carlson
 */
public abstract class GenericController implements Serializable {
	
	private SimpleDateFormat dateFormatBrasil;
	
	public GenericController() {
		dateFormatBrasil = new SimpleDateFormat("dd/MM/yyyy");
	}
	
	/**
	 * Método para retornar o request atual.
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
}