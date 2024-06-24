#include <stdio.h>
#include <stdlib.h>

int main()
{
    int reponse;
    do {

        printf ("Donnez moi un nombre et je vous dirait s'il est paire ou impaire\n");   
        scanf ("%d", &reponse);
        int calc = reponse % 2;

        if(calc == 0)
            printf ("Le nombre est paire\n\n");
        else
            printf ("Le nombre impaire\n\n");

    } while (reponse != 0);

    printf ("Fin du programme car la valeur entree = 0 :) \n\n");
}

