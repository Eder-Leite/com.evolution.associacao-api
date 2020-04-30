CREATE OR REPLACE PROCEDURE FAT_PINSERE_NOTA_LANCHONETE(P_EMPRESA     NUMBER,
                                                        P_FUNCIONARIO NUMBER,
                                                        P_USUARIO     NUMBER,
                                                        P_VALOR       NUMBER,
                                                        P_NOTA        OUT NUMBER) AS

  V_PERIODO       NUMBER;
  V_DEPARTAMENTO  NUMBER;
  V_EVENTO        NUMBER;
  V_TIPO_DESCONTO NUMBER;

BEGIN

  --VALIDA SE JÁ EXISTEM A MESMA NOTA EM UM INTERVALO DE 3 MINUTOS
  FOR VALIDA_REQUISICAO IN (SELECT *
                              FROM FAT_NOTA A
                             WHERE A.NCODIEMPRE = P_EMPRESA
                               AND A.NCODIFUNCI = P_FUNCIONARIO
                               AND A.NCODIUSUAR = P_USUARIO
                               AND A.NVALO_NOTA = P_VALOR
                               AND A.DDATA_NOTA BETWEEN SYSDATE - 0.002 AND
                                   SYSDATE) LOOP
  
    RAISE_APPLICATION_ERROR(-20000,
                            'Atenção essa nota já está inserida, aguarde a autenticação da mesma!#');
  
  END LOOP;

  FOR EVENTO_TIPO_DESCONTO IN (SELECT *
                                 FROM FAT_VEVENTO_LANCHONETE_EMPRESA A
                                WHERE A.NCODIEMPRE = P_EMPRESA) LOOP
  
    V_EVENTO        := EVENTO_TIPO_DESCONTO.NCODIEVENT;
    V_TIPO_DESCONTO := EVENTO_TIPO_DESCONTO.NCODITPDES;
  
  END LOOP;

  IF (V_EVENTO IS NULL) THEN
    RAISE_APPLICATION_ERROR(-20000,
                            'Atenção não existe evento cadastrdo para essa emissão!#');
  END IF;

  IF (V_TIPO_DESCONTO IS NULL) THEN
    RAISE_APPLICATION_ERROR(-20000,
                            'Atenção não existe tipo de desconto cadastrdo para essa emissão!#');
  END IF;

  FOR PERIODO IN (SELECT *
                    FROM CADPERIO A
                   WHERE A.DDINIPERIO <= TRUNC(SYSDATE)
                     AND A.DDFIMPERIO >= TRUNC(SYSDATE)) LOOP
  
    V_PERIODO := PERIODO.NCODIPERIO;
  
  END LOOP;

  IF (V_PERIODO IS NULL) THEN
    RAISE_APPLICATION_ERROR(-20000,
                            'Atenção não existe período cadastrado para essa nota!#');
  END IF;

  FOR DPTO IN (SELECT U.NCODIDEPAR
                 FROM SEGUSUAR U
                WHERE U.NCODIUSUAR = P_USUARIO) LOOP
  
    V_DEPARTAMENTO := DPTO.NCODIDEPAR;
  
  END LOOP;

  P_NOTA := FAT_SFAT_NOTA.NEXTVAL;

  INSERT INTO FAT_NOTA
    (NCODI_NOTA,
     NCODIEMPRE,
     NCODIDEPAR,
     NCODITPDES,
     NCODIEVENT,
     NCODIPERIO,
     NCODIFUNCI,
     NCODIUSUAR,
     DDATA_NOTA,
     CNPAR_NOTA,
     NVALO_NOTA,
     NQFDS_NOTA,
     NCODICODRN,
     DDTAU_NOTA)
  VALUES
    (P_NOTA,
     P_EMPRESA,
     V_DEPARTAMENTO,
     V_TIPO_DESCONTO,
     V_EVENTO,
     V_PERIODO,
     P_FUNCIONARIO,
     P_USUARIO,
     SYSDATE,
     'PARCELA (1/1)',
     P_VALOR,
     0,
     1,
     SYSDATE);

END FAT_PINSERE_NOTA_LANCHONETE;
/

