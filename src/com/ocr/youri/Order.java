package com.ocr.youri;

import javax.sound.midi.Soundbank;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import static java.nio.file.StandardOpenOption.APPEND;

public class Order {

    boolean responseIsGood;
    String orderSummary = "";
    Scanner sc = new Scanner(System.in);
    /**
     * Run asking process for several menus.
     */
    public void runMenus() {
        Path Wfile_csv = Paths.get("C:\\Users\\Youri Mukaz\\IdeaProjects\\MyMenu-I\\ressource\\commande.csv");
        System.out.println("Combien souhaitez vous commander de menu ?");
        int menuQuantity = -1;
        boolean responseIsGood;
        do {
            try {
                menuQuantity= sc.nextInt();
                responseIsGood = true;
            } catch (InputMismatchException e) {
                sc.next();
                System.out.println("Vous devez saisir un nombre, correspondant au nombre de menus souhaités");
                responseIsGood = false;
            }
        } while (!responseIsGood);
        orderSummary = "Résumé de votre commande :%n";
        for (int i = 0; i < menuQuantity; i++) {
            orderSummary += "Menu " + (i + 1) + ":%n";
            String fileLine = runMenu();
            try {
                Files.write(Wfile_csv, String.format(fileLine).getBytes(), APPEND);
            } catch (IOException e) {
                System.out.println("Ooops une erreur est survenue. Merci de réessayer plus tard");
                return;            }
        }
        System.out.println("");
        System.out.println(String.format(orderSummary));
    }
    /**
     * Run asking process for a menu.
     */
    /**
     * Run asking process for a menu.
     */
    public String runMenu() {
        int nbMenu = askMenu();
        int nbSide = -1;
        int nbDrink = -1;
        switch (nbMenu) {
            case 1:
                nbSide = askSide(true)
                nbDrink = askDrink();
                break;
            case 2:
                nbSide = askSide(true);
                break;
            case 3:
                nbSide = askSide(false);
                nbDrink = askDrink();
                break;
        }
        return nbMenu + "," + nbSide + "," +nbDrink+ "%n";
    }

    /**
     * Display a question about a category in the standard input, get response and display it
     * @param category the category of the question
     * @param responses available responses
     * @return the number of the selected choice
     */
    public int askSomething(String category, String[] responses) {
        System.out.println("Choix " + category);
        for (int i = 1; i <= responses.length; i++)
            System.out.println(i + " - " + responses[i - 1]);
        System.out.println("Que souhaitez-vous comme " + category + "?");
        int nbResponse = 0;

        do {
            try {
                nbResponse = sc.nextInt();
                responseIsGood = (nbResponse >= 1 && nbResponse <= responses.length);
            } catch (InputMismatchException e) {
                sc.next();
                responseIsGood = false;
            }
            if (responseIsGood) {
                String choice = "Vous avez choisi comme " + category + " : " + responses[nbResponse - 1];
                orderSummary += choice + "%n";
                System.out.println(choice);
            } else {
                boolean isVowel = "aeiouy".contains(Character.toString(category.charAt(0)));
                if (isVowel)
                    System.out.println("Vous n'avez pas choisi d'" + category + " parmi les choix proposés");
                else
                    System.out.println("Vous n'avez pas choisi de " + category + " parmi les choix proposés");
            }
        } while (!responseIsGood);
        return nbResponse;
    }

    /**
     * Display a question about menu in the standard input, get response and display it
     */
    public int askMenu() {
        String[] menus = {"poulet", "boeuf", "végétarien"};
        return askSomething("menu", menus);
    }

    /**
     * Display a question about side in the standard input, get response and display it
     */
    public int askSide(boolean allSidesEnable) {
        if (allSidesEnable) {
            String[] responsesAllSide = {"légumes frais", "frites", "riz"};
            return askSomething("accompagnement", responsesAllSide);
        } else {
            String[] responsesOnlyRice = {"riz", "pas de riz"};
            return askSomething("accompagnement", responsesOnlyRice);
        }
    }

    /**
     * Display a question about drink in the standard input, get response and display it
     */
    public int askDrink() {
        String[] responsesDrink = {"eau plate", "eau gazeuse", "soda"};
        return askSomething("boisson", responsesDrink);
    }
}