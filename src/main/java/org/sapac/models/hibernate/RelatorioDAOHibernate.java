package org.sapac.models.hibernate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import org.hibernate.Query;
import org.hibernate.Session;
import org.sapac.annotations.DAOQualifier;
import org.sapac.entities.Consulta;
import org.sapac.entities.MembroEquipe;
import org.sapac.models.RelatorioDAO;

@DAOQualifier(DAOQualifier.DAOType.HIBERNATE)
public class RelatorioDAOHibernate extends GenericDAOHibernate implements RelatorioDAO {

	@Override
	public Collection<Map<String, String>> gerarRelatorioDiferenca(MembroEquipe membroEquipe) {
		Session session = getSession();
		
		StringBuilder projecao = new StringBuilder();
		projecao.append("diferenca_area,nivel_dor,")
				.append("profundidade,pressao_arterial,indice_massa_corporal,nivel_glicemia,")
				.append("nivel_creatina,colesterol_hdl,cintura,quadril,")
				.append("macos_cigarro_diarios,ceap");
		StringBuilder hql = new StringBuilder();
		hql.append("SELECT SUM(COALESCE(situacao_anterior.area, 0) - COALESCE(situacao_posterior.area, 0)) AS diferenca_area, ")
			.append("	SUM(COALESCE(situacao_anterior.nivel_dor, 0) - COALESCE(situacao_posterior.nivel_dor, 0)) AS nivel_dor, ")
			.append("	SUM(COALESCE(situacao_anterior.profundidade, 0) - COALESCE(situacao_posterior.profundidade, 0)) AS profundidade, ")
			.append("	variaveis_clinicas_anterior.pressao_arterial - variaveis_clinicas_posterior.pressao_arterial AS pressao_arterial, ")
			.append("	variaveis_clinicas_anterior.indice_massa_corporal - variaveis_clinicas_posterior.indice_massa_corporal AS indice_massa_corporal, ")
			.append("	variaveis_clinicas_anterior.nivel_glicemia - variaveis_clinicas_posterior.nivel_glicemia AS nivel_glicemia, ")
			.append("	variaveis_clinicas_anterior.nivel_creatina - variaveis_clinicas_posterior.nivel_creatina AS nivel_creatina, ")
			.append("	variaveis_clinicas_anterior.colesterol_hdl - variaveis_clinicas_posterior.colesterol_hdl AS colesterol_hdl, ")
			.append("	variaveis_clinicas_anterior.cintura - variaveis_clinicas_posterior.cintura AS cintura, ")
			.append("	variaveis_clinicas_anterior.quadril - variaveis_clinicas_posterior.quadril AS quadril, ")
			.append("	variaveis_clinicas_anterior.macos_cigarro_diarios - variaveis_clinicas_posterior.macos_cigarro_diarios AS macos_cigarro_diarios, ")
			.append("	variaveis_clinicas_anterior.ceap - variaveis_clinicas_posterior.ceap AS ceap, ")
			.append("	consulta.id_consulta FROM ( ")
			.append("		SELECT DISTINCT consulta_posterior.id_consulta AS id_consulta_posterior, consulta_anterior.id_consulta AS id_consulta_anterior, membros_consulta.id_membro_equipe ")
			.append("		FROM sapac.consulta AS consulta_posterior ")
			.append("		INNER JOIN sapac.consulta AS consulta_anterior ON (consulta_anterior.id_consulta <> consulta_posterior.id_consulta) ")
			.append("		INNER JOIN sapac.consulta_membro_equipe AS membros_consulta ON (membros_consulta.id_consulta = consulta_anterior.id_consulta) ")
			.append("		INNER JOIN ( ")
			.append("			SELECT MIN(consulta.data_consulta) AS menor_data, segunda_consulta.id_consulta ")
			.append("				FROM sapac.consulta AS consulta ")
			.append("				INNER JOIN sapac.consulta AS segunda_consulta ON (segunda_consulta.id_consulta <> consulta.id_consulta) ")
			.append("				WHERE ")
			.append("					consulta.data_consulta > segunda_consulta.data_consulta ")
			.append("					AND consulta.id_paciente = segunda_consulta.id_paciente ")
			.append("					AND consulta.situacao = :realizada ")
			.append("				GROUP BY segunda_consulta.id_consulta ")
			.append("		) AS proxima_consulta ON (proxima_consulta.id_consulta = consulta_anterior.id_consulta AND proxima_consulta.menor_data = consulta_posterior.data_consulta) ")
			.append("		WHERE ")
			.append("			consulta_posterior.id_paciente = consulta_anterior.id_paciente ")
			.append("			AND consulta_posterior.situacao = :realizada ")
			.append("			AND consulta_anterior.situacao = :realizada ")
			.append("	) as consultas ")
			.append("	INNER JOIN sapac.consulta AS consulta ON (consulta.id_consulta = consultas.id_consulta_posterior) ")
			.append("	INNER JOIN sapac.variaveis_clinicas AS variaveis_clinicas_posterior ON (variaveis_clinicas_posterior.id_consulta = consultas.id_consulta_posterior) ")
			.append("	INNER JOIN sapac.variaveis_clinicas AS variaveis_clinicas_anterior ON (variaveis_clinicas_anterior.id_consulta = consultas.id_consulta_anterior) ")
			.append("	LEFT JOIN sapac.situacao_ulcera_consulta AS situacao_posterior ON (situacao_posterior.id_consulta = consultas.id_consulta_posterior) ")
			.append("	LEFT JOIN sapac.situacao_ulcera_consulta AS situacao_anterior ON (situacao_anterior.id_consulta = consultas.id_consulta_anterior AND situacao_posterior.id_ulcera = situacao_anterior.id_ulcera) ")
			.append("	WHERE ")
			.append("		consultas.id_membro_equipe = :membroEquipe ")
			.append("	GROUP BY consulta.id_consulta, variaveis_clinicas_anterior.pressao_arterial - variaveis_clinicas_posterior.pressao_arterial, ")
			.append("		variaveis_clinicas_anterior.indice_massa_corporal - variaveis_clinicas_posterior.indice_massa_corporal, ")
			.append("		variaveis_clinicas_anterior.nivel_glicemia - variaveis_clinicas_posterior.nivel_glicemia, ")
			.append("		variaveis_clinicas_anterior.nivel_creatina - variaveis_clinicas_posterior.nivel_creatina, ")
			.append("		variaveis_clinicas_anterior.colesterol_hdl - variaveis_clinicas_posterior.colesterol_hdl, ")
			.append("		variaveis_clinicas_anterior.cintura - variaveis_clinicas_posterior.cintura, ")
			.append("		variaveis_clinicas_anterior.quadril - variaveis_clinicas_posterior.quadril, ")
			.append("		variaveis_clinicas_anterior.macos_cigarro_diarios - variaveis_clinicas_posterior.macos_cigarro_diarios, ")
			.append("		variaveis_clinicas_anterior.ceap - variaveis_clinicas_posterior.ceap ");
		Query query = session.createSQLQuery(hql.toString());
		query.setInteger("membroEquipe", membroEquipe.getId());
		query.setInteger("realizada", Consulta.CONSULTA_REALIZADA);
		
		Collection<Object[]> objetos = query.list();
		String[] campos = projecao.toString().split(",");
		Collection<Map<String, String>> retorno = new ArrayList<Map<String, String>>();
		for (Object[] aObject : objetos) {
			Map<String, String> map = new HashMap<String, String>();
			for (int i = 0; i < campos.length; i++) {
				map.put(campos[i], aObject[i].toString());
			}
			retorno.add(map);
		}
		
		return retorno;
	}