CREATE OR REPLACE PROCEDURE FAT_PINSERE_NOTA_DEPARTAMENTO(P_EMPRESA       NUMBER,
                                                          P_EVENTO        NUMBER,
                                                          P_TIPO_DESCONTO NUMBER,
                                                          P_PERIODO       NUMBER,
                                                          P_PARCELA       NUMBER,
                                                          P_FUNCIONARIO   NUMBER,
                                                          P_USUARIO       NUMBER,
                                                          P_VALOR         NUMBER,
                                                          P_MENSAGEM      OUT VARCHAR2) AS

  V_VALOR        NUMBER := 0;
  V_TOTAL        NUMBER := 0;
  V_PERIODO      NUMBER;
  V_DATA_PERIODO DATE;
  V_DEPARTAMENTO NUMBER;

BEGIN

  FOR PERIODO IN (SELECT *
                    FROM (SELECT A.NCODIPERIO,
                                 A.NMES_PERIO,
                                 A.NANO_PERIO,
                                 TO_DATE('1/' || A.NMES_PERIO || '/' ||
                                         A.NANO_PERIO,
                                         'DD/MM/YYYY') PERIODO
                            FROM CADPERIO A)
                   WHERE NCODIPERIO = P_PERIODO) LOOP
  
    V_DATA_PERIODO := PERIODO.PERIODO;
  
  END LOOP;

  IF (V_DATA_PERIODO IS NULL) THEN
    RAISE_APPLICATION_ERROR(-20000,
                            'Atenção não existe período cadastrado para essa nota!#');
  END IF;

  FOR DPTO IN (SELECT U.NCODIDEPAR
                 FROM SEGUSUAR U
                WHERE U.NCODIUSUAR = P_USUARIO) LOOP
  
    V_DEPARTAMENTO := DPTO.NCODIDEPAR;
  
  END LOOP;

  IF (P_PARCELA = 1) THEN
  
    INSERT INTO FAT_NOTA
      (NCODI_NOTA,
       NCODIEMPRE,
       NCODIDEPAR,
       NCODITPDES,
       NCODIEVENT,
       NCODIPERIO,
       NCODIFUNCI,
       NCODIUSUAR,
       DDATA_NOTA,
       CNPAR_NOTA,
       NVALO_NOTA,
       NQFDS_NOTA,
       NCODICODRN,
       DDTAU_NOTA)
    VALUES
      (FAT_SFAT_NOTA.NEXTVAL,
       P_EMPRESA,
       V_DEPARTAMENTO,
       P_TIPO_DESCONTO,
       P_EVENTO,
       P_PERIODO,
       P_FUNCIONARIO,
       P_USUARIO,
       SYSDATE,
       'PARCELA (1/1)',
       P_VALOR,
       0,
       1,
       SYSDATE);
  
    P_MENSAGEM := 'Nota inserida com sucesso, aguarde a autenticação da mesma!';
  
  ELSE
  
    V_TOTAL := P_VALOR;
    V_VALOR := P_VALOR / P_PARCELA;
    V_VALOR := ROUND(V_VALOR, 2);
  
    FOR PARCELA IN 1 .. P_PARCELA LOOP
    
      FOR PERIODO IN (SELECT *
                        FROM (SELECT A.NCODIPERIO,
                                     A.NMES_PERIO,
                                     A.NANO_PERIO,
                                     TO_DATE('1/' || A.NMES_PERIO || '/' ||
                                             A.NANO_PERIO,
                                             'DD/MM/YYYY') PERIODO
                                FROM CADPERIO A)
                       WHERE PERIODO = V_DATA_PERIODO
                         AND ROWNUM = 1) LOOP
      
        V_PERIODO := PERIODO.NCODIPERIO;
      
      END LOOP;
    
      IF (V_PERIODO IS NULL) THEN
        RAISE_APPLICATION_ERROR(-20000,
                                'Atenção não existe período cadastrado para essa parcela da nota!#');
      END IF;
    
      IF (PARCELA = P_PARCELA) THEN
        V_VALOR := V_TOTAL;
      END IF;
    
      INSERT INTO FAT_NOTA
        (NCODI_NOTA,
         NCODIEMPRE,
         NCODIDEPAR,
         NCODITPDES,
         NCODIEVENT,
         NCODIPERIO,
         NCODIFUNCI,
         NCODIUSUAR,
         DDATA_NOTA,
         CNPAR_NOTA,
         NVALO_NOTA,
         NQFDS_NOTA,
         NCODICODRN,
         DDTAU_NOTA)
      VALUES
        (FAT_SFAT_NOTA.NEXTVAL,
         P_EMPRESA,
         V_DEPARTAMENTO,
         P_TIPO_DESCONTO,
         P_EVENTO,
         V_PERIODO,
         P_FUNCIONARIO,
         P_USUARIO,
         SYSDATE,
         'PARCELA (' || PARCELA || '/' || P_PARCELA ||')',
         V_VALOR,
         0,
         1,
         SYSDATE);
    
      V_PERIODO      := NULL;
      V_TOTAL        := V_TOTAL - V_VALOR;
      V_DATA_PERIODO := ADD_MONTHS(V_DATA_PERIODO, 1);
    
    END LOOP;
  
    P_MENSAGEM := 'Nota inserida com sucesso, aguarde a autenticação da mesma!';
  
  END IF;

