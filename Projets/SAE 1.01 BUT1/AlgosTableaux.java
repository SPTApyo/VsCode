

public class AlgosTableaux {
    public static void main(String[] args) {
        double[] numbers = { 800, 200, 700, 300, 1, 50 };
        double pfTabPodium[];
        double pfTabPodiumScore[];
        pfTabPodium = new double[15];
        pfTabPodiumScore = new double[15];
        Podium(numbers.length, numbers, pfTabPodium, pfTabPodiumScore);
        System.out.println("\n\n");

        System.out.println("Le podium est :");
        for (int cpt = 0; cpt < 3; cpt++) {
            System.out.println("Position n°" + (cpt + 1) + " Joueur " + pfTabPodium[cpt]+" Avec un score de "+pfTabPodiumScore[cpt]);
        }

    }

    public static void Podium(int pfNbAthlete, double pfTabScore[], double pfTabPodium[], double pfTabPodiumScore[]) {
        int i = 0;
        for (int cpt = 0; cpt < pfNbAthlete; cpt++) {
            pfTabPodiumScore[cpt] = pfTabScore[0];
        }
        for (int cpt = 0; cpt < pfNbAthlete; cpt++) {
            i++;
            if (pfTabPodiumScore[0] > pfTabScore[cpt]) {
                pfTabPodium[0] = i;
                pfTabPodiumScore[0] = pfTabScore[cpt];
            }
        }
        i = 0;
        for (int cpt = 0; cpt < pfNbAthlete; cpt++) {
            i++;
            if ((pfTabPodiumScore[1] > pfTabScore[cpt]) && (pfTabPodiumScore[0] < pfTabScore[cpt])) {
                pfTabPodium[1] = i;
                pfTabPodiumScore[1] = pfTabScore[cpt];
            }
        }
        i = 0;
        for (int cpt = 0; cpt < pfNbAthlete; cpt++) {
            i++;
            if ((pfTabPodiumScore[2] > pfTabScore[cpt]) && (pfTabPodiumScore[0] < pfTabScore[cpt])
                    && (pfTabPodiumScore[1] < pfTabScore[cpt])) {
                pfTabPodium[2] = i;
                pfTabPodiumScore[2] = pfTabScore[cpt];
            }
        }

    }
}