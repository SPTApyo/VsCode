--1
Select ndd , nomd
From Discipline
Where cds in ( select cds
            from Sport
            where noms = 'Swimming' );


--2
SELECT DISTINCT ncomplet
FROM Athlete 
WHERE nda IN (
    SELECT nda
    FROM Gagner_I 
    WHERE medaille = 'G'
    AND ndd IN (
        SELECT ndd
        FROM Discipline
        WHERE nomd = 'Men′′s 200m Butterfly'
    ));


--3
SELECT nomd
FROM DISCIPLINE
WHERE ndd IN (
    SELECT ndd
    FROM GAGNER_I
    WHERE nda IN (
        SELECT nda
        FROM ATHLETE
        WHERE ncomplet = 'MARCHAND Leon'
    ));

--4
Select noms
FROM SPORT
where cds in ( select cds
            from DISCIPLINE
            where ndd in ( select ndd
                        from GAGNER_I
                        where medaille = 'G'
                        AND nda in (select nda 
                                    from athlete 
                                    where cio in (select cio
                                                from PAYS
                                                where nomp = 'France' ) ) ) );

--5
Select S.noms,  D.nomd
FROM SPORT S, Discipline D
where S.cds = D.cds
AND D.ndd in ( select ndd
            from GAGNER_I
            where MEDAILLE = 'G'
            AND nda in (select nda 
                        from athlete 
                        where cio in (select cio
                                    from PAYS
                                    where nomp = 'France' ) ) );


--6
Select noms
FROM SPORT
where cds in ( select cds
            from DISCIPLINE
            where ndd in ( select ndd
                        from GAGNER_I
                        where medaille = 'G'
                        AND nda in (select nda 
                                    from athlete 
                                    where cio in (select cio
                                                from PAYS
                                                where nomp = 'France' ) ) )
            OR ndd in ( select ndd
                        from GAGNER_E
                        where medaille = 'G' 
                        AND cio = 'FRA' ) );

--7
select ncomplet
from athlete 
where nda in (select nda
            from PRATIQUER
            where ndd in (select ndd
                        from DISCIPLINE
                        where genred = 'M'
                        AND cds in (select cds
                                    from SPORT
                                    where noms = 'Rugby Sevens')))
AND cio in (select cio
            from GAGNER_E
            where medaille = 'G'
            AND ndd in (select ndd
                        from DISCIPLINE
                        where genred = 'M'
                        AND cds in (select cds
                                    from SPORT
                                    where noms = 'Rugby Sevens')));

--8
select nomp
from PAYS
where cio in (select cio 
                from GAGNER_E
                WHERE medaille IN ('G', 'S', 'B')
                GROUP BY cio
                HAVING COUNT(DISTINCT medaille) = 3);

--9
Select noms
from SPORT
where cds NOT IN (select cds 
                    from discipline 
                    WHERE typed = 'P');

--10
Select ncomplet
from ATHLETE
where cio in (select cio 
                from pays 
                where nomp = 'France')
AND nda in (select nda 
            from pratiquer 
            where ndd in (select ndd
                        from discipline
                        where cds in (select CDS
                                        from SPORT
                                        where noms = 'Judo')))
AND nda NOT IN (select nda
                from gagner_i);

--11
select nomp 
from PAYS
where cio in (select CIO
                from GAGNER_E
                WHERE medaille = 'G')
AND cio NOT IN (select cio 
                from athlete 
                where nda in (select nda
                                from gagner_i
                                WHERE medaille = 'G'));



