public class ProgrammeMystere {


    public static void main(String arguments[]) {
      // création d'un nouvel enregistrement
      Mystere var;   /* 1 */
      var = new Mystere();  /* 2 */
      var.chaine = "des machines";
      var.entier = -50000000;
      // affichage du contenu de ses champs
      System.out.println("var.chaine = "+var.chaine);
      System.out.println("var.entier = "+var.entier);


      Mystere var1;
        var1 = new Mystere(); //   nouvelle variable var1 prendra ici la valeur nommée this dans le constructeur

        System.out.println("var.chaine = "+var1.chaine);
        System.out.println("var.entier = "+var1.entier);
        // affiche la valeur des champs de var1
        // à vous

        // création d'un nouvel enregistrement
        Mystere var2;
        var2 = new Mystere("Claire");

        System.out.println("var.chaine = "+var2.chaine);
        System.out.println("var.entier = "+var2.entier);
        // affiche la valeur des champs de var2
        // à vous

        // création d'un nouvel enregistrement
        Mystere var3 ;
        var3 = new Mystere(2);

        System.out.println("var.chaine = "+var3.chaine);
        System.out.println("var.entier = "+var3.entier);

        // affiche la valeur des champs de var3
        // à vous

        // création d'un nouvel enregistrement
        Mystere var4 ;
        var4 = new Mystere("Laure",36);

        System.out.println("var.chaine = "+var4.chaine);
        System.out.println("var.entier = "+var4.entier);
        // affiche la valeur des champs de var4
        // à vous
    }
  }