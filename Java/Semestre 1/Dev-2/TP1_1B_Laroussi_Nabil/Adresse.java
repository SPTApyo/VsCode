public class Adresse {

    int numrue;
    String nomrue;
    String codepostal;
    String ville;

    Adresse(){
        this.numrue = 1;
        this.nomrue = "";
        this.codepostal = "";
        this.ville = "";
    }

    Adresse(int pfValeurNumRue,String pfValeurNomRue,String pfValeurCodePostal,String pfValeurVille){

        this.numrue = pfValeurNumRue;
        this.nomrue = pfValeurNomRue;
        this.codepostal = pfValeurCodePostal;
        this.ville = pfValeurVille;
    }

    
}
