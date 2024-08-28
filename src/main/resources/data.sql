INSERT INTO permissao (descricao)
SELECT 'Administrador' WHERE NOT EXISTS (SELECT 1 FROM permissao WHERE descricao = 'Administrador');

INSERT INTO permissao (descricao)
SELECT 'Estoquista' WHERE NOT EXISTS (SELECT 1 FROM permissao WHERE descricao = 'Estoquista');

INSERT INTO permissao (descricao)
SELECT 'Cliente' WHERE NOT EXISTS (SELECT 1 FROM permissao WHERE descricao = 'Cliente');