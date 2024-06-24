#ifndef DELFILE_H_INCLUDED
#define DELFILE_H_INCLUDED
void DELFILE(void)
{
char* DEL, Count[500], log[500];
int counting=0;
    sleep(2);
    SETTING();
    printf("Voulez-vous supprimer les fichiers test ?(y/n)\n");
    logs();
    fprintf(inputfile,"%s:",heure);
    fputs("[WAIT]>Voulez-vous supprimer les fichiers test ?(y/n)\n\n", inputfile );
    logs();
    fprintf(inputfile,"%s:",heure);
    fputs("Réponse utilisateur:", inputfile );

    if ((Limit=='Y')||(Limit=='y')&&(numero==limite))
    {
    WAIT();
    printf("R%cponse autonome...\n",130);
    fputs(" «Réponse autonome»...\n\n", inputfile );
    sleep(2);
    }
    else
    {
    while( getchar() != '\n');
    scanf("%c", &DEL);
    fprintf(inputfile, "«%c»",DEL);

    }

    if ((DEL=='y')||(DEL=='Y')||((Limit=='Y')||(Limit=='y')&&(numero==limite)))
    {
         nbfile=0;
         int returnCode;
         do
    {

    nbfile++;
    sprintf(Count,"%s %d.txt", chemin, nbfile);
    WAIT();
    printf("Suppression du fichier test..\n");
    logs();
    fprintf(inputfile,"%s:",heure);
    fputs("[WAIT]>Suppression du fichier test..\n", inputfile );
    if (remove(Count) == 0)
    {
   counting++;
       OK();
   printf("Fichier test supprimer..\n");
   logs();
   fprintf(inputfile,"%s:",heure);
   fputs("[OK]>Fichier test supprimer..\n", inputfile );
    }
    else
    {
       ERREUR();
   printf("Fichier test manquant.. \n");
   logs();
   fprintf(inputfile,"%s:",heure);
   fputs("[ERREUR]>Fichier test manquant.. \n", inputfile );
    }

    }while((nbfile!=numero)&&(nbfile<numero));

    printf("\n");
    OK();
    printf("S%cquence termin%ce int%cgralit%c des fichiers supprimer avec succ%cs\n", 130, 130, 130, 130, 138);
    logs();
    fprintf(inputfile,"%s:",heure);
    fputs("[OK]>Séquence terminée intégralité des fichiers supprimer avec succès \n", inputfile );
    Limit='y';
    limite=numero;
    DEL=NULL;
    while((DEL!='y')||(DEL!='Y'))
    {
    system("pause");
    logs();
    fprintf(inputfile,"\n\n%s:",heure);
    fputs(" //SYSTEM/PAUSE// \n\n", inputfile );
    printf("Exit ?(y/n)\n");
    logs();
    fprintf(inputfile,"%s:",heure);
    fputs("Exit ?(y/n)\n", inputfile );
    while( getchar() != '\n');
    scanf("%c",&DEL);
    logs();
    fprintf(inputfile,"%s:",heure);
    fprintf(inputfile,"Réponse utilisateur: «%c»...", DEL );
    if ((DEL=='y')||(DEL=='Y'))
    {
        sleep(2);
        logs();
        fprintf(inputfile,"\n\n%s:",heure);
        fputs("     //STOP\\\\\n", inputfile );
                logs();
        fprintf(inputfile,"\n\n%s:",heure);
        fputs("     Résumé du test:\n\n", inputfile );
        sprintf(log,"[SETTING]>SET_Cooldown:%d SET_Limite:%d SET_Securiter:%d \n", cooldown, limite, securiter);
        fputs(log, inputfile );
        fprintf(inputfile,"[SETTING]>ACCESS:<<%s>>\n",chemin);


           if (counting==numero)
   {
   fputs("[SUMMARY]>Test complet aucune défaillance détectée.. \n", inputfile );
   fprintf(inputfile,"[SUMMARY]>Fichier présent: %d/%d\n",counting, numero );
   }
   else
{

    ERREUR();
    nbfile=numero-counting;
    logs();
    fprintf(inputfile,"%s:",heure);
    fprintf(inputfile, "[SUMMARY]>Test incomplet %d fichiers manquants.. \n", nbfile);
    fprintf(inputfile,"[SUMMARY]>intégrité des données estimée a %d %%\n\n",pourc);

}
        fclose(inputfile);
        return 0;

    }
    }
    }
    else
    {

    DEL=NULL;
    while((DEL!='y')||(DEL!='Y'))
    {
    system("pause");
    logs();
    fprintf(inputfile,"\n\n%s:",heure);
    fputs(" //SYSTEM/PAUSE// \n\n", inputfile );
    printf("Exit ?(y/n)\n");
    logs();
    fprintf(inputfile,"%s:",heure);
    fputs("Exit ?(y/n)\n", inputfile );
    while( getchar() != '\n');
    scanf("%c",&DEL);
    logs();
    fprintf(inputfile,"%s:",heure);
    fprintf(inputfile,"Réponse utilisateur: «%c»...", DEL );
    if ((DEL=='y')||(DEL=='Y'))
    {
        sleep(2);
        logs();
        fprintf(inputfile,"\n\n%s:",heure);
        fputs("     //STOP\\\\\n", inputfile );



        logs();
        fprintf(inputfile,"\n\n%s:",heure);
        fputs("     Résumé du test:\n\n", inputfile );
        sprintf(log,"[SETTING]>SET_Cooldown:%d SET_Limite:%d SET_Securiter:%d \n", cooldown, limite, securiter);
        fputs(log, inputfile );
        fprintf(inputfile,"[SETTING]>ACCESS:<<%s>>\n",chemin);


           if (counting==numero)
   {
   fputs("[SUMMARY]>Test complet aucune défaillance détectée.. \n", inputfile );
   fprintf(inputfile,"[SUMMARY]>Fichier présent: %d/%d\n",counting, numero );
   }
   else
{

    ERREUR();
    nbfile=numero-counting;
    logs();
    fprintf(inputfile,"%s:",heure);
    fprintf(inputfile, "[SUMMARY]>Test incomplet %d fichiers manquants.. \n", nbfile);
    fprintf(inputfile,"[SUMMARY]>intégrité des données estimée a %d %%\n\n",pourc);

}



        fclose(inputfile);
        return 0;
    }
    }

    }
}
#endif // DELFILE_H_INCLUDED