	@Override
	public Collection<Map<String, String>> gerarRelatorioAnteriorVariaveisClinicas(MembroEquipe membroEquipe) {
		Session session = getSession();
		
		StringBuilder projecao = new StringBuilder();
		projecao.append("nome,data,pressao_arterial,indice_massa_corporal,nivel_glicemia,")
			.append("nivel_creatina,colesterol_hdl,vhs,cintura,")
			.append("quadril,indice_tornozelo_braco,velocidade_hemossedimentacao,")
			.append("mobilidade,desbridamento_cirurgico,compressao,")
			.append("repouso,coopera,diabetes,cardiopatia_isquemica,")
			.append("doenca_renal,acidente_vascular_cerebral,artrite,")
			.append("alteracoes_visao,trombose_vascular_profunda,fratura,")
			.append("cirurgia,doenca_arterial_periferica,alcoolista,")
			.append("dermatite_ocre,lipodermatoesclerose,asma,")
			.append("anemia_falciforme,macos_cigarro_diarios,ceap");
		
		StringBuilder hql = new StringBuilder();
		hql.append("SELECT paciente.nome, consulta.data_consulta, ")
			.append("	variaveis_clinicas.pressao_arterial, variaveis_clinicas.indice_massa_corporal, variaveis_clinicas.nivel_glicemia, ")
			.append("	variaveis_clinicas.nivel_creatina, variaveis_clinicas.colesterol_hdl, variaveis_clinicas.vhs, variaveis_clinicas.cintura, ")
			.append("	variaveis_clinicas.quadril, variaveis_clinicas.indice_tornozelo_braco, variaveis_clinicas.velocidade_hemossedimentacao, ")
			.append("	variaveis_clinicas.mobilidade, variaveis_clinicas.desbridamento_cirurgico, variaveis_clinicas.compressao, ")
			.append("	variaveis_clinicas.repouso, variaveis_clinicas.coopera, variaveis_clinicas.diabetes, variaveis_clinicas.cardiopatia_isquemica, ")
			.append("	variaveis_clinicas.doenca_renal, variaveis_clinicas.acidente_vascular_cerebral,	variaveis_clinicas.artrite, ")
			.append("	variaveis_clinicas.alteracoes_visao, variaveis_clinicas.trombose_vascular_profunda, variaveis_clinicas.fratura, ")
			.append("	variaveis_clinicas.cirurgia, variaveis_clinicas.doenca_arterial_periferica, variaveis_clinicas.alcoolista, ")
			.append("	variaveis_clinicas.dermatite_ocre, variaveis_clinicas.lipodermatoesclerose, variaveis_clinicas.asma, ")
			.append("	variaveis_clinicas.anemia_falciforme, variaveis_clinicas.macos_cigarro_diarios, variaveis_clinicas.ceap ")
			.append("	FROM ( ")
			.append("	SELECT DISTINCT consulta_posterior.id_consulta AS id_consulta_posterior, consulta_anterior.id_consulta AS id_consulta_anterior, membros_consulta.id_membro_equipe ")
			.append("		FROM sapac.consulta AS consulta_posterior ")
			.append("		INNER JOIN sapac.consulta AS consulta_anterior ON (consulta_anterior.id_consulta <> consulta_posterior.id_consulta) ")
			.append("		INNER JOIN sapac.consulta_membro_equipe AS membros_consulta ON (membros_consulta.id_consulta = consulta_anterior.id_consulta) ")
			.append("		INNER JOIN ( ")
			.append("			SELECT MIN(consulta.data_consulta) AS menor_data, segunda_consulta.id_consulta ")
			.append("				FROM sapac.consulta AS consulta ")
			.append("				INNER JOIN sapac.consulta AS segunda_consulta ON (segunda_consulta.id_consulta <> consulta.id_consulta) ")
			.append("				WHERE ")
			.append("					consulta.data_consulta > segunda_consulta.data_consulta ")
			.append("					AND consulta.id_paciente = segunda_consulta.id_paciente ")
			.append("					AND consulta.situacao = :realizada ")
			.append("				GROUP BY segunda_consulta.id_consulta ")
			.append("		) AS proxima_consulta ON (proxima_consulta.id_consulta = consulta_anterior.id_consulta AND proxima_consulta.menor_data = consulta_posterior.data_consulta) ")
			.append("		WHERE ")
			.append("			consulta_posterior.id_paciente = consulta_anterior.id_paciente ")
			.append("			AND consulta_posterior.situacao = :realizada ")
			.append("			AND consulta_anterior.situacao = :realizada ")
			.append("	) AS consultas ")
			.append("	INNER JOIN sapac.consulta AS consulta ON (consulta.id_consulta = consultas.id_consulta_anterior) ")
			.append("	INNER JOIN sapac.paciente AS paciente ON (paciente.id_paciente = consulta.id_paciente) ")
			.append("	INNER JOIN sapac.variaveis_clinicas AS variaveis_clinicas ON (variaveis_clinicas.id_consulta = consultas.id_consulta_anterior) ")
			.append("	WHERE ")
			.append("		consultas.id_membro_equipe = :membroEquipe ")
			.append("	ORDER BY consulta.data_consulta ");
		
		Query query = session.createSQLQuery(hql.toString());
		query.setInteger("membroEquipe", membroEquipe.getId());
		query.setInteger("realizada", Consulta.CONSULTA_REALIZADA);
		
		Collection<Object[]> objetos = query.list();
		String[] campos = projecao.toString().split(",");
		Collection<Map<String, String>> retorno = new ArrayList<Map<String, String>>();
		for (Object[] aObject : objetos) {
			Map<String, String> map = new HashMap<String, String>();
			for (int i = 0; i < campos.length; i++) {
				map.put(campos[i], aObject[i].toString());
			}
			retorno.add(map);
		}
		
		return retorno;
	}

