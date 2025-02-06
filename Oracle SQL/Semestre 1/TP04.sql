INSERT INTO Sport (cds, noms)
VALUES ('BSB', 'Baseball');


SELECT *
FROM Sport
WHERE cds = 'BSB';


INSERT INTO Gagner_I (ndd, nda, medaille, dateg)
VALUES ('288', '1562400','B','03/08/24');


INSERT INTO Gagner_I (ndd, nda, medaille, dateg)
VALUES ('288', '1563682','S','04/08/24');


INSERT INTO Gagner_I (ndd, nda, medaille, dateg)
VALUES ('288', '1956398','G','04/08/24');


SELECT *
FROM Gagner_I
WHERE ndd = 288;


INSERT INTO Discipline (ndd, nomd, typed, genred, cds)
VALUES (22, 'Men''s 100m', 'P', 'M', 'ATH');


INSERT INTO Discipline (ndd, nomd, typed, genred, cds)
VALUES (500, 'Men''s 100m', 'Z', 'M', 'ATH');


INSERT INTO Discipline (ndd, nomd, typed, genred, cds)
VALUES (500, 'Men''s 100m', 'P', 'Z', 'ATH');


INSERT INTO Discipline (ndd, nomd, typed, genred, cds)
VALUES (500, 'Men''s 100m', 'P', 'M', 'ZZZ');


UPDATE Sport
SET noms = 'Beach Volleyball'
WHERE cds = 'VBV';


SELECT *
FROM Sport;


UPDATE Athlete
SET nom = substr(regexp_substr(ncomplet, '[[:upper:]][[:upper:]- ]+'), 1, length(regexp_substr(ncomplet, '[[:upper:]- ]+'))-2),
    prenom = substr(ncomplet, length(substr(regexp_substr(ncomplet, '[[:upper:]][[:upper:]- ]+'), 1, length(regexp_substr(ncomplet, '[[:upper:]- ]+'))))),
    initiale = substr(regexp_substr(ncomplet, '\S+'),1,1) || substr(regexp_substr(ncomplet, '\S+$'),1,1)
WHERE cio = 'FRA';


SELECT ncomplet, nom, prenom, initiale
FROM Athlete
WHERE cio = 'FRA'
ORDER BY nom, prenom;


INSERT INTO Athlete (nda, ncomplet, genre, cio)
VALUES (9000000, 'TESTE Olivier', 'M', 'FRA');
INSERT INTO Pratiquer
VALUES (9000000, 22);


INSERT INTO Athlete (nda, ncomplet, genre, cio)
VALUES (1310005, 'LAROUSSI Nabil', 'M', 'MAR');
INSERT INTO Pratiquer
VALUES (1310005, 22);


INSERT INTO GAGNER_I (nda, ndd, MEDAILLE)
VALUES (1310005,22, 'G');


DELETE FROM Pratiquer
WHERE nda =9000000 AND ndd=22;

DELETE FROM Athlete
WHERE nda=9000000 AND ncomplet='TESTE Olivier' AND genre='M' AND cio='FRA';

DELETE FROM Pratiquer
WHERE nda=1310005 AND ndd=22;

DELETE FROM GAGNER_I
WHERE nda =1310005 AND ndd=22 AND medaille='G';

DELETE FROM Athlete
WHERE nda=1310005 AND ncomplet='LAROUSSI Nabil' AND genre='M' AND cio='MAR';
