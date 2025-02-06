INSERT INTO Sport (cds, noms)
VALUES ('PLB','Pelote basque');

INSERT INTO Discipline (ndd, nomd, typed, genred, cds)
VALUES (seq_discipline.NEXTVAL, 'Grand Chistera', 'T', 'M', 'PLB');
INSERT INTO Discipline (ndd, nomd, typed, genred, cds)
VALUES (seq_discipline.NEXTVAL, 'Xare', 'T', 'M', 'PLB');

SELECT *
FROM Sport
WHERE cds = 'PLB';

ROLLBACK;

SELECT *
FROM Sport
WHERE cds = 'PLB';

INSERT INTO Sport (cds, noms)
VALUES ('PLB','Pelote basque');

INSERT INTO Discipline (ndd, nomd, typed, genred, cds)
VALUES (seq_discipline.NEXTVAL, 'Grand Chistera', 'T', 'M', 'PLB');
INSERT INTO Discipline (ndd, nomd, typed, genred, cds)
VALUES (seq_discipline.NEXTVAL, 'Xare', 'T', 'M', 'PLB');

SELECT *
FROM Sport
WHERE cds = 'PLB';

COMMIT;

SELECT *
FROM Sport
WHERE cds = 'PLB';

ALTER TABLE Discipline
DISABLE CONSTRAINT fk_discipline_cds;

INSERT INTO Discipline (ndd, nomd, typed, genred, cds)
VALUES (seq_discipline.NEXTVAL, 'Hurling', 'T', 'M', 'GLQ');
INSERT INTO Discipline (ndd, nomd, typed, genred, cds)
VALUES (seq_discipline.NEXTVAL, 'Camogie', 'T', 'W', 'GLQ');

COMMIT;

ALTER TABLE Discipline
ENABLE CONSTRAINT fk_discipline_cds;

CREATE TABLE REJET(
    NO_LIGNE ROWID,
    PROPRIO VARCHAR(30),
    NOM_TABLE VARCHAR(30),
    NOM_CONTRAINTE VARCHAR(30)
);

ALTER TABLE Discipline
ENABLE CONSTRAINT fk_discipline_cds
EXCEPTIONS INTO REJET ;

SELECT *
FROM Rejet;

SELECT ROWID, D.*
FROM Discipline D
WHERE ROWID IN (SELECT No_Ligne FROM Rejet);

DELETE FROM Discipline
WHERE ROWID IN (SELECT No_Ligne FROM Rejet);

DELETE FROM Rejet;

ALTER TABLE Discipline
ENABLE CONSTRAINT fk_discipline_cds;

DROP TABLE REJET;


-- double connexion : utilisateur A
INSERT INTO Sport (cds, noms)
VALUES ('GLQ', 'Sports Gaéliques');
INSERT INTO Discipline (ndd, nomd, genred, cds)
VALUES (seq_discipline.NEXTVAL,'Hurling', 'M', 'GLQ');
INSERT INTO Discipline (ndd, nomd, genred, cds)
VALUES (seq_discipline.NEXTVAL,'Camogie', 'M', 'GLQ');

SELECT *
FROM Sport
WHERE cds = 'GLQ';
SELECT *
FROM Discipline
WHERE cds = 'GLQ';

COMMIT;

UPDATE Discipline
SET genred = 'W'
WHERE nomd = 'Camogie';

SELECT *
FROM Discipline
WHERE cds = 'GLQ';

commit;

UPDATE Discipline
SET typed = 'T'
WHERE nomd = 'Hurling';

UPDATE Discipline
SET typed = 'T'
WHERE nomd = 'Camogie';

commit;

SELECT *
FROM Discipline
WHERE cds = 'GLQ';

-- Utilisateur B
SELECT *
FROM Sport
WHERE cds = 'GLQ';
SELECT *
FROM Discipline
WHERE cds = 'GLQ';

DELETE FROM Discipline
WHERE nomd = 'Camogie';

SELECT *
FROM Discipline
WHERE cds = 'GLQ';

ROLLBACK;

UPDATE Discipline
SET typed = 'T'
WHERE nomd = 'Camogie';

UPDATE Discipline
SET typed = 'T'
WHERE nomd = 'Hurling';

commit;

SELECT *
FROM Discipline
WHERE cds = 'GLQ';