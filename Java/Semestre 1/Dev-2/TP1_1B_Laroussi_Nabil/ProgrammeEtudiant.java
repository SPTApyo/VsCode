public class ProgrammeEtudiant {
    public static void main(String[] args) {
      Etudiant tabInfo[];
      tabInfo = new Etudiant[8];//tous les inscrits en info
      Adresse adresse1 = new Adresse(13, "rue des Noyers", "31000", "Toulouse");
      Adresse adresse2 = new Adresse(12, "rue Alfred Kastler", "17000", "La Rochelle");
      Adresse adresse3 = new Adresse(1, "rue des Rossignols", "31700", "Blagnac");
      Adresse adresse4 = new Adresse(20, "place des cerisiers", "11000", "Carcasonne");
      Adresse adresse5 = new Adresse(66, "avenue Michelet", "47000", "Agen");
      Adresse adresse6 = new Adresse(13, "rue des Erables", "31700", "Cahors");
      Adresse adresse7 = new Adresse(7, "rue du Cagire", "31100", "Toulouse");
      tabInfo[0] = new Etudiant ("Peninou", "Andre", "y33", 'L', adresse1);
      tabInfo[1] = new Etudiant ("Canut", "Marie-Françoise", "y23", '1', adresse2);
      tabInfo[2] = new Etudiant ("Demay", "Laurent", "z23", '2', adresse3);
      tabInfo[3] = new Etudiant ("de Michiel", "Marianne", "l87", '1', adresse4);
      tabInfo[4] = new Etudiant ("Sotin", "Pascal", "l67", '1', adresse5);
      tabInfo[5] = new Etudiant ("Stolf", "Patricia", "v12", '1', adresse6);
      tabInfo[6] = new Etudiant ("Nonne", "Laurent", "p56", 'L', adresse7);
      int nbEtudiants = 7;
  
      for(int i = 0; i < nbEtudiants; i++) {
        System.out.print("Nom : "+tabInfo[i].nom + " prénom " +tabInfo[i].prenom+ " Adresse : ");
        afficheAdresse(tabInfo[i].adr);
      }
      System.out.println("");
      for(int i = 0; i < nbEtudiants; i++) {
        System.out.println("Nom : "+tabInfo[i].nom + " prénom " +tabInfo[i].prenom+ " adresse " + tabInfo[i].adr.nomrue);
      }
      System.out.println("");
      for(int i = 0; i < nbEtudiants; i++) {
        System.out.println("Nom : "+tabInfo[i].nom + " prénom " +tabInfo[i].prenom+ " adresse " + tabInfo[i].adr);
      }
  
    }

    public static void afficheAdresse(Adresse pfAdresse) {
      System.out.println(pfAdresse.numrue + " " + pfAdresse.nomrue + ", " + pfAdresse.codepostal + " " + pfAdresse.ville);
    }
  }
