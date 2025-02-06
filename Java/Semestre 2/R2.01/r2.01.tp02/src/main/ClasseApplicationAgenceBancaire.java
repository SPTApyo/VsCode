package main;

import java.util.Locale;
import java.util.Scanner;

import tps.banque.Compte;
import tps.banque.AgenceBancaire;
import tps.banque.exception.ABCompteDejaExistantException;
import tps.banque.exception.ABCompteInexistantException;
import tps.banque.exception.ABCompteNullException;
import tps.banque.exception.CompteException;

public class ClasseApplicationAgenceBancaire {

	/**
	 * Affichage du menu de l'application
	 * 
	 * @param ag
	 *            AgenceBancaire pour récupérer le nom et la localisation
	 */
	public static void afficherMenu(AgenceBancaire pfAg) {
		System.out.println("Menu de " + pfAg.getNomAgence() + " (" + pfAg.getLocAgence() + ")");
		System.out.println("c - Créer un nouveau compte dans l'agence");
		System.out.println("s - Supprimer un compte de l'agence (par son numéro)");
		System.out.println("l - Liste des comptes de l'agence");
		System.out.println("v - Voir un compte (par son numéro)");
		System.out.println("p - voir les comptes d'un Propriétaire (par son nom)");
		System.out.println("d - Déposer de l'argent sur un compte");
		System.out.println("r - Retirer de l'argent sur un compte");
		System.out.println("q - Quitter");
		System.out.print("Choix -> ");
	}

	/**
	 * Temporisation : Affiche un message et attend la frappe de n'importe quel
	 * caractère.
	 */
	public static void tempo() {
		Scanner lect;
		String s;

		lect = new Scanner(System.in);

		System.out.print("Tapper un car + return pour continuer ... ");
		s = lect.next(); // Inutile à stocker mais c'est l'usage normal ...
	}

	public static void main(String[] argv) {

		String choix;
		String nom, numero;
		boolean continuer;
		double montant;
		Scanner lect;
		AgenceBancaire monAg;
		Compte Account;

		lect = new Scanner(System.in);
		lect.useLocale(Locale.US);

		monAg = new AgenceBancaire("Caisse Ep", "Pibrac");

		continuer = true;
		while (continuer) {
			ClasseApplicationAgenceBancaire.afficherMenu(monAg);
			choix = lect.next();
			choix = choix.toLowerCase();
			switch (choix) {


			case "c":
				System.out.print("Num compte -> ");
				numero = lect.next();
				System.out.print("Propriétaire -> ");
				nom = lect.next();

				System.out.println("Création du compte " + numero + " au nom de " + nom);
				try{
					Account = new Compte(numero, nom);
					monAg.addCompte(Account);
				}catch(ABCompteNullException e){
					System.out.print("Erreur : Compte null");
				}catch(ABCompteDejaExistantException e){
					System.out.print("Erreur Compte déjà existant");
				}catch(Exception e){
					System.out.print("Erreur dans la creation du compte");	
				}
				ClasseApplicationAgenceBancaire.tempo();
				break;

			case "s":
				System.out.print("Num compte -> ");
				numero = lect.next();
				try {
					monAg.removeCompte(numero);
					System.out.println("Suppression effectuée\n");
				} catch (ABCompteInexistantException e) {
					System.out.println("Numéro de compte inexistant");
					System.out.println(e.getMessage());
				}
				ClasseApplicationAgenceBancaire.tempo();
				break;
			
			case "l":
				System.out.print("Compte dans " + monAg.getNomAgence() + " (" + monAg.getLocAgence() + ")");
				monAg.afficher();
				ClasseApplicationAgenceBancaire.tempo();
				break;

			case "v":
				System.out.print("Num compte -> ");
				numero = lect.next();
				System.out.println(monAg.getCompte(numero));
				ClasseApplicationAgenceBancaire.tempo();
				break;

			case "p":
				System.out.print("Nom compte -> ");
				nom = lect.next();
				comptesDUnPropretaire(monAg, nom);
				ClasseApplicationAgenceBancaire.tempo();
				break;

			case "d":
				System.out.print("Num compte -> ");
				numero = lect.next();
				System.out.print("Montant -> ");
				montant = lect.nextDouble();
				deposerSurUnCompte (monAg, numero, montant);
				ClasseApplicationAgenceBancaire.tempo();
				break;

			case "r":
				System.out.print("Num compte -> ");
				numero = lect.next();
				System.out.print("Montant -> ");
				montant = lect.nextDouble();
				retirerSurUnCompte (monAg, numero, montant);
				ClasseApplicationAgenceBancaire.tempo();
				break;

			case "q":
				System.out.println("ByeBye");
				ClasseApplicationAgenceBancaire.tempo();
				continuer = false;
				break;

			// A CONTINUER ICI

			default:
				System.out.println("Erreur de saisie ...");
				ClasseApplicationAgenceBancaire.tempo();
				break;
			}
		}

	}

	public static void comptesDUnPropretaire (AgenceBancaire pfAg, String pfNomProprietaire){
        Compte[] tab = new Compte[pfAg.getComptesDe(pfNomProprietaire).length];
        tab = pfAg.getComptesDe(pfNomProprietaire);
        if (tab.length == 0) {
            System.out.println("Pas de compte trouvé pour " +pfNomProprietaire);
        } else {
            System.out.println("Comptes Trouver pour " + pfNomProprietaire + " sont : ");
        }
        for (int cpt = 0; cpt < tab.length; cpt++) {
            System.out.println(tab[cpt].toString());
        }
	}

	public static void deposerSurUnCompte(AgenceBancaire pfAg, String pfNumeroCompte, double pfMontant) {
		try {
			pfAg.getCompte(pfNumeroCompte).deposer(pfMontant);
		} catch (CompteException e) {
			System.out.println("Erreur lors du depot : " + e.getMessage());
		}
	}

	public static void retirerSurUnCompte(AgenceBancaire pfAg, String pfNumeroCompte, double pfMontant) {
		try {
			pfAg.getCompte(pfNumeroCompte).retirer(pfMontant);
		} catch (CompteException e) {
			System.out.println("Erreur lors du retrait : " + e.getMessage());
		}
	}


}