END FAT_PINSERE_NOTA_DEPARTAMENTO;
/

CREATE OR REPLACE PROCEDURE FAT_PAUTENTICA_NOTA(P_EMPRESA  NUMBER,
                                                P_NOTA     NUMBER,
                                                P_TIPO     VARCHAR2,
                                                P_MENSAGEM OUT VARCHAR2) AS
  V_SALDO          NUMBER(19, 2) := 0;
  V_CODIGO_RETORNO NUMBER;

  FUNCTION MOEDA(P_VALOR NUMBER := 0) RETURN VARCHAR2 AS
  BEGIN
    RETURN TO_CHAR(P_VALOR, 'FM999G999G999D90');
  END;

BEGIN

  P_MENSAGEM := 'Nenhum registro alterado!';

  FOR CODIGO IN (SELECT *
                   FROM FAT_NOTA N
                  WHERE N.NCODI_NOTA = P_NOTA
                    AND N.NCODIEMPRE = P_EMPRESA) LOOP
  
    IF (CODIGO.NCODICODRN IN (2, 10)) THEN
      RAISE_APPLICATION_ERROR(-20000,
                              'Atenção essa nota já está autorizada!#');
    END IF;
  
    IF (CODIGO.NCODICODRN IN (3, 4, 9)) THEN
      RAISE_APPLICATION_ERROR(-20000, 'Atenção essa nota está cancelada!#');
    END IF;
  
  END LOOP;

  FOR FOLHA IN (SELECT A.*
                  FROM RECFOLHA A, FAT_NOTA B
                 WHERE A.NCODIEMPRE = B.NCODIEMPRE
                   AND A.NCODIEVENT = B.NCODIEVENT
                   AND A.NCODIPERIO = B.NCODIPERIO
                   AND B.NCODI_NOTA = P_NOTA
                   AND B.NCODIEMPRE = P_EMPRESA) LOOP
  
    RAISE_APPLICATION_ERROR(-20000,
                            'Atenção para esse Evento/Período já existe processo de pagamento!#');
  
  END LOOP;

  FOR FUNC IN (SELECT F.*
                 FROM CADFUNCI F, FAT_NOTA N
                WHERE F.NCODIFUNCI = N.NCODIFUNCI
                  AND N.NCODI_NOTA = P_NOTA) LOOP
  
    IF (FUNC.CSITUFUNCI IN ('BLOQUEADO', 'INATIVO')) THEN
    
      UPDATE FAT_NOTA A
         SET A.NCODICODRN = 6, A.DDTAU_NOTA = SYSDATE
       WHERE A.NCODI_NOTA = P_NOTA;
    
      COMMIT;
    
      RAISE_APPLICATION_ERROR(-20000,
                              'Atenção funcionário bloqueado/inativo!#');
    
    END IF;
  
  END LOOP;

  -- U = USUARIO, F = FUNCIONARIO

  IF (UPPER(P_TIPO) = 'U') THEN
    V_CODIGO_RETORNO := 2;
  ELSE
    V_CODIGO_RETORNO := 10;
  END IF;

  UPDATE FAT_NOTA A
     SET A.NCODICODRN = V_CODIGO_RETORNO, A.DDTAU_NOTA = SYSDATE
   WHERE A.NCODI_NOTA = P_NOTA;

  P_MENSAGEM := 'Nota autorizada com sucesso!';

  FOR LANCHONETE IN (SELECT *
                       FROM FAT_VEVENTO_LANCHONETE_EMPRESA A
                      WHERE A.NCODIEMPRE = P_EMPRESA) LOOP
  
    FOR NOTA IN (SELECT X.NVALO_NOTA,
                        X.NCODIFUNCI,
                        X.NCODIEVENT,
                        X.NCODIPERIO
                   FROM FAT_NOTA X
                  WHERE X.NCODI_NOTA = P_NOTA
                    AND X.NCODIEVENT = LANCHONETE.NCODIEVENT
                    AND X.NCODIEMPRE = LANCHONETE.NCODIEMPRE) LOOP
    
      FOR TOTAL IN (SELECT *
                      FROM (SELECT B.NVLIMFUNCI LIMITE,
                                   SUM(A.NVALO_NOTA) VALOR
                              FROM FAT_NOTA A, CADFUNCI B
                             WHERE A.NCODIFUNCI = B.NCODIFUNCI
                               AND A.NCODIEVENT = NOTA.NCODIEVENT
                               AND A.NCODIPERIO = NOTA.NCODIPERIO
                               AND A.NCODIFUNCI = NOTA.NCODIFUNCI
                               AND A.NCODICODRN IN (2, 10)
                             GROUP BY B.NVLIMFUNCI)) LOOP
      
        IF (TOTAL.VALOR > TOTAL.LIMITE) THEN
        
          UPDATE FAT_NOTA A
             SET A.NCODICODRN = 5, A.DDTAU_NOTA = SYSDATE
           WHERE A.NCODI_NOTA = P_NOTA;
        
          COMMIT;
        
          V_SALDO := TOTAL.LIMITE + NOTA.NVALO_NOTA - TOTAL.VALOR;
        
          RAISE_APPLICATION_ERROR(-20000,
                                  'Nota rejeitada(saldo insuficiente) saldo R$ ' ||
                                  MOEDA(V_SALDO) || ', valor da nota R$ ' ||
                                  MOEDA(NOTA.NVALO_NOTA) || '!#');
        
        END IF;
      
      END LOOP;
    
    END LOOP;
  
  END LOOP;

