import java.io.PrintStream;

public class jeuDeLaVie {

    /**
     * Méthode principale exécutant les tests ou une génération libre, selon le mode sélectionné.
     * 
     * Cette méthode permet d'exécuter soit des tests sur différentes configurations du "Jeu de la Vie" (SAE), 
     * soit de lancer une génération libre dans un environnement sandbox pour tester des fonctions sans lien direct avec la SAE.
     * 
     * Si `modeSae` est `true`, la méthode exécute une série de tests définis pour différentes grilles (tests de SAE).
     * Si `modeSae` est `false`, elle exécute une simulation en mode sandbnabilox avec des paramètres personnalisés pour tester différentes fonctionnalités.
     *  
     * @param args Arguments en ligne de commande (non utilisés dans cette méthode).
     * @author Laroussi Nabil
     */
    public static void main(String[] args) {
        // Flag pour déterminer si le programme doit exécuter les tests de SAE ou entrer en mode Sandbox
        boolean modeSae = false;   // true = génération test de SAE, false = Sandbox pour tests divers. Par défaut, true pour diriger vers le travail de SAE.
        // Flag pour afficher ou non les générations dans la console
        boolean affichageconsole = true; // false par défaut, permet de voir la génération dans la console. true ici pour affichage dans la console.
        System.out.println("Hello World !");
        if (modeSae) {
            try {
                // Nombre maximal de générations pour les tests
                int nbGenMax = 10;
                
                // Appels aux fonctions de test pour chaque grille (5 tests différents)
                testPremiereGrille(nbGenMax, affichageconsole, false);
                testDeuxiemeGrille(nbGenMax, affichageconsole, false);
                testTroisiemeGrille(nbGenMax, affichageconsole, false);
                testQuatriemeGrille(nbGenMax, affichageconsole, false);
                testCinquiemeGrille(nbGenMax, affichageconsole, false);
            } catch (Exception e) {
                // Capture des erreurs dans la partie test de main
                System.out.println("Erreur dans la partie test de main : " + e);
            }
        } else {
            // Mode sandbox : génération libre pour tester différentes fonctions (ne fait pas partie du travail)
            try {
                // Nombre de générations pour la simulation en sandbox
                int nbgen = 1000;
                // Dimensions de la matrice pour la génération libre
                int MatriceLongueur = 60;
                int MatriceLargeur = 40;
                
                // Création de la grille de matrices pour la simulation
                MatriceEntier[] jdv = creerMatriceTableau(nbgen, MatriceLargeur, MatriceLongueur);
                
                // Initialisation de la première grille avec des valeurs aléatoires
                initialiserGrilleRandom(jdv[0]);
                
                // Exécution de la simulation pour un grand nombre de générations, avec affichage et affichage fluide
                executerSimulation(jdv, nbgen, true, true);
            } catch (Exception e) {
                // Capture des erreurs dans la partie sandbox de main
                System.out.println("Erreur dans la parties sandbox de main : " + e);
            }
        }
        System.out.println("Goodbye World !");
    }
  

      


    
    ////////////////////////////////////////////////////////// Execution Simulations JDV //////////////////////////////////////////////////////////

