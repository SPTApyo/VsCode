public class Personne {

    String prenom;
    int anneenaissance;
    int age;

    Personne(){ /*2*/
        this.prenom = "Nouveau";
        this.anneenaissance = 1;
        this.age = 1;
  }

    Personne(String pfValeurPrenom,int pfValeurAnneenNaissance, int pfAnneeActuelle){ /*2*/
        this.prenom = pfValeurPrenom;
        this.anneenaissance = pfValeurAnneenNaissance;
        this.age = pfAnneeActuelle - pfValeurAnneenNaissance;
  }

  

}

