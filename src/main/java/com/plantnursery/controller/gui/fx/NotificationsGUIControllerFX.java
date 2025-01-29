package com.plantnursery.controller.gui.fx;

import com.plantnursery.bean.NotificationBean;
import com.plantnursery.bean.SellerBean;
import com.plantnursery.controller.app.NotificationsController;
import com.plantnursery.exception.NotFoundException;
import com.plantnursery.exception.OperationFailedException;
import com.plantnursery.utils.SessionManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class NotificationsGUIControllerFX extends AbstractGUIControllerFX {

    @FXML
    Button all;

    @FXML
    Button selected;

    @FXML
    TableView<NotificationBean> table;

    @FXML
    TableColumn<NotificationBean, String> type;

    @FXML
    TableColumn<NotificationBean, String> setPlant;

    @FXML
    TableColumn<NotificationBean, String> code;

    @FXML
    TableColumn<NotificationBean, ZonedDateTime> time;

    SellerBean sellerBean;

    List<NotificationBean> notifications;

    ObservableList<NotificationBean> showNotifications;

    @FXML
    public void deleteAll(){
        resetMsg(errorMsg);
        try {
            NotificationsController notificationsController = new NotificationsController();
            notificationsController.deleteAllNotifications(sellerBean);
            showNotifications.clear();
        } catch (OperationFailedException e) {
            setMsg(errorMsg, e.getMessage());
        }
    }

    @FXML
    public void deleteSelected() {
        resetMsg(errorMsg);

        ObservableList<NotificationBean> selectedItems = table.getSelectionModel().getSelectedItems();
        try {
            if (selectedItems.isEmpty()) {
                setMsg(errorMsg, "No items selected");
            } else {
                NotificationsController notificationsController = new NotificationsController();
                notificationsController.deleteNotifications(selectedItems, sellerBean);
                showNotifications.removeAll(selectedItems);
            }
        } catch (OperationFailedException e) {
            setMsg(errorMsg, e.getMessage());
        }

    }

    public void initialize(Integer session) throws OperationFailedException, NotFoundException {
        resetMsg(errorMsg);
        this.currentSession = session;

        sellerBean = (SellerBean) SessionManager.getSessionManager().getSessionFromId(currentSession).getUser();
        NotificationsController notificationsController = new NotificationsController();
        notifications = notificationsController.getNotifications(sellerBean);
        for (NotificationBean notification : notifications) {
            notification.setDateAndTime(notification.getDateAndTime().withZoneSameInstant(ZoneId.systemDefault()));
        }

        showNotifications = FXCollections.observableArrayList(notifications);

        type.setCellValueFactory(new PropertyValueFactory<>("type"));
        setPlant.setCellValueFactory(new PropertyValueFactory<>("setName"));
        code.setCellValueFactory(new PropertyValueFactory<>("orderCode"));
        time.setCellValueFactory(new PropertyValueFactory<>("dateAndTime"));
        time.setCellFactory(param -> new TableCell<>() {
            @Override
            protected void updateItem(ZonedDateTime item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy - HH:mm");
                    setText(item.format(formatter));
                }
            }
        });

        table.editableProperty().setValue(false);
        table.getSelectionModel().setSelectionMode(javafx.scene.control.SelectionMode.MULTIPLE);
        table.setItems(showNotifications);

    }
}