    /**
     * Exécute une simulation du Jeu de la Vie.
     * 
     * La méthode génère plusieurs générations successives jusqu'à ce que l'une des conditions suivantes soit remplie :
     * - Une génération identique à une précédente est détectée (cycle ou état stable).
     * - Le nombre maximal de générations défini par l'utilisateur est atteint.
     * 
     * Chaque génération est calculée en appliquant les règles du Jeu de la Vie. 
     * Si l'option `pfAffichageConsole` est activée, chaque génération est affichée dans la console.
     * 
     * @param pfGrilles IN      Un tableau de matrices représentant les différentes générations successives.
     * @param pfNbGen IN       Le nombre maximal de générations à simuler.
     * @param pfAffichageConsole IN     Un booléen indiquant si les générations doivent être affichées dans la console.
     * 
     * @return le nombre de generations produites (cpt)
     * 
     * @author Duclos Thomas
     */
    public static int executerSimulation(MatriceEntier[] pfGrilles, int pfNbGen, boolean pfAffichageConsole,
            boolean pfAffichageFluide) {
        boolean identique = false; // Indique si une génération identique à une précédente a été trouvée
        int cpt = 0; // Compteur pour suivre le nombre de générations calculées
        try {
            // Boucle principale de simulation
            for (cpt = 0; cpt < pfNbGen && !identique; cpt++) {
                // Vérifie si la génération actuelle est identique à une précédente
                for (int i = 0; i < cpt && !identique; i++) {
                    identique = grilleConnue(pfGrilles[cpt], pfGrilles[i]);
                }

                // Si le nombre maximal de générations n'est pas encore atteint, calcule la
                // suivante
                if (cpt < pfNbGen - 1 && !identique) {
                    calculerGenerationSuivante(pfGrilles[cpt], pfGrilles[cpt + 1]);
                }

                // Affiche la génération dans la console si l'option est activée (Uniquement
                // pour aider au developpement, ne fais pas partie du travail)
                if (pfAffichageConsole && !identique) {
                    PreviewMatrice(pfGrilles[cpt], cpt, pfAffichageFluide);
                }
            }
            if (pfAffichageConsole) {
                // Message de fin indiquant la raison de l'arrêt de la simulation
                System.out.println(
                        "La génération a déjà été produite ou le nombre max de générations a été atteint, fin du programme.\nDernière génération atteinte : "
                                + (cpt));
            }
        } catch (Exception e) {
            // Gestion des exceptions
            System.out.println("Erreur : " + e);
        }
        return cpt;
    }


    /**
     * Calcule la génération suivante dans le cadre du Jeu de la Vie.
     * 
     * La méthode détermine, pour chaque cellule de la matrice actuelle, si elle 
     * reste vivante, meurt ou devient vivante dans la matrice suivante en fonction 
     * des règles du Jeu de la Vie :
     * - Une cellule vivante reste vivante si elle a 2 ou 3 voisins vivants.
     * - Une cellule vivante meurt dans les autres cas (moins de 2 ou plus de 3 voisins vivants).
     * - Une cellule morte devient vivante si elle a exactement 3 voisins vivants.
     * 
     * @param pfMatriceActuelle IN   La matrice représentant l'état actuel.
     * @param pfMatriceSuivante IN/OUT   La matrice dans laquelle sera stockée l'état de la génération suivante.
     * 
     * @author Calvin Evan
     */
    public static void calculerGenerationSuivante(MatriceEntier pfMatriceActuelle, MatriceEntier pfMatriceSuivante) {
        // Parcours des lignes de la matrice (en ignorant les bords)
        for (int i = 1; i < pfMatriceActuelle.nbL - 1; i++) {
            // Parcours des colonnes de la matrice (en ignorant les bords)
            for (int j = 1; j < pfMatriceActuelle.nbC - 1; j++) {
                // Calcul du nombre de voisins vivants pour la cellule (i, j)
                int voisins = calculerVoisins(pfMatriceActuelle, i, j);

                // Si la cellule est vivante
                if (pfMatriceActuelle.tabMat[i][j] == 1) {
                    // Elle reste vivante si elle a 2 ou 3 voisins vivants
                    if (voisins == 2 || voisins == 3) {
                        pfMatriceSuivante.tabMat[i][j] = 1;
                    } else {
                        // Sinon, elle meurt
                        pfMatriceSuivante.tabMat[i][j] = 0;
                    }
                } else {
                    // Si la cellule est morte
                    // Elle devient vivante si elle a exactement 3 voisins vivants
                    if (voisins == 3) {
                        pfMatriceSuivante.tabMat[i][j] = 1;
                    } else {
                        // Sinon, elle reste morte
                        pfMatriceSuivante.tabMat[i][j] = 0;
                    }
                }
            }
        }
    }


