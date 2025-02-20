-- LDD : Creation des tables
-- Table Client
CREATE TABLE Client (
 idNumClient CHAR(5),
 civilite VARCHAR(3),
 nom VARCHAR(30),
 prenom VARCHAR(30) ,
 adNum VARCHAR(6),
 adVoie VARCHAR(30),
 adCP CHAR(5),
 adLocalite VARCHAR(30),
 dateN DATE,
 mel VARCHAR(30),
 telF CHAR(10),
 telP CHAR(10),

 CONSTRAINT pk_Client PRIMARY KEY (idNumClient),
 CONSTRAINT ck_Client_civilite CHECK (civilite IN ('M', 'Mme'))
);
-- Table BonCde (Bon de Commande)
CREATE TABLE BonCde (
 idNumBC DECIMAL,
 dateBC DATE ,
 codeAv CHAR(5),
 remise DECIMAL(*,2),
 modeL CHAR(2),
 typeP CHAR(3),
 numCB CHAR(16),
 dExpCB CHAR(4),
 cryp CHAR(3),
 fraisCR DECIMAL(*,2),
 fraisPort DECIMAL(*,2),
 idNumClient CHAR(5),
 idNomR VARCHAR (30),

 CONSTRAINT pk_BonCde PRIMARY KEY(idNumBC),
 CONSTRAINT fk_BonCde_idNumClient FOREIGN KEY (idNumClient) REFERENCES Client(idNumClient),
 CONSTRAINT fk_BonCde_idNomR FOREIGN KEY (idNomR) REFERENCES Relais(idNomR),
 CONSTRAINT ck_BonCde_idNumBC CHECK (idNumBC > 0),
 CONSTRAINT ck_BonCde_remise CHECK (remise BETWEEN 0 AND 100),
 CONSTRAINT ck_BonCde_modeL CHECK (modeL IN ('Do', 'RD', 'RR')),
 CONSTRAINT ck_BonCde_typeP CHECK (typeP IN ('CAB', 'CHB', 'CHD', 'CRD')),
 CONSTRAINT ck_BonCde_fraisCR CHECK (fraisCR IN (0.00, 6.90)),
 CONSTRAINT ck_BonCde_fraisPort CHECK (fraisPort IN (5.90, 6.00, 7.50))
);
-- Table Article
CREATE TABLE Article (
 idRefA CHAR(9),
 nomA VARCHAR(30) ,
 couleur VARCHAR(30),
 prixU DECIMAL(*,2) ,
 taillesA CHAR(5),
 descA VARCHAR(120),

 CONSTRAINT pk_Article PRIMARY KEY(idRefA),
 CONSTRAINT ck_Article_prixU CHECK (prixU > 0)
);
-- Table Catalogue
CREATE TABLE Catalogue (
 idNomC VARCHAR(30),

 CONSTRAINT pk_Catalogue PRIMARY KEY(idNomC)
);
-- Table Relais
CREATE TABLE Relais (
 idNomR VARCHAR(30),
 adNumR CHAR(6) ,
 adVoieR VARCHAR(30),
 adCPR CHAR(5) ,
 adLocaliteR VARCHAR(30),
 CONSTRAINT pk_Relais PRIMARY KEY(idNomR)
);
-- Table Commander (relation entre BonCde et Article)
CREATE TABLE Commander (
 idNumBC DECIMAL,
 idRefA CHAR(9),
 qteA DECIMAL,
 tailleA DECIMAL,

 CONSTRAINT pk_Commander PRIMARY KEY (idNumBC, idRefA),
 CONSTRAINT fk_Commander_idNumBC FOREIGN KEY (idNumBC) REFERENCES BonCde(idNumBC),
 CONSTRAINT fk_Commander_idRefA FOREIGN KEY (idRefA) REFERENCES Article(idRefA),
 CONSTRAINT ck_Commander_idNumBC CHECK (idNumBC > 0),
 CONSTRAINT ck_Commander_qteA CHECK (qteA > 0)
);
-- Table Presenter (relation entre Catalogue et Article)
CREATE TABLE Presenter (
 idRefA CHAR(9),
 idNomC VARCHAR(30),
 numPage VARCHAR(3),
 codeP CHAR(1),

 CONSTRAINT pk_Presenter PRIMARY KEY (idNomC, idRefA),
 CONSTRAINT fk_Presenter_idRefA FOREIGN KEY (idRefA) REFERENCES Article(idRefA),
 CONSTRAINT fk_Presenter_idNomC FOREIGN KEY (idNomC) REFERENCES Catalogue(idNomC),
 CONSTRAINT ck_Presenter_codeP CHECK (codeP LIKE '[a-z]')
);


