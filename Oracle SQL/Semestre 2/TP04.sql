SELECT nomd 
FROM Disciplines 
WHERE ndd = ANY (
    SELECT ndd 
    FROM Resultats 
    WHERE nda = ANY (
        SELECT nda 
        FROM Athletes 
        WHERE ncomplet = 'MARCHAND LéonAthlete'
    )
);