    /**
     * Calcule le nombre de voisins vivants d'une cellule dans la matrice.
     * 
     * La méthode parcourt les cellules voisines d'une cellule donnée (à l'exclusion
     * de la cellule elle-même) et compte celles qui sont vivantes. La cellule voisine
     * est considérée vivante si sa valeur dans la matrice est égale à 1. Les voisins
     * sont vérifiés dans une matrice de taille 3x3 autour de la cellule cible, mais
     * la cellule cible et les cellules en dehors des limites de la matrice sont ignorées.
     * 
     * @param pfGrille IN    La matrice représentant l'état actuel de la grille, avec des valeurs
     *                 représentant les cellules vivantes (1) et mortes (0).
     * @param pfLigne IN    L'indice de la ligne de la cellule cible dans la matrice.
     * @param pfColonne IN   L'indice de la colonne de la cellule cible dans la matrice.
     * 
     * @return Le nombre de voisins vivants autour de la cellule spécifiée.
     * 
     * @author Calvin Evan
     */
    public static int calculerVoisins(MatriceEntier pfGrille, int pfLigne, int pfColonne) {
        int voisins = 0; // Compteur de voisins vivants
        for (int i = -1; i <= 1; i++) { // Parcours des lignes voisines
            for (int j = -1; j <= 1; j++) { // Parcours des colonnes voisines
                // Ignorer la cellule elle-même (i == 0 et j == 0)
                if (i == 0 && j == 0)
                    continue;

                // Calculer les indices de la cellule voisine
                int ni = pfLigne + i;
                int nj = pfColonne + j;

                // Vérifier si la cellule voisine est dans les limites de la matrice,
                // en excluant les marges (en s'assurant que les indices sont supérieurs ou
                // égaux à 1,
                // et inférieurs aux dimensions de la matrice - 1)
                if (ni >= 1 && ni < pfGrille.nbL - 1 && nj >= 1 && nj < pfGrille.nbC - 1) {
                    voisins += pfGrille.tabMat[ni][nj]; // Ajouter à la somme des voisins vivants
                }
            }
        }
        return voisins; // Retourner le nombre de voisins vivants
    }

    /**
     * Crée un tableau de matrices, chacune avec une marge d'une cellule supplémentaire autour de la taille réelle.
     * 
     * Cette méthode crée un tableau d'objets `MatriceEntier`, où chaque matrice possède un nombre de lignes et de colonnes 
     * spécifié, avec une marge supplémentaire de 2 cellules (une cellule de marge autour de chaque matrice).
     * Le tableau contient `pfNbGrilles` matrices, et chaque matrice a `pfNbLignes` lignes et `pfNbColonnes` colonnes.
     * 
     * @param pfNbGrilles IN Le nombre de matrices à créer dans le tableau.
     *          prec : >0
     * @param pfNbLignes IN Le nombre de lignes de chaque matrice.
     *          prec : >0
     * @param pfNbColonnes IN Le nombre de colonnes de chaque matrice.
     *          prec : >0
     * 
     * @return Un tableau de matrices.
     * 
     * 
     * @author Laroussi Nabil
     */
    public static MatriceEntier[] creerMatriceTableau(int pfNbGrilles, int pfNbLignes, int pfNbColonnes)
            throws Exception {
        // Vérification que les paramètres sont valides
        if (pfNbGrilles <= 0 || pfNbLignes <= 0 || pfNbColonnes <= 0) {
            throw new Exception("Le nombre de matrices, lignes et colonnes doit être supérieur à 0.");
        }

        // Créer un tableau de matrices
        MatriceEntier[] tableauMatrices = new MatriceEntier[pfNbGrilles];

        // Créer une matrice pour chaque index du tableau
        for (int i = 0; i < pfNbGrilles; i++) {
            tableauMatrices[i] = new MatriceEntier(pfNbLignes, pfNbColonnes);
        }

        // Retourner le tableau de matrices
        return tableauMatrices;
    }

    /**
     * Vérifie si deux grilles (matrices) sont identiques.
     * 
     * Cette méthode compare deux objets `MatriceEntier` pour vérifier si elles ont les mêmes dimensions et les mêmes valeurs
     * dans chaque cellule. Elle retourne `true` si les deux grilles sont identiques (même dimensions et même contenu), sinon
     * elle retourne `false`.
     * 
     * @param pfGrille1 IN  La première matrice à comparer.
     * @param pfGrille2 IN  La deuxième matrice à comparer.
     * 
     * @return `true` si les deux grilles sont identiques, sinon `false`.
     * 
     * 
     * @author Duclos Thomas
     */
    public static boolean grilleConnue(MatriceEntier pfGrille1, MatriceEntier pfGrille2) {
        // Comparer les cellules des deux grilles
        for (int i = 0; i < pfGrille1.nbL; i++) {
            for (int j = 0; j < pfGrille1.nbC; j++) {
                // Si une cellule est différente, retourner false
                if (pfGrille1.tabMat[i][j] != pfGrille2.tabMat[i][j]) {
                    return false;
                }
            }
        }
        // Si aucune différence n'est trouvée, les grilles sont identiques
        return true;
    }



