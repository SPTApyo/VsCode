import java.util.Scanner;

public class AlgosTableaux {
    /**
     * Demande à l'utilisateur de saisir un entier, jusqu'à ce que
     * l'entier saisi soit entre les deux bornes en paramètres.
     *
     * @param pfBorneInf IN : borne inférieure
     * @param pfBorneSup IN : borne supérieure
     *
     * @return un entier entre pfBorneInf et pfBorneSup, compris
     */
    public static int saisieIntC(int pfBorneInf, int pfBorneSup) {
        int valeur;
        Scanner clavier = new Scanner(System.in);
        System.out.println("Donnez une valeur comprise entre " + pfBorneInf + " et " + pfBorneSup + "?");
        valeur = clavier.nextInt();
        while (valeur < pfBorneInf || valeur > pfBorneSup) {
            System.out.println("Erreur ! Donnez une valeur comprise entre " + pfBorneInf + " et " + pfBorneSup + "?");
            valeur = clavier.nextInt();
        }
        return valeur;
    }

    /**
     * Demande à l'utilisateur :
     * 1. de saisir le nombre de cases qu'il souhaite remplir, et
     * répète l'opération, jusqu'à ce que ce nombre soit
     * acceptable
     * 2. de remplir les cases une à une.
     *
     * @param pfTab OUT : tableau à remplir
     *
     * @return le nombre de cases remplies dans le tableau
     */
    public static int saisirTableau(int[] pfTab) {
        System.out.println("Combien d'emplacement dans le tableau ?");
        int nbEl = saisieIntC(0, 100);
        for (int cpt = 0; cpt < nbEl; cpt++) {
            System.out.println("Quelle valeur pour la case n°" + cpt);
            pfTab[cpt] = saisieIntC(0, 100);
        }

        System.out.println(nbEl + " cases remplies sur " + pfTab.length);
        return nbEl;
    }

    /**
     * Affiche le tableau en paramètre.
     *
     * @param pfTab  IN : tableau
     * @param pfNbEl IN : nombre de cases remplies dans le tableau
     *
     */
    public static void afficherTableau(int[] pfTab, int pfNbEl) {
        for (int cpt = 0; cpt < pfNbEl; cpt++) {
            System.out.println("Case n°" + cpt + " : " + pfTab[cpt]);
        }
    }

    public static void main(String[] args) {
        System.out.println("\n");
        /* Declaration des variables */
        int nbVal; // nombre de valeurs a traiter
        int tab[]; // tableau permettant de stocker les valeurs

        tab = new int[100];
        nbVal = saisirTableau(tab);
        /*
         * System.out.println("\nContenu du tableau :");
         * afficherTableau(tab,nbVal);
         * System.out.println("\nContenu du tableau inverser :");
         * inverserTableau(tab,nbVal);
         * afficherTableau(tab,nbVal);
         */
        System.out.println("\nContenu du tableau sans doublons:");
        eliminerDoublons(tab, nbVal);

        // Ajouter les appels aux algorithmes :
        // - à saisirTableau5
        // - puis à afficherTableau
        // - puis ...

    }

    public static void inverserTableau(int[] pfTab, int pfNbEl) {
        for (int cpt = 0; cpt < pfNbEl / 2; cpt++) {
            int temp = pfTab[cpt];
            pfTab[cpt] = pfTab[(pfNbEl - 1) - cpt];
            pfTab[(pfNbEl - 1) - cpt] = temp;
        }
    }

    public static void eliminerDoublons(int[] pfTab, int pfNbEl) {
        int temptab[];
        temptab = new int[100];
        temptab[1] = pfTab[1];
        boolean numeroExistant;
        int i = 1;
        for (int cpt = 0; cpt < pfNbEl; cpt++) {
            numeroExistant = false;
            for (int cptbis = 0; cptbis < pfNbEl; cptbis++) {
                if (temptab[cptbis] == pfTab[cpt]) {
                    numeroExistant = true;
                }
            }
            if (numeroExistant == false) {
                i++;
                temptab[i] = pfTab[cpt];

            }

        }
        System.out.println("\nContenu du tableau sans doublons:");
        afficherTableau(temptab, i);

    }

    /*
     * public static int indicesMax(int[] pfTab, int pfNbEl, int[] pfIndices){
     * 
     * int[] indices = new int[10] ;
     * int[] tab = {1,15,-2,15,6};
     * int nbOccurrences ;
     * nbOccurrences = indicesMax(tab, tab.length, indices);
     * afficherTableau(indices, nbOccurrences);
     * 
     * }
     */
}

/****************************************
 * Jeu d'essais pour AlgosTableaux
 *******************************************
 * A vous !!!
 * 
 ****************************************/