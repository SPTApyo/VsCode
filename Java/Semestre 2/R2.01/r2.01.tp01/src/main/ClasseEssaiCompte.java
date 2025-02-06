package main;

import tps.banque.Compte;
import tps.banque.exception.CompteException;

public class ClasseEssaiCompte {

	public static void mainExo1(String[] argv) {
		Compte cUn;
		cUn = new Compte("010101", "Lepoisson Benoit");
		cUn.afficher();
		try {
			cUn.deposer(1000);
			cUn.retirer(-100);
			cUn.deposer(-100);
		} catch (CompteException e) {
			System.out.println("Erreur : " + e);
		}
		cUn.setProprietaire("Nabil laroussi");
		System.out.println(cUn.getNumCompte());
		System.out.println(cUn.getProprietaire());
		System.out.println(cUn.soldeCompte());
		cUn.afficher();
		System.out.println(cUn);
		System.out.println(System.identityHashCode(cUn));
		cUn = null;
		// System.out.println(cUn);
		// System.out.println(System.identityHashCode(cUn));
		cUn.afficher();
	}

	public static void main(String[] argv) {
		// EXO 2 partie 2
		/*
		 * Compte cUn, cDeux;
		 * cUn = new Compte("010101","Jean");
		 * cDeux = new Compte("020202", "Marie");
		 * System.out.println(System.identityHashCode(cUn));
		 * System.out.println(System.identityHashCode(cDeux));
		 * try {
		 * cUn.deposer(1);
		 * cDeux.deposer(1000);
		 * } catch (CompteException e) {
		 * System.out.println(e);
		 * }
		 * cUn.afficher();
		 * System.out.println(cUn.soldeCompte());
		 * cDeux.afficher();
		 * System.out.println(cDeux.soldeCompte());
		 * try {
		 * cUn.retirer(-100);
		 * } catch (CompteException e) {
		 * System.out.println(e);
		 * }
		 * try {
		 * cDeux.deposer(-100);
		 * } catch (CompteException e) {
		 * System.out.println(e);
		 * }
		 * try {
		 * cUn.retirer(1);
		 * cDeux.retirer(1000);
		 * } catch (CompteException e) {
		 * System.out.println(e);
		 * }
		 * cUn.afficher();
		 * System.out.println(cUn.soldeCompte());
		 * cDeux.afficher();
		 * System.out.println(cDeux.soldeCompte());
		 * cDeux = cUn;
		 * cUn.afficher();
		 * System.out.println(System.identityHashCode(cUn));
		 * cDeux.afficher();
		 * System.out.println(System.identityHashCode(cDeux));
		 * try {
		 * cUn.deposer(1000);
		 * } catch (CompteException e) {
		 * System.out.println(e);
		 * }
		 * cUn.afficher();
		 * System.out.println(cUn.soldeCompte());
		 * cDeux.afficher();
		 * System.out.println(cDeux.soldeCompte());
		 * Compte cTrois, cQuatre;
		 * cTrois = cUn;
		 * cQuatre = cUn;
		 * cTrois.afficher();
		 * System.out.println(cTrois.soldeCompte());
		 * cQuatre.afficher();
		 * System.out.println(cQuatre.soldeCompte());
		 * System.out.println(System.identityHashCode(cUn));
		 * System.out.println(System.identityHashCode(cDeux));
		 * System.out.println(System.identityHashCode(cTrois));
		 * System.out.println(System.identityHashCode(cQuatre));
		 * try {
		 * cTrois.deposer(500);
		 * cQuatre.retirer(200);
		 * } catch (CompteException e) {
		 * System.out.println(e);
		 * }
		 * cUn.afficher();
		 * System.out.println(cUn.soldeCompte());
		 * cDeux.afficher();
		 * System.out.println(cDeux.soldeCompte());
		 * cTrois.afficher();
		 * System.out.println(cTrois.soldeCompte());
		 * cQuatre.afficher();
		 * System.out.println(cQuatre.soldeCompte());
		 * cUn = null;
		 * System.out.println(cUn);
		 * //cUn.afficher();
		 * 
		 * cDeux.afficher();
		 * System.out.println(cDeux.soldeCompte());
		 */
		Compte cUn;
		Compte cDeux;
		Compte cptTemp;

		cUn = new Compte("010101", "Jean");
		cDeux = new Compte("020202", "Marie");
		try {
			cUn.deposer(1000);
			cUn.retirer(500);
			cDeux.deposer(2000);
			cDeux.retirer(200);
		} catch (CompteException e) {
			System.out.println("Erreur ...");
		}

		cptTemp = cUn;
		cUn = cDeux;
		cDeux = cptTemp;

		cUn.afficher();
		cDeux.afficher();
	}
}