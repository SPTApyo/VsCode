
import java.lang.reflect.Member;

public class ProgrammeFamille {
    
    public static void main(String [] args) {

        /*------------------------------------------------------*/
        /* Plan pour la fonction main :                         */
        /*                                                      */
        /*  0.  Déclarations des tableaux de noms et prénoms    */
        /*  1.  Affichage des tableaux                          */
        /*  2.  Création de la famille SW                       */
        /*  3.  Création et ajouts des Personne dans la Famille */
        /*  4.  Affichage de la Famille                         */
        /*  5.  Tri de la Famille                               */
        /*  6.  Affichage de la Famille                         */
        /*  7.0 Ajustement des champs de luke, padme et anakin  */
        /*  7.1 Ajout des naissances et unions                  */
        /*  8.  Affichage des champs d'une personne             */
        /*  9.  Affichage du graphe au format texte             */
        /* 10.  Sauvegarde dans un fichier                      */
        /*                                                      */
        /*------------------------------------------------------*/
        /*
        String tabNom[] = {
            "AMIDALA",
            "SKYWALKER",
            "CORDE",
            "ORGANA",
            "SKYWALKER",
            "SKYWALKER",
            "SKYWALKER",
            "SKYWALKER",
            "SKYWALKER",
            "SOLO",
            "SOLO",
            "JADE",
            "SOLO",
            "SOLO",
            "SKYWALKER"
        };
        
	String tabPrenom[] = {
            "Padme",
            "Cade",
            "Morrigan",
            "Leia",
            "Anakin",
            "Ben",
            "Kol",
            "Luke",
            "Nat",
            "Anakin",
            "Han",
            "Mara",
            "Jacen",
            "Jaina",
            "Shmi"
        };	
        System.out.println("Les 15 membres de la famille Skywalker sont :");
        for(int cpt = 0; cpt < tabNom.length; cpt ++){
            System.out.println(tabNom[cpt] + " " + tabPrenom[cpt]);
        }*/

        Famille sky = new Famille();
        Personne luke = new Personne("Skywalker", "Luke");


        ajoutPersonne(sky, luke);
    }

    /** Ajoute pfPersonne à la famille pfFamille
 *
 * @param pfFamille IN/OUT : la famille
 * @param pfPersonne IN : le membre à ajouter
 *
 * @throws Exception si plus de place dans la famille
 */
public static void ajoutPersonne(Famille pfFamille, Personne pfPersonne) throws Exception {
    if(pfFamille.membres.length < pfFamille.nbMembres + 1) {
        throw new Exception("Plus de place dans la famille !") ;
    }
    pfFamille.membres[pfFamille.nbMembres] = pfPersonne ;
    pfFamille.nbMembres ++ ;
}

/** Affiche la famille pfFamille.
 *
 * @param pfFamille IN : la famille
 */
public static void afficherFamille(Famille pfFamille){
    for(int cpt = 0; cpt < pfFamille.nbMembres + 1; cpt ++ ){
       // membre.prenom
        //System.out.println(sky.membres);
    } 
}
}