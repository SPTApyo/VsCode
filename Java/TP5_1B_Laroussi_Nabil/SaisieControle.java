
import java.util.Scanner;
/**
 * Indiquer le ou les numeros de TP et d'exercice.
 *
 * @author (votre nom)
 */
public class SaisieControle
{

    public static void main(String[] args) {
        System.out.println("Debut du main");
        int nbre=0;
        while (nbre<1 || nbre>10){
            System.out.println("Entrer un nombre entier compris entre 1 et 10 :");
            nbre= saisieEntier();
        }
        System.out.println("Vous avez correctement saisi : "+ nbre+".");
        return;
    }

    public static int saisieEntier() {
        Scanner clavier = new Scanner(System.in) ;
        int nombreSaisi = clavier.nextInt() ;
        return nombreSaisi;
    }

}
