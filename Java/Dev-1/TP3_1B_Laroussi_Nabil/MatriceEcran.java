/**
 * Indiquer le ou les numeros de TP et d'exercice.
 *
 * @author (votre nom)
 */
public class MatriceEcran
{
    /**
     *
     * @return une chaîne de caractères représentant une matrice
     */
    public static String genererStringMatrice() {
        int hauteur = 10;
        int compteurLigne=hauteur;
        String matrice="";
        while(compteurLigne>0){
        matrice= matrice + genererStringLigne()+"\n";
        compteurLigne=compteurLigne-1;
        }
        return matrice;
    }

    /**
     *
     * @return une chaîne de caractères représentant une ligne de la matrice
     */
    public static String genererStringLigne() {
        int largeur=10;
        int compteurColonne=largeur;
        String ligne="";
        while(compteurColonne>0){
        ligne=ligne+"*";
        compteurColonne= compteurColonne-1;
        }
        return ligne;
    }

    /**
     *
     * Affiche une matrice à l'écran
     */
    public static void printMatrice() {
        String maMatrice = genererStringMatrice();
        System.out.print(maMatrice);
    }
}