	@Override
	public Collection<Map<String, String>> gerarRelatorioPosteriorVariaveisClinicas(MembroEquipe membroEquipe) {
		Session session = getSession();
		
		StringBuilder projecao = new StringBuilder();
		projecao.append("nome,data,pressao_arterial,indice_massa_corporal,nivel_glicemia,")
			.append("nivel_creatina,colesterol_hdl,vhs,cintura,")
			.append("quadril,indice_tornozelo_braco,velocidade_hemossedimentacao,")
			.append("mobilidade,desbridamento_cirurgico,compressao,")
			.append("repouso,coopera,diabetes,cardiopatia_isquemica,")
			.append("doenca_renal,acidente_vascular_cerebral,artrite,")
			.append("alteracoes_visao,trombose_vascular_profunda,fratura,")
			.append("cirurgia,doenca_arterial_periferica,alcoolista,")
			.append("dermatite_ocre,lipodermatoesclerose,asma,")
			.append("anemia_falciforme,macos_cigarro_diarios,ceap");
		
		StringBuilder hql = new StringBuilder();
		hql.append("SELECT paciente.nome, consulta.data_consulta, ")
			.append("	variaveis_clinicas.pressao_arterial, variaveis_clinicas.indice_massa_corporal, variaveis_clinicas.nivel_glicemia, ")
			.append("	variaveis_clinicas.nivel_creatina, variaveis_clinicas.colesterol_hdl, variaveis_clinicas.vhs, variaveis_clinicas.cintura, ")
			.append("	variaveis_clinicas.quadril, variaveis_clinicas.indice_tornozelo_braco, variaveis_clinicas.velocidade_hemossedimentacao, ")
			.append("	variaveis_clinicas.mobilidade, variaveis_clinicas.desbridamento_cirurgico, variaveis_clinicas.compressao, ")
			.append("	variaveis_clinicas.repouso, variaveis_clinicas.coopera, variaveis_clinicas.diabetes, variaveis_clinicas.cardiopatia_isquemica, ")
			.append("	variaveis_clinicas.doenca_renal, variaveis_clinicas.acidente_vascular_cerebral,	variaveis_clinicas.artrite, ")
			.append("	variaveis_clinicas.alteracoes_visao, variaveis_clinicas.trombose_vascular_profunda, variaveis_clinicas.fratura, ")
			.append("	variaveis_clinicas.cirurgia, variaveis_clinicas.doenca_arterial_periferica, variaveis_clinicas.alcoolista, ")
			.append("	variaveis_clinicas.dermatite_ocre, variaveis_clinicas.lipodermatoesclerose, variaveis_clinicas.asma, ")
			.append("	variaveis_clinicas.anemia_falciforme, variaveis_clinicas.macos_cigarro_diarios, variaveis_clinicas.ceap ")
			.append("	FROM ( ")
			.append("	SELECT DISTINCT consulta_posterior.id_consulta AS id_consulta_posterior, consulta_anterior.id_consulta AS id_consulta_anterior, membros_consulta.id_membro_equipe ")
			.append("		FROM sapac.consulta AS consulta_posterior ")
			.append("		INNER JOIN sapac.consulta AS consulta_anterior ON (consulta_anterior.id_consulta <> consulta_posterior.id_consulta) ")
			.append("		INNER JOIN sapac.consulta_membro_equipe AS membros_consulta ON (membros_consulta.id_consulta = consulta_anterior.id_consulta) ")
			.append("		INNER JOIN ( ")
			.append("			SELECT MIN(consulta.data_consulta) AS menor_data, segunda_consulta.id_consulta ")
			.append("				FROM sapac.consulta AS consulta ")
			.append("				INNER JOIN sapac.consulta AS segunda_consulta ON (segunda_consulta.id_consulta <> consulta.id_consulta) ")
			.append("				WHERE ")
			.append("					consulta.data_consulta > segunda_consulta.data_consulta ")
			.append("					AND consulta.id_paciente = segunda_consulta.id_paciente ")
			.append("					AND consulta.situacao = :realizada ")
			.append("				GROUP BY segunda_consulta.id_consulta ")
			.append("		) AS proxima_consulta ON (proxima_consulta.id_consulta = consulta_anterior.id_consulta AND proxima_consulta.menor_data = consulta_posterior.data_consulta) ")
			.append("		WHERE ")
			.append("			consulta_posterior.id_paciente = consulta_anterior.id_paciente ")
			.append("			AND consulta_posterior.situacao = :realizada ")
			.append("			AND consulta_anterior.situacao = :realizada ")
			.append("	) AS consultas ")
			.append("	INNER JOIN sapac.consulta AS consulta ON (consulta.id_consulta = consultas.id_consulta_posterior) ")
			.append("	INNER JOIN sapac.paciente AS paciente ON (paciente.id_paciente = consulta.id_paciente) ")
			.append("	INNER JOIN sapac.variaveis_clinicas AS variaveis_clinicas ON (variaveis_clinicas.id_consulta = consultas.id_consulta_posterior) ")
			.append("	WHERE ")
			.append("		consultas.id_membro_equipe = :membroEquipe ")
			.append("	ORDER BY consulta.data_consulta ");
		
		Query query = session.createSQLQuery(hql.toString());
		query.setInteger("membroEquipe", membroEquipe.getId());
		query.setInteger("realizada", Consulta.CONSULTA_REALIZADA);
		
		Collection<Object[]> objetos = query.list();
		String[] campos = projecao.toString().split(",");
		Collection<Map<String, String>> retorno = new ArrayList<Map<String, String>>();
		for (Object[] aObject : objetos) {
			Map<String, String> map = new HashMap<String, String>();
			for (int i = 0; i < campos.length; i++) {
				map.put(campos[i], aObject[i].toString());
			}
			retorno.add(map);
		}
		
		return retorno;
	}

