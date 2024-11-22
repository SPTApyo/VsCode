SELECT nda, ncomplet, cio
FROM ATHLETE
WHERE TO_CHAR(daten, 'DD/MM') = (SELECT TO_CHAR(daten, 'DD/MM') FROM ATHLETE WHERE prenom = 'Léon' AND nom = 'MARCHAND');
