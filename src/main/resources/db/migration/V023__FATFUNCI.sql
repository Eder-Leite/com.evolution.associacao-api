CREATE SEQUENCE FAT_SFATFUNCI NOCACHE;
-- CREATE TABLE
CREATE TABLE FATFUNCI
(
  NCODIFUNCI NUMBER NOT NULL,
  NCODIFUNTR NUMBER NOT NULL,
  CNOMEFUNCI VARCHAR2(255) NOT NULL,
  CSTATFUNCI VARCHAR2(100) DEFAULT 'ATIVO' NOT NULL
);
-- ADD COMMENTS TO THE TABLE 
COMMENT ON TABLE FATFUNCI
  IS 'Funcionário para Itens de Serviço';
-- ADD COMMENTS TO THE COLUMNS 
COMMENT ON COLUMN FATFUNCI.NCODIFUNCI
  IS 'código do funcionário';
COMMENT ON COLUMN FATFUNCI.NCODIFUNTR
  IS 'código da função de trabalho';
COMMENT ON COLUMN FATFUNCI.CNOMEFUNCI
  IS 'nome do funcionário';
COMMENT ON COLUMN FATFUNCI.CSTATFUNCI
  IS 'status do funcionário';
-- CREATE/RECREATE PRIMARY, UNIQUE AND FOREIGN KEY CONSTRAINTS 
ALTER TABLE FATFUNCI
  ADD CONSTRAINT PK_FATFUNCI PRIMARY KEY (NCODIFUNCI)
  USING INDEX;
ALTER TABLE FATFUNCI
  ADD CONSTRAINT UK_FATFUNCI UNIQUE (CNOMEFUNCI)
  USING INDEX;
ALTER TABLE FATFUNCI
  ADD CONSTRAINT FK_FATFUNCI_CADFUNTR FOREIGN KEY (NCODIFUNTR)
  REFERENCES CADFUNTR (NCODIFUNTR);
  
INSERT INTO FATFUNCI (NCODIFUNCI, NCODIFUNTR, CNOMEFUNCI, CSTATFUNCI)
VALUES (1, 1, 'JAIR DA SILVA', 'ATIVO');

INSERT INTO FATFUNCI (NCODIFUNCI, NCODIFUNTR, CNOMEFUNCI, CSTATFUNCI)
VALUES (2, 1, 'FABIO RODRIGUES DOS ANJOS', 'ATIVO');

INSERT INTO FATFUNCI (NCODIFUNCI, NCODIFUNTR, CNOMEFUNCI, CSTATFUNCI)
VALUES (3, 2, 'DEVANIR DEZEMBRO', 'ATIVO');

INSERT INTO FATFUNCI (NCODIFUNCI, NCODIFUNTR, CNOMEFUNCI, CSTATFUNCI)
VALUES (4, 2, 'ROBERTO CARLOS POI', 'ATIVO');

INSERT INTO FATFUNCI (NCODIFUNCI, NCODIFUNTR, CNOMEFUNCI, CSTATFUNCI)
VALUES (5, 3, 'ADMINISTRATIVO', 'ATIVO');  