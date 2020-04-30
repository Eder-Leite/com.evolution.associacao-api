-- CREATE TABLE
CREATE SEQUENCE FAT_SFATCODRN NOCACHE START WITH 11; 
CREATE TABLE FATCODRN
(
  NCODICODRN NUMBER NOT NULL,
  CDESCCODRN VARCHAR2(100) NOT NULL
);
-- ADD COMMENTS TO THE TABLE 
COMMENT ON TABLE FATCODRN
  IS 'Tabela de código de retorno de nota';
-- ADD COMMENTS TO THE COLUMNS 
COMMENT ON COLUMN FATCODRN.NCODICODRN
  IS 'código de retorno da nota';
COMMENT ON COLUMN FATCODRN.CDESCCODRN
  IS 'descrição do retorno da nota';
-- CREATE/RECREATE PRIMARY, UNIQUE AND FOREIGN KEY CONSTRAINTS 
ALTER TABLE FATCODRN
  ADD CONSTRAINT PK_FATCODRN PRIMARY KEY (NCODICODRN)
  USING INDEX;
  
INSERT INTO FATCODRN
  (NCODICODRN, CDESCCODRN)
VALUES
  (1, 'NOTA AGUARDANDO AUTENTICAÇĂO');

INSERT INTO FATCODRN
  (NCODICODRN, CDESCCODRN)
VALUES
  (2, 'NOTA AUTORIZADA EMITENTE');

INSERT INTO FATCODRN
  (NCODICODRN, CDESCCODRN)
VALUES
  (3, 'NOTA CANCELADA EMITENTE');

INSERT INTO FATCODRN
  (NCODICODRN, CDESCCODRN)
VALUES
  (4, 'NOTA CANCELADA DESTINATÁRIO');

INSERT INTO FATCODRN
  (NCODICODRN, CDESCCODRN)
VALUES
  (5, 'NOTA REJEITADA(SALDO INSUFICIENTE)');

INSERT INTO FATCODRN
  (NCODICODRN, CDESCCODRN)
VALUES
  (6, 'NOTA REJEITADA(FUNCIONÁRIO BLOQUEADO/INATIVO)');

INSERT INTO FATCODRN
  (NCODICODRN, CDESCCODRN)
VALUES
  (7, 'NOTA REJEITADA(AUTENTICAÇĂO INVÁLIDA UMA TENTATIVA)');

INSERT INTO FATCODRN
  (NCODICODRN, CDESCCODRN)
VALUES
  (8, 'NOTA REJEITADA(AUTENTICAÇĂO INVÁLIDA DUAS TENTATIVA)');

INSERT INTO FATCODRN
  (NCODICODRN, CDESCCODRN)
VALUES
  (9, 'NOTA CANCELADA POR FALTA DE AUTENTICAÇĂO');

INSERT INTO FATCODRN
  (NCODICODRN, CDESCCODRN)
VALUES
  (10, 'NOTA AUTORIZADA DESTINATÁRIO');
  
---------------
-- CREATE TABLE
CREATE SEQUENCE FAT_SFAT_NOTA NOCACHE;
CREATE TABLE FAT_NOTA
(
  NCODI_NOTA NUMBER NOT NULL,
  NCODIEMPRE NUMBER NOT NULL,
  NCODIDEPAR NUMBER NOT NULL,
  NCODITPDES NUMBER NOT NULL,
  NCODIEVENT NUMBER NOT NULL,
  NCODIPERIO NUMBER NOT NULL,
  NCODIFUNCI NUMBER NOT NULL,
  NCODIUSUAR NUMBER NOT NULL,
  DDATA_NOTA DATE DEFAULT SYSDATE NOT NULL,
  CNPAR_NOTA VARCHAR2(15) DEFAULT 'PARCELA 1/1' NOT NULL,
  NVALO_NOTA NUMBER(19,2) DEFAULT 1 NOT NULL,
  NQFDS_NOTA NUMBER DEFAULT 0 NOT NULL,
  NCODICODRN NUMBER DEFAULT 1 NOT NULL,
  DDTAU_NOTA DATE DEFAULT SYSDATE NOT NULL
);
-- ADD COMMENTS TO THE TABLE 
COMMENT ON TABLE FAT_NOTA
  IS 'Tabela de Faturamento(Notas)';
-- ADD COMMENTS TO THE COLUMNS 
COMMENT ON COLUMN FAT_NOTA.NCODI_NOTA
  IS 'código da nota';
COMMENT ON COLUMN FAT_NOTA.NCODIEMPRE
  IS 'código da empresa';