    ////////////////////////////////////////////////////////// Execution Grilles test //////////////////////////////////////////////////////////

    /**
     * Teste la simulation de la première grille sur un certain nombre de générations.
     * 
     * Cette méthode crée un tableau de grilles, initialise la première grille, puis exécute la simulation pour toutes les générations
     * spécifiées. Ensuite, elle génère un fichier HTML représentant l'état des grilles à chaque étape de la simulation. Si une erreur
     * survient lors de l'exécution, elle est capturée et un message d'erreur est affiché dans la console.
     * 
     * @param pfNbGen IN     Le nombre de générations à simuler.
     * @param pfAffichageConsole IN     Un booléen indiquant si l'affichage des résultats dans la console est activé.
     * @param pfAffichageFluide IN     Un booléen indiquant si l'affichage fluide des résultats dans la console est activé.
     * 
     * @author Laroussi Nabil
     */
    public static void testPremiereGrille(int pfNbGen, boolean pfAffichageConsole, boolean pfAffichageFluide) {
        int lastgen;
        try {
            // Créer un tableau de matrices représentant les différentes grilles
            MatriceEntier[] grilles = creerMatriceTableau(pfNbGen, 5, 5);

            // Initialiser la première grille
            initialiserPremiereGrille(grilles[0]);

            // Exécuter la simulation sur les grilles
            lastgen = executerSimulation(grilles, pfNbGen, pfAffichageConsole, pfAffichageFluide);

            // Produire un fichier HTML pour afficher le résultat du test
            produireHTML("TEST 1", grilles, lastgen);
        } catch (Exception e) {
            // En cas d'erreur, afficher le message d'erreur dans la console
            System.out.println("Erreur dans le test N°1: " + e);
        }
    }


    /**
     * Teste la simulation de la deuxième grille sur un certain nombre de générations.
     * 
     * Cette méthode crée un tableau de grilles, initialise la deuxième grille, puis exécute la simulation pour toutes les générations
     * spécifiées. Ensuite, elle génère un fichier HTML représentant l'état des grilles à chaque étape de la simulation. Si une erreur
     * survient lors de l'exécution, elle est capturée et un message d'erreur est affiché dans la console.
     * 
     * @param pfNbGen IN      Le nombre de générations à simuler.
     * @param pfAffichageConsole IN      Un booléen indiquant si l'affichage des résultats dans la console est activé.
     * @param pfAffichageFluide IN     Un booléen indiquant si l'affichage fluide des résultats dans la console est activé.
     * 
     * @author Laroussi Nabil
     */
    public static void testDeuxiemeGrille(int pfNbGen, boolean pfAffichageConsole, boolean pfAffichageFluide) {
        int lastgen;
        try {
            // Créer un tableau de matrices représentant les différentes grilles
            MatriceEntier[] grilles = creerMatriceTableau(pfNbGen, 4, 4);

            // Initialiser la deuxième grille
            initialiserDeuxiemeGrille(grilles[0]);

            // Exécuter la simulation sur les grilles
            lastgen = executerSimulation(grilles, pfNbGen, pfAffichageConsole, pfAffichageFluide);

            // Produire un fichier HTML pour afficher le résultat du test
            produireHTML("TEST 2", grilles, lastgen);
        } catch (Exception e) {
            // En cas d'erreur, afficher le message d'erreur dans la console
            System.out.println("Erreur dans le test N°2 : " + e);
        }
    }