END FAT_PAUTENTICA_NOTA;
/

CREATE OR REPLACE PROCEDURE FAT_PCANCELA_NOTA(P_EMPRESA  NUMBER,
                                              P_NOTA     NUMBER,
                                              P_TIPO     VARCHAR2,
                                              P_MENSAGEM OUT VARCHAR2) AS
  V_CODIGO_RETORNO NUMBER;

BEGIN

  P_MENSAGEM := 'Nenhum registro alterado!';

  FOR FOLHA IN (SELECT A.*
                  FROM RECFOLHA A, FAT_NOTA B
                 WHERE A.NCODIEMPRE = B.NCODIEMPRE
                   AND A.NCODIEVENT = B.NCODIEVENT
                   AND A.NCODIPERIO = B.NCODIPERIO
                   AND B.NCODIEMPRE = P_EMPRESA
                   AND B.NCODI_NOTA = P_NOTA) LOOP
  
    RAISE_APPLICATION_ERROR(-20000,
                            'Atenção para esse Evento/Período já existe processo de pagamento!#');
  
  END LOOP;

  FOR CANCELADA IN (SELECT *
                      FROM FAT_NOTA N
                     WHERE N.NCODI_NOTA = P_NOTA
                       AND N.NCODIEMPRE = P_EMPRESA
                       AND N.NCODICODRN IN (3, 4)) LOOP
  
    RAISE_APPLICATION_ERROR(-20000,
                            'Atenção essa nota já está cancelada!#');
  
  END LOOP;

  FOR FUNC IN (SELECT F.*
                 FROM CADFUNCI F, FAT_NOTA N
                WHERE F.NCODIFUNCI = N.NCODIFUNCI
                  AND N.NCODI_NOTA = P_NOTA
                  AND N.NCODIEMPRE = P_EMPRESA) LOOP
  
    IF (FUNC.CSITUFUNCI IN ('BLOQUEADO', 'INATIVO')) THEN
    
      RAISE_APPLICATION_ERROR(-20000,
                              'Atenção funcionário bloqueado/inativo!#');
    
    END IF;
  
  END LOOP;

  -- U = USUARIO, F = FUNCIONARIO

  IF (UPPER(P_TIPO) = 'U') THEN
    V_CODIGO_RETORNO := 3;
  ELSE
    V_CODIGO_RETORNO := 4;
  END IF;

  UPDATE FAT_NOTA A
     SET A.NCODICODRN = V_CODIGO_RETORNO, A.DDTAU_NOTA = SYSDATE
   WHERE A.NCODI_NOTA = P_NOTA;

  P_MENSAGEM := 'Nota cancelada com sucesso!';

END FAT_PCANCELA_NOTA;
/

CREATE OR REPLACE PROCEDURE FAT_PFALHA_AUTENTICACAO_NOTA(P_EMPRESA NUMBER,
                                                         P_NOTA    NUMBER) AS

