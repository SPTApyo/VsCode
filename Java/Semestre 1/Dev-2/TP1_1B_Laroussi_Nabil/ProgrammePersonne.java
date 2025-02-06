import java.util.Calendar;

public class ProgrammePersonne {
    
    public static void main(String arguments[]) {
        // création d'un nouvel enregistrement
        Personne var;   /* 1 */
        var = new Personne();  /* 2 */
        var.prenom = "thelma";
        var.anneenaissance = 1995;
        var.age = Calendar.getInstance().get(Calendar.YEAR) - var.anneenaissance;

        // affichage du contenu de ses champs
        System.out.println("var.prenom = "+var.prenom);
        System.out.println("var.anneenaissance = "+var.anneenaissance);
        System.out.println("var.age = "+var.age);

        Personne var1;   
        var1 = new Personne("Nabil",2005,Calendar.getInstance().get(Calendar.YEAR));
      
        System.out.println("var.prenom = "+var1.prenom);
        System.out.println("var.anneenaissance = "+var1.anneenaissance);
        System.out.println("var.age = "+var1.age);
      }

}