    /**
     * Teste la simulation de la troisième grille sur un certain nombre de générations.
     * 
     * Cette méthode crée un tableau de grilles, initialise la troisième grille, puis exécute la simulation pour toutes les générations
     * spécifiées. Ensuite, elle génère un fichier HTML représentant l'état des grilles à chaque étape de la simulation. Si une erreur
     * survient lors de l'exécution, elle est capturée et un message d'erreur est affiché dans la console.
     * 
     * @param pfNbGen IN      Le nombre de générations à simuler.
     * @param pfAffichageConsole IN      Un booléen indiquant si l'affichage des résultats dans la console est activé.
     * @param pfAffichageFluide IN     Un booléen indiquant si l'affichage fluide des résultats dans la console est activé.
     * 
     * @author Laroussi Nabil
     */
    public static void testTroisiemeGrille(int pfNbGen, boolean pfAffichageConsole, boolean pfAffichageFluide) {
        int lastgen;
        try {
            // Créer un tableau de matrices représentant les différentes grilles
            MatriceEntier[] grilles = creerMatriceTableau(pfNbGen, 7, 7);

            // Initialiser la troisième grille
            initialiserTroisiemeGrille(grilles[0]);

            // Exécuter la simulation sur les grilles
            lastgen = executerSimulation(grilles, pfNbGen, pfAffichageConsole, pfAffichageFluide);

            // Produire un fichier HTML pour afficher le résultat du test
            produireHTML("TEST 3", grilles, lastgen);
        } catch (Exception e) {
            // En cas d'erreur, afficher le message d'erreur dans la console
            System.out.println("Erreur dans le test N°3 : " + e);
        }
    }


    /**
     * Teste la simulation de la quatrième grille sur un certain nombre de générations.
     * 
     * Cette méthode crée un tableau de grilles, initialise la quatrième grille, puis exécute la simulation pour toutes les générations
     * spécifiées. Ensuite, elle génère un fichier HTML représentant l'état des grilles à chaque étape de la simulation. Si une erreur
     * survient lors de l'exécution, elle est capturée et un message d'erreur est affiché dans la console.
     * 
     * @param pfNbGen IN      Le nombre de générations à simuler.
     * @param pfAffichageConsole IN      Un booléen indiquant si l'affichage des résultats dans la console est activé.
     * @param pfAffichageFluide IN     Un booléen indiquant si l'affichage fluide des résultats dans la console est activé.
     * 
     * @author Laroussi Nabil
     */
    public static void testQuatriemeGrille(int pfNbGen, boolean pfAffichageConsole, boolean pfAffichageFluide) {
        int lastgen;
        try {
            // Créer un tableau de matrices représentant les différentes grilles
            MatriceEntier[] grilles = creerMatriceTableau(pfNbGen, 14, 14);

            // Initialiser la quatrième grille
            initialiserQuatriemeGrille(grilles[0]);

            // Exécuter la simulation sur les grilles
            lastgen = executerSimulation(grilles, pfNbGen, pfAffichageConsole, pfAffichageFluide);

            // Produire un fichier HTML pour afficher le résultat du test
            produireHTML("TEST 4", grilles, lastgen);
        } catch (Exception e) {
            // En cas d'erreur, afficher le message d'erreur dans la console
            System.out.println("Erreur dans le test N°4 : " + e);
        }
    }

    /**
     * Teste la simulation de la cinquième grille sur un certain nombre de générations.
     * 
     * Cette méthode crée un tableau de grilles, initialise la cinquième grille, puis exécute la simulation pour toutes les générations
     * spécifiées. Ensuite, elle génère un fichier HTML représentant l'état des grilles à chaque étape de la simulation. Si une erreur
     * survient lors de l'exécution, elle est capturée et un message d'erreur est affiché dans la console.
     * 
     * @param pfNbGen IN      Le nombre de générations à simuler.
     * @param pfAffichageConsole IN      Un booléen indiquant si l'affichage des résultats dans la console est activé.
     * @param pfAffichageFluide IN     Un booléen indiquant si l'affichage fluide des résultats dans la console est activé.
     * 
     * @author Laroussi Nabil
     */
    public static void testCinquiemeGrille(int pfNbGen, boolean pfAffichageConsole, boolean pfAffichageFluide) {
        int lastgen;
        try {
            // Créer un tableau de matrices représentant les différentes grilles
            MatriceEntier[] grilles = creerMatriceTableau(pfNbGen, 17, 17);

            // Initialiser la cinquième grille
            initialiserCinquiemeGrille(grilles[0]);

            // Exécuter la simulation sur les grilles
            lastgen = executerSimulation(grilles, pfNbGen, pfAffichageConsole, pfAffichageFluide);

            // Produire un fichier HTML pour afficher le résultat du test
            produireHTML("TEST 5", grilles, lastgen);
        } catch (Exception e) {
            // En cas d'erreur, afficher le message d'erreur dans la console
            System.out.println("Erreur dans le test N°5 : " + e);
        }
    }
  


    

