/**
 * 
 *
 * @author
 */
public class ProgrammePile
{



    public static void main(String[] args) {
        Pile pile = new Pile(50) ;
         try {                         
            System.out.println("La pile est vide ? : " + estVide(pile));
            System.out.println("Rajout d'elements dans la pile en cours ...");
            for(int cpt = 1; !pilePleine(pile) ; cpt++){
                empiler(pile, String.valueOf(cpt));
            }
            System.out.println("La pile est pleine ? : " + pilePleine(pile));
            System.out.println(nbElements(pile)+" elements dans la pile.");
            System.out.println(toString(pile));
            System.out.println("La derniere valeur est :" + sommet(pile));
            System.out.println("Suppression de la pile en cours ...");
            while(!estVide(pile)){
                depiler(pile);
            }
            System.out.println(toString(pile));
            System.out.println("La pile est vide ? : " + estVide(pile));
         }catch (Exception e) { System.out.println("Erreur : "+e.getMessage()); }; 
          System.out.println("Bye Bye");
     
          
       }

    /** Creer pile
     * 
     * @return La pile vide
     */
    public static Pile creerPile(){
        Pile nouvellePile = new Pile();
        return nouvellePile;
    }

    /** Verifie si une pile est vide
     * 
     * @param pfPile IN : la pile à vérifier
     * 
     * @return Bolléan qui renvoie si la pile est vide ou non
     */
    public static boolean estVide(Pile pfPile){
        boolean pileVide = false;
        if(pfPile.indiceSommet == -1){
            pileVide = true;
        }
        return pileVide;
    }

    /** Ajoute un élément au sommet de la pile
     * 
     * @param pfPile IN/OUT : la pile
     * @param pfElement IN : l'élément à ajouter
     */
    public static void empiler(Pile pfPile, String pfElement)throws Exception{
        if(pilePleine(pfPile) == false){
            pfPile.elements[pfPile.indiceSommet + 1] = pfElement;
            pfPile.indiceSommet += 1;
        }
        else{
            throw new Exception("La pile est déjà plein");
        }
    }

    /** Depile la pile
     * 
     * @param pfPile IN/OUT : la pile
     */
    public static void depiler(Pile pfPile)throws Exception{
        if(pfPile.indiceSommet != -1){
            pfPile.indiceSommet -= 1;
        }
        else{
            throw new Exception("La pile est déjà vide");
        }
    }

    /** Renvoie le sommet de la pile
     * 
     * @param pfPile IN : la pile
     * 
     * @return La valeur du sommet de la pile
     */
    public static String sommet(Pile pfPile){
        String valSommet = pfPile.elements[pfPile.indiceSommet];
        return valSommet;
    }

    /** Renvoie si la pile est pleine
     * 
     * @param pfPile IN : la pile
     */
    public static boolean pilePleine (Pile pfPile){
        boolean pilePleine = false;
        if(pfPile.indiceSommet == pfPile.elements.length - 1){
            pilePleine = true;
        }
        return pilePleine;
    }
    
    /** Renvoie l'état courant de la pile
     * 
     * @param pfPile IN : la pile
     * 
     * @return Le contenu de la pile
     */
    public static String toString(Pile pfPile){
        String resultat = "";
        for(int i = pfPile.indiceSommet; i >= 0; i--){
            resultat += "|" + pfPile.elements[i];
        }
        resultat += "|vide";
        return resultat;
    }
    
    /** Renvoie le nombre d'élements courant de la pile
     * 
     * @param pfPile IN : la pile à compter
     * 
     * @return Le nombre d'élements de la pile
     */
    public static int nbElements(Pile pfPile){
        int nbrElements = pfPile.indiceSommet + 1;
        return nbrElements;
    }
}