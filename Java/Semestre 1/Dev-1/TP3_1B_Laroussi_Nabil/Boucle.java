/**
 * Indiquer le ou les numeros de TP et d'exercice.
 *
 * @author (votre nom)
 */
public class Boucle
{
    public static void points() {
        int compteur = 0;
        System.out.print("DEBUT");

        while (compteur <= 10 ){
            System.out.print('.'); 
            compteur = compteur + 1 ;
        }

        System.out.println("FIN");
    }

    public static void boum() {
        int compteur = 0;
        while (compteur <= 10 ){
            System.out.print(compteur+" "); 
            compteur = compteur + 1 ;
        }
        System.out.print("boum\n"); 

    }

    public static void boumpair() {
        int compteur = 0;
        while (compteur <= 10 ){
            System.out.print(compteur+" "); 
            compteur = compteur + 2 ;
        }
        System.out.print("boum\n"); 

    }
    
    public static void boumpropre() {
        int compteur = 0;
        String message = "";
        while (compteur <= 10 ){
            message = message+compteur+" ";
            compteur = compteur + 1 ;
        }
        message = message+"boum\n";
        System.out.print(message);
    }
}

