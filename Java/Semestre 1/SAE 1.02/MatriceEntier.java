/**
 * R1.01-Dev2 - TP#6
 *
 * @author Tilian HURÉ
 */
public class MatriceEntier {
    int nbL;
    int nbC;
    int tabMat[][];
    MatriceEntier() throws Exception {
        throw new Exception("Les nombres de lignes et de colonnes ne sont pas renseignés.");
    }
    MatriceEntier(int pfNbLignes, int pfNbColonnes) throws Exception {
        if ((pfNbLignes <= 0) || (pfNbColonnes <= 0)) {
            throw new Exception("Les nombres de lignes et de colonnes doivent être" +
                "supérieurs à 0.");
        } else {
            this.nbL = pfNbLignes;
            this.nbC = pfNbColonnes;
            this.tabMat = new int[this.nbL][this.nbC];
        }
    }
}