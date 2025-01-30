package com.plantnursery.view.cli;

import java.util.InputMismatchException;
import java.util.Scanner;

public class OrderView extends AbstractView {

    @Override
    public int showMenu() {
        printMenu("ORDER PAGE", "Show plants","New order", "Back", "Home", "Exit");
        return getInputMenu(6);
    }

    public void showPlants(String[] plants) {
        printTitle("PLANTS LIST");
        for (String s : plants) {
            showMessage(s);
        }
    }

    public String[] insertData() {
        printTitle("ORDER PROCEDURE");
        String[] data = new String[8];
        Scanner input = new Scanner(System.in);
        getInput(input, data, 0, "Enter your Firstname: ");
        getInput(input, data, 1, "Enter your Lastname: ");
        getInput(input, data, 2, "Enter your Cap: ");
        getInput(input, data, 3, "Enter your City: ");
        getInput(input, data, 4, "Enter your Email: ");
        getInput(input, data, 5, "Enter your Telephone Number (with national prefix): ");
        getInput(input, data, 6, "Enter the Address: ");
        getInput(input, data, 7, "Do you want to pay online using PayPal? (Y/N): ");

        showMessage("DATA INSERTED CHECK");
        showMessage("Firstname: " + data[0]);
        showMessage("Lastname: " + data[1]);
        showMessage("Address: " + data[2]);
        showMessage("City: " + data[3]);
        showMessage("Email: " + data[4]);
        showMessage("Telephone Number: " + data[5]);
        showMessage("PayPal: " + data[6]);

        if(data[6].equalsIgnoreCase("Y")){
            data[6] = "true";
        } else {
            data[6] = "false";
        }

        int choice;
        while (true) {
            try {
                showMessage("Press 0 to confirm or 1 to cancel: ");
                choice = input.nextInt();
                if (choice >= 0 && choice <= 1) {
                    break;
                } else {
                    throw new InputMismatchException();
                }
            } catch (InputMismatchException e) {
                showMessage("Invalid input!");
                input.next();
            }
        }
        if (choice == 0) {
            return data;
        } else {
            return new String[0];
        }
    }
}