BEGIN

  FOR NOTA IN (SELECT *
                 FROM FAT_NOTA A
                WHERE A.NCODI_NOTA = P_NOTA
                  AND A.NCODIEMPRE = P_EMPRESA
                  AND A.NCODICODRN NOT IN (2, 3, 4, 9, 10)) LOOP
  
    FOR FOLHA IN (SELECT *
                    FROM RECFOLHA A
                   WHERE A.NCODIEMPRE = NOTA.NCODIEMPRE
                     AND A.NCODIEVENT = NOTA.NCODIEVENT
                     AND A.NCODIPERIO = NOTA.NCODIPERIO) LOOP
    
      RAISE_APPLICATION_ERROR(-20000,
                              'Atenção para esse Evento/Período já existe processo de pagamento!#');
    
    END LOOP;
  
    IF (NOTA.NQFDS_NOTA = 0) THEN
    
      UPDATE FAT_NOTA A
         SET A.NQFDS_NOTA = 1, A.NCODICODRN = 7, A.DDTAU_NOTA = SYSDATE
       WHERE A.NCODI_NOTA = NOTA.NCODI_NOTA;
    
      COMMIT;
    
      RAISE_APPLICATION_ERROR(-20000,
                              'Nota rejeitada(autenticação inválida uma tentativa!#');
    
    ELSIF (NOTA.NQFDS_NOTA = 1) THEN
    
      UPDATE FAT_NOTA A
         SET A.NQFDS_NOTA = 2, A.NCODICODRN = 8, A.DDTAU_NOTA = SYSDATE
       WHERE A.NCODI_NOTA = NOTA.NCODI_NOTA;
    
      COMMIT;
    
      RAISE_APPLICATION_ERROR(-20000,
                              'Nota rejeitada(autenticação inválida segunda tentativa!#');
    
    ELSIF (NOTA.NQFDS_NOTA = 2) THEN
    
      UPDATE FAT_NOTA A
         SET A.NQFDS_NOTA = 3, A.NCODICODRN = 9, A.DDTAU_NOTA = SYSDATE
       WHERE A.NCODI_NOTA = NOTA.NCODI_NOTA;
    
      --FUNCIONÁRIO BLOQUEADO
      UPDATE CADFUNCI F
         SET F.CSITUFUNCI = 'BLOQUEADO'
       WHERE F.NCODIFUNCI = NOTA.NCODIFUNCI;
    
      COMMIT;
    
      RAISE_APPLICATION_ERROR(-20000,
                              'Nota cancelada por falta de autenticação!#');
    
    END IF;
  
  END LOOP;

  RAISE_APPLICATION_ERROR(-20000, 'Atenção nenhum registro alterado!#');

END FAT_PFALHA_AUTENTICACAO_NOTA;
/

CREATE OR REPLACE PROCEDURE REC_PPROCESSO_FOLHA(P_ACAO    VARCHAR,
                                                P_EMPRESA NUMBER,
                                                P_EVENTO  NUMBER,
                                                P_PERIODO NUMBER) AS
BEGIN

  -- P = PROCESSA, C = CANCELA

  IF (UPPER(P_ACAO) = 'P') THEN
  
    FOR TOTAL IN (SELECT SUM(A.NVALO_NOTA) VALOR
                    FROM FAT_NOTA A
                   WHERE A.NCODIEMPRE = P_EMPRESA
                     AND A.NCODIEVENT = P_EVENTO
                     AND A.NCODIPERIO = P_PERIODO
                     AND A.NCODICODRN IN (2, 10)) LOOP
    
      INSERT INTO RECFOLHA
        (NCODIFOLHA,
         NCODIEMPRE,
         NCODIEVENT,
         NCODIPERIO,
         NVALOFOLHA,
         DDATAFOLHA)
      VALUES
        (REC_SRECFOLHA.NEXTVAL,
         P_EMPRESA,
         P_EVENTO,
         P_PERIODO,
         TOTAL.VALOR,
         SYSDATE);
    
    END LOOP;
  
  ELSIF (UPPER(P_ACAO) = 'C') THEN
  
    DELETE FROM RECFOLHA A
     WHERE A.NCODIEMPRE = P_EMPRESA
       AND A.NCODIEVENT = P_EVENTO
       AND A.NCODIPERIO = P_PERIODO;
  
  END IF;

END REC_PPROCESSO_FOLHA;
/

