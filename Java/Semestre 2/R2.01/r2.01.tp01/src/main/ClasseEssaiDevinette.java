package main;

import tps.jeux.Devinette;
import tps.jeux.exception.ErreurExecutionDevinette;

import java.util.Scanner;
import java.util.Random;

public class ClasseEssaiDevinette {
    
    public static void main(String[] argv) {
        Scanner clavier = new Scanner(System.in);
        for (int cpt = 0; cpt < 3 ; cpt ++){
        Devinette game = new Devinette();
        System.out.println("Devine a quel nombre je pense !" + " ("+game.getBas() +"-"+game.getHaut()+") :");
        while (!game.isDernierCoupGagnant()) {
            int entree = clavier.nextInt();
            try{
            game.soumettreCoup(entree);
            }catch(ErreurExecutionDevinette e){
                System.out.println(e.getMessage());
            }
            if (!game.isValeurDansBornes(entree)){
                System.out.println("Votre valeur n'est pas dans l'intervalle !" + " ("+game.getBas() +"-"+game.getHaut()+") :");
            } else {
                if (game.isDernierCoupTropBas()){
                    System.out.println("Plus haut !" + " ("+game.getBas() +"-"+game.getHaut()+") :");
                } else if (game.isDernierCoupTropHaut()){
                    System.out.println("Plus bas !" + " ("+game.getBas() +"-"+game.getHaut()+") :");
                }
            }
        }
        System.out.println ("Gagné !");
        System.out.println("Vous avez utiliser "+game.getNbCoupsJoues()+ " coups");
    }
    }
}
