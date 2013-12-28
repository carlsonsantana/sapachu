/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sapac.models.hibernate;

import java.util.Collection;
import org.sapac.entities.Consulta;
import org.sapac.entities.DiagnosticoEnfermagem;
import org.sapac.entities.IntervencaoEnfermagem;
import org.sapac.entities.Paciente;
import org.sapac.models.EnfermagemDAO;

/**
 *
 * @author carlson
 */
public class EnfermagemDAOHibernate extends GenericDAOHibernate implements EnfermagemDAO {

	@Override
	public DiagnosticoEnfermagem alterarDiagnosticoEnfermagem(DiagnosticoEnfermagem diagnosticoEnfermagem) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public IntervencaoEnfermagem alterarIntervencaoEnfermagem(IntervencaoEnfermagem intervencaoEnfermagem) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public DiagnosticoEnfermagem procurarDiagnosticoEnfermagem(Paciente paciente) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public Collection<IntervencaoEnfermagem> procurarIntervencoesEnfermagem(Paciente paciente) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public IntervencaoEnfermagem procurarIntervencaoEnfermagem(Consulta consulta) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}
	
}