	@Override
	public Collection<Map<String, String>> gerarRelatorioAnteriorSituacaoUlcera(MembroEquipe membroEquipe) {
		Session session = getSession();
		
		StringBuilder projecao = new StringBuilder();
		projecao.append("nome,data,id_ulcera,area,estado_ulcera,")
			.append("profundidade,nivel_dor,edema,")
			.append("secrecao,ciclo_litico,fibrina,")
			.append("granula,reulcera,prurido,")
			.append("ecsema,odor,sinal_cicatrizacao,")
			.append("circular");
		
		StringBuilder hql = new StringBuilder();
		hql.append("SELECT paciente.nome, consulta.data_consulta, ")
			.append("	situacao_ulcera_consulta.id_ulcera, situacao_ulcera_consulta.area, situacao_ulcera_consulta.estado_ulcera, ")
			.append("	situacao_ulcera_consulta.profundidade, situacao_ulcera_consulta.nivel_dor, situacao_ulcera_consulta.edema, ")
			.append("	situacao_ulcera_consulta.secrecao, situacao_ulcera_consulta.ciclo_litico, situacao_ulcera_consulta.fibrina, ")
			.append("	situacao_ulcera_consulta.granula, situacao_ulcera_consulta.reulcera, situacao_ulcera_consulta.prurido, ")
			.append("	situacao_ulcera_consulta.ecsema, situacao_ulcera_consulta.odor, situacao_ulcera_consulta.sinal_cicatrizacao, ")
			.append("	situacao_ulcera_consulta.circular ")
			.append("	FROM ( ")
			.append("	SELECT DISTINCT consulta_posterior.id_consulta AS id_consulta_posterior, consulta_anterior.id_consulta AS id_consulta_anterior, membros_consulta.id_membro_equipe ")
			.append("		FROM sapac.consulta AS consulta_posterior ")
			.append("		INNER JOIN sapac.consulta AS consulta_anterior ON (consulta_anterior.id_consulta <> consulta_posterior.id_consulta) ")
			.append("		INNER JOIN sapac.consulta_membro_equipe AS membros_consulta ON (membros_consulta.id_consulta = consulta_anterior.id_consulta) ")
			.append("		INNER JOIN ( ")
			.append("			SELECT MIN(consulta.data_consulta) AS menor_data, segunda_consulta.id_consulta ")
			.append("				FROM sapac.consulta AS consulta ")
			.append("				INNER JOIN sapac.consulta AS segunda_consulta ON (segunda_consulta.id_consulta <> consulta.id_consulta) ")
			.append("				WHERE ")
			.append("					consulta.data_consulta > segunda_consulta.data_consulta ")
			.append("					AND consulta.id_paciente = segunda_consulta.id_paciente ")
			.append("					AND consulta.situacao = :realizada ")
			.append("				GROUP BY segunda_consulta.id_consulta ")
			.append("		) AS proxima_consulta ON (proxima_consulta.id_consulta = consulta_anterior.id_consulta AND proxima_consulta.menor_data = consulta_posterior.data_consulta) ")
			.append("		WHERE ")
			.append("			consulta_posterior.id_paciente = consulta_anterior.id_paciente ")
			.append("			AND consulta_posterior.situacao = :realizada ")
			.append("			AND consulta_anterior.situacao = :realizada ")
			.append("	) AS consultas ")
			.append("	INNER JOIN sapac.consulta AS consulta ON (consulta.id_consulta = consultas.id_consulta_anterior) ")
			.append("	INNER JOIN sapac.paciente AS paciente ON (paciente.id_paciente = consulta.id_paciente) ")
			.append("	INNER JOIN sapac.situacao_ulcera_consulta AS situacao_ulcera_consulta ON (situacao_ulcera_consulta.id_consulta = consultas.id_consulta_anterior) ")
			.append("	WHERE ")
			.append("		consultas.id_membro_equipe = :membroEquipe ")
			.append("	ORDER BY consulta.data_consulta ");
		
		Query query = session.createSQLQuery(hql.toString());
		query.setInteger("membroEquipe", membroEquipe.getId());
		query.setInteger("realizada", Consulta.CONSULTA_REALIZADA);
		
		Collection<Object[]> objetos = query.list();
		String[] campos = projecao.toString().split(",");
		Collection<Map<String, String>> retorno = new ArrayList<Map<String, String>>();
		for (Object[] aObject : objetos) {
			Map<String, String> map = new HashMap<String, String>();
			for (int i = 0; i < campos.length; i++) {
				map.put(campos[i], aObject[i].toString());
			}
			retorno.add(map);
		}
		
		return retorno;
	}