COMMENT ON COLUMN FAT_NOTA.NCODIDEPAR
  IS 'código do departamento';
COMMENT ON COLUMN FAT_NOTA.NCODITPDES
  IS 'código do tipo de desconto';
COMMENT ON COLUMN FAT_NOTA.NCODIEVENT
  IS 'código do evento';
COMMENT ON COLUMN FAT_NOTA.NCODIPERIO
  IS 'código do período';
COMMENT ON COLUMN FAT_NOTA.NCODIFUNCI
  IS 'código do funcionário';
COMMENT ON COLUMN FAT_NOTA.NCODIUSUAR
  IS 'código do usuário de emissão';
COMMENT ON COLUMN FAT_NOTA.DDATA_NOTA
  IS 'data da nota';
COMMENT ON COLUMN FAT_NOTA.CNPAR_NOTA
  IS 'número da parcela';
COMMENT ON COLUMN FAT_NOTA.NVALO_NOTA
  IS 'valor da nota';
COMMENT ON COLUMN FAT_NOTA.NQFDS_NOTA
  IS 'quantidade de falha ao digitar senha';
COMMENT ON COLUMN FAT_NOTA.NCODICODRN
  IS 'código de retorno da nota';
COMMENT ON COLUMN FAT_NOTA.DDTAU_NOTA
  IS 'data e hora da autenticação';
-- CREATE/RECREATE INDEXES 
CREATE INDEX FK_FAT_NOTA_SEGDEPAR ON FAT_NOTA (NCODIDEPAR);
CREATE INDEX FK_FAT_NOTA_CADEMPRE ON FAT_NOTA (NCODIEMPRE);
CREATE INDEX FK_FAT_NOTA_CADEVENT ON FAT_NOTA (NCODIEVENT);
CREATE INDEX FK_FAT_NOTA_CADFUNCI ON FAT_NOTA (NCODIFUNCI);
CREATE INDEX FK_FAT_NOTA_CADPERIO ON FAT_NOTA (NCODIPERIO);
CREATE INDEX FK_FAT_NOTA_CADTPDES ON FAT_NOTA (NCODITPDES);
CREATE INDEX FK_FAT_NOTA_SEGUSUAR ON FAT_NOTA (NCODIUSUAR);
CREATE INDEX FK_FAT_NOTA_FATCODRN ON FAT_NOTA (NCODICODRN);
-- CREATE/RECREATE PRIMARY, UNIQUE AND FOREIGN KEY CONSTRAINTS 
ALTER TABLE FAT_NOTA
  ADD CONSTRAINT PK_FAT_NOTA PRIMARY KEY (NCODI_NOTA)
  USING INDEX;
ALTER TABLE FAT_NOTA
  ADD CONSTRAINT FK_FAT_NOTA_SEGDEPAR FOREIGN KEY (NCODIDEPAR)
  REFERENCES SEGDEPAR (NCODIDEPAR);
ALTER TABLE FAT_NOTA
  ADD CONSTRAINT FK_FAT_NOTA_CADEMPRE FOREIGN KEY (NCODIEMPRE)
  REFERENCES CADEMPRE (NCODIEMPRE);
ALTER TABLE FAT_NOTA
  ADD CONSTRAINT FK_FAT_NOTA_CADEVENT FOREIGN KEY (NCODIEVENT)
  REFERENCES CADEVENT (NCODIEVENT);
ALTER TABLE FAT_NOTA
  ADD CONSTRAINT FK_FAT_NOTA_CADFUNCI FOREIGN KEY (NCODIFUNCI)
  REFERENCES CADFUNCI (NCODIFUNCI);
ALTER TABLE FAT_NOTA
  ADD CONSTRAINT FK_FAT_NOTA_CADPERIO FOREIGN KEY (NCODIPERIO)
  REFERENCES CADPERIO (NCODIPERIO);
ALTER TABLE FAT_NOTA
  ADD CONSTRAINT FK_FAT_NOTA_CADTPDES FOREIGN KEY (NCODITPDES)
  REFERENCES CADTPDES (NCODITPDES);
ALTER TABLE FAT_NOTA
  ADD CONSTRAINT FK_FAT_NOTA_SEGUSUAR FOREIGN KEY (NCODIUSUAR)
  REFERENCES SEGUSUAR (NCODIUSUAR);
ALTER TABLE FAT_NOTA
  ADD CONSTRAINT FK_FAT_NOTA_FATCODRN FOREIGN KEY (NCODICODRN)
  REFERENCES FATCODRN (NCODICODRN);