#include <stdio.h>
#include <stdlib.h>
#include <strings.h>
#include <windows.h>
#include <dir.h>
#include <conio.h>
#include <time.h>

#include "Couleur.h"
#include "ProgTest.h"
#include "Calculedif.h"
#include "SETTING.h"
#include "OK.h"
#include "ERREUR.h"
#include "WAIT.h"
#include "CalculPourcent.h"
#include "DELFILE.h"
#include "DEV.h"
#include "getline.h"
#include "DIRECTORY.h"
#include "Caracterealeatoire.h"
#include "HELP.h"



int main(int argc, char *argv[])
{

       HELP();
       system("cls");
       char* Choix=NULL, detection,log[500];
       directory();
       Limit=NULL;
       SETTING();
       printf("Voulez-vous modifier le cooldown(%ds)? (y/n)\n",cooldown);
       scanf("%1c", &Choix);
       sleep(1);

       if ((Choix=='y')||(Choix=='Y'))
       {
       Choix=NULL;
       SETTING();
       printf("Nouvelle valeur de cooldown ?\n");
       scanf("%d", &cooldown);
       }
               if ((Choix=='?')||(Choix=='!'))
        {
        OK();
        printf("Mode DEV activer\n");
        ADMIN="1";
        cooldown=0;
        securiter=0;
        Limit='Y';
        limite=limite+10;
        Choix='Y';
        DEV();
        printf("Valeurs pr%cd%cfinies cooldown:%d limite:%d ", 130, 130, cooldown, limite);
        sleep(2);
        }

        if ((Choix=='C')||(Choix=='c'))
        {
            securiter=0;
            DEV();
            printf("Securiter cooldown desactiv%ce\n",130);
            Choix=NULL;
            SETTING();
            printf("Nouvelle valeur de cooldown ?\n");
            scanf("%d", &cooldown);
        }

       else
       {
       }

       system("cls");
       fclose(fichier);
       fichier = fopen ("C:\\ProgramData\\Grace-SAT\\ACCESS.txt", "r+");


       if  (fichier != NULL)
       {
       if (ADMIN==0){
       SETTING();
       printf("Utiliser le chemin sauvegarder? (y/n)\n");

       Choix=NULL;
    fflush( stdin );
    scanf("%c",&Choix);
    printf("%s",fichier);

       }
       else
       {
           SETTING();
           printf("Utiliser le chemin sauvegarder? (y/n)\n");
           DEV();
           printf("MODE DEV\n");

           while(fgets(chemin, "\n" , fichier)){
           OK();
           printf("Chemin utilis%ce <<", 130);
           Color(13, 0);
           printf("%s",chemin);
           Color(15, 0);
           printf(">>",chemin);
           chemin==NULL;
           sleep(1);
       }
       }

       if ((Choix=='y')||(Choix=='Y'))
       {
        fichier = fopen ("C:\\ProgramData\\Grace-SAT\\ACCESS.txt", "r+");
        while(fgets(chemin, "\n" , fichier)){
        OK();
        printf("Chemin utilis%ce <<", 130);
        Color(13, 0);
        printf("%s",chemin);
        Color(15, 0);
        printf(">>",chemin);
        chemin==NULL;
        }

       fclose(fichier);
       sleep(2);


       }

        else
        {
        WAIT();
        printf("Suppression de l'ancien chemin\n");
        fclose(fichier);
    if (remove("C:\\ProgramData\\Grace-SAT\\ACCESS.txt") == 0)
    {
       OK();
   printf("Fichier supprimer\n");
   sleep(2);
    }
    else
    {
       ERREUR();
   printf("Fichier manquant \n");
   sleep(2);
    }
     SETTING();
        printf("Quel chemin d'acc%cs utiliser ?\n",130);
        Color(7,0);
        printf("Exemple: <<C:\\\\chemin\\\\chemin\\\\...>> \n");
        Color(15,0);
        while( getchar() != '\n');
        scanf("%s[^\n]", &chemin);
        SETTING();
        printf("Sauvegarder le chemin d'acc%cs ?\n",138);
        while( getchar() != '\n');
        scanf("%c", &Choix);

        if ((Choix=='y')||(Choix=='Y'))
        {
        WAIT();
        printf("Cr%cation de la sauvegarde \n",130);
        fichier = fopen ("C:\\ProgramData\\Grace-SAT\\ACCESS.txt", "w+");

       if (fichier != NULL)
        {
        OK();
        printf("Cr%cation de l'emplacement de sauvegarde r%cussie..\n",130,130);
        WAIT();
        printf("Ecriture du nouveau chemin \n");
        sleep(2);
        if ( (fputs(chemin, fichier)) != EOF )
        {
        fseek(fichier, 0, SEEK_END);
        fputc('\\', fichier);
        OK();
        printf("Ecriture du chemin r%cussie..\n",130);
        fichier = fclose(fichier);
        sleep(2);
        }
        else
        {
        ERREUR();
        printf("Echec de l'%criture du chemin\n",130);
        sleep(2);
        }
        }
        else
        {
        ERREUR();
        printf("Echec de la cr%cation de l'emplacement de sauvegarde\n",130);
        sleep(2);
        }
        }

       }
       }

        else
       {
        SETTING();
        printf("Quel chemin d'acc%cs utiliser ?\n",130);
        Color(7,0);
        printf("Exemple: <<C:\\\\chemin\\\\chemin\\\\...>> \n");
        Color(15,0);
        while( getchar() != '\n');
        scanf("%s[^\n]", &chemin);
        SETTING();
        printf("Sauvegarder le chemin d'acc%cs ?\n",138);
        while( getchar() != '\n');
        scanf("%c", &Choix);

        if ((Choix=='y')||(Choix=='Y'))
        {
        WAIT();
        printf("Cr%cation de la sauvegarde \n",130);
        fichier = fopen ("C:\\ProgramData\\Grace-SAT\\ACCESS.txt", "w+");

       if (fichier != NULL)
        {
        OK();
        printf("Cr%cation de l'emplacement de sauvegarde r%cussie..\n",130,130);
        WAIT();
        printf("Ecriture du nouveau chemin \n");
        sleep(2);
        if ( (fputs(chemin, fichier)) != EOF )
        {
        fseek(fichier, 0, SEEK_END);
        fputc('\\', fichier);
        OK();
        printf("Ecriture du chemin r%cussie..\n",130);
        fichier = fclose(fichier);
        sleep(2);
        }
        else
        {
        ERREUR();
        printf("Echec de l'%criture du chemin\n",130);
        sleep(2);
        }
        }
        else
        {
        ERREUR();
        printf("Echec de la cr%cation de l'emplacement de sauvegarde\n",130);
        sleep(2);
        }
        }

        }




       system("cls");
       SETTING();
       printf("Mode automatique? (y/n)\n");
       if (ADMIN==0){
       while( getchar() != '\n');
       scanf("%c", &Limit);
       sleep(1);
       }
              else
       {
           DEV();
           printf("MODE DEV\n");
           sleep(2);
       }
              if ((Limit=='y')||(Limit=='Y'))
       {
       detection='t';
       SETTING();
       printf("limite de fichier ?\n");
       if (ADMIN==0){
       scanf("%d", &limite);
       }
       else
       {
           DEV();
           printf("MODE DEV\n");
           sleep(2);
       }
       }

       else
       {
       }
       system("cls");
       logs();
       fprintf(inputfile,"[SETTING]>[%s/%s]\n",date,heure);
       WAIT();
       sprintf(log,"[SETTING]>SET_Cooldown:%d SET_Limite:%d SET_Securiter:%d \n", cooldown, limite, securiter);
       fputs(log, inputfile );
       fprintf(inputfile,"[SETTING]>ACCESS:<<%s>>\n\n",chemin);
       printf("D%cbut du test serveur ",130);
       logs();
       fprintf(inputfile,"%s:",heure);
       fputs("[WAIT]>Début du test serveur\n", inputfile );
       Color(10,0);
       printf("ALPHA");
       Color(15, 0);
       printf("...\n\n");

       if ((Limit=='y')||(Limit=='Y'))

       {
       WAIT();
       printf("Lancement de la s%cquence automatique \n",130);
       logs();
       fprintf(inputfile,"%s:",heure);
       fputs("[WAIT]>Lancement de la séquence automatique \n", inputfile );
       }
       else
       {

       }

       sleep(1);
       ProgTest();
}






