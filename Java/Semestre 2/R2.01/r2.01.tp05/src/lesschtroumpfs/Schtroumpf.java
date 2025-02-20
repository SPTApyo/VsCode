package lesschtroumpfs;

public class Schtroumpf {
	private String nom;
	private int age;
	private boolean content;
	
	private Village VillageDuSchtroumpf;

    /**
     * Constructeur pour Schtroumpf.
     * @param pfNom le nom du Schtroumpf
     * @param pfAge l'âge du Schtroumpf
     */
    public Schtroumpf(String pfNom, int pfAge) {
		this.nom = pfNom;
		this.age = pfAge;
		this.content = true;
		this.VillageDuSchtroumpf = null;
	}
	
    /**
     * Constructeur pour Schtroumpf avec village.
     * @param pfNom le nom du Schtroumpf
     * @param pfAge l'âge du Schtroumpf
     * @param pfVillage le village du Schtroumpf
     */
	public Schtroumpf(String pfNom, int pfAge, Village pfVillage) {
		this.nom = pfNom;
		this.age = pfAge;
		this.content = true;
		this.VillageDuSchtroumpf = pfVillage;
	}
	
    /**
     * Obtient le nom du Schtroumpf.
     * @return le nom du Schtroumpf
     */
	public String getNom(){
		return this.nom;
	}

    /**
     * Obtient l'âge du Schtroumpf.
     * @return l'âge du Schtroumpf
     */
	public int getAge(){
		return this.age;
	}

    /**
     * Vérifie si le Schtroumpf est content.
     * @return true si le Schtroumpf est content, false sinon
     */
	public boolean estContent(){
		return this.content;
	}

    /**
     * Présente le Schtroumpf en imprimant sa description.
     */
	public void sePresenter(){
		System.out.println(toString());
	}

    /**
     * Obtient le chant du Schtroumpf en fonction de son contentement.
     * @return le chant du Schtroumpf
     */
	public String leChant(){
		return this.content ? "la, la, la Schtroumpf la, la" : "gloups";
	}

    /**
     * Fait chanter le Schtroumpf en imprimant son chant.
     */
	public void chanter(){
		System.out.println(leChant());
	}

    /**
     * Augmente l'âge du Schtroumpf d'un an.
     */
	public void anniversaire(){
		this.age++;
	}

    /**
     * Rend le Schtroumpf content s'il mange une quantité positive de nourriture.
     * @param pfQte la quantité de nourriture
     */
	public void manger(int pfQte){
		if (pfQte > 0){
			this.content = true;
		}
		if (this.VillageDuSchtroumpf != null && this.VillageDuSchtroumpf.getStockSalsepareille()>=3){
			this.VillageDuSchtroumpf.setStockSalsepareille((this.VillageDuSchtroumpf.getStockSalsepareille()-3));
		} else {
			
		}
	}

    /**
     * Le Schtroumpf récolte de la salsepareille.
     * S'il a un village, il ajoute 5 feuilles au stock du village et devient triste.
     * S'il n'a pas de village, il mange les 5 feuilles et reste content.
     */
	public void recolterSalsepareille(){
		if (this.VillageDuSchtroumpf != null) {
			this.VillageDuSchtroumpf.setStockSalsepareille(this.VillageDuSchtroumpf.getStockSalsepareille() + 5);
			this.content = false;
		} else {
			this.content = true; // l'ermite est content de manger la recolte
		}
	}

    /**
     * Le Schtroumpf dîne au village.
     * S'il a un village et que le stock de salsepareille est suffisant, il consomme 3 feuilles et devient content.
     * Sinon, il devient triste.
     */
	public void dinerAuVillage(){
		if (this.VillageDuSchtroumpf != null) {
			if (this.VillageDuSchtroumpf.getStockSalsepareille() >= 3) {
				this.VillageDuSchtroumpf.setStockSalsepareille(this.VillageDuSchtroumpf.getStockSalsepareille() - 3);
				this.content = true;
			} else {
				this.content = false;
			}
		} else {
			this.content = false;
		}
	}

    /**
     * Fait travailler le Schtroumpf, ce qui le rend pas content.
     */
	public void allerTravailler(){
		this.content = false;
	}

    /**
     * Retourne une représentation sous forme de chaîne de caractères du Schtroumpf.
     * @return une représentation sous forme de chaîne de caractères du Schtroumpf
     */
	@Override
	public String toString(){
		return "Je suis " + this.nom + ", j'ai " + this.age + " ans et je suis " + (this.content ? "content" : "pas content") + (this.VillageDuSchtroumpf == null ? ", je suis un ermite !" : ", je viens du village : " + this.VillageDuSchtroumpf.getNom());
	}

    /**
     * Obtient le village du Schtroumpf.
     * @return le village du Schtroumpf
     */
	public Village getVillage(){
		return VillageDuSchtroumpf;
	}

    /**
     * Définit le village du Schtroumpf.
     * @param pfVillage le nouveau village du Schtroumpf
     */
	public void setVillage(Village pfVillage){
		this.VillageDuSchtroumpf = pfVillage;
	}

}
