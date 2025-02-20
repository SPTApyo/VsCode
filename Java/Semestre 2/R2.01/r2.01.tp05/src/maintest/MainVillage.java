package maintest;
import lesschtroumpfs.Village;

public class MainVillage {

	public static void main (String[] argv) {
		String[] nomsSchtroumpfs = {
			"Grand Schtroumpf",
			"Schtroumpf courant (et pas ordinaire)",
			"Schtroumpf ordinaire (et pas courant)",
			"Schtroumpf moralisateur à lunettes",
			"Schtroumpf boudeur",
			"Schtroumpf volant",
			"Schtroumpf étonné",
			"Schtroumpf acrobate",
			"Schtroumpf paresseux"
		};

		Village SchtroumpfVillage = new Village("SchtroumpfLand", 50, nomsSchtroumpfs);
		System.out.println("Nom du village : " + SchtroumpfVillage.getNom());
		System.out.println("Population : " + SchtroumpfVillage.getNbSchtroumpf());
		System.out.println("Stock Salsepareille : " + SchtroumpfVillage.getStockSalsepareille());
		SchtroumpfVillage.solstice_d_ete();
		System.out.println("\n");
		System.out.println("Le chef du village est : " + SchtroumpfVillage.chefDuVillage());
		SchtroumpfVillage.envoyerAuTravail();
		SchtroumpfVillage.SchtroumpfsHeureux();
		System.out.println("\n");

		System.out.println("Partie Finale\n");
		SchtroumpfVillage = new Village("SchtroumpfLand", 50, nomsSchtroumpfs);
		SchtroumpfVillage.solstice_d_ete();
		SchtroumpfVillage.envoyerCueillirSalsepareille();
		SchtroumpfVillage.solstice_d_ete();
		SchtroumpfVillage.dinerTousEnsemble();
		SchtroumpfVillage.solstice_d_ete();
	}

}
