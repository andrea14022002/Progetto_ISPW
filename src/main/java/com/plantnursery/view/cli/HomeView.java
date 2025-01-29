package com.plantnursery.view.cli;

import java.util.Scanner;

public class HomeView extends AbstractView {

    @Override
    public int showMenu() {
        printMenu("HOME PAGE", "Search SetPlants", "Login or register", "Exit");
        return getInputMenu(3);
    }

    public String searchSetPlant() {
        Scanner input = new Scanner(System.in);
        showMessage("Enter the name of the month: ");
        return input.nextLine();
    }

}
