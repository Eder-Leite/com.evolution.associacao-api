-- CREATE TABLE
CREATE TABLE SEGPERMU
(
  NCODIPERMU NUMBER NOT NULL,
  CDESCPERMU VARCHAR2(500) NOT NULL,
  CSIGLPERMU VARCHAR2(255) NOT NULL
);
-- ADD COMMENTS TO THE TABLE 
COMMENT ON TABLE SEGPERMU
  IS 'Tabela de Permissão para os Usuários';
-- ADD COMMENTS TO THE COLUMNS 
COMMENT ON COLUMN SEGPERMU.NCODIPERMU
  IS 'código da permissão';
COMMENT ON COLUMN SEGPERMU.CDESCPERMU
  IS 'descrião da permissão';
COMMENT ON COLUMN SEGPERMU.CSIGLPERMU
  IS 'sigla da permissão';
-- CREATE/RECREATE PRIMARY, UNIQUE AND FOREIGN KEY CONSTRAINTS 
ALTER TABLE SEGPERMU
  ADD CONSTRAINT PK_SEGPERMU PRIMARY KEY (NCODIPERMU)
  USING INDEX;
ALTER TABLE SEGPERMU
  ADD CONSTRAINT UK_SEGPERMU UNIQUE (CSIGLPERMU)
  USING INDEX;
  
CREATE SEQUENCE SEG_SSEGPERMU NOCACHE START WITH 6;  
  
INSERT INTO SEGPERMU (NCODIPERMU, CDESCPERMU, CSIGLPERMU) VALUES (1, 'ADMINISTRADOR', 'ROLE_ADMIN');
INSERT INTO SEGPERMU (NCODIPERMU, CDESCPERMU, CSIGLPERMU) VALUES (2, 'SUPERVISOR RH', 'ROLE_RH');
INSERT INTO SEGPERMU (NCODIPERMU, CDESCPERMU, CSIGLPERMU) VALUES (3, 'SUPERVISOR SETOR', 'ROLE_DEPTO');
INSERT INTO SEGPERMU (NCODIPERMU, CDESCPERMU, CSIGLPERMU) VALUES (4, 'USUARIO', 'ROLE_USER');
INSERT INTO SEGPERMU (NCODIPERMU, CDESCPERMU, CSIGLPERMU) VALUES (5, 'LANCHONETE', 'ROLE_LANCH');  
  
---------------
-- CREATE TABLE
CREATE TABLE SEGDEPAR
(
  NCODIDEPAR NUMBER NOT NULL,
  CNOMEDEPAR VARCHAR2(255) NOT NULL
);
-- ADD COMMENTS TO THE TABLE 
COMMENT ON TABLE SEGDEPAR
  IS 'Tabela de Derpartamento';
-- ADD COMMENTS TO THE COLUMNS 
COMMENT ON COLUMN SEGDEPAR.NCODIDEPAR
  IS 'código do departamento';
COMMENT ON COLUMN SEGDEPAR.CNOMEDEPAR
  IS 'nome do departamento';
-- CREATE/RECREATE PRIMARY, UNIQUE AND FOREIGN KEY CONSTRAINTS 
ALTER TABLE SEGDEPAR
  ADD CONSTRAINT PK_SEGDEPAR PRIMARY KEY (NCODIDEPAR)
  USING INDEX;
ALTER TABLE SEGDEPAR
  ADD CONSTRAINT UK_SEGDEPAR UNIQUE (CNOMEDEPAR)
  USING INDEX;
  
CREATE SEQUENCE SEG_SSEGDEPAR NOCACHE START WITH 14;  
  
