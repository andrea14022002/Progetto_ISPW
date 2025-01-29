package com.plantnursery.model;

import com.plantnursery.model.SetPlant;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;

public class SetPlant implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Integer idSet;

    private String name;

    private String desription;

    private Double price;

    private String temperature;

    private String plantMonth;

    private Integer availability;

    private String sellerUsername;

    private transient List<Plant> plants;

    private transient List<Order> orders;

    private Integer quantity;

    public SetPlant(Integer id, String name, String description, String temp, String plantMonth, Double price, Integer quantity,
                    Integer availability, String sellerUsername) {
        this.idSet = id;
        this.name = name;
        this.desription = description;
        this.temperature = temp;
        this.plantMonth = plantMonth;
        this.quantity = quantity;
        this.price = price;
        this.availability = availability;
        this.sellerUsername = sellerUsername;
        this.orders = new ArrayList<>();
        this.plants = new ArrayList<>();
    }

    public void addOrder(Order order) {
        this.orders.add(new Order(order.getLastname(), order.getFirstname(), order.getAddress(), order.getEmail(),order.getTelephone(),
                order.getCity(),order.getOnlinePayment()));
    }

    public void setOrders(List<Order> orders) {
        if(!this.orders.isEmpty()) {
            return;
        }

        for (Order order : orders) {
            addOrder(order);
        }
    }

    public void setIdSet(Integer idSet) {
        this.idSet = idSet;
    }

    public void setSellerUsername(String sellerUsername) {
        this.sellerUsername = sellerUsername;
    }

    public void setDescription(String desc) {
        this.desription = desc;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOrderClosed(Integer availability) {
        this.availability = availability;
    }

    public String getName() {
        return this.name;
    }

    public String getPlantMonth() {
        return this.plantMonth;
    }

    public String getTemperature() {
        return this.temperature;
    }

    public String getDescription() {
        return this.desription;
    }

    public Double getPrice() {
        return this.price;
    }

    public String getSellerUsername() {
        return this.sellerUsername;
    }

    public Integer getOrderClosed() {
        return this.availability;
    }

    public Integer getIdSet() {
        return this.idSet;}

    /*
    public Integer getSetAvailability(String type) {
        if (getLimitTicket(type) == 0){
            return 0;
        }

        int availability = getLimitTicket(type) - ticketsSold.get(type);
        if(availability < 0) {
            throw new IllegalStateException("Impossible to have negative availability.");
        }
        return availability;
    }

    public Integer getBookedTickets(String type) {
        return ticketsSold.get(type);
    }
*/
    public List<Order> getOrders() {
        return this.orders;
    }

    public List<Plant> getPlants(){
        return this.plants;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getQuantity(){
        return quantity;
    }

    public void setTransientParams() {
        this.orders = new ArrayList<>();
        this.plants = new ArrayList<>();
        //this.ticketsSold = new HashMap<>();
    }


}
