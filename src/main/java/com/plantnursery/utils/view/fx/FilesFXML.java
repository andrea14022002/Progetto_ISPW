package com.plantnursery.utils.view.fx;

public enum FilesFXML {
    HOME("/HomeView.fxml"),

    LIST_SETPLANTS("/ListSetPlantsView.fxml"),

    SETPLANT("/SetPlantDetailsView.fxml"),

    ORDER("/OrderView.fxml"),

    LOGIN("/LoginAndSignupView.fxml"),

    NOTIFICATION("/NotificationsView.fxml"),

    SELLER_HOME("/SellerHomeView.fxml"),;

    private final String path;

    FilesFXML(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

}