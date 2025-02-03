package com.plantnursery.model;

import com.plantnursery.exception.EncryptionException;
import java.io.Serial;
import java.util.*;

public class Seller extends User{
    @Serial
    private static final long serialVersionUID = 1L;

    public Seller(String username, String password, String email, String firstName, String lastName, String infoPayPal, String address) throws EncryptionException {
        super(username, password);
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.infoPayPal = infoPayPal;
        this.address = address;
    }

    private final String firstName;

    private final String lastName;

    private String email;

    private final String infoPayPal;

    private String address;

    private transient List<SetPlant> setPlants = new ArrayList<>();

    private transient List<Notification> notifs = new ArrayList<>();

    public void addSetPlant(SetPlant setPlant) {
        this.setPlants.add(new SetPlant(setPlant.getIdSet(), setPlant.getName(), setPlant.getDescription(), setPlant.getTemperature(),
                setPlant.getPlantMonth(), setPlant.getPrice(), setPlant.getSellerUsername()));
    }

    public List<SetPlant> getSets() {
        return this.setPlants;
    }

    public void modifyEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return this.email;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public String getInfoPayPal() {
        return this.infoPayPal;
    }

    public void addNotif(Notification notif) {
        this.notifs.add(new Notification(notif.getType(), notif.getDateAndTime(), notif.getSetName(), notif.getOrderCode()));
    }

    public void removeNotif(Notification notif) {
        this.notifs.removeIf(n -> n.getType().equals(notif.getType())
                && n.getDateAndTime().equals(notif.getDateAndTime())
                && n.getSetName().equals(notif.getSetName())
                && n.getOrderCode().equals(notif.getOrderCode()));
    }

    public List<Notification> getNewNotif() {
        return this.notifs;
    }

    public void addNotif(List<Notification> notifs) {
        for (Notification notif : notifs) {
            this.addNotif(notif);
        }
    }

    public void removeNotif(List<Notification> notifs) {
        for (Notification notif : notifs) {
            this.removeNotif(notif);
        }
    }
    public void setTransientParams() {
        this.setPlants = new ArrayList<>();
        this.notifs = new ArrayList<>();
    }

    public void addSetPlants(List<SetPlant> setPlants) {
        for (SetPlant setPlant : setPlants) {
            this.addSetPlant(setPlant);
        }
    }

    public String getAddress() {
        return this.address;
    }

    public String getCity() {
        return this.address;
    }

}
