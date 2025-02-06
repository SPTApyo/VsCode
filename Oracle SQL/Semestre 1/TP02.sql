create table PRATIQUER(
        nda DECIMAL(7),
        ndd DECIMAL,
        CONSTRAINT pk_PRATIQUER PRIMARY KEY (nda,ndd),
        CONSTRAINT fk_PRATIQUER_nda FOREIGN KEY (nda) REFERENCES ATHLETE(nda),
        CONSTRAINT fk_PRATIQUER_ndd FOREIGN KEY (ndd) REFERENCES DISCIPLINE(ndd)
    );


        
create table GAGNER_I(
        nda DECIMAL(7),
        ndd DECIMAL,
        medaille CHAR(1),
        dateg date,
        CONSTRAINT pk_GAGNER_I PRIMARY KEY (nda,ndd),
        CONSTRAINT fk_GAGNER_I_nda FOREIGN KEY (nda) REFERENCES ATHLETE(nda),
        CONSTRAINT fk_GAGNER_I_ndd FOREIGN KEY (ndd) REFERENCES DISCIPLINE(ndd)
    );




create table GAGNER_E(
        cio char(3),
        ndd DECIMAL,
        medaille CHAR(1),
        dateg date,
        CONSTRAINT pk_GAGNER_E PRIMARY KEY (ndd,medaille,cio),
        CONSTRAINT fk_GAGNER_E_ndd FOREIGN KEY (ndd) REFERENCES DISCIPLINE(ndd),
        CONSTRAINT fk_GAGNER_E_cio FOREIGN KEY (cio) REFERENCES Pays(cio)
    );
