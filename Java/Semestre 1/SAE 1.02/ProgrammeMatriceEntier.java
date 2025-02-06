/**
 * R1.01-Dev2 - TP#6
 *
 * @author Tilian HURÉ
 */
public class ProgrammeMatriceEntier {
    /**
     * Créée une matrice d'entiers.
     * @param pfMatriceEntier OUT : matrice à créer
     * @param pfNbLignes IN : nombre de lignes de la matrice
     * @param pfNbColonnes IN : nombre de colonnes de la matrice
     */
    public static void creerMatrice(MatriceEntier pfMatriceEntier, int pfNbLignes, int pfNbColonnes) throws Exception {
        if ((pfNbLignes > 0) & (pfNbColonnes > 0)) {
            try {
                pfMatriceEntier = new MatriceEntier(pfNbLignes, pfNbColonnes);
            } catch (Exception e) {
                System.out.println(e);
            }
        } else {
            throw new Exception("Les nombres de lignes et de colonnes doivent être " +
                "supérieurs à 0.");
        }
    }
    
    /**
     * Renvoie le nombre de lignes d'une matrice d'entiers.
     * @param pfMatriceEntier IN : matrice d'entiers
     * @return le nombre de lignes de pfMatriceEntier
     */
    public static int getNbLignes(MatriceEntier pfMatriceEntier) {
        return pfMatriceEntier.nbL;
    }
    
    /**
     * Renvoie le nombre de colonnes d'une matrice d'entiers.
     * @param pfMatriceEntier IN : matrice d'entiers
     * @return le nombre de colonnes de pfMatriceEntier
     */
    public static int getNbColonnes(MatriceEntier pfMatriceEntier) {
        return pfMatriceEntier.nbC;
    }
    
    /**
     * Renvoie l'élément d'une matrice en fonction de la ligne et de la colonne données.
     * @param pfMatriceEntier IN : matrice d'entiers
     * @param pfLigne IN : indice de la ligne
     * @param pfColonne IN : indice de la colonne
     * @return l'élément de pfMatriceEntier d'indice pfLigne, pfColonne
     */
    public static int getElement(MatriceEntier pfMatriceEntier, int pfLigne, int pfColonne) throws Exception {
        int element = 0;
        if ((0 <= pfLigne && pfLigne < getNbLignes(pfMatriceEntier)) &&
            (0 <= pfColonne && pfColonne < getNbColonnes(pfMatriceEntier))) {
            element = pfMatriceEntier.tabMat[pfLigne][pfColonne];
        } else {
            throw new Exception("Les indices de la ligne et de la colonne doivent être " +
                "compris entre 0 et les indices maximals des lignes/colonnes.");
        }
        return element;
    }
    
    /**
     * Additionne les entiers de la ligne donnée d'une matrice d'entiers.
     * @param pfMatriceEntier IN : matrice d'entiers
     * @param pfLigne IN : ligne d'entier à additionner
     * @return la somme des entiers de pfLigne de pfMatriceEntier
     */
    public static int somLigne(MatriceEntier pfMatriceEntier, int pfLigne) throws Exception {
        int somme = 0;
        if (0 <= pfLigne && pfLigne < getNbLignes(pfMatriceEntier)) {
            for (int colonne=0; colonne<getNbColonnes(pfMatriceEntier); colonne++) {
                somme += getElement(pfMatriceEntier, pfLigne, colonne);
            }            
        } else {
            throw new Exception("L'indice de la ligne doit être " +
                "compris entre 0 et l'indice maximal des lignes.");
        }
        return somme;
    }
    
    /**
     * Additionne les entiers de la colonne donnée d'une matrice d'entiers.
     * @param pfMatriceEntier IN : matrice d'entiers
     * @param pfColonne IN : colonne d'entier à additionner
     * @return la somme des entiers de pfColonne de pfMatriceEntier
     */
    public static int somColonne(MatriceEntier pfMatriceEntier, int pfColonne) throws Exception {
        int somme = 0;
        if (0 <= pfColonne && pfColonne < getNbColonnes(pfMatriceEntier)) {
            for (int ligne=0; ligne<getNbLignes(pfMatriceEntier); ligne++) {
                somme += getElement(pfMatriceEntier, ligne, pfColonne);
            }
        } else {
            throw new Exception("L'indice de la colonne doit être " +
                "compris entre 0 et l'indice maximal des colonnes.");
        }
        return somme;
    }
    
    /**
     * Vérifie si une matrice d'entier est carrée.
     * @param pfMatriceEntier IN : matrice d'entiers
     * @return true si pfMatriceEntier est carrée, sinon false
     */
    public static boolean estCarree(MatriceEntier pfMatriceEntier) {
        return pfMatriceEntier.nbC == pfMatriceEntier.nbL;
    }
    
