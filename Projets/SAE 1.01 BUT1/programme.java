import java.util.Scanner; // Importation de la classe Scanner pour lire les entrées de l'utilisateur

public class programme {

    /**
     * Méthode principale qui exécute le programme.
     * Elle demande à l'utilisateur de saisir des valeurs pour la longueur, le nombre de participants et le nombre de portes,
     * puis elle enregistre les chronomètres des manches 1 et 2, calcule le chrono total et affiche les résultats.
     * 
     * @author Titouan
     *
     */
    public static void main(String[] args) {
        // Déclaration des variables pour la longueur, le nombre de participants et le nombre de portes
        int longueur, nbParticipant, nbPortes;
        // Déclaration des tableaux pour stocker les chronomètres des deux manches et le total
        int[] chronoManche1 = new int[50], chronoManche2 = new int[50], chronoTotal = new int[50];

        String podium;
        
        // Saisie contrôlée pour la longueur, le nombre de participants et le nombre de portes
        System.out.println("Veuillez saisir la longueur de la piste.");
        longueur = saisieControlee(250, 400);
        System.out.println("Veuillez saisir le nombre de participant.");
        nbParticipant = saisieControlee(1, 50);
        System.out.println("Veuillez saisir le nombre de portes.");
        nbPortes = saisieControlee(18, 22);

        // Saisie des chronomètres pour la première manche
        System.out.println("---------------------------------------------\n" + "Chrono manche 1: \n");
        saisieChronoManche1TabD(nbParticipant, chronoManche1, nbPortes);

        // Saisie des chronomètres pour la deuxième manche
        System.out.println("---------------------------------------------\n" + "Chrono manche 2: \n");
        saisieChronoManche2TabD(nbParticipant, chronoManche1, chronoManche2, nbPortes);

        // Calcul et affichage des chronomètres totaux
        System.out.println("---------------------------------------------\n" + "Chrono total :\n");
        calcChronoTot(chronoTotal, chronoManche1, chronoManche2, nbParticipant);

        // Affichage des résultats
        System.out.println("---------------------------------------------\n" + "Résultats :\n");
        affichageChrono(nbParticipant, chronoTotal);
        
        podium = creationpodium(nbParticipant, chronoTotal);
        System.out.println("\n Le podium est :\n" + podium);
    }

    /**
     * Demande à l'utilisateur de saisir un entier, jusqu'à ce que
     * l'entier saisi soit entre les deux valeurs imposées.
     * 
     * @param pfBorneInf La borne inférieure
     * @param pfBorneSup La borne supérieure
     * @return valeur autorisée
     *
     * @author Clarisse
     *
     */
    public static int saisieControlee(int pfBorneInf, int pfBorneSup) {
        Scanner clavier = new Scanner(System.in); // Création d'un objet Scanner pour lire les entrées
        System.out.println("Donnez une valeur comprise entre " + pfBorneInf + " et " + pfBorneSup);
        int saisie = clavier.nextInt(); // Lecture de l'entrée utilisateur
        // Boucle pour vérifier que la saisie est dans les limites
        while (saisie < pfBorneInf || saisie > pfBorneSup) {
            System.out.println("Erreur ! Donnez une valeur comprise entre " + pfBorneInf + " et " + pfBorneSup);
            saisie = clavier.nextInt(); // Nouvelle saisie si l'entrée est invalide
        }
        return saisie; // Retourne la valeur valide
    }

