/**
 * Indiquer le ou les numeros de TP et d'exercice.
 *
 * @author (Laroussi Nabil Groupe A1)
 */
public class Geometrie
{
    /**
     * @param diametre
     *      Le diametre du disque (nombre positif).
     * @return la surface du disque
     */
    public static double surfaceDisque(double diametre) {
        double pi=3.1415, rayon=diametre/2, surface;
    surface = pi*(rayon*rayon);

        return surface;
    }
    /**Jeu de test rectangle
     *  Longueur| Largeur| resultat
     *     5    |    2   |    10
     *     2    |   0.5  |     1
     *    0.5   |  0.25  |   0.125
     */   
    /**
     * @param longueur
     *      La longueur du rectangle (nombre positif).
     * @param largeur
     *      La largeur du rectangle (nombre positif, inférieur ou égal à la longueur).
     * @return la surface du rectangle
     */

    public static double surfaceRectangle(double longueur, double largeur) {
        double surface;
        surface= longueur*largeur;
        return surface;
    }
    /**
     * @param cote
     *      La longueur d'un cote du carré (nombre positif).
     * @return la surface du carré
     */
    public static double surfaceCarre(double cote) {
        double surface;
    surface = surfaceRectangle(cote, cote);
        return surface;
    }
    /**Jeu de test Gazon
     *  longueurTerrain| largeurTerrain| diametrePiscine | longueurMaison | largeurMaison | largeurAppenti| Resultat Gazon
     *        50       |       29      |       5         |       12       |       10      |        5      |     1285.36
     */   
    /**
     * Surface du gazon, en m2. Toutes les données sont en m.
     * Terrain, maison, appenti et piscine s'organisent comme vu en TD.
     */
    public static double surfaceGazon(double longueurTerrain, double largeurTerrain,
    double diametrePiscine, double longueurMaison, double largeurMaison, double largeurAppenti) {
        double surfaceGazon;
        double surfaceTerrain = surfaceRectangle(longueurTerrain, largeurTerrain); 
        double surfaceMaison  = surfaceRectangle(longueurMaison, largeurMaison); 
        double surfacePiscine = surfaceDisque(diametrePiscine); 
        double surfaceAppenti = surfaceRectangle(largeurMaison,largeurAppenti);
        surfaceGazon = surfaceTerrain - (surfaceMaison + surfacePiscine + surfaceAppenti/2);
        return surfaceGazon;
    }
    /**Jeu de test informationTonte
     *  longueurTerrain| largeurTerrain| diametrePiscine | longueurMaison | largeurMaison | largeurAppenti| Resultat surface| vitesse | Resultat duree 
     *        50       |       29      |       5         |       12       |       10      |        5      |     1285.36     |  100    |     12,85
     *        20       |       20      |       10        |       15       |       15      |        5      |     58,96       |  100    |     0,59
     */   
    public static void informationTonte(double longueurTerrain, double largeurTerrain,
    double diametrePiscine, double longueurMaison, double largeurMaison, double largeurAppenti) {
    double surface = surfaceGazon(longueurTerrain, largeurTerrain, diametrePiscine, longueurMaison, largeurMaison, largeurAppenti);
    double vitesse= 100, duree;
    duree = surface/vitesse;
    System.out.printf("\nil y a %,.2f m² à tondre\n", surface);
    System.out.printf("Cela prendra %,.2f heures\n", duree);
    
    }
}

