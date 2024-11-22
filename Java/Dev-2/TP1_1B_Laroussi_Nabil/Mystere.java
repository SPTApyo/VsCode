public class Mystere {

    // toute variable de type Mystere contiendra un champ chaine et un champ entier
    String chaine ;
    int entier ;
    Mystere(){ /*1*/
        this.chaine = "Nouveau" ;  // this.chaine représente le champ chaine du nouvel objet
        this.entier = 0 ;
  }

  Mystere(String pfValeurInitialeChaine){ /*2*/
        this.chaine = pfValeurInitialeChaine ;
        this.entier = 1 ;
  }

  Mystere(int pfValeurInitialeEntier){ /*3*/
        this.chaine = "Bla bla" ;
        this.entier = pfValeurInitialeEntier ;
  }

  Mystere(String pfValeurInitialeChaine, int pfValeurInitialeEntier){ /*4*/
        this.chaine = pfValeurInitialeChaine ;
        this.entier = pfValeurInitialeEntier ;
  }
    // les champs pourraient aussi bien représenter des nom et age des
    // identificateurs de champs plus explicites seraient alors utiles
  
  }