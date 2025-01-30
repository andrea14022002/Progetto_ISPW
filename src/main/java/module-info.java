module PlantNursery {
    requires java.logging;
    requires javafx.fxml;
    requires javafx.controls;
    requires javafx.graphics;
    requires java.sql;
    requires java.desktop;

    opens com.plantnursery.controller.gui.fx;
    opens com.plantnursery.bean;
    opens com.plantnursery.payment;
    opens com.plantnursery.utils.dao;

    exports com.plantnursery.controller.gui.fx;
    exports com.plantnursery.bean;
    exports com.plantnursery.utils.dao;
    exports com.plantnursery.payment;
    exports com.plantnursery.exception;
    exports com.plantnursery;
    exports com.plantnursery.utils;
    opens com.plantnursery.utils;
    exports com.plantnursery.utils.view.fx;
    opens com.plantnursery.utils.view.fx;
    opens com.plantnursery.utils.dao.factory;
}