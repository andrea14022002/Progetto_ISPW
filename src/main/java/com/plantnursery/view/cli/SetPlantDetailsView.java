package com.plantnursery.view.cli;

public class SetPlantDetailsView extends AbstractView {

    @Override
    public int showMenu() {
        printMenu("SET PLANT DETAILS PAGE", "Show all info", "Order plants", "Order management", "Back", "Home", "Exit");
        return getInputMenu(6);
    }

    public void showInfo(String[] info) {
        printTitle("SET PLANT INFO");
        for (String s : info) {
            showMessage(s);
        }
    }

}
