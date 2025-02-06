SELECT cio, ncomplet
FROM PAYS;


SELECT DISTINCT p.ncomplet, a.cio
FROM PAYS p, ATHLETE a
WHERE a.cio = p.cio;


SELECT DISTINCT p.cio, p.ncomplet
FROM PAYS p,  ATHLETE a
WHERE p.cio = a.cio (+);


SELECT DISTINCT p.cio, p.ncomplet
FROM PAYS p
LEFT OUTER JOIN ATHLETE a ON p.cio = a.cio;


SELECT p.cio, p.ncomplet
FROM PAYS p, ATHLETE a
WHERE p.cio  = a.cio (+) AND a.cio IS NULL;


SELECT p.cio, p.ncomplet
FROM PAYS p
LEFT OUTER JOIN ATHLETE a ON p.cio = a.cio
WHERE a.cio IS NULL;


SELECT p.cio, p.ncomplet
FROM PAYS p, GAGNER_E g
WHERE p.cio = g.cio(+)
  AND g.cio IS NULL;


SELECT p.cio, p.ncomplet
FROM PAYS p
LEFT OUTER JOIN GAGNER_E g ON p.cio = g.cio
WHERE g.cio IS NULL;


SELECT DISTINCT p.cio, p.ncomplet
FROM PAYS p, ATHLETE a, GAGNER_I g
WHERE p.cio = a.cio
  AND a.nda = g.nda(+)
  AND g.nda IS NULL;


SELECT DISTINCT p.cio, p.ncomplet
FROM PAYS p
JOIN ATHLETE a ON p.cio = a.cio
LEFT OUTER JOIN GAGNER_I g ON a.nda = g.nda
WHERE g.nda IS NULL;


SELECT a.ncomplet
FROM ATHLETE a, GAGNER_I g
WHERE a.nda = g.nda(+)
  AND g.nda IS NULL
  AND a.cio = 'FRA';


SELECT a.ncomplet
FROM ATHLETE a
LEFT OUTER JOIN GAGNER_I g ON a.nda = g.nda
WHERE g.nda IS NULL
  AND a.cio = 'FRA';


SELECT DISTINCT a.ncomplet
FROM ATHLETE a, PRATIQUER p, DISCIPLINE d, SPORT s, GAGNER_I g
WHERE a.cio = 'USA'
  AND a.nda = p.nda
  AND p.ndd = d.ndd
  AND d.cds = s.cds
  AND s.noms = 'Athletics'
  AND a.nda = g.nda(+)
  AND g.nda IS NULL;


SELECT DISTINCT a.ncomplet
FROM ATHLETE a
JOIN PRATIQUER p ON a.nda = p.nda
JOIN DISCIPLINE d ON p.ndd = d.ndd
JOIN SPORT s ON d.cds = s.cds
LEFT OUTER JOIN GAGNER_I g ON a.nda = g.nda
WHERE a.cio = 'USA'
  AND s.noms = 'Athletics'
  AND g.nda IS NULL;


SELECT DISTINCT a.ncomplet, g.medaille
FROM ATHLETE a, DISCIPLINE d, PRATIQUER p, GAGNER_I g
WHERE a.cio = 'FRA'
AND d.cds = 'SWM'
AND p.ndd = d.ndd
AND a.nda = p.nda
and g.nda (+) = a.nda
ORDER BY a.ncomplet;


SELECT DISTINCT a.ncomplet,
CASE
 WHEN g.medaille = 'G' THEN 'Gold'
 WHEN g.medaille = 'S' THEN 'Silver'
 WHEN g.medaille = 'B' THEN 'Bronze'
 ELSE 'aucune'
END as casewhen
FROM ATHLETE a, DISCIPLINE d, PRATIQUER p, GAGNER_I g
WHERE a.cio = 'FRA'
AND d.cds = 'SWM'
AND p.ndd = d.ndd
AND a.nda = p.nda
and g.nda (+) = a.nda
ORDER BY a.ncomplet;