    /**
     * Applique des pénalités basées sur le nombre de portes touchées et ratées
     * par un participant.
     * 
     * @param pfTabChrono Le tableau des chronomètres des participants
     * @param pfParticipant L'indice du participant dans le tableau
     * @param pfNbPortes Le nombre total de portes dans la compétition
     *
     * @author Titouan
     *
     */
    public static void penalite(int[] pfTabChrono, int pfParticipant, int pfNbPortes) {
        Scanner clavier = new Scanner(System.in); // Création d'un objet Scanner pour lire les entrées utilisateur
        int porteTouche, porteRate; // Variables pour stocker le nombre de portes touchées et ratées

        // Saisie du nombre de portes touchées
        System.out.print("Combien de portes le participant n°" + (pfParticipant + 1) + " a-t-il touché ?" + "\n");
        porteTouche = clavier.nextInt(); // Lecture de l'entrée utilisateur pour les portes touchées

        // Vérification de la saisie pour les portes touchées
        while (porteTouche < 0 || porteTouche > pfNbPortes) {
            System.out.print("Erreur ! Combien de portes le participant n°" + (pfParticipant + 1) + " a-t-il touché ?" + "\n");
            porteTouche = clavier.nextInt(); // Redemande si la saisie est invalide
        }

        // Saisie du nombre de portes ratées
        System.out.print("Combien de portes le participant n°" + (pfParticipant + 1) + " a-t-il raté ?" + "\n");
        porteRate = clavier.nextInt(); // Lecture de l'entrée utilisateur pour les portes ratées

        // Vérification de la saisie pour les portes ratées
        while (porteRate < 0 || porteRate > (pfNbPortes - porteTouche)) {
            System.out.print("Erreur ! Combien de portes le participant n°" + (pfParticipant + 1)
                + " a-t-il raté ? Le nombre est compris entre 0 et " + (pfNbPortes - porteTouche) + "\n");
            porteRate = clavier.nextInt(); // Redemande si la saisie est invalide
        }

        // Application des pénalités au chrono: 2000 ms par porte touchée et 50000 ms par porte ratée
        pfTabChrono[pfParticipant] = pfTabChrono[pfParticipant] + (2000 * porteTouche) + (50000 * porteRate);
    }

    /**
     * Saisit les temps des participants et les stocke dans un tableau.
     * 
     * @param pfNbParticipant Le nombre de participants
     * @param pfTab Le tableau dans lequel les temps des participants seront stockés
     *
     * @author Titouan
     *
     */
    public static void saisieTab(int pfNbParticipant, double pfTab[]) {
        Scanner scanner = new Scanner(System.in); // Création d'un objet Scanner pour lire les entrées utilisateur
        int i; // Variable d'itération pour la boucle

        // Boucle pour saisir le temps de chaque participant
        for (i = 0; i < pfNbParticipant; i++) {
            System.out.print("Entrez le temps (en millisecondes) du participant " + (i + 1) + " : ");
            pfTab[i] = scanner.nextDouble(); // Lecture du temps saisi et stockage dans le tableau
        }
    }

    /**
     * Saisit un chronomètre à partir de l'entrée utilisateur et vérifie qu'il est supérieur ou égal à une valeur minimale.
     * 
     * @param pfMin La valeur minimale que le chronomètre doit respecter
     * @return Le chronomètre saisi par l'utilisateur, qui est supérieur ou égal à pfMin
     *
     * @author Clarisse
     * 
     */
    public static int saisieChronoMin(int pfMin) {
        int saisie; // Variable pour stocker la saisie de l'utilisateur
        Scanner clavier = new Scanner(System.in); // Création d'un objet Scanner pour lire les entrées utilisateur

        // Lecture de la saisie initiale de l'utilisateur
        saisie = clavier.nextInt();

        // Vérification que la saisie est supérieure ou égale à la valeur minimale
        while (saisie < pfMin) {
            System.out.println("Merci de donner un chrono en règle. Veuillez recommencer : ");
            saisie = clavier.nextInt(); // Redemande la saisie si elle est inférieure à pfMin
        }

        return saisie; // Retourne la saisie valide
    }

