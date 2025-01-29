package com.plantnursery.utils.dao.factory;

import com.plantnursery.dao.OrderDAO;
import com.plantnursery.dao.*;
import com.plantnursery.dao.fs.*;
import com.plantnursery.utils.dao.factory.FactorySingletonDAO;

public class FSFactory extends FactorySingletonDAO {

    public OrderDAO getOrderDAO(){
        return new OrderFS();
    }

    public SetPlantDAO getSetPlantDAO(){
        return new SetPlantFS();
    }

    public PlantDAO getPlantDAO(){
        return new PlantFS();
    }

    public SellerDAO getSellerDAO(){
        return new SellerFS();
    }

    public NotificationDAO getNotificationDAO(){
        return new NotificationFS();
    }
}
