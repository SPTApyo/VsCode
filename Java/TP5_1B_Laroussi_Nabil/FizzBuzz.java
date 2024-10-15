import java.util.Scanner;
public class FizzBuzz {

    /**
     * Fonction qui calcule la suite des annonces du jeu FizzBuzz.
     * <p>Il est à noter que cette fonction comporte la précondition
     * suivante : 1 <= nbMax.</p>
     * <p>Si cette inégalité n'est pas respectée, la fonction est
     * libre de faire ce que bon lui semble.</p>
     * @param nbMax le nombre d'annonces au total.
     * @return une chaîne de caractères contenant les annonces, séparées
     *         par des espaces
     */
    public static String genererSuiteFizzBuzz(int nbMax) {
        String tempFizzBuzz="";
        if (1>nbMax) {
            System.out.println("La précondition de genererSuiteFizzBuzz n'est pas respectée.");
        } else {
            for(int cpt=1; cpt<=nbMax; cpt++){
                if ((cpt %3 == 0) && (cpt %5 == 0)){

                    tempFizzBuzz = tempFizzBuzz + "FizzBuzz ";

                }

                else if (cpt %3 == 0){

                    tempFizzBuzz = tempFizzBuzz + "Fizz ";

                }

                else if (cpt %5 == 0){

                    tempFizzBuzz = tempFizzBuzz + "Buzz ";            

                } else {tempFizzBuzz = tempFizzBuzz + cpt +" ";}
            }
        }
        return tempFizzBuzz;
    }

    public static int saisieEntier() {
        Scanner clavier = new Scanner(System.in) ;
        return clavier.nextInt() ;
    }

    public static void main(String [] arguments ) {
        String suiteFizzBuzz;
        int nbMax = -1;
        while(nbMax < 1 || nbMax > 100){
            System.out.println("\nNombre d'annonces FizzBuzz à afficher (entre 1 et 100) ?");
            nbMax = saisieEntier();
        }
        System.out.println("Nombre d'annonces FizzBuzz : "+ nbMax +"\n\n");

        // Génération de la chaîne de caractères
        suiteFizzBuzz = genererSuiteFizzBuzz(nbMax);
        System.out.println(suiteFizzBuzz);

    }
}