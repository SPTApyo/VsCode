import java.util.Scanner;

public class JourSuivant {

    /**
     * Fait saisir une date à l'utilisateur
     *
     * @param pfDate OUT : un tableau de trois cases représentant une
     *               date. 1ere case : jour, 2nde case : mois, 3eme case : annee
     *
     */
    public static void saisieDate(int[] pfDate) {
        if (pfDate.length != 3) {
            System.out.print("Le tableau représentant la date a une taille inattendue : ");
            System.out.println(pfDate.length + " case(s) au lieu de 3 !");
        }
        Scanner clavier = new Scanner(System.in);
        for (int cpt = 0; cpt < pfDate.length; cpt++) {
            switch (cpt) {
                case 0:
                    System.out.println("Quel Jour ?");
                    break;
                case 1:
                    System.out.println("Quel Mois ?");
                    break;
                case 2:
                    System.out.println("Quelle année ?");
                    break;
                default:
                    System.out.println("Oups");
            }
            pfDate[cpt] = clavier.nextInt();
        }
    }

    /**
     * Calcul la validité d'une date
     *
     * @param pfDate IN : date initiale
     * @return true si et seulement si pfDate est valide
     *
     */
    public static boolean dateValide(int[] pfDate) {
        if (pfDate.length != 3) {
            System.out.print("Un tableau représentant une date a une taille inattendue : ");
            System.out.println(pfDate.length + " case(s) au lieu de 3 !");
        }

        if ((pfDate[1] >= 1) && (pfDate[1] <= 12) && (pfDate[2] >= 1582)) { // Verification mois= [1;12] année=[1582;+infini[
            if ((pfDate[1] % 2 == 0) && (pfDate[1] != 2) || (pfDate[1] == 7)) { // Verification mois sur 31 jours
                if (pfDate[0] <= 31) {
                    return true;
                }

            } else if ((pfDate[1] % 2 != 0) && (pfDate[1] != 2)) { // Verification mois sur 30 jours
                if (pfDate[0] <= 30) {
                    return true;
                }

            } else if (pfDate[1] == 2) {
                if (pfDate[2] % 4 == 0 && (pfDate[2] % 100 != 0 || pfDate[2] % 400 == 0)) { // Verification année bissextile
                    if (pfDate[0] <= 29) {
                        return true;
                    }
                } else {
                    if (pfDate[0] <= 28) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Calcul du jour suivant
     *
     * @param pfDateJourCourant IN : date initiale
     * @param pfDateJourSuivant OUT : date du jour suivant
     *
     */
    public static void jourSuivant(int[] pfDateJourCourant, int[] pfDateJourSuivant) {
        if (pfDateJourCourant.length != 3 || pfDateJourSuivant.length != 3) {
            System.out.print("Un tableau représentant une date a une taille inattendue : ");
            System.out.println(pfDateJourCourant.length + " ou " + pfDateJourSuivant.length
                    + " case(s) au lieu de 3 !");
        }
        int jour = pfDateJourCourant[0];
        int mois = pfDateJourCourant[1];
        int annee = pfDateJourCourant[2];

        int joursDansMois;

        // Déterminer le nombre de jours dans le mois
        if (mois == 2) { // Février
            if (annee % 4 == 0 && (annee % 100 != 0 || annee % 400 == 0)) {
                joursDansMois = 29;
            } else {
                joursDansMois = 28;
            }
        } else if (mois == 4 || mois == 6 || mois == 9 || mois == 11) { // Avril, Juin, Septembre, Novembre
            joursDansMois = 30;
        } else { // Janvier, Mars, Mai, Juillet, Août, Octobre, Décembre
            joursDansMois = 31;
        }

        // Incrémenter le jour
        if (jour < joursDansMois) {
            pfDateJourSuivant[0] = jour + 1;
            pfDateJourSuivant[1] = mois;
            pfDateJourSuivant[2] = annee;
        } else {
            pfDateJourSuivant[0] = 1; // Remise à 1 pour le jour suivant
            if (mois == 12) { // Passage à l'année suivante
                pfDateJourSuivant[1] = 1; // Janvier
                pfDateJourSuivant[2] = annee + 1; // Augmenter l'année
            } else {
                pfDateJourSuivant[1] = mois + 1; // Mois suivant
                pfDateJourSuivant[2] = annee; // Année inchangée
            }
        }
    }

    /**
     * Calcul du surlendemain
     *
     * @param pfDateJourCourant  IN : date initiale
     * @param pfDatesurlendemain OUT : date du surlendemain
     *
     */
    public static void surlendemain(int[] pfDateJourCourant, int[] pfDatesurlendemain) {
        if (pfDateJourCourant.length != 3 || pfDatesurlendemain.length != 3) {
            System.out.print("Un tableau représentant une date a une taille inattendue : ");
            System.out.println(pfDateJourCourant.length + " ou " + pfDatesurlendemain.length
                    + " case(s) au lieu de 3 !");
        }
        int jour = pfDateJourCourant[0];
        int mois = pfDateJourCourant[1];
        int annee = pfDateJourCourant[2];

        int joursDansMois;

        // Déterminer le nombre de jours dans le mois
        if (mois == 2) { // Février
            if (annee % 4 == 0 && (annee % 100 != 0 || annee % 400 == 0)) {
                joursDansMois = 29;
            } else {
                joursDansMois = 28;
            }
        } else if (mois == 4 || mois == 6 || mois == 9 || mois == 11) { // Avril, Juin, Septembre, Novembre
            joursDansMois = 30;
        } else { // Janvier, Mars, Mai, Juillet, Août, Octobre, Décembre
            joursDansMois = 31;
        }

        // Incrémenter le jour
        if (jour + 1 < joursDansMois) {
            pfDatesurlendemain[0] = jour + 2;
            pfDatesurlendemain[1] = mois;
            pfDatesurlendemain[2] = annee;
        } else {
            pfDatesurlendemain[0] = 2;
            if (mois == 12) { // Passage à l'année suivante
                pfDatesurlendemain[1] = 1; // Janvier
                pfDatesurlendemain[2] = annee + 1; // Augmenter l'année
            } else {
                pfDatesurlendemain[1] = mois + 1; // Mois suivant
                pfDatesurlendemain[2] = annee; // Année inchangée
            }
        }
    }

    public static void main(String[] args) {

        /* Déclaration des variables */
        int[] date = new int[3];
        int[] datesuiv = new int[3];
        int[] datesurlendemain = new int[3];
        boolean valide = false;
        /* -- Etape 1 -- */
        saisieDate(date);
        /* -- Etape 2 -- */
        valide = dateValide(date);

        /* -- Etape 3 -- */
        if (valide) {
            jourSuivant(date, datesuiv);
            surlendemain(date, datesurlendemain);
            System.out.println(
                    "La date du " + datesurlendemain[0] + "/" + datesurlendemain[1] + "/" + datesurlendemain[2] + " vient apres le "
                            + datesuiv[0] + "/" + datesuiv[1] + "/" + datesuiv[2]
                            + " qui vient apres le " + date[0] + "/" + date[1] + "/" + date[2]
                            + ".");
        } else {
            System.out.println("La date du "
                    + date[0] + "/" + date[1] + "/" + date[2]
                    + " n'est pas une date valide.");
        }
    }

}