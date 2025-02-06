create table Pays(
    cio CHAR(2),
    nomp VARCHAR(30),
    ncomplet VARCHAR(50),
    CONSTRAINT pk_pays PRIMARY KEY (cio)
);
   
create table SPORT(
    cds CHAR(3),
    noms VARCHAR(30),
    urls VARCHAR(100),
    CONSTRAINT pk_sport PRIMARY KEY (cds)
);
create table DISCIPLINE(
        ndd DECIMAL,
        nomd VARCHAR(50),
        typed CHAR(1),
        genred CHAR(1),
        cds CHAR(3),
        CONSTRAINT pk_DISCIPLINE PRIMARY key (ndd),
        CONSTRAINT fk_DISCIPLINE_cds FOREIGN key (cds) REFERENCES SPORT(cds)
   );


   create table ATHLETE(
        nda DECIMAL(7),
        ncomplet VARCHAR(50),
        genre CHAR(1),
        taille DECIMAL(3),
        poids  DECIMAL(4,1),
        daten DATE,
        villen VARCHAR(30),
        paysn VARCHAR(30),
        cio char(3),
        CONSTRAINT pk_ATHLETE PRIMARY KEY (nda),
        CONSTRAINT fk_DISCIPLINE_cio FOREIGN KEY (cio) REFERENCES Pays(cio)
    );