    ////////////////////////////////////////////////////////// Initialisations Grilles test //////////////////////////////////////////////////////////
  


    /**
     * Initialise la première grille avec des valeurs spécifiques.
     * 
     * Cette méthode assigne des valeurs à certaines cellules de la grille (représentée par `pfGrille`), en définissant des cellules vivantes.
     * 
     * @param pfGrille IN/OUT La grille à initialiser, représentée par un objet de type `MatriceEntier`. 
     *                 Cette méthode modifie directement l'état de cette grille.
     * 
     * 
     * @author Laroussi Nabil
     */
    public static void initialiserPremiereGrille(MatriceEntier pfGrille) {
        pfGrille.tabMat[1][1] = 1;
        pfGrille.tabMat[2][2] = 1;
        pfGrille.tabMat[3][3] = 1;
    }


    /**
     * Initialise la deuxième grille avec des valeurs spécifiques.
     * 
     * Cette méthode assigne des valeurs à certaines cellules de la grille (représentée par `pfGrille`), en définissant des cellules vivantes.
     * 
     * @param pfGrille IN/OUT La grille à initialiser, représentée par un objet de type `MatriceEntier`. 
     *                 Cette méthode modifie directement l'état de cette grille.
     * 
     * 
     * @author Laroussi Nabil
     */
    public static void initialiserDeuxiemeGrille(MatriceEntier pfGrille) {
        pfGrille.tabMat[1][1] = 1;
        pfGrille.tabMat[2][1] = 1;
        pfGrille.tabMat[1][2] = 1;
        pfGrille.tabMat[2][2] = 1;
    }

    /**
     * Initialise la troisième grille avec des valeurs spécifiques.
     * 
     * Cette méthode assigne des valeurs à certaines cellules de la grille (représentée par `pfGrille`), en définissant des cellules vivantes.
     * 
     * @param pfGrille IN/OUT La grille à initialiser, représentée par un objet de type `MatriceEntier`. 
     *                 Cette méthode modifie directement l'état de cette grille.
     * 
     * 
     * @author Laroussi Nabil
     */
    public static void initialiserTroisiemeGrille(MatriceEntier pfGrille) {
        // Ligne 1
        for (int j = 2; j <= 4; j++) {
            pfGrille.tabMat[1][j] = 1;
        }

        // Ligne 2
        pfGrille.tabMat[2][1] = 1;
        pfGrille.tabMat[2][3] = 1;
        pfGrille.tabMat[2][5] = 1;

        // Ligne 3
        for (int j = 2; j <= 4; j++) {
            pfGrille.tabMat[3][j] = 1;
        }
    }
    
    /**
     * Initialise la quatrième grille avec des valeurs spécifiques.
     * 
     * Cette méthode assigne des valeurs à certaines cellules de la grille (représentée par `pfGrille`), en définissant des cellules vivantes.
     * 
     * @param pfGrille IN/OUT La grille à initialiser, représentée par un objet de type `MatriceEntier`. 
     *                 Cette méthode modifie directement l'état de cette grille.
     * 
     * 
     * @author Laroussi Nabil
     */
    public static void initialiserQuatriemeGrille(MatriceEntier pfGrille) {
        // Carrés dans les coins
        for (int i = 1; i <= 2; i++) {
            for (int j = 7; j <= 8; j++) {
                pfGrille.tabMat[i][j] = 1;
            }
        }

        for (int i = 5; i <= 6; i++) {
            for (int j = 1; j <= 2; j++) {
                pfGrille.tabMat[i][j] = 1;
            }
        }

        for (int i = 11; i <= 12; i++) {
            for (int j = 5; j <= 6; j++) {
                pfGrille.tabMat[i][j] = 1;
            }
        }

        for (int i = 7; i <= 8; i++) {
            for (int j = 11; j <= 12; j++) {
                pfGrille.tabMat[i][j] = 1;
            }
        }

        // Croix centrale
        for (int j = 5; j <= 8; j++) {
            pfGrille.tabMat[4][j] = 1;
        }

        for (int i = 5; i <= 8; i++) {
            pfGrille.tabMat[i][4] = 1;
        }

        for (int i = 5; i <= 8; i++) {
            pfGrille.tabMat[i][9] = 1;
        }

        for (int j = 5; j <= 8; j++) {
            pfGrille.tabMat[9][j] = 1;
        }

        pfGrille.tabMat[5][7] = 1;
        pfGrille.tabMat[6][6] = 1;
        pfGrille.tabMat[7][6] = 1;
    }

