CREATE SEQUENCE seq_clientes_id
    start with 1
    increment by 1
    nocycle
    NOCACHE;

create sequence seq_endereco_id
    start with 1
    increment by 1
    nocycle
    nocache;

create sequence seq_carro_id
    start with 1
    increment by 1
    nocycle
    nocache;

create sequence seq_orcamentos_id
    start with 1
    increment by 1
    nocycle
    nocache;

create sequence seq_usuarios_id
    start with 1
    increment by 1
    nocycle
    nocache;

CREATE SEQUENCE seq_ordem_servico_id
    START WITH 1
    INCREMENT BY 1
    NOCYCLE
    NOCACHE;



CREATE TABLE usuarios (
    usuario_id  INTEGER        DEFAULT seq_usuarios_id.NEXTVAL PRIMARY KEY,
    nome        VARCHAR2(150)  NOT NULL,
    senha       VARCHAR2(255)  NOT NULL,
    funcao      VARCHAR2(30)   NOT NULL
);

CREATE TABLE clientes (
    cliente_id      INTEGER        DEFAULT seq_clientes_id.NEXTVAL PRIMARY KEY,
    nome            VARCHAR2(100)  NOT NULL,
    cpf             VARCHAR2(14)   NOT NULL,
    email           VARCHAR2(254)  NOT NULL,
    telefone        VARCHAR2(50),
    data_nascimento DATE           NOT NULL,

    CONSTRAINT uq_clientes_cpf   UNIQUE (cpf),
    CONSTRAINT uq_clientes_email UNIQUE (email)
);

CREATE TABLE enderecos (
    endereco_id INTEGER        DEFAULT seq_endereco_id.NEXTVAL PRIMARY KEY,
    cliente_id  INTEGER        NOT NULL,
    logradouro  VARCHAR2(150)  NOT NULL,
    cidade      VARCHAR2(100)  NOT NULL,
    bairro      VARCHAR2(100)  NOT NULL,
    numero      VARCHAR2(5)    NOT NULL,
    cep         VARCHAR2(9)    NOT NULL,

    CONSTRAINT fk_enderecos_cliente FOREIGN KEY (cliente_id)
        REFERENCES clientes (cliente_id)
);

CREATE TABLE carros (
    carro_id    INTEGER        DEFAULT seq_carro_id.NEXTVAL PRIMARY KEY,
    cliente_id  INTEGER        NOT NULL,
    marca       VARCHAR2(50)   NOT NULL,
    modelo      VARCHAR2(50)   NOT NULL,
    cor         VARCHAR2(20)   NOT NULL,
    placa       VARCHAR2(8)    NOT NULL,
    versao      VARCHAR2(50)   NOT NULL,
    chassi      VARCHAR2(17)   NOT NULL,
    cambio      VARCHAR2(20)   NOT NULL,

    CONSTRAINT uq_carros_placa  UNIQUE (placa),
    CONSTRAINT uq_carros_chassi UNIQUE (chassi),
    CONSTRAINT fk_carros_cliente FOREIGN KEY (cliente_id)
        REFERENCES clientes (cliente_id)
);

CREATE TABLE ordens_de_servico (
    ordem_servico_id      INTEGER         DEFAULT seq_ordem_servico_id.NEXTVAL PRIMARY KEY,
    cliente_id            INTEGER         NOT NULL,
    carro_id              INTEGER         NOT NULL,
    data_ordem_servico    DATE            NOT NULL,
    valor                 NUMBER(10, 2)   NOT NULL,
    observacoes           VARCHAR2(500),
    status                VARCHAR2(30)    NOT NULL,

    CONSTRAINT fk_ordens_cliente FOREIGN KEY (cliente_id)
        REFERENCES clientes (cliente_id),
    CONSTRAINT fk_ordens_carro FOREIGN KEY (carro_id)
        REFERENCES carros (carro_id)
);

CREATE TABLE orcamentos (
    orcamento_id     INTEGER        DEFAULT seq_orcamentos_id.NEXTVAL PRIMARY KEY,
    ordem_servico_id INTEGER        NOT NULL,
    usuario_id       INTEGER        NOT NULL,
    itens            VARCHAR2(1000),
    mao_de_obra      NUMBER(10, 2)  NOT NULL,
    is_aprovado      NUMBER(1)      DEFAULT 0 NOT NULL,
    data             DATE           NOT NULL,

    CONSTRAINT chk_orcamentos_aprovado CHECK (is_aprovado IN (0, 1)),
    CONSTRAINT fk_orcamentos_ordem   FOREIGN KEY (ordem_servico_id)
        REFERENCES ordens_de_servico (ordem_servico_id),
    CONSTRAINT fk_orcamentos_usuario FOREIGN KEY (usuario_id)
                                REFERENCES usuarios (usuario_id)
);



