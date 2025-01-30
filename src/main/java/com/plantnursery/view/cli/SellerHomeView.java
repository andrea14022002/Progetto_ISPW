package com.plantnursery.view.cli;

public class SellerHomeView extends AbstractView {

    @Override
    public int showMenu() {
        printMenu("SELLER HOME PAGE", "View Set Plants", "View Notifications", "View Settings", "Log Out", "Exit");
        return getInputMenu(5);
    }
}
