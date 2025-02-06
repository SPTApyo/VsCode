SELECT DISTINCT D.ndd, D.nomd
FROM DISCIPLINE D, SPORT S
WHERE D.cds = S.cds
  AND S.noms = 'Swimming';


SELECT DISTINCT D.ndd, D.nomd
FROM DISCIPLINE D
INNER JOIN SPORT S ON D.cds = S.cds
WHERE S.noms = 'Swimming';


SELECT DISTINCT A.nda, A.ncomplet
FROM ATHLETE A, GAGNER_I G
WHERE A.nda = G.nda
  AND G.medaille = 'G'
  AND A.cio = 'FRA';


SELECT DISTINCT A.nda, A.ncomplet
FROM ATHLETE A
INNER JOIN GAGNER_I G ON A.nda = G.nda
WHERE G.medaille = 'G'
  AND A.cio = 'FRA';


SELECT DISTINCT A.nda, A.ncomplet
FROM ATHLETE A, PRATIQUER P, DISCIPLINE D, SPORT S
WHERE A.nda = P.nda
  AND P.ndd = D.ndd
  AND D.cds = S.cds
  AND S.noms = 'Swimming'
  AND A.cio = 'FRA';


SELECT DISTINCT A.nda, A.ncomplet
FROM ATHLETE A
INNER JOIN PRATIQUER P ON A.nda = P.nda
INNER JOIN DISCIPLINE D ON P.ndd = D.ndd
INNER JOIN SPORT S ON D.cds = S.cds
WHERE S.noms = 'Swimming'
  AND A.cio = 'FRA';


SELECT DISTINCT A.nda, A.ncomplet, G.medaille
FROM ATHLETE A, PRATIQUER P, DISCIPLINE D, SPORT S, GAGNER_I G
WHERE A.nda = P.nda
  AND P.ndd = D.ndd
  AND D.cds = S.cds
  AND A.nda = G.nda
  AND S.noms = 'Swimming'
  AND A.cio = 'FRA';


SELECT DISTINCT A.nda, A.ncomplet, G.medaille
FROM ATHLETE A
INNER JOIN PRATIQUER P ON A.nda = P.nda
INNER JOIN DISCIPLINE D ON P.ndd = D.ndd
INNER JOIN SPORT S ON D.cds = S.cds
INNER JOIN GAGNER_I G ON A.nda = G.nda
WHERE S.noms = 'Swimming'
  AND A.cio = 'FRA';


SELECT DISTINCT S.noms, D.nomd
FROM DISCIPLINE D, SPORT S, GAGNER_E G
WHERE D.cds = S.cds
  AND G.ndd = D.ndd
  AND G.cio = 'FRA'
ORDER BY S.noms;


SELECT DISTINCT S.noms, D.nomd
FROM SPORT S
INNER JOIN DISCIPLINE D ON D.cds = S.cds
INNER JOIN GAGNER_E G ON G.ndd = D.ndd
WHERE G.cio = 'FRA'
ORDER BY S.noms;


SELECT DISTINCT S.noms
FROM SPORT S, DISCIPLINE D, PRATIQUER P, ATHLETE A
WHERE A.nda = P.nda
  AND P.ndd = D.ndd
  AND D.cds = S.cds
  AND A.ncomplet = 'RINER Teddy';


SELECT DISTINCT S.noms
FROM ATHLETE A
INNER JOIN PRATIQUER P ON A.nda = P.nda
INNER JOIN DISCIPLINE D ON P.ndd = D.ndd
INNER JOIN SPORT S ON D.cds = S.cds
WHERE A.ncomplet = 'RINER Teddy';


SELECT DISTINCT S.noms
FROM SPORT S, DISCIPLINE D, PRATIQUER P, ATHLETE A
WHERE A.nda = P.nda
  AND P.ndd = D.ndd
  AND D.cds = S.cds
  AND A.ncomplet IN ('MARCHAND Leon', 'MANAUDOU Florent');


SELECT DISTINCT S.noms
FROM ATHLETE A
INNER JOIN PRATIQUER P ON A.nda = P.nda
INNER JOIN DISCIPLINE D ON P.ndd = D.ndd
INNER JOIN SPORT S ON D.cds = S.cds
WHERE A.ncomplet IN ('MARCHAND Leon', 'MANAUDOU Florent');


SELECT DISTINCT A.cio, A.ncomplet
FROM ATHLETE A, PAYS P
WHERE A.prenom = P.nomp;


SELECT DISTINCT A.cio, A.ncomplet
FROM ATHLETE A
INNER JOIN PAYS P ON A.prenom = P.nomp;


SELECT DISTINCT D.nomd, A.ncomplet, A.genre
FROM DISCIPLINE D, PRATIQUER P, ATHLETE A
WHERE A.nda = P.nda
  AND P.ndd = D.ndd
  AND ((A.genre = 'M' AND D.genred = 'W')
       OR (A.genre = 'F' AND D.genred = 'M'));


SELECT DISTINCT D.nomd, A.ncomplet, A.genre
FROM ATHLETE A
INNER JOIN PRATIQUER P ON A.nda = P.nda
INNER JOIN DISCIPLINE D ON P.ndd = D.ndd
WHERE (A.genre = 'M' AND D.genred = 'W')
   OR (A.genre = 'F' AND D.genred = 'M');