    /**
     * Saisit les chronomètres des participants pour la première manche et les stocke dans un tableau.
     * 
     * @param pfNbSaisie Le nombre de participants à saisir.
     * @param pfTab     Le tableau où les chronomètres des participants seront stockés.
     * @param pfNbPortes Le nombre de portes, utilisé pour le calcul des pénalités.
     *
     * @author Titouan
     *
     */
    public static void saisieChronoManche1TabD(int pfNbSaisie, int[] pfTabChrono, int pfNbPortes) {
        int i; // Variable d'itération pour la boucle
        String controle; // Variable pour stocker la réponse de l'utilisateur
        Scanner clavier = new Scanner(System.in); // Création d'un objet Scanner pour lire les entrées utilisateur

        // Boucle pour saisir les données pour chaque participant
        for (i = 0; i < pfNbSaisie; i++) {
            // Demande à l'utilisateur si le participant est tombé à l'eau
            System.out.println("Le participant n°" + (i + 1) + " est-il tombé à l'eau ?(o/n)");
            controle = clavier.nextLine(); // Lecture de la réponse

            // Vérification que la réponse est valide (o/n)
            while (!(controle.equals("o") || controle.equals("n"))) {
                System.out.println("Erreur ! Le participant n°" + (i + 1) + " est-il tombé à l'eau ?(o/n)");
                controle = clavier.nextLine(); // Redemande la réponse si elle est invalide
            }

            // Si le participant n'est pas tombé à l'eau
            if (controle.equals("n")) {
                System.out.println("Quel est son chrono ?"); // Demande le chrono
                pfTabChrono[i] = saisieChronoMin(0); // Appelle la méthode pour saisir le chrono, avec 0 comme valeur minimale
                penalite(pfTabChrono, i, pfNbPortes); // Applique les pénalités
            } else {
                pfTabChrono[i] = 0; // Si le participant est tombé à l'eau, on stocke 0 dans le tableau
            }
        }
    }

    /**
     * Saisit les chronomètres des participants pour la deuxième manche et les stocke dans un tableau.
     * 
     * @param pfNbSaisie Le nombre de participants à saisir.
     * @param pfTabM1   Le tableau contenant les résultats de la première manche.
     * @param pfTab     Le tableau où les chronomètres des participants pour la deuxième manche seront stockés.
     * @param pfNbPortes Le nombre de portes, utilisé pour le calcul des pénalités.
     *
     * @author Titouan
     *
     */
    public static void saisieChronoManche2TabD(int pfNbSaisie, int pfTabM1[], int pfTabChrono[], int pfNbPortes) {
        int i; // Variable d'itération pour la boucle
        String controle; // Variable pour stocker la réponse de l'utilisateur
        Scanner clavier = new Scanner(System.in); // Création d'un objet Scanner pour lire les entrées utilisateur

        // Boucle pour saisir les données pour chaque participant
        for (i = 0; i < pfNbSaisie; i++) {
            // Vérifie si le participant n'est pas éliminé (chrono de la manche 1 différent de 0)
            if (pfTabM1[i] != 0) {
                // Demande à l'utilisateur si le participant est tombé à l'eau
                System.out.println("Le participant n°" + (i + 1) + " est-il tombé à l'eau ?(o/n)");
                controle = clavier.nextLine(); // Lecture de la réponse

                // Vérification que la réponse est valide (o/n)
                while (!(controle.equals("o") || controle.equals("n"))) {
                    System.out.println("Erreur ! Le participant n°" + (i + 1) + " est-il tombé à l'eau ?(o/n)");
                    controle = clavier.nextLine(); // Redemande la réponse si elle est invalide
                }

                // Si le participant n'est pas tombé à l'eau
                if (controle.equals("n")) {
                    System.out.println("Quel est son chrono ?"); // Demande le chrono
                    pfTabChrono[i] = saisieChronoMin(0); // Appelle la méthode pour saisir le chrono, avec 0 comme valeur minimale
                    penalite(pfTabChrono, i, pfNbPortes); // Applique les pénalités
                } else if (controle.equals("o")) { // Si le participant est tombé à l'eau
                    pfTabChrono[i] = 0; // Stocke 0 dans le tableau
                }
            } else { // Si le participant est éliminé
                System.out.println("Le participant n°" + (i+1)  + " étant éliminé, il ne participera pas à la manche n°2."); // Message d'élimination
                pfTabChrono[i] = 0; // Stocke 0 dans le tableau pour le participant éliminé
            }
        }
    }

