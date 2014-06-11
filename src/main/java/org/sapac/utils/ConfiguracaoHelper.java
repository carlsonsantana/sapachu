package org.sapac.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
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
		try {
			InputStream file = this.getClass().getClassLoader().getResourceAsStream("/sapac.properties");
			Properties properties = new Properties();
			properties.load(file);
			diretorioFotosUpload = properties.getProperty("uploads_path");
		} catch (IOException ex) {
			Logger.getLogger(ConfiguracaoHelper.class.getName()).log(Level.SEVERE, null, ex);
		}
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
