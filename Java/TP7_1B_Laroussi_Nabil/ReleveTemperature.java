import java.util.Scanner;

public class ReleveTemperature {// TestFonctionPrincipale reutiliser pour la suite

    /**
     * Programme principal, le paramètre du main n'étant pas utilisé ici nous ne
     * commentons pas,
     * il devra l'être en cas d'utilisation (cf. suite)
     */
    public static void main(String[] args) {
        double tab[];
        tab = new double[60];
        System.out.println("Combien de mesures sur 30 jours ?");
        int nbMesures = saisieIntMinMax(0, 60);
        if (nbMesures <= 0){
            System.out.println("Aucune valeur enregistrée");
            System.exit(0);
        }
        //randomGen(30, tab, nbMesures);
        saisirTableau(tab , nbMesures);
        System.out.println("La moyenne de temperature sur 30 jours est de : " + moyenne(nbMesures, tab) + "\nAvec Temp max : "
                + max(nbMesures, tab) + " et Temp min : " + min(nbMesures, tab));
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
    public static double moyenne(int pfNb, double pfTab[]) {
        double moy = 0;
        for (int cpt = 0; cpt < pfNb; cpt++) {
            moy = moy + pfTab[cpt];
        }
        moy /= pfNb;
        moy = Math.round(moy * 100.0) / 100.0;
        return moy;
    }

    /**
     * Prend la plus grande valeur du tableau pfTab[] et la retourne
     * 
     * @param pfNb    IN le nombre de d'emplacement dans pfTab
     * @param pfTab[] IN le tableau contenant les valeurs
     *                  prec : pfNb>0
     * @return Valeur la plus haute du tableau
     * 
     */
    public static double max(int pfNb, double pfTab[]) {
        double max = 0;
        for (int cpt = 0; cpt < pfNb; cpt++) {
            if (max <= pfTab[cpt]) {
                max = pfTab[cpt];
            }
        }
        return max;
    }

    /**
     * Prend la plus petite valeur du tableau pfTab[] et la retourne
     * 
     * @param pfNb    IN le nombre de d'emplacement dans pfTab
     * @param pfTab[] IN le tableau contenant les valeurs
     *                  prec : pfNb>0
     * @return Valeur la plus basse du tableau
     * 
     */
    public static double min(int pfNb, double pfTab[]) {
        double min = pfTab[0];
        for (int cpt = 0; cpt < pfNb; cpt++) {
            if (min >= pfTab[cpt]) {
                min = pfTab[cpt];
            }
        }
        return min;
    }

    /**
     * Genere plusieurs valeurs aleatoire pour le remplissage du tableau
     * 
     * @param max       IN Valeur maximale generer
     * @param pfNb      IN Nombre maximum de valeurs dans le tableau
     * @param temptab[] OUT Tableau remplie par la fonction
     *                  prec : pfNb>0
     */             
    public static void randomGen(int max, double temptab[], int pfNb) {
        for (int cpt = 0; cpt < pfNb; cpt++) {
            temptab[cpt] = Math.round((Math.random() * (max - 1)) * 100.0) / 100.0;
        }
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

        System.out.println("comprise entre " + pfMin + " et " + pfMax + " :");
        saisie = clavier.nextInt();
        while (saisie < pfMin || saisie > pfMax) {
            System.out.println("comprise entre " + pfMin + " et " + pfMax + " :");
            saisie = clavier.nextInt();
        }
        return saisie;
    }
    /**
     * Demande à l'utilisateur :
     * 1. de saisir le nombre de cases qu'il souhaite remplir, et
     * répète l'opération, jusqu'à ce que ce nombre soit
     * acceptable
     * 2. de remplir les cases une à une.
     * 
     * @param nbEl IN : Nombre d'emplacement a remplir dans le tableau
     * @param pfTab OUT : tableau à remplir
     *
     * @return le nombre de cases remplies dans le tableau
     */
    public static int saisirTableau(double[] pfTab, int nbEl) {
        for (int cpt = 0; cpt < nbEl; cpt++) {
            System.out.println("Quelle valeur pour la case n°" + cpt);
            pfTab[cpt] = saisieIntMinMax(0, 30);
        }

        System.out.println(nbEl + " cases remplies sur " + pfTab.length);
        return nbEl;
    }
}
