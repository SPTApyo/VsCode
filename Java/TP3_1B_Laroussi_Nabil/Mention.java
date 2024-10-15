import java.util.Scanner;
/**
 * Indiquer le ou les numeros de TP et d'exercice.
 *
 * @author (votre nom)
 */
public class Mention
{
        /**
     * permet de retourner une valeur entiere saisie au clavier comprise entre pfBorneInf et pfBorneSup
     * @param pfBorneInf IN borne inférieure
     * @param pfBorneSup IN borne supérieure
     * @return une valeur comprise entre pfBorneInf et pfBorneSup
     */
    public static double saisieC(double pfBorneInf, double pfBorneSup){
	double valeurC;
	Scanner clavier = new Scanner(System.in) ;
	System.out.println("Donnez une valeur comprise entre "+pfBorneInf+" et "+pfBorneSup+ "?");
	valeurC=clavier.nextDouble();
	while (valeurC<pfBorneInf || valeurC>pfBorneSup){
	    System.out.println("Erreur ! Donnez une valeur comprise entre "+pfBorneInf+" et "+pfBorneSup+ "?");
	    valeurC=clavier.nextDouble();
	}
	return valeurC;
    }
		
		
    // Mais que fait ce programme ???	
    public static void calcul() {
	
	
	// Declaration des variables
	double note1, note2, note3;
	double moyenne;
	int mention;
	    
	    
	// Saisie des 3 notes
	System.out.println("Donnez la note 1");
	note1 = saisieC(0,20);
	System.out.println("Donnez la note 2");
	note2 = saisieC(0,20);
	System.out.println("Donnez la note 3");
	note3 = saisieC(0,20);
		
		
	
	// Calcul de la somme et de la moyenne
	moyenne = (note1+note2+note3)/3;
		
			
		
	if(moyenne <10){
	System.out.println("La moyenne des 3 notes est : "+ moyenne);
	System.out.println("Mention obtenu : collé !");
}	
	if(moyenne >=10 && moyenne <12){
	System.out.println("La moyenne des 3 notes est : "+ moyenne);
	System.out.println("Mention obtenu : passable !");
}	
	if(moyenne >=12 && moyenne <14){
	System.out.println("La moyenne des 3 notes est : "+ moyenne);
	System.out.println("Mention obtenu : assez bien !");
}	
	if(moyenne >=14 && moyenne <16){
	System.out.println("La moyenne des 3 notes est : "+ moyenne);
	System.out.println("Mention obtenu : bien !");
}
	if(moyenne >=16){
	System.out.println("La moyenne des 3 notes est : "+ moyenne);
	System.out.println("Mention obtenu : très bien !");
}			
  }
} 


