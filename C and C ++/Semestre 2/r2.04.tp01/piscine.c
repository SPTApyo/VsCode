#include <stdio.h>

int main(void) {
    int age;
    char choix;
    float montant;
    printf("Quel age avez-vous ?\n");
    scanf("%d",&age);
    if (age <= 5){
        printf("Votre entrée est gratuite !");
        return 0;
    } else if (age >= 6 && age <= 16){
        montant = 3;
    } else if (age > 16 && age < 60) {
        montant = 6;
    } else {
        montant = 4.5;
    }
    printf("Avez-vous une carte de fidelitée ? - (o-O)\n");
    scanf(" %c",&choix);
    if (choix == 'o' || choix == 'O'){
        printf("Vous devez payer %.2f€", montant-2);
    } else {
        printf("Vous devez payer %.2f€\nMais vous auriez pu payer %.2f€ avec la carte de fidelitée", montant , montant-2);
    }
}