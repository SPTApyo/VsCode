package main;

import java.util.*;

public class TestHashMap {

    public static void main(String[] argv) {

        HashMap<String, String> annuaire = new HashMap<String, String>();

        annuaire.put("Albert", "06.45.32.98.45");
        annuaire.put("Michael", "06.78.45.12.65");
        annuaire.put("Tartuffe", "07.85.25.14.96");
        annuaire.put("Vivagel", "05.31.85.15.95");

        System.out.println(annuaire.size());
        System.out.println(annuaire);
        String search;

        search = annuaire.get("Albert");
        if (search != null) {
            System.out.println("Albert : " + search);
        } else {
            System.out.println("absent");
        }

        search = annuaire.get("Toto");
        if (search != null) {
            System.out.println("Toto : " + search);
        } else {
            System.out.println("absent");
        }

        search = annuaire.get("Vivagel");
        if (search != null) {
            System.out.println("Vivagel : " + search);
        } else {
            System.out.println("absent");
        }

        search = annuaire.get("VIVAGEL");
        if (search != null) {
            System.out.println("VIVAGEL : " + search);
        } else {
            System.out.println("absent");
        }

        search = annuaire.put("Albert", "06.85.58.47.42"); 
        if (search != null) {
            System.out.println("Albert ancienne val : " + search);
        } else {
            System.out.println("Albert ancienne val : absent");
        }

        search = annuaire.get("Albert");
        if (search != null) {
            System.out.println("Albert : " + search);
        } else {
            System.out.println("Albert : absent");
        }

        System.out.println(annuaire.size());
        System.out.println(annuaire);

        search = annuaire.put("Simon", "07.56.98.14.74"); 
        if (search != null) {
            System.out.println("Simon ancienne val : " + search);
        } else {
            System.out.println("Simon ancienne val : absent");
        }

        search = annuaire.get("Simon");
        if (search != null) {
            System.out.println("Simon : " + search);
        } else {
            System.out.println("Simon : absent");
        }

        System.out.println(annuaire);
        annuaire.clear();
        System.out.println(annuaire.size());
        System.out.println(annuaire);
    }

}
