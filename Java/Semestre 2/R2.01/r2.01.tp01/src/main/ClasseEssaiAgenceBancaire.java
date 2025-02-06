package main;

import tps.banque.Compte;
import tps.banque.exception.ABCompteDejaExistantException;
import tps.banque.exception.ABCompteInexistantException;
import tps.banque.exception.ABCompteNullException;
import tps.banque.exception.CompteException;
import tps.banque.AgenceBancaire;

public class ClasseEssaiAgenceBancaire {

    public static void main(String[] argv) {
        AgenceBancaire monAg;
        monAg = new AgenceBancaire("LaBanqueDesPasPauvre", "5 rue des roue");
        monAg.afficher();
        System.out.println(monAg.getNbComptes());

        Compte cUn, cDeux, cTrois;
        cUn = new Compte("0101", "prop1");
        cDeux = new Compte("0202", "prop2");
        cTrois = new Compte("0303", "prop2");

        try {
            monAg.addCompte(cUn);
            monAg.addCompte(cDeux);
            monAg.addCompte(cTrois);
        } catch (ABCompteNullException e) {
            System.out.println(e.getMessage());
        } catch (ABCompteDejaExistantException e) {
            System.out.println(e.getMessage());
        }
        monAg.afficher();
        Compte CompteRecherche = monAg.getCompte("9999");
        if (CompteRecherche == null) {
            System.out.println("Pas de compte trouvé");
        } else {
            System.out.println("Le compte trouvé est : " + CompteRecherche);
        }

        CompteRecherche = monAg.getCompte("0101");
        if (CompteRecherche == null) {
            System.out.println("Pas de compte trouvé");
        } else {
            System.out.println("Le compte trouvé est : " + CompteRecherche);
            try {
                CompteRecherche.deposer(1000);
            } catch (CompteException e) {
                System.out.println("Erreur : " + e);
            }
            CompteRecherche.afficher();
        }
        String SCompteRecherche = "prop2";
        Compte[] tab = new Compte[2];
        tab = monAg.getComptesDe(SCompteRecherche);
        if (tab.length == 0) {
            System.out.println("Pas de compte trouvé pour ");
        } else {
            System.out.println("Comptes Trouver pour " + SCompteRecherche + " sont : ");
        }
        for (int cpt = 0; cpt < tab.length; cpt++) {
            System.out.println(tab[cpt].toString());
        }

        try {
            monAg.getCompte("0202").deposer(2000);
            monAg.getCompte("0303").deposer(3000);
        } catch (CompteException e) {
            System.out.println(e.getMessage());
        }

        for (int cpt = 0; cpt < tab.length; cpt++) {
            System.out.println(tab[cpt].toString());
        }

        SCompteRecherche = "ABSENT";
        Compte[] tabbis = new Compte[2];
        tabbis = monAg.getComptesDe(SCompteRecherche);
        if (tabbis.length == 0) {
            System.out.println("Pas de compte trouvé pour " + SCompteRecherche);
        } else {
            System.out.println("Comptes Trouver pour " + SCompteRecherche + " sont : ");
        }
        for (int cpt = 0; cpt < tabbis.length; cpt++) {
            System.out.println(tabbis[cpt].toString());
        }

        try {
            monAg.addCompte(null);
        } catch (ABCompteNullException e) {
            System.out.println(e.getMessage());
        } catch (ABCompteDejaExistantException e) {
            System.out.println(e.getMessage());
        }

        try {
            monAg.removeCompte("0202");
            monAg.removeCompte("9999");
        } catch (ABCompteInexistantException e) {
            System.out.println(e.getMessage());
        }

        Compte cQuatre;
        cQuatre = new Compte("0101", "prop99");

        try {
            monAg.addCompte(cQuatre);
        } catch (ABCompteNullException e) {
            System.out.println(e.getMessage());
        } catch (ABCompteDejaExistantException e) {
            System.out.println(e.getMessage());
        }
    }
}
