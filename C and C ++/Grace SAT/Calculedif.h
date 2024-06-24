#ifndef CALCULEDIF_H_INCLUDED
#define CALCULEDIF_H_INCLUDED

void calculdif(void)
{
char* Count[500], detection;
int counting=0;

if ((kbhit()))
{
   detection=getch() ;

    if ((detection == 't') || (detection == 'T'))
{
  SETTING();
  printf("Arr%cter le test ?(y/n)\n",136);
  logs();
  fprintf(inputfile,"%s:",heure);
  fputs("[SETTING]>arrêter le test ?(y/n)\n\n", inputfile );
  while( getchar() != '\n');
  scanf("%c", &Exit);
  logs();
  fprintf(inputfile,"%s:",heure);
  fprintf(inputfile, "Réponse utilisateur: «%c»", Exit );
}
if ((detection == 'H') || (detection == 'h'))
{
  HELP();
}

}
else
{
}

  if (((Limit=='y')||(Limit=='Y')&&(numero==limite))||(Exit=='y')||(Exit=='Y'))
  {
   fclose(fichier);
   Color(12,0);
   printf("Fin du test..\n");
   logs();
   fprintf(inputfile,"%s:",heure);
   fputs("Fin du test..\n", inputfile );
   printf("Pr%cparation du calcule de fichier..\n",130);
   logs();
   fprintf(inputfile,"%s:",heure);
   fputs("Préparation du calcule de fichier..\n", inputfile );
   Color(15, 0);
   WAIT();
   printf("Calcule en cours..\n");
   logs();
   fprintf(inputfile,"%s:",heure);
   fputs("Calcule en cours..\n\n", inputfile );
   sleep(3);

        do
    {
    nbfile++;
    sprintf(Count,"%s %d.txt", chemin, nbfile);
    fichier = fopen(Count, "r");
    if (fichier != NULL)
    {
   counting++;
   fclose(fichier);
       OK();
   printf("Fichier test pr%csent..\n",130);
   logs();
   fprintf(inputfile,"%s:",heure);
   fputs("[OK]>Fichier test présent..\n", inputfile );
   if (nbfile==numero/2)
   {
    WAIT();
    printf("50%% effectu%ce\n",130);
    logs();
    fprintf(inputfile,"%s:",heure);
    fputs("[WAIT]>50% effectuée\n", inputfile );
   Color(12, 0);
   printf("Cooldown 3 seconde...\n");
   Color(15, 0);
   fprintf(inputfile,"%s:",heure);
   fputs("Cooldown 3 seconde...\n",inputfile);
    sleep(3);
   }
   else
   {
    }
    }
    else
    {
       ERREUR();
   printf("Fichier test manquant.. \n");
   logs();
   fprintf(inputfile,"%s:",heure);
   fputs("[ERREUR]>Fichier test manquant..\n", inputfile );
   fclose(fichier);
    }

    }while((nbfile!=numero));
        WAIT();
        printf("100%% effectu%ce\n\n",130);
        logs();
        fprintf(inputfile,"%s:",heure);
        fputs("[WAIT]>100% effectuée\n\n", inputfile );
        OK();
    printf("Fichier pr%csent: %d/%d\n",130, counting, numero);
    logs();
    fprintf(inputfile,"%s:",heure);
    fprintf(inputfile,"[OK]>Fichier présent: %d/%d\n",counting, numero );
   if (counting==numero)
   {
   OK();
   printf("Test complet aucune d%cfaillance d%ctect%ce.. \n",130,130,130);
   logs();
   fprintf(inputfile,"%s:",heure);
   fputs("[OK]>Test complet aucune défaillance détectée.. \n", inputfile );
   DELFILE();
   }
   else
{

    ERREUR();
    nbfile=numero-counting;
    printf("Test incomplet %d fichiers manquants..\n", nbfile);
    logs();
    fprintf(inputfile,"%s:",heure);
    fprintf(inputfile, "[ERREUR]>Test incomplet %d fichiers manquants.. \n", nbfile);
    WAIT();
    printf("Calcule des pertes...\n");
    logs();
    fprintf(inputfile,"%s:",heure);
    fputs("[WAIT]>Calcule des pertes... \n", inputfile );
    CalculPourcent();
    DELFILE();
}

  }

}



#endif // CALCULEDIF_H_INCLUDED
