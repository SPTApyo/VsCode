/**
 * Indiquer le ou les numeros de TP et d'exercice.
 *
 * @author (Nabil Laroussi)
 */

import java.util.Scanner;

public class Sauts {
    /**
     * Retourne une valeur entière saisie au clavier comprise entre
     * deux valeurs
     * 
     * @param pfMin IN la valeur minimale
     * @return valeur entière supérieur pfMin
     * 
     */
    public static double saisieDoubleMin(double pfMin) {
        double saisie;
        Scanner clavier = new Scanner(System.in);
        System.out.println("Donnez une valeur au moins égale à " + pfMin + " :");
        saisie = clavier.nextDouble();
        while (saisie < pfMin) {
            System.out.println("Donnez une valeur au moins égale à " + pfMin + " :");
            saisie = clavier.nextDouble();
        }
        return saisie;
    }

    /**
     * Retourne une valeur entière saisie au clavier comprise entre
     * deux valeurs
     * 
     * @param pfMin IN la valeur minimale
     * @param pfMax IN la valeur maximale
     * @return valeur entière comprise entre pfMin et pfMax (inclus)
     */
    public static int saisieIntMinMax(int pfMin, int pfMax) {
        int saisie;
        Scanner clavier = new Scanner(System.in);

        System.out.println("Donnez une valeur (nombre de sauts) comprise entre " + pfMin + " et " + pfMax + " :");
        saisie = clavier.nextInt();
        while (saisie < pfMin || saisie > pfMax) {
            System.out.println("Donnez une valeur (nombre de sauts) comprise entre " + pfMin + " et " + pfMax + " :");
            saisie = clavier.nextInt();
        }
        return saisie;
    }

    /**
     * Saisit nbSaisies réels positifs ou nuls au clavier et les stocke dans le
     * tableau tab
     * 
     * @param pfNbSaisies IN nombre de réels à saisir
     * @param pfTab       OUT le tableau où stocker les saisies
     */
    public static void saisieTabD(int pfNbSaisies, double pfTab[]) {
        int i;
        Scanner clavier = new Scanner(System.in);
        for (i = 0; i < pfNbSaisies; i++) {
            System.out.println("Valeur n°" + (i + 1) + " :");
            pfTab[i] = saisieDoubleMin(0);
        }
    }

    /**
     * Calcule et retourne la moyenne des sauts
     * 
     * @param pfNbSauts IN le nombre de sauts
     * @param pfTab     IN le tableau contenant les valeurs de chaque saut
     *                  prec : pfNbSauts>0
     *                  
     * @return la moyenne des sauts
     */
    public static double moyenneSauts(int pfNbSauts, double pfTab[]) {
        double moy= 0;
        for(int cpt=0; cpt<pfNbSauts; cpt++){
            moy = moy + pfTab[cpt];
        }
        moy/=pfNbSauts;
        return moy;
    }

    /**
     * Prend la plus grande valeur du tableau pfTab[] et la retourne
     * 
     * @param pfNbSauts IN le nombre de saut
     * @param pfTab[] IN le tableau contenant les valeurs de chaque saut
     * 
     * @return le meilleur saut dans la variable double max
     * 
     */
    public static double meilleursaut(int pfNbSauts, double pfTab[]) {
        double max = 0;
        for(int cpt=0; cpt<pfNbSauts; cpt++){
            if (max <=pfTab[cpt]){
                max = pfTab[cpt];
            }
        }
        return max;
    }

    /**
     * Prend la plus petite valeur du tableau pfTab[] et la retourne
     * 
     * @param pfNbSauts IN le nombre de saut
     * @param pfTab[] IN le tableau contenant les valeurs de chaque saut
     * 
     * @return le pire saut dans la variable double min
     * 
     */
    public static double piresaut(int pfNbSauts, double pfTab[]) {
        double min = pfTab[0];
        for(int cpt=0; cpt<pfNbSauts; cpt++){
            if (min >=pfTab[cpt]){
                min = pfTab[cpt];
            }
        }
        return min;
    }

    /**
     * Programme principal :
     * - saisit tous les sauts
     * - calcule et affiche la moyenne des sauts
     * - calcule et affiche le meilleur saut
     * - calcule et affiche le pire saut
     */
    public static void main(String[] args) {
        int nbMax = saisieIntMinMax(0,15);
        double tabSaut[], affichage;
        tabSaut = new double[15];

        /* --- Saisie des sauts --- */
        saisieTabD(nbMax,tabSaut);

        if (nbMax>0){
            /* --- Moyenne des sauts --- */
            affichage = moyenneSauts(nbMax,tabSaut);
            System.out.println("Moyenne des sauts : "+affichage+" m");
            
            /* --- Meilleur saut --- */
            affichage = meilleursaut(nbMax,tabSaut);
            System.out.println("Meilleur saut : "+affichage+" m");

            /* --- Pire saut --- */
            affichage = piresaut(nbMax,tabSaut);
            System.out.println("Pire saut : "+affichage+" m");
        }else{
            System.out.println("aucun saut effectué ..");
        }

    }
}