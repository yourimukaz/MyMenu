package com.ocr.youri;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static junit.framework.TestCase.assertEquals;


public class OrderTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private Object Order;
    private Object Interaction;

    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(System.out);
    }

    Order order = new Order();

    @Test
    public void Given_ChikenWithFriesAndWaterInStandardInput_When_MenuIsRun_Then_DisplayCorrectProcess() {
        System.setIn(new ByteArrayInputStream("1\n2\n3\n".getBytes()));
        order = new Order();
        order.runMenu();
        String[] output = outContent.toString().replace("\r\n", "\n").split("\n");
        assertEquals("Vous avez choisi comme menu : poulet", output[5]);
        assertEquals("Vous avez choisi comme accompagnement : frites", output[11]);
        assertEquals("Vous avez choisi comme boisson : soda", output[17]);
    }
    @Test
    public void Given_BeefWithVegetableInStandardInput_When_MenuIsRun_Then_DisplayCorrectProcess() {
        System.setIn(new ByteArrayInputStream("2\n1\n".getBytes()));
        order = new Order();
        order.runMenu();
        String[] output = outContent.toString().replace("\r\n", "\n").split("\n");
        assertEquals("Vous avez choisi comme menu : boeuf", output[5]);
        assertEquals("Vous avez choisi comme accompagnement : légumes frais", output[11]);
    }
    @Test
    public void Given_VegetarianWithNoRiceAndSparklingWaterInStandardInput_When_MenuIsRun_Then_DisplayCorrectProcess() {
        System.setIn(new ByteArrayInputStream("3\n2\n2\n".getBytes()));
        order = new Order();
        order.runMenu();
        String[] output = outContent.toString().replace("\r\n", "\n").split("\n");
        assertEquals("Vous avez choisi comme menu : végétarien", output[5]);
        assertEquals("Vous avez choisi comme accompagnement : pas de riz", output[10]);
        assertEquals("Vous avez choisi comme boisson : eau gazeuse", output[16]);
    }

    @Test
    public void Given_OneMenuChikenWithFriesAndWaterInStandardInput_When_MenusIsRun_Then_DisplayCorrectProcess() {
        System.setIn(new ByteArrayInputStream("1\n1\n2\n3\n".getBytes()));
        order = new Order();
        order.runMenus();
        String[] output = outContent.toString().replace("\r\n", "\n").split("\n");
        assertEquals("Vous avez choisi comme menu : poulet", output[6]);
        assertEquals("Vous avez choisi comme accompagnement : frites", output[12]);
        assertEquals("Vous avez choisi comme boisson : soda", output[18]);
    }
    @Test
    public void Given_TwoMenu_BeefWithVegetable_VegetarianWithNoRiceAndSparklingWaterInStandardInput_When_MenusIsRun_Then_DisplayCorrectProcess() {
        System.setIn(new ByteArrayInputStream("2\n2\n1\n3\n2\n2\n".getBytes()));
        order = new Order();
        order.runMenus();
        String[] output = outContent.toString().replace("\r\n", "\n").split("\n");
        assertEquals("Vous avez choisi comme menu : boeuf", output[6]);
        assertEquals("Vous avez choisi comme accompagnement : légumes frais", output[12]);
        assertEquals("Vous avez choisi comme menu : végétarien", output[18]);
        assertEquals("Vous avez choisi comme accompagnement : pas de riz", output[23]);
        assertEquals("Vous avez choisi comme boisson : eau gazeuse", output[29]);
    }
    /** @Test
    public void Given_BadResponseAndResponse1_When_AskAboutCarWithThreeResponses_Then_DisplayErrorAndGoodResponse() {
    System.setIn(new ByteArrayInputStream("5\n1\n".getBytes()));
    Interaction = new Interaction();
    String[] responses = {"BMW", "Audi", "Mercedes"};
    Interaction.askSomething("voiture", responses);
    String[] output = outContent.toString().replace("\r\n", "\n").split("\n");
    assertEquals(true, output[0].contains("voiture"));
    assertEquals("Vous n'avez pas choisi de voiture parmi les choix proposés", output[5]);
    assertEquals("Vous avez choisi comme voiture : BMW", output[6]);
    }
     */

    @Test
    public void Given_Chiken_When_AskAboutMenus_Then_DisplayChikenChoice() {
        System.setIn(new ByteArrayInputStream("1\n".getBytes()));
        order = new Order();
        order.askMenu();
        String[] output = outContent.toString().replace("\r\n", "\n").split("\n");
        assertEquals("Vous avez choisi comme menu : poulet", output[5]);
    }
    @Test
    public void Given_FriesWithAllSidesEnabled_When_AskAboutSides_Then_DisplayFriesChoice() {
        System.setIn(new ByteArrayInputStream("2\n".getBytes()));
        order = new Order();
        order.askSide(true);
        String[] output = outContent.toString().replace("\r\n", "\n").split("\n");
        assertEquals("Vous avez choisi comme accompagnement : frites", output[5]);
    }
    @Test
    public void Given_Water_When_AskAboutDrinks_Then_DisplayWaterChoice() {
        System.setIn(new ByteArrayInputStream("1\n".getBytes()));
        order = new Order();
        order.askDrink();
        String[] output = outContent.toString().replace("\r\n", "\n").split("\n");
        assertEquals("Vous avez choisi comme boisson : eau plate", output[5]);
    }
    @Test
    public void Given_Response2_When_AskAboutCarWithThreeResponses_Then_ReturnNumber2() {
        System.setIn(new ByteArrayInputStream("5\n2\n".getBytes()));
        order = new Order();
        String[] responses = {"BMW", "Audi", "Mercedes"};
        int choice = order.askSomething("voiture", responses);
        assertEquals(2, choice);
    }
    @Test
    public void Given_Chiken_When_AskAboutMenus_Then_Return1() {
        System.setIn(new ByteArrayInputStream("1\n".getBytes()));
        order = new Order();
        int choice = order.askMenu();
        assertEquals(1, choice);
    }
    @Test
    public void Given_Response_When_CallingAskQuestion_Then_FillOrderSummaryCorrectly() {
        System.setIn(new ByteArrayInputStream(String.format("1%n").getBytes()));
        order = new Order();
        String[] responses = {"BMW", "Audi", "Mercedes"};
        int choice = order.askSomething("voiture", responses);
        assertEquals("Vous avez choisi comme voiture : BMW%n", order.orderSummary);
    }
    @Test
    public void Given_Responses_When_CallingRunMenus_Then_FillOrderSummaryCorrectly() {
        System.setIn(new ByteArrayInputStream(String.format("2%n1%n1%n1%n2%n2%n").getBytes()));
        order = new Order();
        order.runMenus();
        assertEquals("Résumé de votre commande :%n" +
                "Menu 1:%n" +
                "Vous avez choisi comme menu : poulet%n" +
                "Vous avez choisi comme accompagnement : légumes frais%n" +
                "Vous avez choisi comme boisson : eau plate%n" +
                "Menu 2:%n" +
                "Vous avez choisi comme menu : boeuf%n" +
                "Vous avez choisi comme accompagnement : frites%n" , order.orderSummary);
    }
    @Test
    public void Given_TextResponse_When_CallingAskQuestion_Then_DisplayErrorSentence() {
        System.setIn(new ByteArrayInputStream(String.format("texte%n1%n").getBytes()));
        order = new Order();
        String[] responses = {"BMW", "Audi", "Mercedes"};
        order.askSomething("voiture", responses);
        String[] output = outContent.toString().replace("\r\n", "\n").split("\n");
        assertEquals("Vous n'avez pas choisi de voiture parmi les choix proposés", output[5]);
    }
    @Test
    public void Given_BadMenusQuantityInStandardInput_When_MenusIsRun_Then_DisplayErrorSentence() {
        System.setIn(new ByteArrayInputStream(String.format("texte%n1%n1%n2%n3%n").getBytes()));
        order = new Order();
        order.runMenus();
        String[] output = outContent.toString().replace("\r\n", "\n").split("\n");
        assertEquals("Vous devez saisir un nombre, correspondant au nombre de menus souhaités", output[1]);
    }
}