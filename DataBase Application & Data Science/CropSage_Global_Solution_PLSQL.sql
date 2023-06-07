set serveroutpu on
set verify off

/*
Código em pl/sql que insere automaticamente as informações da tabela:
T_GS_PRODUTO
*/
BEGIN
    INSERT INTO t_gs_produto (cd_produto, nm_produto, ds_produto, ds_plantio) VALUES (1, 'Arroz', 'De Outubro (01/10) - Dezembro (31/12)');
    INSERT INTO t_gs_produto (cd_produto, nm_produto, ds_produto, ds_plantio) VALUES (2, 'Milho', 'De Setembro (25/09) - Novembro (01/11)');
    INSERT INTO t_gs_produto (cd_produto, nm_produto, ds_produto, ds_plantio) VALUES (3, 'Trigo', 'De Junho (01/06) - Agosto (31/08)');
    INSERT INTO t_gs_produto (cd_produto, nm_produto, ds_produto, ds_plantio) VALUES (4, 'Feijão', 'De Janeiro (01/09) - Novembro (31/11)');
    INSERT INTO t_gs_produto (cd_produto, nm_produto, ds_produto, ds_plantio) VALUES (5, 'Batata', 'De Janeiro (01/01) - Junho (01/06)');
    INSERT INTO t_gs_produto (cd_produto, nm_produto, ds_produto, ds_plantio) VALUES (6, 'Batata', 'De Janeiro (01/01) - Junho (01/06)');
    INSERT INTO t_gs_produto (cd_produto, nm_produto, ds_produto, ds_plantio) VALUES (7, 'Batata', 'De Janeiro (01/01) - Junho (01/06)');
    INSERT INTO t_gs_produto (cd_produto, nm_produto, ds_produto, ds_plantio) VALUES (8, 'Batata', 'De Janeiro (01/01) - Junho (01/06)');
    INSERT INTO t_gs_produto (cd_produto, nm_produto, ds_produto, ds_plantio) VALUES (9, 'Batata', 'De Janeiro (01/01) - Junho (01/06)');
    INSERT INTO t_gs_produto (cd_produto, nm_produto, ds_produto, ds_plantio) VALUES (10, 'Batata', 'De Janeiro (01/01) - Junho (01/06)');
    INSERT INTO t_gs_produto (cd_produto, nm_produto, ds_produto, ds_plantio) VALUES (11, 'Batata', 'De Janeiro (01/01) - Junho (01/06)');
  COMMIT;
END;

select * from t_gs_produto;




/*
Código em pl/sql que insere automaticamente as informações da tabela:
T_GS_USUARIO
*/
BEGIN
  INSERT INTO t_gs_usuario (cd_usuario, nm_usuario, ds_email, ds_senha) 
  VALUES (1, 'Usuário 1', 'usuario1@example.com', 'senha1');
  
  INSERT INTO t_gs_usuario (cd_usuario, nm_usuario, ds_email, ds_senha) 
  VALUES (2, 'Usuário 2', 'usuario2@example.com', 'senha2');
  
  INSERT INTO t_gs_usuario (cd_usuario, nm_usuario, ds_email, ds_senha) 
  VALUES (3, 'Usuário 3', 'usuario3@example.com', 'senha3');
  
  INSERT INTO t_gs_usuario (cd_usuario, nm_usuario, ds_email, ds_senha) 
  VALUES (4, 'Usuário 4', 'usuario4@example.com', 'senha4');
  
  INSERT INTO t_gs_usuario (cd_usuario, nm_usuario, ds_email, ds_senha) 
  VALUES (5, 'Usuário 5', 'usuario5@example.com', 'senha5');
  
  COMMIT;
end;

select * from t_gs_usuario;




/*
Código em pl/sql que  as insere informações da tabela:
"T_GS_PRODUTO"
Essa tabela irá pedir as informações do Usuario e verificar se ele já existe, 
caso ele ja exista irá dar um erro, esse erro será registrado na tabela
"T_GS_REGISTRO_LOG". Agora, caso esse usuário não exista irá inserir
as informações na tabela T_GS_USUARIO
*/
DECLARE
  v_cd_usuario t_gs_usuario.cd_usuario%TYPE;
  v_nm_usuario t_gs_usuario.nm_usuario%TYPE;
  v_cd_erro t_gs_registro_log.cd_erro%TYPE;
