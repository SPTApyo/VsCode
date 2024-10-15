







import java.util.Scanner;
public class FinDAnnee {

    /** Programme principal, le paramètre du main n'étant pas utilisé ici nous ne commentons pas,
    il devra l'être en cas d'utilisation (cf. suite) 
     */
    public static void main(String[] args) {
        System.out.println("Debut du main");
        int mois=0;
        int annee=0;
        
        while (mois<1 || mois>12){
            System.out.println("Saisir un numéro de mois ?");
            mois= saisieEntier();
        }
        
        while(annee<1582){
            System.out.println("Saisir une année ?");
            annee= saisieEntier();
        }
        
        while(mois<=12){
            int nbjours = nbJours(mois, annee);
            String SMois = convmois(mois);
            System.out.println(SMois + annee+", "+nbjours+" jours");
            mois=mois+1;
        }
        return;

    }

    public static int saisieEntier() {
        Scanner clavier = new Scanner(System.in) ;
        int nombreSaisi = clavier.nextInt() ;
        return nombreSaisi;
    }

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

    public static String convmois(int mois) {
        String smois = "";
        switch(mois){

            case 1: 
            smois= "Janvier ";
            break; 
            case 2: 
            smois= "Février ";
            break;
            case 3: 
            smois= "Mars ";
            break;
            case 4: 
            smois= "Avril ";
            break;
            case 5: 
            smois= "Mai ";
            break;
            case 6: 
            smois= "Juin ";
            break;
            case 7: 
            smois= "Juillet ";
            break;
            case 8: 
            smois= "Août ";
            break;
            case 9: 
            smois= "Septembre ";
            break;
            case 10: 
            smois= "Octobre ";
            break;
            case 11: 
            smois= "Novembre ";
            break;
            case 12: 
            smois= "Décembre ";
            break;
            default:
            System.out.println("Choix incorrect");

        }
        return smois;
    }

}