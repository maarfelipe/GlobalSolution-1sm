set serveroutpu on
set verify off

/*
C�digo em pl/sql que insere automaticamente as informa��es da tabela:
T_GS_PRODUTO
*/
BEGIN
    INSERT INTO t_gs_produto (cd_produto, nm_produto, ds_produto) VALUES (1, 'Arroz', 'Produto de planta��o');
    INSERT INTO t_gs_produto (cd_produto, nm_produto, ds_produto) VALUES (2, 'Milho', 'Produto de planta��o');
    INSERT INTO t_gs_produto (cd_produto, nm_produto, ds_produto) VALUES (3, 'Trigo', 'Produto de planta��o');
    INSERT INTO t_gs_produto (cd_produto, nm_produto, ds_produto) VALUES (4, 'Feij�o', 'Produto de planta��o');
    INSERT INTO t_gs_produto (cd_produto, nm_produto, ds_produto) VALUES (5, 'Batata', 'Produto de planta��o');
    INSERT INTO t_gs_produto (cd_produto, nm_produto, ds_produto) VALUES (6, 'Batata', 'Produto de planta��o');
    INSERT INTO t_gs_produto (cd_produto, nm_produto, ds_produto) VALUES (7, 'Batata', 'Produto de planta��o');
    INSERT INTO t_gs_produto (cd_produto, nm_produto, ds_produto) VALUES (8, 'Batata', 'Produto de planta��o');
    INSERT INTO t_gs_produto (cd_produto, nm_produto, ds_produto) VALUES (9, 'Batata', 'Produto de planta��o');
    INSERT INTO t_gs_produto (cd_produto, nm_produto, ds_produto) VALUES (10, 'Batata', 'Produto de planta��o');
    INSERT INTO t_gs_produto (cd_produto, nm_produto, ds_produto) VALUES (11, 'Batata', 'Produto de planta��o');
    
    
  COMMIT;
END;

select * from t_gs_produto;




/*
C�digo em pl/sql que insere automaticamente as informa��es da tabela:
T_GS_USUARIO
*/
BEGIN
  INSERT INTO t_gs_usuario (cd_usuario, nm_usuario, ds_email, ds_senha) 
  VALUES (1, 'Usu�rio 1', 'usuario1@example.com', 'senha1');
  
  INSERT INTO t_gs_usuario (cd_usuario, nm_usuario, ds_email, ds_senha) 
  VALUES (2, 'Usu�rio 2', 'usuario2@example.com', 'senha2');
  
  INSERT INTO t_gs_usuario (cd_usuario, nm_usuario, ds_email, ds_senha) 
  VALUES (3, 'Usu�rio 3', 'usuario3@example.com', 'senha3');
  
  INSERT INTO t_gs_usuario (cd_usuario, nm_usuario, ds_email, ds_senha) 
  VALUES (4, 'Usu�rio 4', 'usuario4@example.com', 'senha4');
  
  INSERT INTO t_gs_usuario (cd_usuario, nm_usuario, ds_email, ds_senha) 
  VALUES (5, 'Usu�rio 5', 'usuario5@example.com', 'senha5');
  
  COMMIT;
end;

select * from t_gs_usuario;




/*
C�digo em pl/sql que  as insere informa��es da tabela:
"T_GS_PRODUTO"
Essa tabela ir� pedir as informa��es do Usuario e verificar se ele j� existe, 
caso ele ja exista ir� dar um erro, esse erro ser� registrado na tabela
"T_GS_REGISTRO_LOG". Agora, caso esse usu�rio n�o exista ir� inserir
as informa��es na tabela T_GS_USUARIO
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
C�digo em pl/sql que insere automaticamente as informa��es da tabela:
T_GS_LOCALIZACAO
*/
BEGIN
  INSERT INTO t_gs_localizacao (cd_localizacao, fk_usuario_localizacao, ds_longitude, ds_latitude, nm_localizacao, ds_cep)
  VALUES (1, 1, 'Long1', 'Latitude 1', 'Localiza��o 1', 'CEP 1');
  
  INSERT INTO t_gs_localizacao (cd_localizacao, fk_usuario_localizacao, ds_longitude, ds_latitude, nm_localizacao, ds_cep)
  VALUES (2, 2, 'Long2', 'Latitude 2', 'Localiza��o 2', 'CEP 2');
  
  INSERT INTO t_gs_localizacao (cd_localizacao, fk_usuario_localizacao, ds_longitude, ds_latitude, nm_localizacao, ds_cep)
  VALUES (3, 2, 'Long3', 'Latitude 3', 'Localiza��o 3', 'CEP 3');
  
  INSERT INTO t_gs_localizacao (cd_localizacao, fk_usuario_localizacao, ds_longitude, ds_latitude, nm_localizacao, ds_cep)
  VALUES (4, 2, 'Long4', 'Latitude 4', 'Localiza��o 4', 'CEP 4');
  
  INSERT INTO t_gs_localizacao (cd_localizacao, fk_usuario_localizacao, ds_longitude, ds_latitude, nm_localizacao, ds_cep)
  VALUES (5, 1, 'Long5', 'Latitude 5', 'Localiza��o 5', 'CEP 5');
  
  COMMIT;
END;



/*
C�digo em pl/sql que gera um relatorio de todas as localiza��es vinculadas 
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
        DBMS_OUTPUT.PUT_LINE('CD_Localiza��o: ' || v_ds_localizacao.cd_localizacao);
        DBMS_OUTPUT.PUT_LINE('Longitude: ' || v_ds_localizacao.ds_longitude);
        DBMS_OUTPUT.PUT_LINE('Latitude: ' || v_ds_localizacao.ds_latitude);
        DBMS_OUTPUT.PUT_LINE('Nome LOcaliza��o: ' || v_ds_localizacao.nm_localizacao);
        DBMS_OUTPUT.PUT_LINE('CEP: ' || v_ds_localizacao.ds_cep);
        DBMS_OUTPUT.PUT_LINE('------------------------------');
    END LOOP;
    CLOSE c_localizacoes;
EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Erro ao exibir as localiza��es: ' || SQLERRM);
END;







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
  VALUES (5, 5, 5, 5, 5, 5, 29, 60, 6.1, 80);

  COMMIT;
END;





DECLARE
    v_cd_solo t_gs_solo.cd_solo%TYPE := &cd_solo; -- Insira o c�digo do solo desejado aqui
    v_cd_produto t_gs_produto.cd_produto%TYPE;
    v_nm_produto t_gs_produto.nm_produto%TYPE;
    CURSOR c_produtos (p_cd_solo t_gs_solo.cd_solo%TYPE) IS
        SELECT p.cd_produto, p.nm_produto
        FROM t_gs_solo s
        INNER JOIN t_gs_produto p ON s.fk_produto = p.cd_produto
        WHERE s.cd_solo = p_cd_solo;
BEGIN
    OPEN c_produtos(v_cd_solo);
    LOOP
        FETCH c_produtos INTO v_cd_produto, v_nm_produto;
        EXIT WHEN c_produtos%NOTFOUND;
        
        -- Exibe as informa��es do produto
        DBMS_OUTPUT.PUT_LINE('Codigo Produto: ' || v_cd_produto);
        DBMS_OUTPUT.PUT_LINE('Nome produto: ' || v_nm_produto);
        DBMS_OUTPUT.PUT_LINE('------------------------------');
    END LOOP;
    CLOSE c_produtos;
EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Erro ao exibir os produtos: ' || SQLERRM);
END;




select * from t_gs_solo;








