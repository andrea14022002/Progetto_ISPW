package com.plantnursery.utils.dao.factory;

import com.plantnursery.dao.*;

public abstract class FactorySingletonDAO {

    protected static FactorySingletonDAO instance = null;

    protected FactorySingletonDAO(){}

    public static synchronized FactorySingletonDAO getDefaultDAO (){
        if (instance == null) {
            String daoType = System.getProperty("DAO_TYPE");
            switch (TypeDAO.valueOf(daoType)) {
                case JDBC:
                    instance = new JDBCFactory();
                    break;
                case FS:
                    instance = new FSFactory();
                    break;
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
