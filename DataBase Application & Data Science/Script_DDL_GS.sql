DROP TABLE t_gs_produto CASCADE CONSTRAINTS;

DROP TABLE t_gs_solo CASCADE CONSTRAINTS;

DROP TABLE t_gs_usuario CASCADE CONSTRAINTS;

DROP TABLE t_gs_registro_log CASCADE CONSTRAINTS;


CREATE TABLE t_gs_produto (
    cd_produto NUMBER(5) primary key,
    nm_produto VARCHAR2(50) NOT NULL,
    ds_produto VARCHAR2(50) NOT NULL
);


CREATE TABLE t_gs_usuario (
    cd_usuario    NUMBER(6) primary key,
    nm_usuario    VARCHAR2(100),
    ds_email      VARCHAR2(100),
    ds_senha      VARCHAR2(100),
    dt_nascimento DATE 
);
commit;

CREATE TABLE t_gs_solo (
    cd_solo        NUMBER(6) primary key,
    fk_produto     references t_gs_produto,
    fk_usuario     references t_gs_usuario,
    qt_nitrogenio  NUMBER(4) NOT NULL,
    qt_potassio    NUMBER(4) NOT NULL,
    qt_fosforo     NUMBER(4),
    qt_temperatura NUMBER(2) NOT NULL,
    qt_umidade     NUMBER(10) NOT NULL,
    ds_ph          NUMBER(10) NOT NULL,
    ds_chuva       NUMBER(10) NOT NULL
);
commit;

CREATE TABLE t_gs_registro_log (
	cd_erro NUMBER(5), 
    nm_usuario varchar2(50),
    dt_ocorrencia DATE,
    ds_mensagem varchar2(50)
);
commit;


