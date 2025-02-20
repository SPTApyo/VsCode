package lesschtroumpfs;

import java.util.*;

public class Village {
	private String nomVillage;
	private ArrayList<Schtroumpf> Population = new ArrayList<Schtroumpf>();
	private int nbSchtroumpf;
	private int stockSalsepareille;

	/**
	 * Constructeur pour Village.
	 * @param pfNomV le nom du village
	 * @param pfStockSalsepareille le stock initial de salsepareille
	 * @param pfNomsDesSchtroumpf les noms des Schtroumpfs du village
	 */
	public Village(String pfNomV, int pfStockSalsepareille, String[] pfNomsDesSchtroumpf){
		this.nomVillage = pfNomV;
		this.stockSalsepareille = pfStockSalsepareille;
		for (int i = 0; i < pfNomsDesSchtroumpf.length; i++) {
			String nom = pfNomsDesSchtroumpf[i];
			this.Population.add(new Schtroumpf(nom, (int)(Math.random() * 150), this));
			this.nbSchtroumpf++;
		}
	}

	/**
	 * Retourne le nom du village.
	 * @return le nom du village
	 */
	public String getNom(){
		return this.nomVillage;
	}

	/**
	 * Retourne le nombre de Schtroumpfs dans le village.
	 * @return le nombre de Schtroumpfs
	 */
	public int getNbSchtroumpf(){
		return this.nbSchtroumpf;
	}

	/**
	 * Retourne le nombre de Schtroumpfs heureux dans le village.
	 * @return le nombre de Schtroumpfs heureux
	 */
	public int getNbSchtroumpfHeureux(){
		int cpt = 0, taille = this.getNbSchtroumpf();
		for (int i = 0; i < taille; i++) {
			if (this.Population.get(i).estContent()) {
				cpt++;
			}
		}
		return cpt;
	}

	/**
	 * Retourne le stock de salsepareille du village.
	 * @return le stock de salsepareille
	 */
	public int getStockSalsepareille(){
		return this.stockSalsepareille;
	}

	/**
	 * Définit le stock de salsepareille du village.
	 * @param pfVal la nouvelle valeur du stock de salsepareille
	 */
	public void setStockSalsepareille(int pfVal){
		this.stockSalsepareille = pfVal;
	}

	/**
	 * Fête le solstice d'été en faisant se présenter et chanter tous les Schtroumpfs.
	 * Affiche le nom du village, la population totale et le nombre de Schtroumpfs heureux.
	 */
	public void solstice_d_ete(){
		int cpt = 0;
		int taille = this.getNbSchtroumpf();
		for(int i = 0; i < taille; i++){
			this.Population.get(i).sePresenter();
			this.Population.get(i).chanter();
			if (this.Population.get(i).estContent()) {
				cpt++;
			}
		}
		System.out.println("Nom du village : " + this.getNom());
		System.out.println("Population du solstice : " + this.getNbSchtroumpf());
		System.out.println("Population du solstice Heureuse : " + cpt);
	}

	/**
	 * Retourne le Schtroumpf le plus âgé du village, considéré comme le chef.
	 * @return le Schtroumpf le plus âgé
	 */
	public Schtroumpf chefDuVillage(){
		int IDchefDuVillage = 0;
		int taille = this.getNbSchtroumpf();
		for(int i = 1; i < taille; i++){
			if ( this.Population.get(IDchefDuVillage).getAge() < this.Population.get(i).getAge()) {
				IDchefDuVillage = i;
			}
		}
		return this.Population.get(IDchefDuVillage);
	}

	/**
	 * Envoie travailler la moitié des Schtroumpfs heureux du village.
	 * Les Schtroumpfs envoyés travailler deviennent tristes.
	 */
	public void envoyerAuTravail(){
		int taille = this.getNbSchtroumpf();
		ArrayList<Integer> schtroumpfsContent = new ArrayList<>();
		for (int i = 0; i < taille; i++) {
			if (this.Population.get(i).estContent()) {
				schtroumpfsContent.add(i);
			}
		}
		taille = schtroumpfsContent.size();
		for (int i = 0; i < taille; i+=2) {
			this.Population.get(schtroumpfsContent.get(i)).allerTravailler();
		}
	}

	/**
	 * Envoie cueillir de la salsepareille la moitié des Schtroumpfs heureux du village.
	 * Les Schtroumpfs envoyés cueillir de la salsepareille deviennent tristes.
	 * Met à jour le stock de salsepareille du village.
	 */
	public void envoyerCueillirSalsepareille(){
		this.stockSalsepareille = this.getStockSalsepareille()+(this.getNbSchtroumpfHeureux()/2)*5;
		this.envoyerAuTravail();
	}

	/**
	 * Affiche et fait chanter tous les Schtroumpfs heureux du village.
	 */
	public void SchtroumpfsHeureux(){
		int taille = this.getNbSchtroumpf();
		for(int i = 0; i < taille; i++){
			if (this.Population.get(i).estContent()) {
				this.Population.get(i).sePresenter();
				this.Population.get(i).chanter();
			}
		}
	}

	/**
	 * Fait dîner tous les Schtroumpfs du village.
	 * Chaque Schtroumpf heureux consomme 3 feuilles de salsepareille.
	 */
	public void dinerTousEnsemble(){
		int taille = this.getNbSchtroumpf();
		for(int i = 0; i < taille; i++){
			this.Population.get(i).dinerAuVillage();
		}
	}

}
