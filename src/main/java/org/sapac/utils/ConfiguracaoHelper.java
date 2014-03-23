package org.sapac.utils;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

@Named
@ApplicationScoped
public class ConfiguracaoHelper {
	private String formulaPixelsTransformaCentimetros;
	private String diretorioFotosUpload;
	
	@PostConstruct
	public void init() {
		diretorioFotosUpload = "//home//carlson//uploads//";
	}
	
	public String getDiretorioFotosUpload() {
		return diretorioFotosUpload;
	}
	
	public void setFormulaPixelsTransformaCentimetros(String formula) {
		this.formulaPixelsTransformaCentimetros = formula;
	}
	
	public String getFormulaPixelsTransformaCentimetros() {
		return formulaPixelsTransformaCentimetros;
	}
}
