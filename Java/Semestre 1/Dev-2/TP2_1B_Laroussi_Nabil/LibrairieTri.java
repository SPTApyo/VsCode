
/**
 * Indiquer le ou les numeros de TP et d'exercice.
 *
 * @author Nabil Laroussi
 */
import java.io.*;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Scanner;
public class LibrairieTri
{
    public static void saisieTableau(int pfNbElements, TNP pfTNP) {
        Scanner clavier = new Scanner(System.in);
        pfTNP.nbElts = pfNbElements;
        if (pfNbElements > pfTNP.tailleMax) {
            System.out.println("Erreur : nombre d'éléments trop grand");
            return;
        }
        for (int i = 0; i < pfNbElements; i++) {
            System.out.print("Entrez la valeur " + (i + 1) + ": ");
            double valeur = clavier.nextDouble(); 
            pfTNP.tab[i] = valeur;
        }
    }

    public static void afficherTableau(TNP pfTNP){
        for ( int i = 0; i < pfTNP.nbElts; i++){
            System.out.println(pfTNP.tab[i]);
        }
    }

    public static int maxST( int pfdeb, int pffin, TNP pfTNP){
        int rangMax = pfdeb;
        for( int i = pfdeb +1; i <= pffin; i++) {
            if (pfTNP.tab[i] > pfTNP.tab[rangMax]){
                rangMax = i;
            }
        }
        return rangMax;  
    }


    public static void echange( int pfI, int pfJ, TNP pfTNP){
        double echange;
        echange = pfTNP.tab[pfI];
        pfTNP.tab[pfI] = pfTNP.tab[pfJ];
        pfTNP.tab[pfJ] = echange;
    }

    public static void TriMax(TNP pfTNP){
        int fin = pfTNP.nbElts - 1;
        int rangM ;

        while (fin >= 1){
            rangM = maxST(0, fin, pfTNP);
            echange( rangM, fin, pfTNP);
            fin --;
        }
    }

    public static void sauverTableau(TNP pfTNP, String pfNomFichier) {
        try {
            PrintStream out = new PrintStream(new File(pfNomFichier));
            NumberFormat fr = NumberFormat.getInstance(Locale.FRENCH);
            for (int i = 0; i < pfTNP.nbElts; i++) {
                out.println(fr.format(pfTNP.tab[i])); 
            }

            out.close();
        } catch (IOException e) {
            System.out.println("Erreur lors de l'écriture dans le fichier: " + e.getMessage());
        }
    }

    public static void chargerTableau(TNP pfTNP, int pfNbElements, String pfNomFichier) {
        try {
            Scanner lecteur = new Scanner(new File(pfNomFichier));
            for (int i = 0; i < pfNbElements; i++) {
                if (lecteur.hasNextDouble()) {
                    pfTNP.tab[i] = lecteur.nextDouble();
                }
            }
            pfTNP.nbElts = pfNbElements;
            lecteur.close();
        } catch (IOException e) {
            System.out.println("Erreur lors de la lecture du fichier: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        String nomfich = "FichierTab";
        int nbelem = 10;
        TNP tab = new TNP(100);
        saisieTableau(nbelem, tab);
        sauverTableau(tab, nomfich);
        chargerTableau(tab, nbelem, nomfich);
        TriMax(tab);
        afficherTableau(tab);
        for (int i = 0; i < tab.nbElts; i++) {
            System.out.println("Élément " + i + ": " + tab.tab[i]);
        }
    } 
}

