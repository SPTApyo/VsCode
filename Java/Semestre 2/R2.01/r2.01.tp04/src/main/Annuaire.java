package main;

import java.util.HashMap;
import java.util.Scanner;
public class Annuaire {

	public static void main(String[] args) {
		String choix;
		Scanner clavier;
		HashMap<String, String> annuaire;

		clavier = new Scanner(System.in);
		annuaire = new HashMap<String, String>();

		annuaire.put("Albert", "06.45.32.98.45");
		annuaire.put("Michael", "06.78.45.12.65");
		annuaire.put("Tartuffe", "07.85.25.14.96");
		annuaire.put("Vivagel", "05.31.85.15.95");

		do {
			System.out.println("\nA (Ajouter une personne) - C (Consulter l'annuaire) - V (Voir tout Vl'annuaire) - Q (Quitter)");
			choix = clavier.nextLine();
			System.out.println();
			choix = choix.toUpperCase();
			switch (choix) {
			case "A":
				Annuaire.ajouterEntreeAnnuaire(annuaire, clavier);
				break;
			case "C":
				Annuaire.consulterAnnuaire(annuaire, clavier);
				break;
			case "V":
				Annuaire.voirToutLAnnuaire(annuaire);
				break;
			case "Q":
				break;
			default:
				System.out.println("\n\nMauvais Choix ...\n\n");
			}
		} while (! choix.equals("Q"));
		System.out.println("Bye bye ...");
	}

	/**
	 * Voir l'annuaire : afficher à l'écran le contenu de l'annuaire (une ligne de code ...).
	 * @param pfAnnuaire annuaire à afficher
	 */
	private static void voirToutLAnnuaire(HashMap<String, String> pfAnnuaire) {
		System.out.println(pfAnnuaire);
	}

	/**
	 * Consulter l'annuaire : saisir un nom et afficher : le numéro de téléphone s'il est trouvé, le message "absent de l'annuaire" sinon.
	 * @param pfAnnuaire l'annuaire où chercher le numéro
	 * @param pfClavier le scanner (branché au clavier) pour faire la saisie du nom
	 */
	private static void consulterAnnuaire(HashMap<String, String> pfAnnuaire,   Scanner pfClavier) {
        System.out.println("Nom à chercher : ");
        String nom = pfClavier.nextLine();
        String search = pfAnnuaire.get(nom);
        if (search != null) {
            System.out.println(nom + " : " + search);
        } else {
            System.out.println("absent de l’annuaire");
        }
	}

	/**
	 * Ajouter dans l'annuaire : saisir un nom et un numéro de téléphone,
	 * 		ajouter le nom et le numéro à l'annuaire
	 * 		et afficher un message : l'éventuel ancien numéro s'il existait, afficher "Ajouté" si l'ancien numéro n'existe pas (nouveau nom).
	 * @param pfAnnuaire l'annuaire où ajouter le nom et le numéro
	 * @param pfClavier le scanner (branché au clavier) pour faire la saisie du nom et du téléphone
	 */
	private static void ajouterEntreeAnnuaire(HashMap<String, String> pfAnnuaire,   Scanner pfClavier) {
        System.out.println("Nom à ajouter : ");
        String nom = pfClavier.nextLine();
        System.out.println("Téléphone à ajouter : ");
        String tel = pfClavier.nextLine();
        String search = pfAnnuaire.put(nom, tel);
        if (search != null) {
            System.out.println(nom + " ancien numéro : " + search);
        } else {
            System.out.println("Ajouté");
        }
	}
}
