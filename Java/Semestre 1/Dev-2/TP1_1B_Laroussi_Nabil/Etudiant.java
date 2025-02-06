public class Etudiant {

    String nom;
    String prenom;
    String ine;
    Adresse adr;
    char promotion;

    Etudiant(){
        this.nom = "";
        this.prenom = "";
        this.ine = "";
        this.adr = new Adresse();
        this.promotion = '1';
    }

    Etudiant(String pfValeurNom, String pfValeurPrenom, String pfValeurINE, char pfValeurPromotion, Adresse pfValeurAdresse){
        this.nom = pfValeurNom;
        this.prenom = pfValeurPrenom;
        this.ine = pfValeurINE;
        this.adr = pfValeurAdresse;
        this.promotion = pfValeurPromotion;
    }


    
}
