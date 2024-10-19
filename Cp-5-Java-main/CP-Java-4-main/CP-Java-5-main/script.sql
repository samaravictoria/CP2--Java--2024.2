CREATE TABLE t_cliente (
    id_cliente NUMBER(5) CONSTRAINT pk_cliente PRIMARY KEY,
    nr_cpf VARCHAR2(11) NOT NULL,
    nm_cliente VARCHAR2(50) NOT NULL,
    dt_nascimento DATE NOT NULL,
    ds_endereco VARCHAR2(100) NOT NULL,
    ds_email VARCHAR2(30) NOT NULL
);

CREATE TABLE t_locadora (
    id_locadora NUMBER(5) CONSTRAINT pk_locadora PRIMARY KEY,
    nm_locadora VARCHAR2(100) NOT NULL,
    ds_endereco VARCHAR2(100) NOT NULL,
    nr_telefone VARCHAR2(20)
);

CREATE TABLE t_filme (
    id_filme NUMBER(5) CONSTRAINT pk_filme PRIMARY KEY,
    id_locadora NUMBER(5) CONSTRAINT fk_filme_locadora REFERENCES t_locadora(id_locadora),
    nm_filme VARCHAR2(150) NOT NULL,
    nm_diretor VARCHAR2(50) NOT NULL,
    ano_lancamento NUMBER(4) NOT NULL,
    ds_genero VARCHAR2(20) NOT NULL,
    ds_sinopse VARCHAR2(500) NOT NULL,
    tm_duracao NUMBER(3) NOT NULL,
    ds_classificacao VARCHAR2(10) NOT NULL
);

CREATE TABLE t_funcionario (
    id_funcionario NUMBER(5) CONSTRAINT pk_funcionario PRIMARY KEY,
    id_locadora NUMBER(5) CONSTRAINT fk_func_locad REFERENCES t_locadora(id_locadora),
    nr_cpf VARCHAR2(11) NOT NULL,
    nm_funcionario VARCHAR2(50) NOT NULL,
    dt_nascimento DATE NOT NULL,
    ano_admissao NUMBER(4) NOT NULL

);

CREATE TABLE t_aluguel (
    id_aluguel NUMBER(5) CONSTRAINT pk_aluguel PRIMARY KEY,
    id_filme NUMBER(5) CONSTRAINT fk_aluguel_filme REFERENCES t_filme(id_filme),
    id_cliente NUMBER(5) CONSTRAINT fk_clie_aluguel REFERENCES t_cliente(id_cliente),
    dt_aluguel DATE NOT NULL,
    dt_devolucao DATE
);

CREATE SEQUENCE sq_t_filme INCREMENT by 1 START with 1 NOCACHE;
CREATE SEQUENCE sq_t_cliente INCREMENT by 1 START with 1 NOCACHE;
CREATE SEQUENCE sq_t_funcionario INCREMENT by 1 START with 1 NOCACHE;
CREATE SEQUENCE sq_t_locadora INCREMENT by 1 START with 1 NOCACHE;
CREATE SEQUENCE sq_t_aluguel INCREMENT by 1 START with 1 NOCACHE;