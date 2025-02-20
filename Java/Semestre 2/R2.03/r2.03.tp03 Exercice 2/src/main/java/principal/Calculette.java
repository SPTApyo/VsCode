package principal;

import pile.Pile;

public class Calculette {

	/* Calcul d'une expression post-fixée.
	 * Modifier la valeur de expr dans le code pour une autre exécution
	 */
	public static void main(String args[]) {

		Pile pileOperandes; // Pile des opérandes durant le calcul
		int operande; // opérande lue dans l'expression post fixée
		int operandeGauche; // opérande gauche d'une opération;
		int operandeDroite; // opérande droite d'une opération
		int res; // résultats intermédiaires de calculs
		boolean exprOK; // Permet d'arrêtre le calcul si une erreur est rencontrée.
		String[] exprPostFixee; // expression "éclatee" en opérandes/opérateurs
		String expr; // expression initiale (ex : "12 14 + 45 78 + *")
		int nbNombre = 0, nbOperateur = 0;

		expr = "a b";

		System.out.println("Expression intiale : "+expr);

		exprPostFixee = expr.split(" ");
		pileOperandes = new Pile(exprPostFixee.length);
		exprOK = true;

		for (int i = 0; i < exprPostFixee.length && exprOK; i++) {
			switch (exprPostFixee[i]) {
				case "+" :
				case "-" :
				case "/" :
				case "*" :
				nbOperateur++;
					break;
				default:
				nbNombre++;
					break;
			}
		}
		if (exprPostFixee.length <= 2) {
			System.out.println("Erreur durant le calcul : manque d’opérande !");
			exprOK = false;
		}

		if (nbNombre/2 >= nbOperateur || nbNombre <= nbOperateur) {
			System.out.println("Erreur détectée durant le calcul : trop d’opérandes !");
			exprOK = false;
		}
		
		
		for (int i = 0; i < exprPostFixee.length && exprOK; i++) {
			switch (exprPostFixee[i]) {
			case "+" :
			case "-" :
			case "/" :
			case "*" :
				// récupérer opérande droite
				operandeDroite = pileOperandes.sommet();
				pileOperandes.depiler();
				// récupérer opérande gauche
				operandeGauche = pileOperandes.sommet();
				pileOperandes.depiler();
						
				// faire le calcul
				res = 0;
				switch (exprPostFixee[i]) {
				case "+" : res = operandeGauche + operandeDroite; 
					break;
				case "-" : res = operandeGauche - operandeDroite; 
					break;
				case "/" : res = operandeGauche / operandeDroite; 
					break;
				case "*" : res = operandeGauche * operandeDroite; 
					break;
				}
				// empiler le résultat
				pileOperandes.empiler(res);

				break;
			default:
				try {
					operande = Integer.parseInt(exprPostFixee[i]); // operande
					pileOperandes.empiler(operande);
				} catch (NumberFormatException nfe) {
					exprOK = false;
					System.out.println("Erreur durant le calcul : conversion String > int : " + nfe.getMessage());
				}
			}
		}
		if (exprOK) {
			res = pileOperandes.sommet();
			pileOperandes.depiler();
			System.out.println("Aucune erreur detectee. Le resultat est " + res);
		} else {
			System.out.println("Erreur detectee durant le calcul");
		}
	}
}
