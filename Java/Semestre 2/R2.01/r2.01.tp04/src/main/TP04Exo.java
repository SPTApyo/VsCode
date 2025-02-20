package main;

import java.util.*;


public class TP04Exo {
	public static void main(String[] argv) {

		ArrayList <String> alChaines = new ArrayList <String> ();
		int taille = 0;

		alChaines.add("chaine1");
		alChaines.add("chaine2");
		alChaines.add("chaine3");

		System.out.println("taille alChaines = "+alChaines.size());

		taille = alChaines.size();
		for(int cpt = 0; cpt < taille; cpt++){
			System.out.println(alChaines.get(cpt));
		}

		System.out.println("");
		alChaines.add("Fin");
		System.out.println("taille alChaines = "+alChaines.size());
		alChaines.add(0,"Debut");
		System.out.println("taille alChaines = "+alChaines.size());
		alChaines.add(alChaines.size()/2,"Milieu");
		System.out.println("taille alChaines = "+alChaines.size());

		taille = alChaines.size();
		for(int cpt = 0; cpt < taille; cpt++){
			System.out.println(alChaines.get(cpt));
		}
		System.out.println("taille alChaines = "+taille);

		for(int cpt = 0; cpt < 15; cpt++){
			alChaines.add("chaine "+cpt);
		}

		taille = alChaines.size();
		for(int cpt = 0; cpt < taille; cpt++){
			System.out.println(alChaines.get(cpt));
		}
		System.out.println("taille alChaines = "+taille);


		alChaines.set(0, "DEBUT MODIFIER !");
		alChaines.set(alChaines.size()-1, "FIN MODIFIER !");
		alChaines.set(alChaines.size()/2, "MILIEU MODIFIER !");

		taille = alChaines.size();
		for(int cpt = 0; cpt < taille; cpt++){
			System.out.println(alChaines.get(cpt));
		}
		System.out.println("taille alChaines = "+taille);

		System.out.println(alChaines);

		System.out.println("");
		alChaines.remove(0);
		System.out.println("taille alChaines = "+alChaines.size());
		alChaines.remove(alChaines.size()-1);
		System.out.println("taille alChaines = "+alChaines.size());
		alChaines.remove(alChaines.size()/2);
		System.out.println("taille alChaines = "+alChaines.size());


		//alChaines.clear();

		taille = alChaines.size();
		for(int cpt = 0; cpt < taille; cpt++){
			System.out.println(alChaines.get(cpt));
		}
		System.out.println("taille alChaines = "+taille);

		for ( String s : alChaines ) {
			System.out.println ( s.toUpperCase() + " lg : " + s.length() );
		}

		System.out.println("");
		Iterator<String> it = alChaines.iterator();
		while (it.hasNext()) {
			System.out.println(it.next());
		}

		ArrayList<Double> alChainesbis = new ArrayList<Double>();
		for(double cpt = 0; cpt < Math.random()*1000; cpt+=0.5){
			alChainesbis.add(cpt);
		}

		System.out.println("");
		Iterator<Double> itbis = alChainesbis.iterator();
		while (itbis.hasNext()) {
			System.out.println(itbis.next());
		}
	}
}