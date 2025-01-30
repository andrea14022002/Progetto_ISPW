package com.plantnursery.view.cli;

import java.util.InputMismatchException;
import java.util.Scanner;

public class ListSetPlantsView extends AbstractView {

    private static final String INPUT_ERROR = "Invalid input!";

    @Override
    public int showMenu() {
        printMenu("SET PLANT PAGE", "Show all set plants", "Select set plant", "Home", "Exit");
        return getInputMenu(4);
    }

    public Integer selectSetPlant() {
        Scanner input = new Scanner(System.in);
        showMessage("Enter the number of the set plant: ");
        while (true) {
            try {
                return input.nextInt();
            } catch (InputMismatchException e) {
                showMessage(INPUT_ERROR);
                input.next();
            }
        }
    }

    public void showSets(String[] sets) {
        printTitle("SET PLANTS LIST");
        int lengthToPrint = sets.length;
        int i = 0;
        while (lengthToPrint > 0) {
            int max = Math.min(5, lengthToPrint) + i;
            while (i < max) {
                showMessage(sets[i]);
                i++;
            }
            lengthToPrint -= max;
            if (lengthToPrint > 0) {
                showMessage("Insert 0 to show more set plants or 1 to show menu: ");
                Scanner input = new Scanner(System.in);
                int choice;
                boolean choiceFlag = true;
                while (choiceFlag){
                    choice = getIntChoice(input);
                    switch (choice) {
                        case 0:
                            choiceFlag = false;
                            break;
                        case 1:
                            return;
                        default:
                            showMessage(INPUT_ERROR);
                    }
                }
            }
        }
    }

    private int getIntChoice(Scanner input) {
        try {
            return input.nextInt();
        } catch (InputMismatchException e) {
            input.next();
            return -1;
        }
    }

}
