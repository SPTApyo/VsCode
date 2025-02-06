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
        int nbVal, nbOccurrences; // nombre de valeurs a traiter

        //////////////////////////////////////// Jeux de
        //////////////////////////////////////// test//////////////////////////////////////////////////

        // int tab[]={}; //jeu de test n°1
        // nbVal = 0;

        // int tab[]={-1,4,5,-2,-8}; //jeu de test n°12
        // nbVal = 5;

        int tab[] = { 1, 5, 6, 2, 7 }; // jeu de test n°3
        nbVal = 5;

        //////////////////////////////////////// Jeux de
        //////////////////////////////////////// test//////////////////////////////////////////////////

        int temptab[]; // tableaux permettant de stocker les valeurs

        // tab = new int[100];
        temptab = new int[50];
        // nbVal = saisirTableau(tab);

        System.out.println("\nContenu du tableau :");
        afficherTableau(tab, nbVal);
        System.out.println("\nMoyenne du tableau :");
        System.out.println(moyennenegatif(tab, nbVal));
        System.out.println("\nContenu du tableau inverser :");
        inverserTableau(tab, nbVal);
        afficherTableau(tab, nbVal);
        System.out.println("\nContenu du tableau sans doublons:");
        nbOccurrences = eliminerDoublons(tab, nbVal, temptab);
        afficherTableau(temptab, nbOccurrences);
        temptab = new int[50]; // reinitialisation du tableau temporaire
        System.out.println("\nIndices des cases avec la plus grande valeur :");
        nbOccurrences = indicesMax(tab, nbVal, temptab);
        afficherTableau(temptab, nbOccurrences);
    }

    /*
     * Inverse l'ordre du tableau
     *
     * @param pfTab IN : un tableau de valeurs entières
     * 
     * @param pfNbEl IN : le nombre de valeurs
     * 
     *
     */
    public static void inverserTableau(int[] pfTab, int pfNbEl) {
        for (int cpt = 0; cpt < pfNbEl / 2; cpt++) {
            int temp = pfTab[cpt];
            pfTab[cpt] = pfTab[(pfNbEl - 1) - cpt];
            pfTab[(pfNbEl - 1) - cpt] = temp;
        }
    }

    /*
     * Elimine les doublons
     *
     * @param pfTab IN : un tableau de valeurs entières
     * 
     * @param pfNbEl IN : le nombre de valeurs
     * 
     * @param pfTabsansdoublons OUT : le tableau sans doublons
     *
     * @return le nombre de case dans le tableau sans doublons
     */
    public static int eliminerDoublons(int[] pfTab, int pfNbEl, int[] pfTabsansdoublons) {
        int i = 0;
        for (int cpt = 0; cpt < pfNbEl; cpt++) {
            boolean numeroExistant = false;
            for (int j = 0; j < i; j++) {
                if (pfTabsansdoublons[j] == pfTab[cpt]) {
                    numeroExistant = true;
                    break;
                }
            }
            if (!numeroExistant) {
                pfTabsansdoublons[i] = pfTab[cpt];
                i++;
            }
        }
        return i;
    }

    /*
     * Stocke les indices des occurences du maximum
     *
     * @param pfTab IN : un tableau de valeurs entières
     * 
     * @param pfNbEl IN : le nombre de valeurs
     * 
     * @param pfIndices OUT : le tableau des indices
     *
     * @return le nombre d'occurrences du max
     */
    public static int indicesMax(int[] pfTab, int pfNbEl, int[] pfIndices) {
        int max = 0, i = 0;
        for (int cpt = 0; cpt < pfNbEl; cpt++) {
            if (max < pfTab[cpt]) {
                max = pfTab[cpt];
            }
        }
        for (int cpt = 0; cpt < pfNbEl; cpt++) {
            if (max <= pfTab[cpt]) {
                pfIndices[i++] = cpt;
            }
        }
        return i;
    }

    /**
     * Calcule et retourne la moyenne des valeurs d'un tableau
     * 
     * @param pfNb  IN le nombre de d'emplacement dans pfTab
     * @param pfTab IN le tableau contenant les valeurs
     *              prec : pfNb>0
     * 
     * @return la moyenne des valeurs
     */
    public static double moyennenegatif(int pfTab[], int pfNb) {
        boolean erreur = false;
        double moy = 0;
        try {
            maFonctionexception(pfTab, pfNb);
        } catch (Exception e) {
            System.out.println("[CRASH " + e + "] ");
            erreur = true;
        }
        if (erreur == true) {
            System.out.println("Sous fonction moyenne annulee : erreur detectée");
        } else {
            for (int cpt = 0; cpt < pfNb; cpt++) {
                moy = moy + pfTab[cpt];
            }
            moy /= pfNb;
            moy = Math.round(moy * 100.0) / 100.0;
            return moy;
        }
        return 404;
    }

    public static void maFonctionexception(int pfTab[], int pfNb) throws Exception {
        boolean presencenegatif = false;
        if (pfNb <= 0) {
            throw new Exception("Erreur Tableau vide"); // leve une exception si pfnb (longueur du tableau fourni par
                                                        // l'utilisateur) est nul
        } else {
            for (int cpt = 0; cpt < pfNb && presencenegatif == false; cpt++) { // Continue la boucle tant que le tableau
                                                                               // n'a pas ete entierement verifiée et
                                                                               // qu'aucune valeur negative a encore ete
                                                                               // trouvée
                if (pfTab[cpt] < 0) {
                    presencenegatif = true; // presence d'une valeur negative detectée dans le tableau
                }
            }
        }
        if (presencenegatif == false) {
            throw new Exception("Erreur Aucune valeur negative dans le tableau"); // leve une exception si aucune valeur
                                                                                  // negative n'est trouvée dans le
                                                                                  // tableau
        }
    }
}

/****************************************
 * Jeu d'essais pour AlgosTableaux
 *******************************************
 * A vous !!!
 * 
 ****************************************/