module PrivateEvents {
    requires java.logging;
    requires javafx.fxml;
    requires javafx.controls;
    requires javafx.graphics;
    requires java.sql;
    requires java.desktop;

    opens com.privateevents.controller.gui.fx;
    opens com.privateevents.utils.view.fx;
    opens com.privateevents.bean;
    opens com.privateevents.engineering.payment;
    opens com.privateevents.utils.dao;

    exports com.privateevents.controller.gui.fx;
    exports com.privateevents.bean;
    exports com.privateevents.utils.view.fx;
    exports com.privateevents.utils.dao;
    exports com.privateevents.engineering.payment;
    exports com.privateevents.exception;
    exports com.privateevents;
    exports com.privateevents.utils;
    opens com.privateevents.utils;
}