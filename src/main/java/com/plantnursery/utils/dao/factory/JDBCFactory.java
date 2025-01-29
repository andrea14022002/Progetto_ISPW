package com.plantnursery.utils.dao.factory;

import com.plantnursery.dao.*;
import com.plantnursery.dao.jdbc.*;
import com.plantnursery.utils.dao.factory.FactorySingletonDAO;

public class JDBCFactory extends FactorySingletonDAO {

        public OrderDAO getOrderDAO(){
            return new OrderJDBC();
        }

        public SetPlantDAO getSetPlantDAO(){
            return new SetPlantJDBC();
        }

        public PlantDAO getPlantDAO(){
            return new PlantJDBC();
        }

        public SellerDAO getSellerDAO(){
            return new SellerJDBC();
        }

        public NotificationDAO getNotificationDAO(){
            return new NotificationJDBC();
        }
}
