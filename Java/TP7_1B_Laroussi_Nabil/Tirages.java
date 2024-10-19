import java.util.Scanner;

public class Tirages {//TestFonctionPrincipale reutiliser pour la suite

    /**
     * Programme principal, le paramètre du main n'étant pas utilisé ici nous ne
     * commentons pas,
     * il devra l'être en cas d'utilisation (cf. suite)
     */
    public static void main(String[] args) {
        int maxrandom = 10;
        int largeur;
        int tab[];
        tab = new int[maxrandom];
        if (args.length != 0) {
            largeur = Integer.parseInt(args[0]);
            if (largeur < 0) {
                largeur = saisieIntMin(0);
            }
        } else {
            largeur = saisieIntMin(0);
        }
        int iteration = largeur * largeur;
        randomGen(maxrandom, tab, iteration, largeur);
        afficherTableauChaine(tab);
    }
        /**
     * Affiche contenu de la table
     * @param temptab IN Tableau a afficher
     */
    public static void afficherTableauChaine(int[] temptab) {
        System.out.println("Totaux :");
        for (int cpt = 0; cpt < (temptab.length - 1); cpt++) {
            System.out.println("Nombre de " + cpt + " : " + temptab[cpt]);
        }
    }
        /**
     * Crée la matrice en generant les chiffres aleatoire 
     * @param max IN Tableau a afficher
     * @param temptab OUT Tableau ou sont stocker le compte des chiffres aleatoire
     * @param iteration IN Nombre de chiffre aleatoire a generer
     * @param largeur IN Largeur de la matrice 
     */
    public static void randomGen(int max, int[] temptab, int iteration, int largeur) {
        String matrice = "";
        int cptmatrice = 1;
        for (int cpt = 0; cpt < iteration; cpt++) {
            int randomtomax = (int) Math.round(Math.random() * (max - 1));
            temptab[randomtomax]++;
            matrice = matrice + randomtomax;
            cptmatrice++;
            if (cptmatrice > largeur) {
                matrice = matrice + "\n";
                cptmatrice = 1;
            }
        }
        System.out.println("\nMatrice :\n" + matrice);
    }
    /**
     * Retourne une valeur entière saisie au clavier superieur a une valeur
     * 
     * @param pfMin IN la valeur minimale
     * @return valeur entière comprise entre pfMin et pfMax (inclus)
     */
    public static int saisieIntMin(int pfMin) {
        int saisie;
        Scanner clavier = new Scanner(System.in);
        System.out.println("Pour N x N tirages aleatoires : choisissez N ? (minimum : " + pfMin + ") :");
        saisie = clavier.nextInt();
        while (saisie < pfMin) {
            System.out.println("Pour N x N tirages aleatoires : choisissez N ? (minimum : " + pfMin + ") :");
            saisie = clavier.nextInt();
        }
        clavier.close();
        return saisie;
    }
}
