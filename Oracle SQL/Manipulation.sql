Select p.nomp
from pays p,athlete a     
where a.cio=p.cio
group by p.nomp having count(a.cio)>300;

select s.cds    
from sport s, discipline d
where d.cds = s.cds
group by s.cds having count(d.cds)>30;

select a.ncomplet   
from sport s, discipline d,athlete a,pratiquer p
where a.cio = 'FRA'
AND s.noms = 'Swimming'
AND d.cds = s.cds
AND p.ndd = d.ndd  
AND a.nda = p.nda
group by a.ncomplet having count(d.cds)>1;

select a.ncomplet   
from discipline d,athlete a,pratiquer p
where a.cio = 'FRA'
AND p.nda = a.nda
AND d.ndd = p.ndd
group by a.ncomplet having count(DISTINCT d.cds)>1;

SELECT g.cio, p.nomp
FROM GAGNER_E G, PAYS P 
WHERE g.cio = p.cio
GROUP BY g.cio, p.nomp
HAVING COUNT(DISTINCT g.medaille) = 3;

SELECT g.cio, p.nomp, s.noms || ' - ' || d.nomd as DISCIPLINE
FROM discipline d, pays p, gagner_e g, sport s
WHERE s.cds = d.cds
AND g.ndd = d.ndd
AND p.cio = g.cio
GROUP BY g.cio, p.nomp, s.noms, d.nomd 
HAVING COUNT(DISTINCT g.medaille)>1;


SELECT s.noms, MIN(a.taille) as TMIN, MAX(a.taille) as TMAX, ROUND(AVG(a.taille),6) as TMOY
FROM pays p, sport s, athlete a, pratiquer pr, discipline d
WHERE s.cds = d.cds
AND pr.ndd = d.ndd
AND pr.nda = a.nda
AND a.taille is not null
GROUP BY s.noms
HAVING COUNT(DISTINCT a.cio)>=20;

SELECT P.cio, P.nomp, SUM(CASE WHEN A.genre = 'M' THEN 1 ELSE 0 END) AS nbm, SUM(CASE WHEN A.genre = 'F' THEN 1 ELSE 0 END) AS nbf
FROM ATHLETE A, PAYS P
WHERE A.cio = P.cio
GROUP BY P.cio, P.nomp
HAVING SUM(CASE WHEN A.genre = 'M' THEN 1 ELSE 0 END) = SUM(CASE WHEN A.genre = 'F' THEN 1 ELSE 0 END);

SELECT A.ncomplet, COUNT(DISTINCT P.ndd) AS nbd_disciplines, COUNT(DISTINCT G.medaille) AS nb_medailles
FROM ATHLETE A, PRATIQUER P ,GAGNER_I G
WHERE A.cio = 'FRA'
AND A.nda = G.nda
AND A.nda = P.nda
GROUP BY A.ncomplet
HAVING COUNT(DISTINCT P.ndd) > 1;


SELECT S.noms
FROM SPORT S, DISCIPLINE D
WHERE S.cds = D.cds
GROUP BY S.cds, S.noms
HAVING COUNT(D.ndd) = COUNT(CASE WHEN D.typed = 'T' THEN 1 END);








