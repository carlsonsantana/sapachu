INSERT INTO sapac.usuario (id_usuario, ativo, coordenador, nome_usuario, senha) VALUES
	(1, TRUE, TRUE, 'admin', '91f5167c34c400758115c2a6826ec2e3');
INSERT INTO sapac.membro_equipe (id_membro_equipe, cpf, email, matricula, nome, rg, vinculo, id_usuario) VALUES
	(1, '522.536.049-17', 'admin@admin.com', '00', 'Administrador', '00-0', 1, 1);