INSERT INTO SEGDEPAR (NCODIDEPAR, CNOMEDEPAR) VALUES (1, 'DPTO - LANCHONETE');
INSERT INTO SEGDEPAR (NCODIDEPAR, CNOMEDEPAR) VALUES (2, 'DPTO - RECURSOS HUMANO');
INSERT INTO SEGDEPAR (NCODIDEPAR, CNOMEDEPAR) VALUES (3, 'DPTO - ADMINISTRATIVO');
INSERT INTO SEGDEPAR (NCODIDEPAR, CNOMEDEPAR) VALUES (4, 'DPTO - BENS FORNECIMENTO');
INSERT INTO SEGDEPAR (NCODIDEPAR, CNOMEDEPAR) VALUES (5, 'DPTO - COMERCIAL');
INSERT INTO SEGDEPAR (NCODIDEPAR, CNOMEDEPAR) VALUES (6, 'DPTO - FINANCEIRO');
INSERT INTO SEGDEPAR (NCODIDEPAR, CNOMEDEPAR) VALUES (7, 'DPTO - CONTABILIDADE');
INSERT INTO SEGDEPAR (NCODIDEPAR, CNOMEDEPAR) VALUES (8, 'DPTO - AUTO POSTO');
INSERT INTO SEGDEPAR (NCODIDEPAR, CNOMEDEPAR) VALUES (9, 'DPTO - DIRETORIA');
INSERT INTO SEGDEPAR (NCODIDEPAR, CNOMEDEPAR) VALUES (10, 'DPTO - TI');
INSERT INTO SEGDEPAR (NCODIDEPAR, CNOMEDEPAR) VALUES (11, 'DPTO - TRANSPORTE');
INSERT INTO SEGDEPAR (NCODIDEPAR, CNOMEDEPAR) VALUES (12, 'DPTO - DESTILARIA');
INSERT INTO SEGDEPAR (NCODIDEPAR, CNOMEDEPAR) VALUES (13, 'DPTO - CONTROLADORIA');  
---------------
-- CREATE TABLE
CREATE TABLE SEGUSUAR
(
  NCODIUSUAR NUMBER NOT NULL,
  NCODIDEPAR NUMBER NOT NULL,
  CNOMEUSUAR VARCHAR2(100) NOT NULL,
  CEMAIUSUAR VARCHAR2(255) NOT NULL,
  CSENHUSUAR VARCHAR2(255) NOT NULL,
  CSTATUSUAR VARCHAR2(15) NOT NULL,
  NQTACUSUAR NUMBER DEFAULT 0 NOT NULL,
  DDTUAUSUAR DATE DEFAULT SYSDATE NOT NULL,
  DDTCAUSUAR DATE DEFAULT SYSDATE NOT NULL
);
-- ADD COMMENTS TO THE TABLE 
COMMENT ON TABLE SEGUSUAR
  IS 'Tabela de Usuário';
-- ADD COMMENTS TO THE COLUMNS 
COMMENT ON COLUMN SEGUSUAR.NCODIUSUAR
  IS 'código do usuário';
COMMENT ON COLUMN SEGUSUAR.NCODIDEPAR
  IS 'código do departamento';
COMMENT ON COLUMN SEGUSUAR.CNOMEUSUAR
  IS 'nome do usuário';
COMMENT ON COLUMN SEGUSUAR.CEMAIUSUAR
  IS 'email do usuário';
COMMENT ON COLUMN SEGUSUAR.CSENHUSUAR
  IS 'senha do usuário';
COMMENT ON COLUMN SEGUSUAR.CSTATUSUAR
  IS 'situação do usuário';
COMMENT ON COLUMN SEGUSUAR.NQTACUSUAR
  IS 'quantidade de acessos';
COMMENT ON COLUMN SEGUSUAR.DDTUAUSUAR
  IS 'data do ultimo acesso';
COMMENT ON COLUMN SEGUSUAR.DDTCAUSUAR
  IS 'data de cadastro';
-- CREATE/RECREATE INDEXES 
CREATE INDEX FK_SEGUSUAR_SEGDEPAR ON SEGUSUAR (NCODIDEPAR);
-- CREATE/RECREATE PRIMARY, UNIQUE AND FOREIGN KEY CONSTRAINTS 
ALTER TABLE SEGUSUAR
  ADD CONSTRAINT PK_SEGUSUAR PRIMARY KEY (NCODIUSUAR)
  USING INDEX;
ALTER TABLE SEGUSUAR
  ADD CONSTRAINT UK_SEGUSUAR UNIQUE (CEMAIUSUAR)
  USING INDEX;
ALTER TABLE SEGUSUAR
  ADD CONSTRAINT FK_SEGUSUAR_SEGDEPAR FOREIGN KEY (NCODIDEPAR)
  REFERENCES SEGDEPAR (NCODIDEPAR);
  
