public class Famille {
    // nombre de membres dans la famille
    int nbMembres ;

    // membres de la famille
    Personne[] membres ;


    /** Construit une famille vide.
     */
    Famille() {
        this.membres = new Personne[100] ;
        this.nbMembres = 0 ;
    }

}