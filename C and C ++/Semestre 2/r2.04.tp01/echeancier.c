#include <stdio.h>
#include <stdbool.h>

int main(void) {
    int mois, annee;
    float rst, interet, mensualite;
    
    printf("Entrez le mois et l'année : ");
    scanf("%d %d", &mois, &annee);
    printf("Entrez le restant dû : ");
    scanf("%f", &rst);
    printf("Entrez le taux d'interet (%%) : ");
    scanf("%f", &interet);
    interet /= 100;
    printf("Entrez la mensualité : ");
    scanf("%f", &mensualite);

    printf("\n Mois | Année | Restant dû  | Intérêt du mois | Remboursement | Mensualité \n");
    printf("---------------------------------------------------------------------------\n");

    while (rst > 0) {
        float interet_du_mois = rst * interet;
        float remboursement = mensualite - interet_du_mois;
        if (mensualite > rst + interet_du_mois) {
            remboursement = rst;
            mensualite = rst + interet_du_mois;
        }
        rst -= remboursement;
        printf("%02d/%d | %.2f | %.2f | %.2f | %.2f\n", mois, annee, rst, interet_du_mois, remboursement, mensualite);
        if (mois >= 12) {
            annee++;
            mois = 1;
        } else {
            mois++;
        }
    }

    return 0;
}