    /**
     * Calcule le chronomètre total pour chaque participant en additionnant les chronomètres des deux manches.
     * 
     * @param pfChronoTot Le tableau où les chronomètres totaux seront stockés.
     * @param pfTabM1    Le tableau contenant les résultats de la première manche.
     * @param pfTabM2    Le tableau contenant les résultats de la deuxième manche.
     * @param pfNbParticipant Le nombre de participants.
     *
     * @author Clarisse
     *
     */
    public static void calcChronoTot(int[] pfChronoTot, int pfTabM1[], int pfTabM2[], int pfNbParticipant) {
        int i; // Variable d'itération pour la boucle

        // Boucle pour calculer le chrono total pour chaque participant
        for (i = 0; i < pfNbParticipant; i++) {
            // Vérifie si le participant a un chrono valide dans les deux manches
            if ((pfTabM1[i] != 0) && (pfTabM2[i] != 0)) {
                pfChronoTot[i] = pfTabM1[i] + pfTabM2[i]; // Calcule le chrono total
            } else {
                pfChronoTot[i] = 0; // Si un des chronos est 0, le total est également 0
            }
        }    
    }

    /**
     * Affiche les chronomètres totaux des participants.
     * 
     * @param pfNbParticipant Le nombre de participants.
     * @param pfChronoTotal  Le tableau contenant les chronomètres totaux des participants.
     *
     * @author Clarisse
     *
     */
    public static void affichageChrono(int pfNbParticipant, int pfChronoTotal[]) {
        int i; // Variable d'itération pour la boucle

        // Boucle pour afficher le chrono total de chaque participant
        for (i = 0; i < pfNbParticipant; i++) {
            // Vérifie si le participant a un chrono valide
            if (pfChronoTotal[i] != 0) {
                // Affiche le temps total du participant
                System.out.println("Le participant n°" + (i + 1) + " a un temps total de " + pfChronoTotal[i] + "ms");
            } else {
                // Affiche un message indiquant que le participant n'a pas participé ou a été disqualifié
                System.out.println("Le participant n°" + (i + 1) + " n'a pas participé ou a été disqualifié.");
            }
        }
    }
    
