#ifndef CALCULPOURCENT_H_INCLUDED
#define CALCULPOURCENT_H_INCLUDED

void CalculPourcent(void)
{
    int pourcent=0;
    pourcent=nbfile*100;
    pourcent=pourcent/numero;
    ERREUR();
    printf("Donn%ce perdue estim%ce a %d %%\n",130, 130, pourcent);
    logs();
    fprintf(inputfile,"%s:",heure);
    fprintf(inputfile,"Donn�e perdue estim�e a %d %%\n",pourcent);
    pourcent=100-pourcent;
    OK();
    printf("int%cgrit%c des donn%ces estim%ce a %d %%\n\n", 130, 130, 130, 130, pourcent);
    logs();
    fprintf(inputfile,"%s:",heure);
    fprintf(inputfile,"int�grit� des donn�es estim�e a %d %%\n\n",pourcent);
    pourc=pourcent;
}
#endif // CALCULPOURCENT_H_INCLUDED

