import junit.textui.TestRunner;
import junit.framework.TestSuite;
import junit.framework.TestCase;

public class PileTest extends TestCase {
  static int totalAssertions = 0;
  static int bilanAssertions = 0;

  /*
   Types des opérations du type Pile
  */
  public void test_type_new_Pile() throws Exception {
    Pile pile = new Pile() ;
	  
    totalAssertions++ ;
    assertEquals("new Pile() retourne une Pile", "Pile", pile.getClass().getName());
    bilanAssertions++ ;
  }


  public void test_type_sommet() throws Exception {
    Pile pile = new Pile() ;
    ProgrammePile.empiler(pile,"XXX") ;
	  
    totalAssertions++ ;
    assertEquals("sommet(pile) retourne une String", "java.lang.String", ProgrammePile.sommet(pile).getClass().getName());
    bilanAssertions++ ;
  }

  /*
   Axiomes du type Pile
  */
  public void test_axiome1() {
    Pile pile = new Pile() ;
	  
    totalAssertions++ ;
    assertTrue("Une nouvelle pile est vide", ProgrammePile.estVide(pile));
    bilanAssertions++ ;
  }

  public void test_axiome2() throws Exception {
    Pile pile = new Pile() ;
    ProgrammePile.empiler(pile,"XXX") ;
	  
    totalAssertions++ ;
    assertFalse("Apres empiler(pile) : pile n'est pas vide", ProgrammePile.estVide(pile));
    bilanAssertions++ ;
  }

  public void test_axiome3() throws Exception {
    Pile pile = new Pile() ;
    ProgrammePile.empiler(pile,"XXX");
    ProgrammePile.depiler(pile) ; 
    
    totalAssertions++ ;
    assertTrue("Apres empiler(pile), depiler(pile) : pile est vide", ProgrammePile.estVide(pile));
    bilanAssertions++ ;
  }

  public void test_axiome4() throws Exception {
    Pile pile = new Pile() ;
    ProgrammePile.empiler(pile,"XXX") ;
	  
    totalAssertions++ ;
    assertEquals("Apres empiler(pile,\"XXX\") : Sommet == \"XXX\"", "XXX", ProgrammePile.sommet(pile));
    bilanAssertions++ ;
  }

  public void test_axiome5() throws Exception {
    Pile pile = new Pile() ;
    ProgrammePile.empiler(pile,"000");
    ProgrammePile.empiler(pile,"XXX");
    ProgrammePile.depiler(pile);
	  
    totalAssertions++ ;
    assertEquals("Apres empiler(pile,\"000\"), empiler(pile,\"XXX\"), depiler(pile) : Sommet == \"000\"", "000", ProgrammePile.sommet(pile));
    bilanAssertions++ ;
  }

  public void test_axiomes3et5() throws Exception {
    Pile pile = new Pile() ;
    ProgrammePile.empiler(pile,"000");
    ProgrammePile.empiler(pile,"XXX");
    ProgrammePile.depiler(pile) ;
	  
    totalAssertions++ ;
    assertEquals("Apres empiler(pile,\"000\"), empiler(pile,\"XXX\"), depiler(pile) : Sommet == \"000\"", "000", ProgrammePile.sommet(pile));
    bilanAssertions++ ;
	  
    ProgrammePile.depiler(pile) ;
	  
    totalAssertions++ ;
    assertTrue("Apres depiler(pile) : pile est vide", ProgrammePile.estVide(pile));
    bilanAssertions++ ;
  }

  /*
   Préconditions du type Pile
  */
  public void test_precondition1() {
    Pile pile = new Pile() ;
    boolean exception = false ;
    try { ProgrammePile.sommet(pile) ; } 
    catch (Exception e) { exception = true ; };
    
    totalAssertions++ ;
    assertTrue("Si estVide(pile), sommet(pile) leve une exception", exception);
    bilanAssertions++ ;
  }

  public void test_precondition2() {
    Pile pile = new Pile() ;
    boolean exception = false ;
    try { ProgrammePile.depiler(pile) ; } 
    catch (Exception e) { exception = true ; };
    
    totalAssertions++ ;
    assertTrue("Si estVide(pile), depiler(pile) leve une exception", exception);
    bilanAssertions++ ;
  }

  
  /*
   main() de la classe de Test
  */
  public static void main(String[] args) {
    junit.textui.TestRunner.run(new TestSuite(PileTest.class));
    if (bilanAssertions == totalAssertions) { System.out.print("Bravo !"); }
    System.out.println(" "+bilanAssertions+"/"+totalAssertions+" assertions verifiees");
  } // fin main

    /*
   Limites (concrètes) du type Pile
  */

  public void test_pile_pleine() throws Exception {
    Pile unePile = new Pile(2) ;
    boolean exception = false ;
    ProgrammePile.empiler(unePile,"111") ;
    ProgrammePile.empiler(unePile,"222") ;
    try { ProgrammePile.empiler(unePile,"333") ; }
    catch (Exception e) { exception = true ; };

    totalAssertions++ ;
    assertTrue("Si pile pleine, empiler() leve une exception", exception);
    bilanAssertions++ ;
  }

  public void test_depiler_pile_pleine() throws Exception {
    Pile unePile = new Pile(2) ;
    boolean exception = false ;
    ProgrammePile.empiler(unePile,"111") ;
    ProgrammePile.empiler(unePile,"222") ;
    try { ProgrammePile.empiler(unePile,"333") ; }
    catch (Exception e) { exception = true ; };

    totalAssertions++ ;
    assertTrue("Si pile pleine, empiler() leve une exception", exception);
    bilanAssertions++ ;

    totalAssertions++ ;
    assertEquals("Apres pile pleine, sommet() retourne l'avant dernier empile", "222", ProgrammePile.sommet(unePile));
    bilanAssertions++ ;
  }
  
  /*
   Opérations supplémentaires
  */

  public void test_toString_vide() throws Exception {
    Pile unePile = new Pile(2) ;

    totalAssertions++ ;
    assertEquals("toString(pile) retourne '|vide'", "|vide", ProgrammePile.toString(unePile));
    bilanAssertions++ ;
  }
  public void test_toString_non_vide() throws Exception {
    Pile unePile = new Pile(2) ;
    ProgrammePile.empiler(unePile,"A");
    ProgrammePile.empiler(unePile,"B");

    totalAssertions++ ;
    assertEquals("toString() retourne '|B|A|vide'", "|B|A|vide", ProgrammePile.toString(unePile));
    bilanAssertions++ ;
  }
  public void test_nbElements() throws Exception {
    Pile unePile = new Pile(2) ;

    totalAssertions++ ;
    assertEquals("nbElements() retourne 0", 0, ProgrammePile.nbElements(unePile));
    bilanAssertions++ ;

    ProgrammePile.empiler(unePile,"A");
    ProgrammePile.empiler(unePile,"B");

    totalAssertions++ ;
    assertEquals("nbElements() retourne 2", 2, ProgrammePile.nbElements(unePile));
    bilanAssertions++ ;

    ProgrammePile.depiler(unePile);

    totalAssertions++ ;
    assertEquals("nbElements() retourne 1", 1, ProgrammePile.nbElements(unePile));
    bilanAssertions++ ;

    ProgrammePile.depiler(unePile);

    totalAssertions++ ;
    assertEquals("nbElements() retourne 0", 0, ProgrammePile.nbElements(unePile));
    bilanAssertions++ ;
  }
}
 // fin PileTest