BEGIN
  v_cd_usuario := &cd_usuario;
  BEGIN
    SELECT nm_usuario
    INTO v_nm_usuario
    FROM t_gs_usuario
    WHERE cd_usuario = v_cd_usuario;
  EXCEPTION
    WHEN NO_DATA_FOUND THEN
      v_nm_usuario := NULL;
  END;

  IF v_nm_usuario IS NULL THEN
    v_nm_usuario := '&nm_usuario';
    INSERT INTO t_gs_usuario (cd_usuario, nm_usuario, ds_email, ds_senha)
    VALUES (v_cd_usuario, v_nm_usuario, '&ds_email', '&ds_senha');
  ELSE
    SELECT MAX(cd_erro) INTO v_cd_erro FROM t_gs_registro_log;
    v_cd_erro := COALESCE(v_cd_erro, 0) + 1;
    INSERT INTO t_gs_registro_log (cd_erro, nm_usuario, dt_ocorrencia, ds_mensagem)
    VALUES (v_cd_erro, v_nm_usuario, SYSDATE, 'Erro: Usuario ja cadastrado em nosso sistema');
       DBMS_OUTPUT.PUT_LINE('Usuario ja cadastrado no sistema');
  END IF;

  COMMIT;
EXCEPTION
  WHEN OTHERS THEN
    ROLLBACK;
    DBMS_OUTPUT.PUT_LINE('Erro: ' || SQLERRM);
END;

select * from t_gs_usuario;
select * from t_gs_registro_log;



/*
Código em pl/sql que insere automaticamente as informações da tabela:
T_GS_LOCALIZACAO
*/
BEGIN
  INSERT INTO t_gs_localizacao (cd_localizacao, fk_usuario_localizacao, ds_longitude, ds_latitude, nm_localizacao, ds_cep)
  VALUES (1, 1, 'Long1', 'Latitude 1', 'Localização 1', 'CEP 1');
  
  INSERT INTO t_gs_localizacao (cd_localizacao, fk_usuario_localizacao, ds_longitude, ds_latitude, nm_localizacao, ds_cep)
  VALUES (2, 2, 'Long2', 'Latitude 2', 'Localização 2', 'CEP 2');
  
  INSERT INTO t_gs_localizacao (cd_localizacao, fk_usuario_localizacao, ds_longitude, ds_latitude, nm_localizacao, ds_cep)
  VALUES (3, 2, 'Long3', 'Latitude 3', 'Localização 3', 'CEP 3');
  
  INSERT INTO t_gs_localizacao (cd_localizacao, fk_usuario_localizacao, ds_longitude, ds_latitude, nm_localizacao, ds_cep)
  VALUES (4, 2, 'Long4', 'Latitude 4', 'Localização 4', 'CEP 4');
  
  INSERT INTO t_gs_localizacao (cd_localizacao, fk_usuario_localizacao, ds_longitude, ds_latitude, nm_localizacao, ds_cep)
  VALUES (5, 1, 'Long5', 'Latitude 5', 'Localização 5', 'CEP 5');
  
  COMMIT;
END;
select * from t_gs_localizacao;

/*
Código em pl/sql que gera um relatorio de todas as localizações vinculadas 
a um usuario 
*/
DECLARE
    v_cd_usuario t_gs_usuario.cd_usuario%TYPE; 
    v_ds_localizacao t_gs_localizacao%ROWTYPE;
    CURSOR c_localizacoes IS
        SELECT *
        FROM t_gs_localizacao
        WHERE fk_usuario_localizacao = v_cd_usuario;
BEGIN
    v_cd_usuario := &cd_usuario; 
    OPEN c_localizacoes;
    LOOP
        FETCH c_localizacoes INTO v_ds_localizacao;
        EXIT WHEN c_localizacoes%NOTFOUND;
         DBMS_OUTPUT.PUT_LINE('------------------------------');
        DBMS_OUTPUT.PUT_LINE('CD_Localização: ' || v_ds_localizacao.cd_localizacao);
        DBMS_OUTPUT.PUT_LINE('Longitude: ' || v_ds_localizacao.ds_longitude);
        DBMS_OUTPUT.PUT_LINE('Latitude: ' || v_ds_localizacao.ds_latitude);
        DBMS_OUTPUT.PUT_LINE('Nome LOcalização: ' || v_ds_localizacao.nm_localizacao);
        DBMS_OUTPUT.PUT_LINE('CEP: ' || v_ds_localizacao.ds_cep);
        DBMS_OUTPUT.PUT_LINE('------------------------------');
    END LOOP;
    CLOSE c_localizacoes;
EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Erro ao exibir as localizações: ' || SQLERRM);
END;






