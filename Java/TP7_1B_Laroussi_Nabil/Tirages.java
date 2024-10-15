import java.util.Scanner;

public class Tirages {

    /**
     * Programme principal, le paramètre du main n'étant pas utilisé ici nous ne
     * commentons pas,
     * il devra l'être en cas d'utilisation (cf. suite)
     */
    public static void main(String[] args) {
        int maxrandom = 10;
        int largeur = 0;
        int tab[];
        tab = new int[maxrandom];
        if (args.length != 0) {
            largeur = Integer.parseInt(args[0]);
            if (largeur < 0) {
                largeur = saisieIntMinMax(0);
            }
        } else {
            largeur = saisieIntMinMax(0);
        }
        int iteration = largeur * largeur;
        randomGen(maxrandom, tab, iteration, largeur);
        afficherTableauChaine(tab);
    }

    public static void afficherTableauChaine(int[] tab) {
        System.out.println("Totaux :");
        for (int cpt = 0; cpt < (tab.length - 1); cpt++) {
            System.out.println("Nombre de " + cpt + " : " + tab[cpt]);
        }
    }

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

    public static int saisieIntMinMax(int pfMin) {
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
