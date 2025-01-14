#ifndef CARACTEREALEATOIRE_H_INCLUDED
#define CARACTEREALEATOIRE_H_INCLUDED

void random(void)
{
    int boucle=0,boucle2=0;
    int a = 14;
    int b = 11;
    int i = 0;
    while(boucle!=40){

    srand(time(NULL));
    for(i='a';i<'z';i++)
    boucle2=0;
   while(boucle2!=6){
    sprintf(writes,"%s%c",writes,rand()%100+'A');
    sprintf(writes,"%s%c",writes,rand()%100+'A');
    sprintf(writes,"%s%c",writes,rand()%100+'A');
    sprintf(writes,"%s%c",writes,rand()%100+'A');
    sprintf(writes,"%s%c",writes,rand()%100+'A');
    sprintf(writes,"%s%c",writes,rand()%100+'A');
    boucle2++;
   }
    sprintf(writes,"%s\n",writes);

    boucle++;

    }
    }


#endif // CARACTEREALEATOIRE_H_INCLUDED
