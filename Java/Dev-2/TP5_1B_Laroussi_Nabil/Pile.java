
/**
 * @author Laroussi Nabil
 */
public class Pile {
    int elements[];
    int indiceSommet;

    Pile() {
        this.elements = new int[100];
        this.indiceSommet = -1;
    }

    Pile(int pfTaille) {
        this.elements = new int[pfTaille];
        this.indiceSommet = -1;
    }
}
