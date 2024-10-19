    import java.util.Scanner;
/**
 * Indiquer le ou les numeros de TP et d'exercice.
 *
 * @author (Laroussi Nabil 1B)
 */
public class DatesEtCompagnie
{
    /**
     *  @param annee une année
     *  @return vrai si l'année est bissextile, faux sinon
     */
    public static boolean estBissextile(int annee) {
        if (annee % 4 == 0 && (annee % 100 != 0 || annee % 400 == 0)) {
            return true;
        }
        else{
            return false ;
        }
    }

    public static int nbJours(int mois, int annee) {
        if ((mois % 2 == 0) && (mois !=2) || (mois ==7)){
            return 31;
        } else if((mois % 2 != 0)&& (mois !=2)){
            return 30;
        } else if (mois ==2){
            boolean bissextile = estBissextile(annee);
            if (bissextile == true){
                return 29;}
        }
        return 28;
    }

    public static boolean estValide(int jour, int mois, int annee) {
        if (jour<1 || mois<1 || mois>12 || annee<1582){return false;}
        int Jmax = nbJours(mois,annee);
        if (Jmax>=jour){
            return true ;}
        else
            return false;
    }

    public static void saisieCalculAffichageValidite() {
        System.out.println("Saisir un entier pour Jour");
        int Jour= saisieEntier();
        System.out.println("Saisir un entier pour Mois");
        int Mois= saisieEntier();
        System.out.println("Saisir un entier pour Annee");
        int Annee= saisieEntier();

        boolean Valide = estValide(Jour, Mois, Annee);

        if (Valide == true){
            System.out.println(Jour+"/"+Mois+"/"+Annee+" est valide");
        }else{
            System.out.println(Jour+"/"+Mois+"/"+Annee+" est invalide");
        }
    }

    public static int saisieEntier() {
        Scanner clavier = new Scanner(System.in) ;
        int nombreSaisi = clavier.nextInt() ;
        return nombreSaisi;
    }
}

