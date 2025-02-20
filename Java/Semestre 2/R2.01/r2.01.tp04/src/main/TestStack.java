package main;

import java.util.*;

public class TestStack {

    public static void main(String[] argv) {

        Stack<String> pileString = new Stack<String>();

        pileString.push("chaine1");
        pileString.push("chaine2");
        pileString.push("chaine3");

        System.out.println("taille pileString = " + pileString.size());
        System.out.println("pileString = " + pileString);
        System.out.println("sommet pileString = " + pileString.peek());
        System.out.println("pileString = " + pileString);
        System.out.println("sommet pileString.pop = " + pileString.pop());
        System.out.println("pileString = " + pileString);
        System.out.println("sommet pileString = " + pileString.peek());
        System.out.println("pileString = " + pileString);
        pileString.clear();
        System.out.println("pileString = " + pileString);
        pileString.push("chaine4");
        pileString.push("chaine5");
        pileString.push("chaine6");
        pileString.push("chaine7");
        System.out.println("pileString = " + pileString);
        while (!pileString.isEmpty()) {
            System.out.println("pileString.pop = " + pileString.pop());
        }
        System.out.println("pileString = " + pileString);
        System.out.println("sommet pileString = " + pileString.peek());
        System.out.println("pileString.pop = " + pileString.pop());
    }

}