    /**
     * Initialise la cinquième grille avec des valeurs spécifiques.
     * 
     * Cette méthode assigne des valeurs à certaines cellules de la grille (représentée par `pfGrille`), en définissant des cellules vivantes.
     * 
     * @param pfGrille IN/OUT La grille à initialiser, représentée par un objet de type `MatriceEntier`. 
     *                 Cette méthode modifie directement l'état de cette grille.
     * 
     * 
     * @author Laroussi Nabil
     */
    public static void initialiserCinquiemeGrille(MatriceEntier pfGrille) {

        for (int i = 4; i <= 5; i++) {
            for (int j = 4; j <= 12; j++) {
                if (j != 6) {
                    pfGrille.tabMat[i][j] = 1;
                }
            }
        }

        for (int i = 6; i <= 9; i++) {
            for (int j = 4; j <= 5; j++) {
                pfGrille.tabMat[i][j] = 1;
            }
        }

        for (int i = 11; i <= 12; i++) {
            for (int j = 4; j <= 9; j++) {
                pfGrille.tabMat[i][j] = 1;
            }
        }

        for (int i = 7; i <= 12; i++) {
            for (int j = 11; j <= 12; j++) {
                pfGrille.tabMat[i][j] = 1;
            }
        }
    }
      


    
    ////////////////////////////////////////////////////////// Matrice to HTML //////////////////////////////////////////////////////////
  


    static String css = "<style>";
        static {
        css += "table {";
        css += "border-collapse:collapse;";
        css += "border: 1px solid black;";
        css += "width:auto;";
        css += "margin: 10px;"; // Ajout d'un léger espacement autour des tableaux
        css += "display: inline-table;";
        css += "}";
        css += "tr,td {";
        css += "border: 1px solid black;";
        css += "height:25px;"; // Hauteur des cellules
        css += "width:25px;";  // Largeur des cellules (même que la hauteur)";
        css += "}";
        css += ".on { background-color:grey; }";
        css += "</style>";
    }

    static String debutPage = "<html><head>";
        static {
        debutPage += "<title>TP: Jeu de la vie</title>";
        debutPage += "<meta http-equiv='Content-Type' content='application/xhtml+xml; charset=UTF-8' />";
        debutPage += css;
        debutPage += "</head><body>";
    }
    static String finPage = "</body></html>";

    /**
     * Produit un fichier HTML représentant l'état des grilles pour chaque génération.
     * 
     * Cette méthode génère un fichier HTML contenant une table pour chaque grille (représentant une génération) et la convertit en HTML 
     * à l'aide de la méthode `matriceToHTML`. Chaque table est précédée d'un titre indiquant le numéro de la génération. Le fichier HTML
     * est ensuite écrit sur le disque sous le nom spécifié par `pfNomFich` avec l'extension `.html`.
     * 
     * @param pfNomFich IN  Le nom du fichier HTML à générer, sans l'extension. Le fichier sera créé dans le répertoire actuel avec 
     *                  l'extension `.html`.
     * @param pfGrilles IN  Un tableau de grilles (matrices) représentant les différentes générations du Jeu de la Vie.
     * 
     * @param pfNbGen IN  Nombre de générations produites
     * 
     * @author Laroussi Nabil
     */
    public static void produireHTML(String pfNomFich, MatriceEntier[] pfGrilles, int pfNbGen) {
        // Créer un flux de sortie pour écrire dans le fichier HTML
        try (PrintStream out = new PrintStream(pfNomFich + ".html")) {
            // Écrire le début du fichier HTML (la structure de la page)
            out.print(debutPage);

            // Ajouter un titre principal à la page HTML
            out.println("<h1>Jeu de la Vie: " + pfNomFich + "</h1>");

            // Parcourir chaque grille (chaque génération)
            for (int cpt = 0; cpt < pfNbGen; cpt++) {
                // Ajouter une table pour chaque génération avec un titre (caption)
                out.println("<table><caption>Génération " + cpt + "</caption>");

                // Convertir la matrice en HTML et l'ajouter à la table
                out.print(matriceToHTML(pfGrilles[cpt]));

                // Fermer la table pour cette génération
                out.println("</table>");
            }

            // Ajouter la fin de la page HTML
            out.print(finPage);
        } catch (Exception e) {
            System.out.println("Erreur lors de la creation du fichier HTML : " + e);
        }
    }


