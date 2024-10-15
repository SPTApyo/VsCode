/**
 * Indiquer le ou les numeros de TP et d'exercice.
 *
 * @author (Laroussi Nabil)
 */
public class Sapins
{   
    /**
     * permet de retourner une chaîne de caractères avec nb caractères car identiques
     * @param nb  nombre de caractères de la chaîne
     * @param car caractère constituant la chaîne
     * @return la chaîne de caractères de nb caractères car
     */
    public static String genererStringLigne (int nb, String car){
        int compteur=0;
        String Ligne="";
        while(nb>compteur){
            Ligne=Ligne + car;  
            compteur = compteur + 1;
        }
        return Ligne; // A modifier
    }

    /**
     * permet de retourner une chaîne de caractères représentant le sapin plein demandé
     * @param hauteur  hauteur du sapin
     * @return la chaîne de caractères sapin plein
     */
    public static String genererSapinPlein (int hauteur){
        int compteur=0;
        String matrice="";
        while(hauteur>compteur){
            matrice= matrice +genererStringLigne((hauteur-compteur)," ")+genererStringLigne(compteur+(1+compteur),"*")+"\n";
            compteur = compteur + 1;
        }   
        System.out.print(matrice); // A modifier
        return matrice;
    }

    public static String genererSapinCouche (int hauteur){
        int compteur=0;
        String matrice="";
        while(hauteur>compteur){
            matrice= matrice +genererStringLigne(compteur,"*")+"\n";
            compteur = compteur + 1;
        }
        while((hauteur*2)>compteur){
            matrice= matrice +genererStringLigne((hauteur*2)-compteur,"*")+"\n";
            compteur = compteur + 1;
        }
        System.out.print(matrice); // A modifier
        return matrice;
    }

    public static String genererSapinVide (int hauteur){
        int compteur=0;
        String matrice="";
        matrice= "\n"+matrice +genererStringLigne((hauteur)," ")+"*"+"\n";
        while(hauteur-2>compteur){
            matrice= matrice +genererStringLigne(((hauteur-1)-compteur)," ")+"*"+genererStringLigne(compteur+(1+compteur)," ")+"*"+"\n";  
            compteur = compteur + 1;
        }   
        matrice= matrice +" "+genererStringLigne((hauteur*2)-1,"*")+"\n";   
        System.out.print(matrice); // A modifier
        return matrice;
    }
    // A vous pour genererSapinVide et genererSapinCouche

}