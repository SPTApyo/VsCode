#ifndef DIRECTORY_H_INCLUDED
#define DIRECTORY_H_INCLUDED

void directory(void)
{

char *dirname, log[500];
dirname = "C:\\ProgramData\\Grace-SAT";
WAIT();
printf("V%crification de la pr%csence du r%cpertoire de donn%ce\n",130,130,130,130);
sleep(2);
 if ( opendir(dirname) == NULL )
 {
   WAIT();
   printf("R%cpertoire absent\n",130,130);
   WAIT();
   printf("Cr%cation du nouveau r%cpertoire\n",130,130);
   mkdir(dirname);
   }
      if ( mkdir(dirname) == NULL )

      {
       ERREUR();
       printf("Echec de la cr%cation du nouveau r%cpertoire\n", 130, 130);
       ERREUR();
       printf("Fermeture du programme dans 5 secondes veuillez contacter le cr%cateur\n",130);
       sleep(5);
       return 0;
      }
      else
      {


       OK();
       printf("Pr%csence du r%cpertoire\n", 130, 130, 130);

       sleep(2);
       dirname = "C:\\ProgramData\\Grace-SAT\\Grace-SAT-LOGS";
WAIT();
printf("V%crification de la pr%csence du r%cpertoire de LOGS\n",130,130,130,130);
sleep(2);
 if ( opendir(dirname) == NULL )
 {
   WAIT();
   printf("R%cpertoire absent\n",130,130);
   WAIT();
   printf("Cr%cation du nouveau r%cpertoire\n",130,130);
   mkdir(dirname);
      if ( mkdir(dirname) == NULL )

      {
       ERREUR();
       printf("Echec de la cr%cation du nouveau r%cpertoire\n", 130, 130);
       ERREUR();
       printf("Fermeture du programme dans 5 secondes veuillez contacter le cr%cateur\n",130);
       sleep(5);
       return 0;
      }
      else
      {
      }
       OK();
       printf("Cr%cation du r%cpertoire des LOGS r%cussie\n",130,130,130);
       sleep(2);
       system("cls");

 }



  else
   {
    logs();
    sprintf(log,"C:\\ProgramData\\Grace-SAT\\Grace-SAT-LOGS\\logfile %s%s.txt",date,heure);
    OK();
    printf("R%cpertoire pr%csent\n",130,130);
    inputfile = fopen(log,"w+");

     if (inputfile != NULL)
     {
         OK();
         printf("Cr%cation du fichier logfile;%s%s.txt r%cussie", 130,date,heure, 130);
         sleep(2);
         system("cls");
     }
     else
     {
       ERREUR();
       printf("Echec de la cr%cation du fichier LOGS\n", 130, 130);
       ERREUR();
       printf("Fermeture du programme dans 5 secondes veuillez contacter le cr%cateur\n",130);
       sleep(5);
       return 0;
     }
   }

   }

}
#endif // DIRECTORY_H_INCLUDED
