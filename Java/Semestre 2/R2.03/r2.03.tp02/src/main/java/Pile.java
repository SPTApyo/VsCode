
public class Pile {

    int elements[];
    int indiceSommet;

    public Pile(int pfTaille) {
        this.elements = new int[pfTaille];
        this.indiceSommet = -1;
    }

    public boolean est_vide() {
        // TODO Auto-generated method stub
        return indiceSommet == -1;
    }

    public void empiler(int i) {
        // TODO Auto-generated method stub
        this.elements[this.indiceSommet + 1] = i;
        this.indiceSommet++;
    }

    public int depiler() {
        // TODO Auto-generated method stub
        this.indiceSommet = this.indiceSommet - 1;
        return this.elements[this.indiceSommet + 1];
    }

}
