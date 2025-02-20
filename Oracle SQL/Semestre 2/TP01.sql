//1
SELECT noms
FROM Sport;

//2
SELECT nomd
FROM Discipline
WHERE cds = 'ATH'
AND GENRED = 'W';

//3
SELECT ncomplet
FROM Athlete
WHERE cio = 'FRA';

//4
SELECT ncomplet
FROM Athlete
WHERE genre = 'F'
AND cio = 'FRA'
AND ((daten BETWEEN '01/01/1990' AND '31/12/1995')
OR (daten BETWEEN '01/01/2000' AND '31/12/2005'));

//5
SELECT ncomplet, genre, taille
FROM Athlete
WHERE (genre = 'M'
AND (taille < 155 OR taille > 210))
OR (genre = 'F'
AND taille > 200)
ORDER BY taille;

//6
SELECT D.nomd
FROM Sport S, Discipline D
WHERE S.noms = 'Judo'
AND D.cds = S.cds;

//7
SELECT D.nomd, A.ncomplet
FROM Athlete A, Gagner_i G, Discipline D
WHERE A.cio = 'FRA'
AND G.medaille = 'G'
AND A.nda =  G.nda
AND D.ndd = G.ndd;

//8
SELECT DISTINCT s.noms 
FROM discipline d1, discipline d2, sport s, 
pratiquer p1,pratiquer p2, athlete a1, athlete a2
WHERE a1.ncomplet = 'MARCHAND Leon'
AND a2.ncomplet = 'MANAUDOU Florent'
AND a1.nda = p1.nda
AND a2.nda = p2.nda
AND p2.ndd = d2.ndd
AND p1.ndd = d1.ndd
AND d1.cds = s.cds
AND d2.cds = s.cds;

//9
SELECT A2.ncomplet
FROM athlete A1, athlete A2
WHERE A1.ncomplet = 'MARCHAND Leon'
AND A1.daten = A2.daten
AND A2.ncomplet <> 'MARCHAND Leon';

//10
SELECT A2.ncomplet, A2.daten
FROM athlete A1, athlete A2
WHERE A1.ncomplet = 'MARCHAND Leon'
AND A1.daten < A2.daten
AND A2.cio = 'FRA'
ORDER BY A2.daten DESC;

//11
--Classique
SELECT distinct P.nomp 
FROM pays P, athlete A
WHERE A.cio(+) = P.cio 
AND A.nda IS NULL;

--ANSI
SELECT DISTINCT P.nomp  
FROM pays P
LEFT OUTER JOIN athlete A ON A.cio = P.cio
WHERE A.nda IS NULL;

//12
SELECT cds, COUNT(ndd)
FROM Discipline
GROUP BY cds;

//13
SELECT A.genre, COUNT(A.nda)
FROM Athlete A, Gagner_i G
WHERE G.medaille = 'G'
AND A.cio = 'FRA'
AND A.nda = G.nda
GROUP BY  A.genre;

//14
SELECT COUNT(ndd)
FROM Discipline D, Sport S
WHERE D.typed = 'P'
AND D.cds = S.cds
AND S.noms = 'Athletics';

//15
SELECT P.nomp 
FROM pays P, gagner_e G1,  gagner_e G2,  gagner_e G3
WHERE P.cio = G1.cio
AND P.cio = G2.cio
AND P.cio = G3.cio
AND G1.medaille = 'G'
AND G2.medaille = 'S'
AND G3.medaille = 'B'
GROUP BY P.nomp
HAVING count(G1.medaille) >0
AND count(G2.medaille) >0
AND count(G3.medaille) >0;