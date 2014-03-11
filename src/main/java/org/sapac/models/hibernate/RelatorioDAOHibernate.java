/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sapac.models.hibernate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.sapac.annotations.DAOQualifier;
import org.sapac.entities.MembroEquipe;
import org.sapac.models.RelatorioDAO;

/**
 *
 * @author carlson
 */
@DAOQualifier(DAOQualifier.DAOType.HIBERNATE)
public class RelatorioDAOHibernate extends GenericDAOHibernate implements RelatorioDAO {

	@Override
	public Collection<Map<String, String>> gerarRelatorio(MembroEquipe membroEquipe) {
		Session session = getSession();
		Transaction transaction = session.beginTransaction();
		
		StringBuilder projecao = new StringBuilder();
		projecao.append("v.pressaoArterial,v.indiceMassaCorporal,")
				.append("v.nivelGlicemia,v.nivelCreatina,v.colesterolHDL,v.vhs,")
				.append("v.cintura,v.quadril,v.indiceTornozeloBraco,v.velocidadeHemossedimentacao,")
				.append("v.mobilidade,v.desbridamentoCirurgico,v.compressao,v.repouso,")
				.append("v.coopera,v.diabetes,v.cardiopatiaIsquemica,v.doencaRenal,")
				.append("v.acidenteVascularCerebral,v.artrite,v.alteracoesVisao,")
				.append("v.tromboseVascularProfunda,v.fratura,v.cirurgia,v.doencaArterialPeriferica,")
				.append("v.alcoolista,v.dermatiteOcre,v.lipodermatoesclerose,v.asma,v.anemiaFalciforme,")
				.append("v.macosCigarroDiarios,v.ceap");
		StringBuilder hql = new StringBuilder();
		hql.append("SELECT ")
				.append(projecao.toString())
				.append(" FROM Consulta consulta ")
				.append(" INNER JOIN consulta.variaveisClinicas v ")
				.append(" INNER JOIN consulta.situacoesUlcera situacaoUlcera ")
				.append(" INNER JOIN consulta.membrosEquipe membroEquipe ")
				.append(" WHERE ")
				.append(" membroEquipe.id = :membroEquipe ")
				;
		String sql = hql.toString();
		Query query = session.createQuery(sql);
		query.setInteger("membroEquipe", membroEquipe.getId());
		
		Collection<Object[]> objetos = query.list();
		transaction.commit();
		
		String[] campos = projecao.toString().split(",");
		Collection<Map<String, String>> retorno = new ArrayList<Map<String, String>>();
		for (Object[] aObject : objetos) {
			Map<String, String> map = new HashMap<String, String>();
			for (int i = 0; i < campos.length; i++) {
				map.put(campos[i], aObject[i].toString());
			}
		}
		
		return retorno;
	}
	
}