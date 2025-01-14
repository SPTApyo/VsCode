public class CrashSousAlgo {
    // Une fonction qui à l'occasion peut émettre une exception
    public static int maFonction1() throws Exception {  
          if (Math.random() > 0.5) {
              throw new Exception("maFonction1 a eu une defaillance (desole)"); 
          }
      return 99; 
    } // fin maFonction
  
    public static void main(String arguments[]) {
      try {
          int resultat = maFonction1() ;
      }
      catch (Exception e){ System.out.println("[CRASH "+e+"] "); } 
  
      System.out.println("Qu'il serait bon d'arriver ici ...");
    } // fin main
  
  } // fin class