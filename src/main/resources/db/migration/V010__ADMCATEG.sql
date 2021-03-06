CREATE SEQUENCE ADM_SADMCATEG NOCACHE;
-- CREATE TABLE
CREATE TABLE ADMCATEG
(
  NCODICATEG NUMBER NOT NULL,
  CDESCCATEG VARCHAR2(255) NOT NULL,
  CSTATCATEG VARCHAR2(100) DEFAULT 'ATIVO' NOT NULL
);
-- ADD COMMENTS TO THE TABLE 
COMMENT ON TABLE ADMCATEG
  IS 'Categoria de Produto';
-- ADD COMMENTS TO THE COLUMNS 
COMMENT ON COLUMN ADMCATEG.NCODICATEG
  IS 'código da categoria';
COMMENT ON COLUMN ADMCATEG.CDESCCATEG
  IS 'descrição da categoria';
COMMENT ON COLUMN ADMCATEG.CSTATCATEG
  IS 'status da categoria';
-- CREATE/RECREATE PRIMARY, UNIQUE AND FOREIGN KEY CONSTRAINTS 
ALTER TABLE ADMCATEG
  ADD CONSTRAINT PK_ADMCATEG PRIMARY KEY (NCODICATEG)
  USING INDEX;
  
INSERT INTO ADMCATEG (NCODICATEG, CDESCCATEG, CSTATCATEG)
VALUES (1100, 'COMBUSTÍVEIS', 'ATIVO');

INSERT INTO ADMCATEG (NCODICATEG, CDESCCATEG, CSTATCATEG)
VALUES (1200, 'PEÇAS E ÓLEOS LUBRIFICANTES', 'ATIVO');

INSERT INTO ADMCATEG (NCODICATEG, CDESCCATEG, CSTATCATEG)
VALUES (1300, 'LOJA DE CONVENIĘNCIA', 'ATIVO');

INSERT INTO ADMCATEG (NCODICATEG, CDESCCATEG, CSTATCATEG)
VALUES (1400, 'PRESTAÇĂO DE SERVIÇO', 'ATIVO');
  