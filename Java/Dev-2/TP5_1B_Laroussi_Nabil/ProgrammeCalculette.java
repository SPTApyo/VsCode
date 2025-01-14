
/**
 * @author Laroussi Nabil
 */
public class ProgrammeCalculette {
    /**
     * Programme principal, prends les arguments pour effectuer le calcul
     * 
     */
    public static void main(String[] args) {
        Pile PileElements = creerPile();
        System.out.println("Nombre d'argument : " + args.length);

        for (int i = 0; i < args.length; i++) {
            checkVal(PileElements, args[i]);
        }
        System.out.println("Le résultat est " + sommet(PileElements));
    }

    /**
     * Ajoute la valeur actuelle ou effectue les opérations
     * 
     * @param pfPile     Pile contenant les valeurs
     * @param pfargument Argument a traiter
     */
    public static void checkVal(Pile pfPile, String pfargument) {
        int valDessus;
        int secondval;
        int newval;
        switch (pfargument) {
            case "+":
                // Addition
                valDessus = sommet(pfPile);
                try {
                    depiler(pfPile);
                } catch (Exception e) {
                }
                ;
                secondval = sommet(pfPile);
                try {
                    depiler(pfPile);
                } catch (Exception e) {
                }
                ;
                newval = (secondval + valDessus);
                try {
                    empiler(pfPile, newval);
                } catch (Exception e) {
                }
                break;
            case "-":
                // Soustraction
                valDessus = sommet(pfPile);
                try {
                    depiler(pfPile);
                } catch (Exception e) {
                }
                ;
                secondval = sommet(pfPile);
                try {
                    depiler(pfPile);
                } catch (Exception e) {
                }
                ;
                newval = (secondval - valDessus);
                try {
                    empiler(pfPile, newval);
                } catch (Exception e) {
                }
                break;
            case "'*'":
                // Multiplication
                valDessus = sommet(pfPile);
                try {
                    depiler(pfPile);
                } catch (Exception e) {
                }
                ;
                secondval = sommet(pfPile);
                try {
                    depiler(pfPile);
                } catch (Exception e) {
                }
                ;
                newval = (secondval * valDessus);
                try {
                    empiler(pfPile, newval);
                } catch (Exception e) {
                }
                break;
            case "/":
                // Division
                valDessus = sommet(pfPile);
                try {
                    depiler(pfPile);
                } catch (Exception e) {
                }
                ;
                secondval = sommet(pfPile);
                try {
                    depiler(pfPile);
                } catch (Exception e) {
                }
                ;
                if (valDessus == 0) {
                    System.out.println("Impossible car division par 0");
                } else {
                    newval = (secondval / valDessus);
                    try {
                        empiler(pfPile, newval);
                    } catch (Exception e) {
                    }
                }
                break;
            default:
                int valeurEntier = Integer.parseInt(pfargument);
                try {
                    empiler(pfPile, valeurEntier);
                } catch (Exception e) {
                }
                ;
                break;
        }
    }

    /**
     * Creer pile
     * 
     * @return La pile vide
     */
    public static Pile creerPile() {
        Pile nouvellePile = new Pile();
        return nouvellePile;
    }

    /**
     * Verifie si une pile est vide
     * 
     * @param pfPile IN : la pile à vérifier
     * 
     * @return Bolléan qui renvoie si la pile est vide ou non
     */
    public static boolean estVide(Pile pfPile) {
        boolean pileVide = false;
        if (pfPile.indiceSommet == -1) {
            pileVide = true;
        }
        return pileVide;
    }

    /**
     * Ajoute un élément au sommet de la pile
     * 
     * @param pfPile    IN/OUT : la pile
     * @param pfElement IN : l'élément à ajouter
     */
    public static void empiler(Pile pfPile, int pfElement) throws Exception {
        if (pilePleine(pfPile) == false) {
            pfPile.elements[pfPile.indiceSommet + 1] = pfElement;
            pfPile.indiceSommet += 1;
        } else {
            throw new Exception("La pile est déjà plein");
        }
    }

    /**
     * Depile la pile
     * 
     * @param pfPile IN/OUT : la pile
     */
    public static void depiler(Pile pfPile) throws Exception {
        if (pfPile.indiceSommet != -1) {
            pfPile.indiceSommet -= 1;
        } else {
            throw new Exception("La pile est déjà vide");
        }

    }

    /**
     * Renvoie le sommet de la pile
     * 
     * @param pfPile IN : la pile
     * 
     * @return La valeur du sommet de la pile
     */
    public static int sommet(Pile pfPile) {
        int valSommet = pfPile.elements[pfPile.indiceSommet];
        return valSommet;
    }

    /**
     * Renvoie si la pile est pleine
     * 
     * @param pfPile IN : la pile
     */
    public static boolean pilePleine(Pile pfPile) {
        boolean pilePleine = false;
        if (pfPile.indiceSommet == pfPile.elements.length - 1) {
            pilePleine = true;
        }
        return pilePleine;
    }

    /**
     * Renvoie l'état courant de la pile
     * 
     * @param pfPile IN : la pile
     * 
     * @return Le contenu de la pile
     */
    public static String toString(Pile pfPile) {
        String resultat = "";
        for (int i = pfPile.indiceSommet; i >= 0; i--) {
            resultat += "|" + pfPile.elements[i];
        }
        resultat += "|vide";
        return resultat;
    }

    /**
     * Renvoie le nombre d'élements courant de la pile
     * 
     * @param pfPile IN : la pile à compter
     * 
     * @return Le nombre d'élements de la pile
     */
    public static int nbElements(Pile pfPile) {
        int nbrElements = pfPile.indiceSommet + 1;
        return nbrElements;
    }
}