-- LMD : Insertions des tables

--insertion dans article
INSERT INTO Article
VALUES ('D0026.023', 'A. Parka 3 en 1', 'Camel', 149.00, '34-56', 'Gilet Amevible doublé Thermolactyl');
INSERT INTO Article
VALUES ('D6606.001', 'B. Jean coupe Mom', 'Demin foncé', 45.99, '38-52', '99% coton, 1% élasthanne');
INSERT INTO Article
VALUES ('D0615.004', 'C.sac', 'Marron animalier', 59.99, '00000', 'Croûte de cuir vachette');
INSERT INTO Article
VALUES ('D5333.025', 'D.Pull', 'Beige rayé', 49.99, '34-56', 'marinière épaule boutonnée');
INSERT INTO Article
VALUES ('D5333.013', 'D.Pull', 'Blanc rayé', 49.99, '34-56', 'marinière épaule boutonnée');
INSERT INTO Article
VALUES ('D5333.001', 'D.Pull', 'Marine rayé', 49.99, '34-56', 'marinière épaule boutonnée');
INSERT INTO Article
VALUES ('D0295.009', 'T-shirts Essential', NULL, 25.99, '38', NULL);
INSERT INTO Article
VALUES ('D6917.006', 'Fine côte thermolacty', NULL, 25.99, '38', NULL);

--insertion dans client
INSERT INTO Client
VALUES ('56789', 'M', 'ASSEIN', 'Marc', '18', 'Avenue des Chênes', '31400','Toulouse', null, null, null, null);
INSERT INTO Client
VALUES ('12678', 'Mme', 'Aztakès', 'Helene', '300', 'Rue Ferrari', '72000','Le Hans', null, 'helene.aztakes@orange.fr', null, '0607000000');
INSERT INTO Client
VALUES ('12345', 'M', 'VERSE', 'ALAIN', '69', 'Rue du contre sens', '31700','Blagnac', '20/01/1984', 'alain.verse@gmail.C1', '0505000000','0606000000');

--insertion dans Relais
INSERT INTO Relais
VALUES ('LA POSTE PICKUP CAPITOLE' , 9 ,'RUE LAFAYETTE', 31000 ,'Toulouse');
INSERT INTO Relais
VALUES ('PICKUP LA POSTE REMUSAT', 25 ,'RUE DE REMUSAT', 31000 ,'Toulouse');
INSERT INTO Relais
VALUES ('EXCKLUSIVE', 20 ,'RUE DE SAINTE URSULE', 31000 ,'Toulouse');
INSERT INTO Relais
VALUES ('FOUR TWENTY CBD SHOP', 40 ,'RUE PARGAMINIERES', 31000 ,'Toulouse');
INSERT INTO Relais
VALUES ('MY LOOK', 12 ,'RUE DES CHANGES', 31000 ,'Toulouse');
INSERT INTO Relais
VALUES ('SMART WASH TOULOUSE', 29 ,'RUE DE REMPART MATABIAU', 31000,'Toulouse');
INSERT INTO Relais
VALUES ('GSM service 31', '3bis' ,'BOULEVARD DE STRASBOURG', 31000,'Toulouse');
INSERT INTO Relais
VALUES ('MIDICA RELAIS', 22 ,'RUE DES TOURNEURS', 31000 ,'Toulouse');
INSERT INTO Relais
VALUES ('KDC NUTRITION', 9 ,'RUE DE BAYARD', 31000 ,'Toulouse');
INSERT INTO Relais
VALUES ('ASIA SHOP', 9 ,'RUE MATABIAU', 31000 ,'Toulouse');
INSERT INTO Relais
VALUES ('CARREFOUR CITY', 43 ,'RUE DE METZ', 31000 ,'Toulouse');
INSERT INTO Relais
VALUES ('LA POSTE DE TOULOUSE ARDNAUD', 17 ,'BOULEVARD LASCROSSES', 31000,'Toulouse');

