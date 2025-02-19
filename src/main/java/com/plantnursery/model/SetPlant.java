package com.plantnursery.model;

import java.io.Serial;
import java.io.Serializable;
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

    private Integer quantity;

    private Integer availability;

    private String sellerUsername;

    private transient List<Plant> plants;

    private transient List<Order> orders;

    public SetPlant(Integer id, String name, String description, String temp, String plantMonth,
                    Integer quantity, Integer availability, Double price, String sellerUsername) {
        this.idSet = id;
        this.name = name;
        this.desription = description;
        this.temperature = temp;
        this.plantMonth = plantMonth;
        this.price = price;
        this.availability = availability;
        this.quantity = quantity;
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
        return this.idSet;
    }

    public Integer getQuantity() {
        return this.quantity;
    }

    public Integer getAvailability() {
        return this.availability;
    }

    public Integer getIdSet() {
        return this.idSet;}

    public List<Order> getOrders() {
        return this.orders;
    }

    public List<Plant> getPlants(){
        return this.plants;
    }

    public void setTransientParams() {
        this.orders = new ArrayList<>();
        this.plants = new ArrayList<>();
    }


}