	@Override
	public Collection<Map<String, String>> gerarRelatorioPosteriorSituacaoUlcera(MembroEquipe membroEquipe) {
		Session session = getSession();
		
		StringBuilder projecao = new StringBuilder();
		projecao.append("nome,data,id_ulcera,area,estado_ulcera,")
			.append("profundidade,nivel_dor,edema,")
			.append("secrecao,ciclo_litico,fibrina,")
			.append("granula,reulcera,prurido,")
			.append("ecsema,odor,sinal_cicatrizacao,")
			.append("circular");
		
		StringBuilder hql = new StringBuilder();
		hql.append("SELECT paciente.nome, consulta.data_consulta, ")
			.append("	situacao_ulcera_consulta.id_ulcera, situacao_ulcera_consulta.area, situacao_ulcera_consulta.estado_ulcera, ")
			.append("	situacao_ulcera_consulta.profundidade, situacao_ulcera_consulta.nivel_dor, situacao_ulcera_consulta.edema, ")
			.append("	situacao_ulcera_consulta.secrecao, situacao_ulcera_consulta.ciclo_litico, situacao_ulcera_consulta.fibrina, ")
			.append("	situacao_ulcera_consulta.granula, situacao_ulcera_consulta.reulcera, situacao_ulcera_consulta.prurido, ")
			.append("	situacao_ulcera_consulta.ecsema, situacao_ulcera_consulta.odor, situacao_ulcera_consulta.sinal_cicatrizacao, ")
			.append("	situacao_ulcera_consulta.circular ")
			.append("	FROM ( ")
			.append("	SELECT DISTINCT consulta_posterior.id_consulta AS id_consulta_posterior, consulta_anterior.id_consulta AS id_consulta_anterior, membros_consulta.id_membro_equipe ")
			.append("		FROM sapac.consulta AS consulta_posterior ")
			.append("		INNER JOIN sapac.consulta AS consulta_anterior ON (consulta_anterior.id_consulta <> consulta_posterior.id_consulta) ")
			.append("		INNER JOIN sapac.consulta_membro_equipe AS membros_consulta ON (membros_consulta.id_consulta = consulta_anterior.id_consulta) ")
			.append("		INNER JOIN ( ")
			.append("			SELECT MIN(consulta.data_consulta) AS menor_data, segunda_consulta.id_consulta ")
			.append("				FROM sapac.consulta AS consulta ")
			.append("				INNER JOIN sapac.consulta AS segunda_consulta ON (segunda_consulta.id_consulta <> consulta.id_consulta) ")
			.append("				WHERE ")
			.append("					consulta.data_consulta > segunda_consulta.data_consulta ")
			.append("					AND consulta.id_paciente = segunda_consulta.id_paciente ")
			.append("					AND consulta.situacao = :realizada ")
			.append("				GROUP BY segunda_consulta.id_consulta ")
			.append("		) AS proxima_consulta ON (proxima_consulta.id_consulta = consulta_anterior.id_consulta AND proxima_consulta.menor_data = consulta_posterior.data_consulta) ")
			.append("		WHERE ")
			.append("			consulta_posterior.id_paciente = consulta_anterior.id_paciente ")
			.append("			AND consulta_posterior.situacao = :realizada ")
			.append("			AND consulta_anterior.situacao = :realizada ")
			.append("	) AS consultas ")
			.append("	INNER JOIN sapac.consulta AS consulta ON (consulta.id_consulta = consultas.id_consulta_posterior) ")
			.append("	INNER JOIN sapac.paciente AS paciente ON (paciente.id_paciente = consulta.id_paciente) ")
			.append("	INNER JOIN sapac.situacao_ulcera_consulta AS situacao_ulcera_consulta ON (situacao_ulcera_consulta.id_consulta = consultas.id_consulta_posterior) ")
			.append("	WHERE ")
			.append("		consultas.id_membro_equipe = :membroEquipe ")
			.append("	ORDER BY consulta.data_consulta ");
		
		Query query = session.createSQLQuery(hql.toString());
		query.setInteger("membroEquipe", membroEquipe.getId());
		query.setInteger("realizada", Consulta.CONSULTA_REALIZADA);
		
		Collection<Object[]> objetos = query.list();
		String[] campos = projecao.toString().split(",");
		Collection<Map<String, String>> retorno = new ArrayList<Map<String, String>>();
		for (Object[] aObject : objetos) {
			Map<String, String> map = new HashMap<String, String>();
			for (int i = 0; i < campos.length; i++) {
				map.put(campos[i], aObject[i].toString());
			}
			retorno.add(map);
		}
		
		return retorno;
	}
}