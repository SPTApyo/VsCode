Select Count(cio) as nbp
FROM pays;


Select count(distinct cio) as nbp
from athlete;


select genred, COUNT(ndd) as nbd
from discipline
WHERE GENRED IS not null
GROUP by genred;


Select count(cds) as NBD
From Discipline
where cds = 'ATH' AND typed = 'P';


Select d.ndd , d.nomd, count(p.nda) as NBA
From Discipline d, pratiquer p
where d.cds = 'ATH'
AND p.ndd = d.ndd
Group by d.ndd, d.nomd
Order by NBA DESC;


Select
CASE A.Genre
 WHEN 'M' THEN 'Homme'
 ELSE 'Femme'
END as Genre , round(avg(A.taille),6) as taille
FROM DISCIPLINE D, PRATIQUER P, ATHLETE A
where D.cds = 'BKB'
AND P.ndd = D.ndd
And A.nda = P.nda
GROUP BY A.GENRE;


Select S.cds, S.noms, round(avg(A.taille),6) as TMOY, max(A.taille) as TMAX, min(A.taille) as TMIN
FROM DISCIPLINE D, PRATIQUER P, ATHLETE A, SPORT S
where P.ndd = D.ndd
And A.nda = P.nda
And d.cds = s.cds
GROUP BY S.cds,S.noms;


SELECT P1.cio, P1.nomp, COUNT(DISTINCT A2.nda) AS NBP
FROM PAYS P1, ATHLETE A1, ATHLETE A2, PRATIQUER PR2, PRATIQUER PR3, DISCIPLINE D1, DISCIPLINE D2, SPORT S
WHERE A1.ncomplet = 'RINER Teddy'
  AND PR2.nda = A1.nda
  AND PR2.ndd = D1.ndd
  AND D1.cds = S.cds
  AND S.cds = D2.cds
  AND D2.ndd = PR3.ndd
  AND PR3.nda = A2.nda
  AND A2.cio = P1.cio
GROUP BY P1.cio, P1.nomp;


Select D.TYPED, D.GENRED, round(avg(TO_CHAR(A.DATEN, 'YYYY'))) as DMOY, min(TO_CHAR(A.DATEN, 'YYYY')) as DMIN, max(TO_CHAR(A.DATEN, 'YYYY')) as DMAX
FROM DISCIPLINE D, PRATIQUER P, ATHLETE A
where d.cds = 'ATH'
And P.ndd = D.ndd
And A.nda = P.nda
GROUP BY D.TYPED,D.GENRED;


Select S.NOMS, D.TYPED, count(distinct a.cio) as NBP
FROM DISCIPLINE D, PRATIQUER P, ATHLETE A, SPORT S
Where d.cds = s.cds
And d.ndd = p.ndd
And a.nda = p.nda
GROUP BY S.NOMS,D.TYPED
Order by S.noms,NBP ASC;
