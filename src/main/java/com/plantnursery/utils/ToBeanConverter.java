package com.plantnursery.utils;

import com.plantnursery.bean.*;
import com.plantnursery.exception.IncorrectDataException;
import com.plantnursery.model.*;
import java.time.ZoneId;

public class ToBeanConverter {

    private ToBeanConverter() {
        throw new IllegalStateException("Utility class.");
    }

    public static SetPlantBean fromSetToSetPlantBean(SetPlant setPlant) throws IncorrectDataException {
        SetPlantBean setPlantBean = new SetPlantBean();
        setPlantBean.setIdSet(setPlant.getIdSet());
        setPlantBean.setName(setPlant.getName());
        setPlantBean.setTemperature(setPlant.getTemperature());
        setPlantBean.setPlantMonth(setPlant.getPlantMonth());
        setPlantBean.setPrice(setPlant.getPrice());
        setPlantBean.setDescription(setPlant.getDescription());
        setPlantBean.setSellerUsername(setPlant.getSellerUsername());
        setPlantBean.setAvailability(setPlant.getOrderClosed());


        return setPlantBean;
    }

    public static NotificationBean fromNotificationToNotificationBean(Notification notification) {
        NotificationBean notificationBean = new NotificationBean();
        notificationBean.setType(notification.getType().toString());
        notificationBean.setName(notification.getSetName());
        notificationBean.setOrderCode(notification.getOrderCode());
        notificationBean.setDateAndTime(notification.getDateAndTime().atZone(ZoneId.systemDefault()));
        return notificationBean;
    }

    public static OrderBean fromOrderToOrderBean(Order order) throws IncorrectDataException {
        OrderBean orderBean = new OrderBean();
        orderBean.setLastName(order.getLastname());
        orderBean.setFirstName(order.getFirstname());
        orderBean.setAddress(order.getAddress());
        orderBean.setEmail(order.getEmail());
        orderBean.setTelephone(order.getTelephone());
        orderBean.setCity(order.getCity());
        orderBean.setOnlinePayment(order.getOnlinePayment());
        return orderBean;
    }

    public static PlantBean fromPlantToPlantBean(Plant plant) throws IncorrectDataException {
        PlantBean plantBean = new PlantBean();
        plantBean.setName(plant.getName());
        plantBean.setScientificName(plant.getScientificName());
        return plantBean;
    }

    public static SellerBean fromSellerToSellerBean(Seller seller) throws IncorrectDataException {
        SellerBean sellerBean = new SellerBean();
        sellerBean.setUsername(seller.getUsername());
        sellerBean.setFirstName(seller.getFirstName());
        sellerBean.setLastName(seller.getLastName());
        sellerBean.setEmail(seller.getEmail());
        sellerBean.setInfoPayPal(seller.getInfoPayPal());
        sellerBean.setAddress(seller.getAddress());
        sellerBean.setCity(seller.getCity());
        return sellerBean;
    }

}