/*
Código em pl/sql que insere automaticamente as informações da tabela:
T_GS_SOLO
*/
BEGIN
  INSERT INTO t_gs_solo (cd_solo, fk_produto, fk_usuario, qt_nitrogenio, qt_potassio, qt_fosforo, qt_temperatura, qt_umidade, ds_ph, ds_chuva)
  VALUES (1, 1, 1, 1, 1, 1, 25, 50, 6.5, 100);

  INSERT INTO t_gs_solo (cd_solo, fk_produto, fk_usuario, qt_nitrogenio, qt_potassio, qt_fosforo, qt_temperatura, qt_umidade, ds_ph, ds_chuva)
  VALUES (2, 1, 2, 2, 2, 2, 26, 52, 6.4, 85);

  INSERT INTO t_gs_solo (cd_solo, fk_produto, fk_usuario, qt_nitrogenio, qt_potassio, qt_fosforo, qt_temperatura, qt_umidade, ds_ph, ds_chuva)
  VALUES (3, 1, 3, 3, 3, 3, 27, 55, 6.3, 90);

  INSERT INTO t_gs_solo (cd_solo, fk_produto, fk_usuario, qt_nitrogenio, qt_potassio, qt_fosforo, qt_temperatura, qt_umidade, ds_ph, ds_chuva)
  VALUES (4, 1, 4, 4, 4, 4, 28, 58, 6.2, 95);

  INSERT INTO t_gs_solo (cd_solo, fk_produto, fk_usuario, qt_nitrogenio, qt_potassio, qt_fosforo, qt_temperatura, qt_umidade, ds_ph, ds_chuva)
  VALUES (5, 2, 5, 5, 5, 5, 29, 60, 6.1, 80);
  
  INSERT INTO t_gs_solo (cd_solo, fk_produto, fk_usuario, qt_nitrogenio, qt_potassio, qt_fosforo, qt_temperatura, qt_umidade, ds_ph, ds_chuva)
  VALUES (6, 2, 5, 5, 5, 5, 29, 60, 6.1, 80);
  
  INSERT INTO t_gs_solo (cd_solo, fk_produto, fk_usuario, qt_nitrogenio, qt_potassio, qt_fosforo, qt_temperatura, qt_umidade, ds_ph, ds_chuva)
  VALUES (7, 3, 5, 5, 5, 5, 29, 60, 6.1, 80);
  
  INSERT INTO t_gs_solo (cd_solo, fk_produto, fk_usuario, qt_nitrogenio, qt_potassio, qt_fosforo, qt_temperatura, qt_umidade, ds_ph, ds_chuva)
  VALUES (8, 4, 5, 5, 5, 5, 29, 60, 6.1, 80);
  
  INSERT INTO t_gs_solo (cd_solo, fk_produto, fk_usuario, qt_nitrogenio, qt_potassio, qt_fosforo, qt_temperatura, qt_umidade, ds_ph, ds_chuva)
  VALUES (9, 4, 5, 5, 5, 5, 29, 60, 6.1, 80);
  
  INSERT INTO t_gs_solo (cd_solo, fk_produto, fk_usuario, qt_nitrogenio, qt_potassio, qt_fosforo, qt_temperatura, qt_umidade, ds_ph, ds_chuva)
  VALUES (10, 5, 5, 5, 5, 5, 29, 60, 6.1, 80);

  COMMIT;
END;
select * from t_gs_solo;




/*
CCódigo em pl/sql que gera um relatório de quantos solos estão 
plantados com um determinado produto
*/
DECLARE
  v_produto_codigo t_gs_produto.cd_produto%TYPE; 
  v_produto_nome   t_gs_produto.nm_produto%TYPE;
  v_total_solos    NUMBER := 0;  

  CURSOR c_solos IS
    SELECT s.cd_solo, p.nm_produto FROM t_gs_solo s
    JOIN t_gs_produto p ON s.fk_produto = p.cd_produto
    WHERE p.cd_produto = v_produto_codigo;

BEGIN
  v_produto_codigo := &codigo_produto;
  SELECT nm_produto INTO v_produto_nome
  FROM t_gs_produto
  WHERE cd_produto = v_produto_codigo;

  FOR r_solo IN c_solos LOOP
    v_total_solos := v_total_solos + 1;

    DBMS_OUTPUT.PUT_LINE('Solo ' || r_solo.cd_solo || ' está plantado com ' || v_produto_nome);
  END LOOP;

  DBMS_OUTPUT.PUT_LINE('Total de solos com o produto ' || v_produto_nome || ': ' || v_total_solos);
END;










