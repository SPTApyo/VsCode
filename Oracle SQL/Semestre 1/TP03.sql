ALTER TABLE SPORT
MODIFY noms VARCHAR(50)
DESC SPORT;


ALTER TABLE SPORT
MODIFY noms VARCHAR(40)
DESC ATHLETE;


ALTER TABLE ATHLETE
ADD(
    nom VARCHAR(30),
    prenom VARCHAR(30),
    initiale CHAR(2),
    civilite VARCHAR(3)
)


SET PAGESIZE 5000;

SELECT * FROM Athlete;

ALTER TABLE ATHLETE DROP COLUMN civilite;
ALTER TABLE ATHLETE ADD constraint UK_SPORT_NOMS UNIQUE (noms)

ALTER TABLE SPORT ADD constraint UK_SPORT_NOMS UNIQUE (noms)

ALTER TABLE GAGNER_I ADD ( constraint ck_gagner_i_medaille CHECK (medaille in('G','S','B')))
ALTER TABLE GAGNER_E ADD ( constraint ck_gagner_e_medaille CHECK (medaille in('G','S','B')))

ALTER TABLE DISCIPLINE ADD ( constraint ck_discipline_ndd CHECK (ndd>0))
ALTER TABLE DISCIPLINE ADD ( constraint ck_discipline_typed CHECK (typed in ('P','T')))
ALTER TABLE DISCIPLINE ADD ( constraint ck_discipline_genred CHECK (genred in ('M','W','X','O')))


ALTER TABLE athlete ADD ( constraint ck_athlete_taille CHECK (taille>=0))
ALTER TABLE athlete ADD ( constraint ck_athlete_poids CHECK (poids>=0))
ALTER TABLE athlete ADD ( constraint ck_athlete_genre CHECK (genre in ('M','F')))


ALTER TABLE DISCIPLINE ADD ( constraint ck_DISCIPLINE_cds CHECK (cds IS NOT NULL))


SELECT * FROM user_constraints where table_name in('SPORT','DISCIPLINE','PAYS','ATHLETE','GAGNER_E','GAGNER_I','PRATIQUER');