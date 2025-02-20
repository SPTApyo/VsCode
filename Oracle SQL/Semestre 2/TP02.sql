--1
(SELECT cio
FROM Athlete A, Pratiquer P, Discipline D, Sport S
WHERE A.nda = P.nda
AND P.ndd = D.ndd
AND D.cds = S.cds
AND noms = 'Athletics')
UNION
(SELECT cio
FROM Athlete A, Pratiquer P, Discipline D, Sport S
WHERE A.nda = P.nda
AND P.ndd = D.ndd
AND D.cds = S.cds
AND noms = 'Swimming');

--2
(SELECT cio
FROM Athlete A, Pratiquer P, Discipline D, Sport S
WHERE A.nda = P.nda
AND P.ndd = D.ndd
AND D.cds = S.cds
AND noms = 'Athletics')
INTERSECT
(SELECT cio
FROM Athlete A, Pratiquer P, Discipline D, Sport S
WHERE A.nda = P.nda
AND P.ndd = D.ndd
AND D.cds = S.cds
AND noms = 'Swimming');

--3
(SELECT cio
FROM Athlete A, Pratiquer P, Discipline D, Sport S
WHERE A.nda = P.nda
AND P.ndd = D.ndd
AND D.cds = S.cds
AND noms = 'Athletics')
MINUS
(SELECT cio
FROM Athlete A, Pratiquer P, Discipline D, Sport S
WHERE A.nda = P.nda
AND P.ndd = D.ndd
AND D.cds = S.cds
AND noms = 'Swimming');

--4
(SELECT cio
FROM Athlete A, Pratiquer P, Discipline D, Sport S
WHERE A.nda = P.nda
AND P.ndd = D.ndd
AND D.cds = S.cds
AND noms = 'Swimming')
MINUS
(SELECT cio
FROM Athlete A, Pratiquer P, Discipline D, Sport S
WHERE A.nda = P.nda
AND P.ndd = D.ndd
AND D.cds = S.cds
AND noms = 'Athletics');

--5
(SELECT noms
FROM Gagner_e GE, Discipline D, Sport S
WHERE GE.cio = 'FRA'
AND D.ndd = GE.ndd
AND S.cds = D.cds)
UNION
(SELECT noms
FROM Gagner_i GI, Athlete A, Pays P, Discipline D, Sport S
WHERE GI.nda = A.nda
AND A.cio = 'FRA'
AND GI.ndd = D.ndd
AND S.cds = D.cds);

--6
(SELECT noms
FROM Gagner_e GE, Discipline D, Sport S
WHERE GE.cio = 'FRA'
AND D.ndd = GE.ndd
AND S.cds = D.cds)
INTERSECT
(SELECT noms
FROM Gagner_i GI, Athlete A, Pays P, Discipline D, Sport S
WHERE GI.nda = A.nda
AND A.cio = 'FRA'
AND GI.ndd = D.ndd
AND S.cds = D.cds);

--7
(SELECT noms
FROM Gagner_i GI, Athlete A, Pays P, Discipline D, Sport S
WHERE GI.nda = A.nda
AND A.cio = 'FRA'
AND GI.ndd = D.ndd
AND S.cds = D.cds)
MINUS
(SELECT noms
FROM Gagner_e GE, Discipline D, Sport S
WHERE GE.cio = 'FRA'
AND D.ndd = GE.ndd
AND S.cds = D.cds);

--8
(SELECT cio
FROM Athlete A, Pratiquer P, Discipline D, Sport S
WHERE A.nda = P.nda
AND P.ndd = D.ndd
AND D.cds = S.cds
AND S.noms = 'Athletics')
MINUS
(SELECT cio
FROM Athlete A, Pratiquer P, Discipline D, Sport S
WHERE A.nda = P.nda
AND P.ndd = D.ndd
AND D.cds = S.cds
AND S.noms <> 'Athletics');

--9
(SELECT GE.cio
FROM Gagner_E GE, Discipline D, Sport S
WHERE GE.ndd = D.ndd
AND D.cds = S.cds
AND S.noms = 'Swimming')
INTERSECT
(SELECT GE.cio
FROM Gagner_E GE, Discipline D, Sport S
WHERE GE.ndd = D.ndd
AND D.cds = S.cds
AND S.noms = 'Athletics')
INTERSECT
(SELECT GE.cio
FROM Gagner_E GE, Discipline D, Sport S
WHERE GE.ndd = D.ndd
AND D.cds = S.cds
AND S.noms = 'Basketball');

--10
((SELECT GE.cio
FROM Gagner_E GE, Discipline D, Sport S
WHERE GE.ndd = D.ndd
AND D.cds = S.cds
AND S.noms = 'Swimming')
INTERSECT
(SELECT GE.cio
FROM Gagner_E GE, Discipline D, Sport S
WHERE GE.ndd = D.ndd
AND D.cds = S.cds
AND S.noms = 'Athletics'))
MINUS
(SELECT GE.cio
FROM Gagner_E GE, Discipline D, Sport S
WHERE GE.ndd = D.ndd
AND D.cds = S.cds
AND S.noms = 'Basketball');

--11
(SELECT S.noms, P.nomp
FROM Gagner_e GE, Pays P, Discipline D, Sport S
WHERE GE.medaille = 'G'
AND GE.cio = P.cio
AND GE.ndd = D.ndd
AND D.cds = S.cds)
INTERSECT
(SELECT S.noms, P.nomp
FROM Gagner_i GI, Athlete A, Pays P, Discipline D, Sport S
WHERE GI.medaille = 'G'
AND GI.nda = A.nda
AND A.cio = P.cio
AND GI.ndd = D.ndd
AND D.cds = S.cds);

--12
(SELECT P.nomp
FROM Gagner_e GE, Pays P
WHERE GE.medaille = 'G'
AND GE.cio = P.cio)
MINUS
(SELECT P.nomp
FROM Gagner_i GI, Athlete A, Pays P
WHERE GI.medaille = 'G'
AND GI.nda = A.nda
AND A.cio = P.cio);

--13
((SELECT P.nomp
FROM Gagner_E GE, Discipline D, Sport S, Pays P
WHERE GE.ndd = D.ndd
AND D.cds = S.cds
AND S.noms = 'Swimming'
AND GE.cio = P.cio
AND GE.medaille = 'G')
UNION
(SELECT P.nomp
FROM Gagner_i GI, Discipline D, Sport S, Pays P, Athlete A
WHERE GI.ndd = D.ndd
AND D.cds = S.cds
AND S.noms = 'Swimming'
AND GI.nda = A.nda
AND A.cio = P.cio
AND GI.medaille = 'G'))
MINUS
((SELECT P.nomp
FROM Gagner_E GE, Discipline D, Sport S, Pays P
WHERE GE.ndd = D.ndd
AND D.cds = S.cds
AND S.noms <> 'Swimming'
AND GE.cio = P.cio
AND GE.medaille = 'G')
UNION
(SELECT P.nomp
FROM Gagner_i GI, Discipline D, Sport S, Pays P, Athlete A
WHERE GI.ndd = D.ndd
AND D.cds = S.cds
AND S.noms <> 'Swimming'
AND GI.nda = A.nda
AND A.cio = P.cio
AND GI.medaille = 'G'));