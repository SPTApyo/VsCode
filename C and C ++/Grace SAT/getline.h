#ifndef GETLINE_H_INCLUDED
#define GETLINE_H_INCLUDED

void logs(void) {
    char seconde[48],minute[48],heur[48],jour[48],mois[48],annee[48];
    time_t secondes;
    struct tm instant;

    time(&secondes);
    instant=*localtime(&secondes);

    sprintf(seconde,"%d",instant.tm_sec);
    sprintf(minute,"%d",instant.tm_min);
    sprintf(heur,"%d",instant.tm_hour);

if (strlen(seconde)<2)
 {
     sprintf(seconde,"0%d",instant.tm_sec);
 }
  if (strlen(minute)<2)
 {
     sprintf(minute,"0%d",instant.tm_min);
 }
  if (strlen(heur)<2)
 {
     sprintf(heur,"0%d",instant.tm_hour);
 }
     sprintf(jour,"%d",instant.tm_mday);
    sprintf(mois,"%d",instant.tm_mon+1);
    sprintf(annee,"%d",instant.tm_year-100);

if (strlen(jour)<2)
 {
    sprintf(jour,"0%d",instant.tm_mday);
 }
  if (strlen(mois)<2)
 {
    sprintf(mois,"0%d",instant.tm_mon);
 }
  if (strlen(annee)<2)
 {
    sprintf(annee,"0%d",instant.tm_year-100);
 }





    sprintf(date,"[%s_%s_%s]",jour, mois, annee);
    sprintf(heure,"[%s;%s;%s]",heur, minute, seconde);

    }


#endif // GETLINE_H_INCLUDED
