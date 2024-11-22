public class CrashHueBoum {
  public static void main(String[] args) {

    try {
      // Traitements et affichages
      int compteur = 10;
      while (compteur >= 0) {
        System.out.print(compteur * compteur / compteur);
        System.out.print(' ');
        compteur = compteur - 1;
      }
    } catch (Exception e) {
      System.out.println(e);
    }
    System.out.println("BOUM");

    System.out.println("Qu'il serait bon d'arriver ici ...");

    try {
      // Traitements et affichages
      int tabChiffres[] = { 10, 9, 8, 7, 6, 5, 4, 3, 2, 1 };
      int indice = 0;
      while (indice <= 10) {
        System.out.print(tabChiffres[indice]);
        System.out.print(' ');
        indice = indice + 1;
      }
    } catch (Exception e) {
      System.out.println(e);
    }
    System.out.println("BOUM");

  }

}
