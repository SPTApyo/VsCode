public class Main {
    public static void main(String[] args) {
        Pile maPile = new Pile(10);
        if (!maPile.est_vide()) System.out.println("NOK 1");

        maPile.empiler(5);
        if (maPile.est_vide()) System.out.println("NOK 2");

        int element = maPile.depiler();
        if (!maPile.est_vide()) System.out.println("NOK 3");
        if (element != 5) System.out.println("NOK 4");
     }
}