CREATE SEQUENCE SEG_SSEGUSUAR NOCACHE START WITH 2;

INSERT INTO SEGUSUAR
  (NCODIUSUAR,
   NCODIDEPAR,
   CNOMEUSUAR,
   CEMAIUSUAR,
   CSENHUSUAR,
   CSTATUSUAR,
   NQTACUSUAR,
   DDTUAUSUAR,
   DDTCAUSUAR)
VALUES
  (1,
   10,
   'EDER APARECIDO LEITE',
   'eder@evolutionsistemas.com.br',
   '$2a$10$T7zt4bcndKy0IOo4OIbjIOrhJcQKurlg3VTzED7C2QCJ1JxnZXabS',
   'ATIVO',
   0,
   SYSDATE,
   SYSDATE);  
---------------  
-- CREATE TABLE
CREATE TABLE SEGPERUS
(
  NCODIPERMU NUMBER NOT NULL,
  NCODIUSUAR NUMBER NOT NULL
);
  
-- ADD COMMENTS TO THE TABLE 
COMMENT ON TABLE SEGPERUS
  IS 'Permissão do Usuário';
-- ADD COMMENTS TO THE COLUMNS 
COMMENT ON COLUMN SEGPERUS.NCODIPERMU
  IS 'código da permissão';
COMMENT ON COLUMN SEGPERUS.NCODIUSUAR
  IS 'código do usuário';
  
-- CREATE/RECREATE INDEXES 
CREATE INDEX FK_SEGPERUS_SEGPERMI ON SEGPERUS (NCODIPERMU);
CREATE INDEX FK_SEGPERUS_SEGUSUAR ON SEGPERUS (NCODIUSUAR);  
  
-- CREATE/RECREATE PRIMARY, UNIQUE AND FOREIGN KEY CONSTRAINTS 
ALTER TABLE SEGPERUS ADD CONSTRAINT FK_SEGPERUS_SEGPERMU FOREIGN KEY (NCODIPERMU) REFERENCES SEGPERMU (NCODIPERMU);
ALTER TABLE SEGPERUS ADD CONSTRAINT FK_SEGPERUS_SEGUSUAR FOREIGN KEY (NCODIUSUAR) REFERENCES SEGUSUAR (NCODIUSUAR);

INSERT INTO SEGPERUS (NCODIUSUAR, NCODIPERMU) VALUES (1, 1);
INSERT INTO SEGPERUS (NCODIUSUAR, NCODIPERMU) VALUES (1, 2);
INSERT INTO SEGPERUS (NCODIUSUAR, NCODIPERMU) VALUES (1, 3);
INSERT INTO SEGPERUS (NCODIUSUAR, NCODIPERMU) VALUES (1, 4);
INSERT INTO SEGPERUS (NCODIUSUAR, NCODIPERMU) VALUES (1, 5);

---------------
-- CREATE TABLE
CREATE SEQUENCE SEG_SSEGACESU NOCACHE;
CREATE TABLE SEGACESU
(
  NCODIACESU NUMBER NOT NULL,
  NCODIUSUAR NUMBER NOT NULL,
  DDATAACESU DATE DEFAULT SYSDATE NOT NULL
);
-- ADD COMMENTS TO THE COLUMNS 
COMMENT ON COLUMN SEGACESU.NCODIACESU
  IS 'código do acesso do usuário';
COMMENT ON COLUMN SEGACESU.NCODIUSUAR
  IS 'código do usuário';
COMMENT ON COLUMN SEGACESU.DDATAACESU
  IS 'data de acesso do usuário';
-- CREATE/RECREATE INDEXES 
CREATE INDEX FK_SEGACESU_SEGUSUAR ON SEGACESU (NCODIUSUAR);
-- CREATE/RECREATE PRIMARY, UNIQUE AND FOREIGN KEY CONSTRAINTS 
ALTER TABLE SEGACESU
  ADD CONSTRAINT PK_SEGACESU PRIMARY KEY (NCODIACESU)
  USING INDEX;
ALTER TABLE SEGACESU
  ADD CONSTRAINT FK_SEGACESU_SEGUSUAR FOREIGN KEY (NCODIUSUAR)
  REFERENCES SEGUSUAR (NCODIUSUAR);