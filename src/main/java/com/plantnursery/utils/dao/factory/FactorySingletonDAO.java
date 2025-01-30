package com.plantnursery.utils.dao.factory;

import com.plantnursery.dao.*;

public abstract class FactorySingletonDAO {

    protected static FactorySingletonDAO instance = null;

    protected FactorySingletonDAO(){}

    public static synchronized FactorySingletonDAO getDefaultDAO (){
        if (instance == null) {
            String daoType = System.getProperty("DAO_TYPE");
            if (daoType != null && daoType.equalsIgnoreCase("JDBC")) {
                instance = new JDBCFactory();
            } else if (daoType != null && daoType.equalsIgnoreCase("FS")) {
                instance = new FSFactory();
            } else {
                throw new IllegalArgumentException("Invalid DAO_TYPE: " + daoType);
            }
            return instance;
        }
        return instance;
    }

    public abstract OrderDAO getOrderDAO();

    public abstract SetPlantDAO getSetPlantDAO();

    public abstract PlantDAO getPlantDAO();

    public abstract SellerDAO getSellerDAO();

    public abstract NotificationDAO getNotificationDAO();

}
