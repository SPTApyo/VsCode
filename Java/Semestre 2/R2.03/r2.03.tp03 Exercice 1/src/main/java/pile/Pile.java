package pile;

/**
 * Classe permettant de gérer une Pile de valeurs entières.
 * L'accès est LIFO (Last In First Out).
 * Exemple :
		 Pile p;
		 p = new Pile();
		 p.empiler(10);
		 p.empiler(20);
		 System.out.println(p.sommet()); // 20
		 p.depiler();
		 System.out.println(p.sommet()); // 10
		 System.out.println(p.estVide()); // false
 */
public class Pile {
	private int[] elements; // tableau des éléments contenus dans la Pile
	private int indiceSommet; // indice du dernier élément empilé, -1 => pile est vide
	
	/**
	 * Constructeur par défaut.
	 * Construit une Pile de taille 100 maxi.
	 */
	public Pile() {
		this.elements = new int[100];
		this.indiceSommet = -1;
	}

	/**
	 * Constructeur par défaut.
	 * Construit une Pile de taille 100 maxi.
	 */
	public Pile(int pfTaille) {
		this.elements = new int[pfTaille];
		this.indiceSommet = -1;
	}

	/**
	 * Indique si la Pile est pleine (true) ou pas.
	 * @return true si la Pile est pleine
	 */
	public boolean estPleine() {
		return (this.indiceSommet == this.elements.length-1);
	}
	
	/**
	 * Empile pfElement sur le haut de la Pile.
	 * @param pfElement
	 * 		prec: si estPleine() == False
	 * 
	 */
	public void empiler(int pfElement) {
		this.indiceSommet = this.indiceSommet + 1;
		this.elements[this.indiceSommet] = pfElement;
	}

	/**
	 * Indique si la Pile est vide (true) ou pas.
	 * @return true si la Pile est vide
	 */
	public boolean estVide() {
		return (this.indiceSommet == -1);
	}

	/**
	 * Dépile le sommet de la Pile.
	 * @see 	Pile#estVide()
	 * 		prec: si estVide() == False
	 * 
	 */
	public void depiler()  {
		this.indiceSommet = this.indiceSommet - 1;
	}

	/**
	 * Permet d'obtenir la valeur contenu au sommet de la Pile.
	 * @return la valeur au sommet de la Pile
	 * 		prec: si estVide() == False
	 * 
	 */
	public int sommet() {
		return (this.elements[this.indiceSommet]);
	}
}