    /**
     * Convertit une matrice en une représentation HTML sous forme de tableau.
     * 
     * Cette méthode génère une chaîne de caractères représentant une table HTML. Chaque cellule de la matrice est convertie en une 
     * cellule `<td>` dans la table HTML. Si la valeur de la cellule est 1, la cellule est marquée avec la classe CSS 'on'.
     * 
     * @param pfGrille IN   La grille à convertir, représentée par un objet de type `MatriceEntier`.
     * 
     * @return Une chaîne de caractères contenant le code HTML représentant la matrice sous forme de tableau.
     * 
     * 
     * @author Laroussi Nabil
     */
    public static String matriceToHTML(MatriceEntier pfGrille) {
        String html = "";

        // Parcourir chaque ligne
        for (int i = 0; i < pfGrille.nbL; i++) {
            html += "<tr>"; // Début de la ligne de la table HTML

            // Parcourir chaque colonne
            for (int j = 0; j < pfGrille.nbC; j++) {
                html += "<td"; // Début de la cellule de la table HTML

                // Vérifier si la cellule est vivante (valeur = 1) et lui attribuer la classe
                // 'on'
                if (pfGrille.tabMat[i][j] == 1) {
                    html += " class='on'";
                }

                html += "></td>"; // Fin de la cellule
            }

            html += "</tr>"; // Fin de la ligne de la table HTML
        }

        return html; // Retourner le code HTML généré
    }




    ////////////////////////////////////////////////////////// Zone fonctions de TEST et outils  //////////////////////////////////////////////////////////
    //
    // (Ne fait pas partie du travail, outil d'aide pour le dev)
    // (Contient probablement des choses qu'on a pas encore vu (pardon...))
    // 
    //
    // Fonction pour aider l'affichage du JDV dans la console avec un affichage plus " fluide "
    public static void clearConsole() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception e) {
            System.out.println("Erreur lors de l'effacement de la console : " + e);
        }
    }

    // Méthode pour afficher une matrice dans la console
    public static void PreviewMatrice(MatriceEntier pfMatrice, int pfNumGen, boolean pfAffichageFluide) {
        int gps = 11;
        int delay = 1000 / gps;
        String affiche = "";
        try {
            if (pfAffichageFluide) {
                clearConsole(); // Efface la console à chaque nouvelle génération pour un affichage fluide
            }

            System.out.println("Génération " + (pfNumGen + 1) + ":");

            // Remplir 'affiche' ligne par ligne
            for (int i = 0; i < pfMatrice.nbL; i++) {
                // Pour chaque ligne, ajouter chaque élément dans affiche
                for (int j = 0; j < pfMatrice.nbC; j++) {
                    affiche += (pfMatrice.tabMat[i][j] == 1 ? "⬜" : "⬛") + " ";
                }
                // Ajouter un saut de ligne à la fin de chaque ligne
                affiche += "\n";
            }

            // Afficher 'affiche' en une seule fois après que tout a été rempli
            System.out.print(affiche);

            // Affichage fluide avec un délai, si activé
            if (pfAffichageFluide) {
                Thread.sleep(delay); // Simule le système FPS en limitant l'affichage et la génération
            }

        } catch (Exception e) {
            System.out.println("Erreur lors de l'affichage : " + e);
        }
    }

    // Méthode pour initialiser la grille avec des valeurs aléatoires pour le jeu de
    // la vie
    public static void initialiserGrilleRandom(MatriceEntier pfMatrice) {
        for (int i = 1; i < pfMatrice.nbL - 1; i++) {
            for (int j = 1; j < pfMatrice.nbC - 1; j++) {
                pfMatrice.tabMat[i][j] = Math.random() > 0.5 ? 1 : 0; // Cellule vivante (1) ou morte (0)
            }
        }
    }
}
