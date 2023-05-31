set serveroutpu on
set verify off


BEGIN
  INSERT INTO t_gs_produto (cd_produto, nm_produto, ds_produto) VALUES (1, 'Arroz', 'Produto de planta��o');
  INSERT INTO t_gs_produto (cd_produto, nm_produto, ds_produto) VALUES (2, 'Milho', 'Produto de planta��o');
  INSERT INTO t_gs_produto (cd_produto, nm_produto, ds_produto) VALUES (3, 'Trigo', 'Produto de planta��o');
  INSERT INTO t_gs_produto (cd_produto, nm_produto, ds_produto) VALUES (4, 'Feij�o', 'Produto de planta��o');
  INSERT INTO t_gs_produto (cd_produto, nm_produto, ds_produto) VALUES (5, 'Batata', 'Produto de planta��o');
  COMMIT;
END;

select * from t_gs_produto;



BEGIN
  INSERT INTO t_gs_usuario (cd_usuario, nm_usuario, ds_email, ds_senha, dt_nascimento) 
  VALUES (1, 'Usu�rio 1', 'usuario1@example.com', 'senha1', TO_DATE('1990-01-01', 'YYYY-MM-DD'));
  
  INSERT INTO t_gs_usuario (cd_usuario, nm_usuario, ds_email, ds_senha, dt_nascimento) 
  VALUES (2, 'Usu�rio 2', 'usuario2@example.com', 'senha2', TO_DATE('1990-01-02', 'YYYY-MM-DD'));
  
  INSERT INTO t_gs_usuario (cd_usuario, nm_usuario, ds_email, ds_senha, dt_nascimento) 
  VALUES (3, 'Usu�rio 3', 'usuario3@example.com', 'senha3', TO_DATE('1990-01-03', 'YYYY-MM-DD'));
  
  INSERT INTO t_gs_usuario (cd_usuario, nm_usuario, ds_email, ds_senha, dt_nascimento) 
  VALUES (4, 'Usu�rio 4', 'usuario4@example.com', 'senha4', TO_DATE('1990-01-04', 'YYYY-MM-DD'));
  
  INSERT INTO t_gs_usuario (cd_usuario, nm_usuario, ds_email, ds_senha, dt_nascimento) 
  VALUES (5, 'Usu�rio 5', 'usuario5@example.com', 'senha5', TO_DATE('1990-01-05', 'YYYY-MM-DD'));
  
  COMMIT;
end;

select * from t_gs_usuario;


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
    INSERT INTO t_gs_usuario (cd_usuario, nm_usuario, ds_email, ds_senha, dt_nascimento)
    VALUES (v_cd_usuario, v_nm_usuario, '&ds_email', '&ds_senha', TO_DATE('&dt_nascimento', 'DD/MM/YYYY'));
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