    /**
     * Prend en entrée le nombre de participants et le chrono total des deux manches pour crée un classement en 3 positions et le renvoie sous forme de chaine de caractere
     * @author Laroussi Nabil 1B
     * 
     * @param pfNbParticipant IN : Nombre de participants
     * @param pfChronoTotal IN : Tableau des scores (Temps chrono)
     * 
     * @return le string Podium qui contient le classement ainsi que les possible surplus dans les positions du classement
     */
    public static String creationpodium(int pfNbParticipant, int[] pfChronoTotal) {
        String joueurs1ere = "", joueurs2eme = "", joueurs3eme = "", surplus1ere = "", surplus2eme = "",
                surplus3eme = "", Podium = "";
        double[] pfTabPodiumScore = new double[3];
        int totalJoueur = 0, joueur;

        // Vérifications des entrées
        if (pfChronoTotal.length < pfNbParticipant) {
            System.out.println("Le nombre de participants ne peut être plus grand que le tableau des scores");
            System.exit(0);
        } else if (pfNbParticipant < 1) {
            System.out.println("Le nombre de participants ne peut être <= 0");
            System.exit(0);
        }
        for (int i = 0; i < pfNbParticipant; i++) {
            if (pfChronoTotal[i] < 0) {
                System.out.println("Erreur : Un des scores (Index : " + i + ") est inférieur à 0");
                System.exit(0);
            }
        }

        // Initialisation du podium à la plus grande valeur possible
        for (int i = 0; i < 3; i++) {
            pfTabPodiumScore[i] = Double.MAX_VALUE;
        }

        // Recherche de la première position
        for (int i = 0; i < pfNbParticipant; i++) {
            joueur = i + 1; // Numéro du joueur (case du tableau pfChronoTotal)
            if (pfChronoTotal[i] < pfTabPodiumScore[0]) {
                // Met à jour la 1ère place
                totalJoueur++;
                pfTabPodiumScore[0] = pfChronoTotal[i];
                joueurs1ere = "Participant " + joueur;
            } else if (pfChronoTotal[i] == pfTabPodiumScore[0] && !joueurs1ere.isEmpty()) {
                // Ajoute le joueur à la 1ère place
                if (totalJoueur < 3) { // Verification de si le joueur est en surplus dans le podium ou pas
                    totalJoueur++;
                    joueurs1ere += ", Participant " + joueur;
                } else {// Ajout du joueur a la liste de surplus
                    if (surplus1ere.isEmpty()) {
                        surplus1ere += "Participant " + joueur;
                    } else {
                        surplus1ere += ", Participant " + joueur;
                    }
                }
            }
        }

        // Recherche de la deuxième position
        for (int i = 0; i < pfNbParticipant; i++) {
            joueur = i + 1; // Numéro du joueur (case du tableau pfChronoTotal)
            if (pfChronoTotal[i] < pfTabPodiumScore[1] && pfChronoTotal[i] > pfTabPodiumScore[0]) {
                // Met à jour la 2ème place
                totalJoueur++;
                pfTabPodiumScore[1] = pfChronoTotal[i];
                joueurs2eme = "Participant " + joueur;
            } else if (pfChronoTotal[i] == pfTabPodiumScore[1] && !joueurs2eme.isEmpty()) {
                // Ajoute le joueur à la 2ème place
                if (totalJoueur < 3) { // Verification de si le joueur est en surplus dans le podium ou pas
                    totalJoueur++;
                    joueurs2eme += ", Participant " + joueur;
                } else {// Ajout du joueur a la liste de surplus
                    if (surplus2eme.isEmpty()) {
                        surplus2eme += "Participant " + joueur;
                    } else {
                        surplus2eme += ", Participant " + joueur;
                    }
                }
            }
        }
        // Recherche de la troisième position
        for (int i = 0; i < pfNbParticipant; i++) {
            joueur = i + 1; // Numéro du joueur (case du tableau pfChronoTotal)
            if (pfChronoTotal[i] < pfTabPodiumScore[2]
                    && (pfChronoTotal[i] > pfTabPodiumScore[0] && pfChronoTotal[i] > pfTabPodiumScore[1])) {
                // Met à jour la 3ème place
                totalJoueur++;
                pfTabPodiumScore[2] = pfChronoTotal[i];
                joueurs3eme = "Participant " + joueur;
            } else if (pfChronoTotal[i] == pfTabPodiumScore[2] && !joueurs3eme.isEmpty()) {
                // Ajoute le joueur à la 3ème place
                if (totalJoueur < 3) { // Verification de si le joueur est en surplus dans le podium ou pas
                    totalJoueur++;
                    joueurs3eme += ", Participant " + joueur;
                } else { // Ajout du joueur a la liste de surplus
                    if (surplus3eme.isEmpty()) {
                        surplus3eme += "Participant " + joueur;
                    } else {
                        surplus3eme += ", Participant " + joueur;
                    }
                }
            }

        }

        // Création de la chaîne de caractères du podium
        // Première place
        if (!joueurs1ere.isEmpty()) {
            Podium += "1ère place : " + joueurs1ere + " avec un chrono de : " + pfTabPodiumScore[0] + " ms\n";
        }

        // Deuxième place
        if (!joueurs2eme.isEmpty()) {
            Podium += "2ème place : " + joueurs2eme + " avec un chrono de : " + pfTabPodiumScore[1] + " ms\n";
        } else if (!surplus1ere.isEmpty()) //Surplus de la première place
            Podium += "Le(s) " + surplus1ere
                    + " peut ou peuvent accéder à la première place, mais la taille limite du podium est atteinte\n";

        // Troisième place
        if (!joueurs3eme.isEmpty()) {
            Podium += "3ème place : " + joueurs3eme + " avec un chrono de : " + pfTabPodiumScore[2] + " ms\n";
        } else if (!surplus2eme.isEmpty()) //Surplus de la deuxième place
            Podium += "Le(s) " + surplus2eme
                    + " peut ou peuvent accéder à la deuxième place, mais la taille limite du podium est atteinte\n";

        if (!surplus3eme.isEmpty()) //Surplus de la troisième place
            Podium += "Le(s) " + surplus3eme
                    + " peut ou peuvent accéder à la troisième place, mais la taille limite du podium est atteinte\n";
        // Retourne le podium dans le main
        return Podium + "\n";
    }
}