    /**
     * Vérifie si une matrice d'entier est diagonale.
     * @param pfMatriceEntier IN : matrice d'entiers
     * @return true si pfMatriceEntier est diagonale, sinon false
     */
    public static boolean estDiagonale(MatriceEntier pfMatriceEntier) throws Exception {
        boolean diagonale = true;
        int ligne = 0, colonne = 0, j = 0;
        if (estCarree(pfMatriceEntier)) {
            while ((ligne < getNbLignes(pfMatriceEntier)) && diagonale) {
                for (int i=0; i<getNbLignes(pfMatriceEntier); i++) {
                    if ((i != ligne && j != colonne) && (pfMatriceEntier.tabMat[ligne][i] != 0) &&
                        (getElement(pfMatriceEntier, i, colonne) != 0)) {
                        diagonale = false;
                    }
                }
                ligne++;
                colonne++;
            }
        } else {
            throw new Exception("La matrice doit être carrée.");
        }
        return diagonale;
    }
    
    /**
     * Met à jour l'élément d'une matrice d'entiers en fonction de la ligne et de la colonne données.
     * @param pfMatriceEntier IN/OUT : matrice d'entiers
     * @param pfLigne IN : ligne de l'élément à modifier
     * @param pfColonne IN : colonne de l'élément à modifier
     * @param pfValeur IN : valeur de l'élément à modifier
     */
    public static void setElement(MatriceEntier pfMatriceEntier, int pfLigne, int pfColonne, int pfValeur) throws Exception {
        if ((0 <= pfLigne && pfLigne < getNbLignes(pfMatriceEntier)) &&
            (0 <= pfColonne && pfColonne < getNbColonnes(pfMatriceEntier))) {
            pfMatriceEntier.tabMat[pfLigne][pfColonne] = pfValeur;
        } else {
            throw new Exception("Les indices de la ligne et de la colonne doivent être " +
                "compris entre 0 et les indices maximals des lignes/colonnes.");
        }
    }
    
    /**
     * Construit la première diagonale d'une matrice d'entiers en fonction d'e la valeur donnée.
     * @param pfMatriceEntier IN/OUT : matrice d'entiers
     * @param pfValeur IN : valeur des éléments à modifier dans la diagonale
     */
    public static void setPremiereDiagonale(MatriceEntier pfMatriceEntier, int pfValeur) throws Exception {
        if (estCarree(pfMatriceEntier)) {
            for (int i=0; i<pfMatriceEntier.nbL; i++) {
                pfMatriceEntier.tabMat[i][i] = pfValeur;
            }
        } else {
            throw new Exception("La matrice doit être carrée.");
        }
    }
    
    /**
     * Construit la seconde diagonale d'une matrice d'entiers en fonction d'e la valeur donnée.
     * @param pfMatriceEntier IN/OUT : matrice d'entiers
     * @param pfValeur IN : valeur des éléments à modifier dans la diagonale
     */
    public static void setSecondeDiagonale(MatriceEntier pfMatriceEntier, int pfValeur) throws Exception {
        int colonne = pfMatriceEntier.nbC - 1;
        if (estCarree(pfMatriceEntier)) {
            for (int ligne=0; ligne<pfMatriceEntier.nbL; ligne++) {
                pfMatriceEntier.tabMat[ligne][colonne] = pfValeur;
                colonne--;
            }
        } else {
            throw new Exception("La matrice doit être carrée.");
        }
    }
    
    /**
     * Multiplie tous les éléments d'une matrice d'entiers par la valeur donnée.
     * @param pfMatriceEntier IN/OUT : matrice d'entiers
     * @param pfValeur IN : valeur de la multiplication
     */
    public static void mulMatNombre(MatriceEntier pfMatriceEntier, int pfValeur) {
        for (int ligne=0; ligne<pfMatriceEntier.nbL; ligne++) {
            for (int colonne=0; colonne<pfMatriceEntier.nbC; colonne++) {
                pfMatriceEntier.tabMat[ligne][colonne] *= pfValeur;
            }
        }
    }
    
    /**
     * Convertie une matrice d'entier en une chaîne de caractères.
     * @param pfMatriceEntier IN : matrice d'entiers
     * @return pfMatriceEntier sous forme de chaîne de caractères
     */
    public static String toString(MatriceEntier pfMatriceEntier) {
        String chaine = "";
        for (int ligne=0; ligne<getNbLignes(pfMatriceEntier); ligne++) {
            for (int colonne=0; colonne<getNbColonnes(pfMatriceEntier); colonne++) {
                try {
                    chaine += getElement(pfMatriceEntier, ligne, colonne) + " ";
                } catch(Exception e) {
                    System.out.println(e);
                }
            }
            chaine += System.getProperty("line.separator");
        }
        return chaine;
    }
    
    /**
     * Convertie une matrice d'entier en une chaîne de caractères formatée en HTML.
     * @param pfMatriceEntier IN : matrice d'entiers
     * @return pfMatriceEntier sous forme de chaîne de caractères formatée en HTML
     */
    public static String toHTML(MatriceEntier pfMatriceEntier) {
        String ln = System.getProperty("line.separator"),
            chaine = "<table border=\"1\">" + ln;
        for (int ligne=0; ligne<getNbLignes(pfMatriceEntier); ligne++) {
            chaine += "<tr>";
            for (int colonne=0; colonne<getNbColonnes(pfMatriceEntier); colonne++) {
                try {
                    chaine += "<td>" + getElement(pfMatriceEntier, ligne, colonne) + "</td>";
                } catch(Exception e) {
                    System.out.println(e);
                }
            }
            chaine += "</tr>" + ln;
        }
        chaine += "</table>" + ln;
        return chaine;
    }
}