--insertion dans BonCde
INSERT INTO BonCde
VALUES (1, null, '39626', null, 'RD', 'CAB', '1111999922224444', '0127','192', null, 7.50, '56789', null);
INSERT INTO BonCde
VALUES (2, null, '39626', null, 'RD', 'CRD', null, null, null, 6.90, 7.50,'12678', null);
INSERT INTO BonCde
VALUES (3, null, '39626', null, 'Do', 'CHD', null, null, null, null, 5.90,'12678', null);
INSERT INTO BonCde
VALUES (4, null, '39626', null, 'RR', 'CAB', '9999888877776666', '0126','987', null, 6.00, '12345', 'CARREFOUR CITY');

--insertion dans Commander
INSERT INTO Commander
VALUES ( 1, 'D5333.013', 2, 42);
INSERT INTO Commander
VALUES ( 2, 'D0295.009', 3, 38);
INSERT INTO Commander
VALUES ( 2, 'D6917.006', 1, 38);
INSERT INTO Commander
VALUES ( 3, 'D0026.023', 1, 40);
INSERT INTO Commander
VALUES ( 3, 'D5333.001', 1, 38);
INSERT INTO Commander
VALUES ( 4, 'D5333.025', 1, 36);

SELECT Count (idNumBC) as "NBCde"
FROM BonCde; 

SELECT 
    SUM(c.qteA * a.prixU) AS Montant_Total_Commande,
    COUNT(*) AS Nombre_Lignes_Commande
FROM Commander c
LEFT OUTER JOIN Article a ON c.idRefA = a.idRefA;

SELECT 
    c.idRefA AS Article,
    SUM(c.qteA) AS Nombre_Ventes
FROM Commander c
GROUP BY c.idRefA
ORDER BY Nombre_Ventes DESC;

SELECT 
    bc.idNumClient AS Num_Client,
    COUNT(*) AS Nombre_Commandes
FROM BonCde bc
GROUP BY bc.idNumClient;

SELECT 
    cl.adLocalite AS Ville,
    COUNT(bc.idNumBC) AS Nombre_Commandes,
    SUM(c.qteA * a.prixU) AS Montant_Total
FROM Client cl
LEFT OUTER JOIN BonCde bc ON cl.idNumClient = bc.idNumClient
LEFT OUTER JOIN Commander c ON bc.idNumBC = c.idNumBC
LEFT OUTER JOIN Article a ON c.idRefA = a.idRefA
GROUP BY cl.adLocalite;


SELECT prixU
FROM article
WHERE idRefA = 'D6917.006';

SELECT C2.idNumClient , C1.qteA
FROM Commander C1, client C2, bonCde B
WHERE C2.idNumClient = B.idNumClient
AND B.idNumBC = C1.idNumBC
AND C1.idRefA = 'D6917.006';

SELECT 
    cl.nom AS Nom_Client,
    cl.prenom AS Prenom_Client,
    COUNT(bc.idNumBC) AS Nombre_Commandes,
    SUM(c.qteA * a.prixU) AS Montant_Total_Commande
FROM Client cl
LEFT OUTER JOIN BonCde bc ON cl.idNumClient = bc.idNumClient
LEFT OUTER JOIN Commander c ON bc.idNumBC = c.idNumBC
LEFT OUTER JOIN Article a ON c.idRefA = a.idRefA
GROUP BY cl.idNumClient, cl.nom, cl.prenom
HAVING COUNT(bc.idNumBC) > 1 AND SUM(c.qteA * a.prixU) > 200;
