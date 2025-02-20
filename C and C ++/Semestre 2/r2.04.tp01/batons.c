#include <stdio.h>
#include <stdlib.h>

#define start 20
#define baton '|'

void affichebaton(int nb, char c);

int saisie(int max);

int saisie(int max) {
    int saisie;
    printf("Entrez le nombre de batons a prendre (1-%d) : ", max);
    scanf("%d", &saisie);
    while (saisie > max || saisie < 1) {
        printf("Erreur, veuillez saisir un nombre entre 1 et %d : ", max);
        scanf("%d", &saisie);
    }
    return saisie;
}

void affichebaton(int nb, char c) {
    for (int i = 0; i < nb; i++) {
        printf("%c", c);
    }
    printf("\n");
}

int main(void) {
    int nbBaton = start, joueur = 0, tirage;
    printf("\nBienvenue dans le jeu des bâtons ! Bâtons de départ : %d\n", start);
    affichebaton(start, baton);
    while (nbBaton > 0) {
        if (joueur == 1 && nbBaton > 0){
            //Joueur
            printf("\nAu tour du joueur !\n");
            if (nbBaton > 3) {
                tirage = saisie(3);
            } else {
                tirage = saisie(nbBaton);
            }
            printf("Le joueur a tirer %d :\n", tirage);
            joueur = 0;
        } else if (nbBaton > 0) {
            printf("\nAu tour de l'ordinateur !\n");
            //Ordinateur
            if (nbBaton > 7){
                tirage = rand() % 3 + 1;
            } else {
                switch (nbBaton){
                    case 1:
                        tirage = 1;
                        break;
                    
                    case 2:
                        tirage = 1;
                        break;

                    case 3:
                        tirage = 2;
                        break;

                    case 4:
                        tirage = 3;
                        break;

                    case 5:
                        tirage = 1;
                        break;

                    case 6:
                        tirage = 1;
                        break;

                    case 7:
                        tirage = 2;
                        break;

                    default:
                        break;
                }          
            }
            printf("L'ordinateur a tirer %d :\n", tirage);
            joueur = 1;
        }
        nbBaton -= tirage;
        affichebaton(nbBaton, baton);
        printf("Il reste maintenant %d Bâtons !\n", nbBaton);
    }
    if (joueur == 1){
        printf("Le joueur a gagné !\n");
    } else {
        printf("L'ordinateur a gagné !\n");
    }
    return 0;
}
