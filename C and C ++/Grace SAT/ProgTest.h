#ifndef PROGTEST_H_INCLUDED
#define PROGTEST_H_INCLUDED


char* Exit='null', Limit, chemin[500],date[240],heure[240],writes[1500];
int  cooldown=5, ADMIN=0,numero= 0, nbfile=0, limite, securiter=1, pourc=0;
FILE* *fichier,* inputfile;

void ProgTest(void)
{
char*  tmp[500];
if((securiter==1)&&((cooldown==0)||(cooldown=1)))
{
    cooldown=2;
    SETTING();
    printf("Securit%ce Activ%ce cooldown augment%ce de %d Seconde\n\n",130, 130, 130, cooldown);
    logs();
    fprintf(inputfile,"%s:",heure);
    fputs("[SETTING]>Securitée activée cooldown augmentée de 2 seconde \n", inputfile );

}
else
{
    Color(12,0);
    printf("ATTENTION Securiter desactiv%ce\n\n",130);
    Color(15,0);
    logs();
    fprintf(inputfile,"%s:",heure);
    fputs("ATTENTION securiter desactivée \n\n", inputfile );
}
sleep(3);

   random();
do
{
   numero++;
   printf ("%d\n",numero);
   printf ("_________________________________________________________________\n");
   fprintf(inputfile,"%d\n",numero);
   fputs("_________________________________________________________________\n", inputfile );
   sprintf(tmp,"%s %d.txt", chemin, numero);
   fichier = fopen(tmp, "w+");
   WAIT();
   printf("Cr%Cation du fichier...\n",130);
   logs();
   fprintf(inputfile,"%s:",heure);
   fputs("[WAIT]>Création du fichier...\n", inputfile );

   if (fichier != NULL)

   {
       OK();
   printf("Cr%cation du fichier test reussie.\n",130);
   logs();
   fprintf(inputfile,"%s:",heure);
   fputs("[OK]>Création du fichier test reussie\n", inputfile );

   WAIT();
   printf("Ecriture du fichier...\n");
   logs();
   fprintf(inputfile,"%s:",heure);
   fputs("[WAIT]>Écriture du fichier...\n", inputfile );
   WAIT();
   printf("V%crification du fichier...\n",130);
   logs();
   fprintf(inputfile,"%s:",heure);
   fputs("[WAIT]>Vérification du fichier...\n", inputfile );

           if ( (fprintf (fichier, "%s\n%s\n\n", writes)) != EOF )
          {
             OK();
             printf("Ecriture du fichier test reussie.\n");
             logs();
             fprintf(inputfile,"%s:",heure);
             fputs("[OK]>Écriture du fichier test reussie.\n", inputfile );
             fprintf (fichier, "%s\n%s\n", writes);

          }

           else
          {
             ERREUR();
             printf("Echec de l'%ccriture du fichier test..\n",130);
             logs();
             fprintf(inputfile,"%s:",heure);
             fputs("[ERREUR]>Échec de l'écriture du fichier test..\n", inputfile );
          }
    }

    else
    {
       ERREUR();
   printf("Echec de la cr%cation du fichier test..\n", 130);
   logs();
   fprintf(inputfile,"%s:",heure);
   fputs("[ERREUR]>Échec de la création du fichier test..\n", inputfile );
    }

   fclose(fichier);
   Color(12, 0);
   printf("Cooldown %d seconde(s)...\n",cooldown);
   Color(15, 0);
   fprintf(inputfile,"%s:",heure);
   fprintf(inputfile, "Cooldown %d seconde...\n", cooldown );
   printf ("_________________________________________________________________\n");
   fputs("_________________________________________________________________\n", inputfile );
   sleep(cooldown);

   calculdif();

}while(((Exit!='y')||(Exit!='Y'))&&((Limit=='Y')||(Limit!='y')&&(numero!=limite)));

}

#endif // PROGTEST_H_INCLUDED

