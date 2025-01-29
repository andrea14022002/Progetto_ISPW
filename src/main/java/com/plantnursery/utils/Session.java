package com.plantnursery.utils;

import com.plantnursery.bean.SetPlantBean;
import com.plantnursery.bean.UserBean;

public class Session {

    private UserBean user;
    private String month;
    private SetPlantBean setPlant;

    public void setUser(UserBean user) {
        this.user = user;
    }

    public UserBean getUser() {
        return user;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getMonth() {
        return month;
    }

    public void setSetPlant(SetPlantBean setPlant) {
        this.setPlant = setPlant;
    }

    public SetPlantBean getSetPlant() {
        return setPlant;
    }

    public void reset() {
        this.user = null;
        this.month = null;
        this.setPlant = null;
    }

    public void softReset() {
        this.month = null;
        this.setPlant = null;
    }

    public void resetSetPlant() {
        this.setPlant = null;
    }

    public void resetMonth() {
        this.month = null;
    }
}
