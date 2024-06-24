#ifndef COULEUR_H_INCLUDED
#define COULEUR_H_INCLUDED

void Color(int couleurDuTexte,int couleurDeFond) // fonction d'affichage de couleurs
{
        HANDLE H=GetStdHandle(STD_OUTPUT_HANDLE);
        SetConsoleTextAttribute(H,couleurDeFond*16+couleurDuTexte);
}

#endif // COULEUR_H_INCLUDED


