package com.plantnursery.controller.app;

import com.plantnursery.bean.OrderBean;
import com.plantnursery.bean.PlantBean;
import com.plantnursery.bean.SetPlantBean;
import com.plantnursery.exception.DuplicateEntryException;
import com.plantnursery.exception.IncorrectDataException;
import com.plantnursery.exception.NotFoundException;
import com.plantnursery.exception.OperationFailedException;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CreateOrderControllerTest {

    @Test
    void testFindMonthSetPlants() throws OperationFailedException, NotFoundException {
        System.setProperty("DAO_TYPE", "JDBC");
        CreateOrderController createOrderController = new CreateOrderController();
        List<SetPlantBean> setPlantsBean = null;
        setPlantsBean = createOrderController.findSetPlants("October");
        assertNotEquals(new ArrayList<>(), setPlantsBean);
    }

    @Test
    void testGetPlantsSetPlants() throws OperationFailedException, NotFoundException {
        System.setProperty("DAO_TYPE", "JDBC");
        CreateOrderController createOrderController = new CreateOrderController();
        List<SetPlantBean> setPlantBeans = createOrderController.findSetPlants("October");
        List<PlantBean> plantsBean = createOrderController.getPlants(setPlantBeans.getFirst());
        assertFalse(plantsBean.isEmpty());
    }

    @Test
    void testSendOrder() throws OperationFailedException, IncorrectDataException, DuplicateEntryException, NotFoundException {
        System.setProperty("DAO_TYPE", "JDBC");
        CreateOrderController createOrderController = new CreateOrderController();
        SetPlantBean setPlantBean = createOrderController.findSetPlants("Milan").getFirst();
        OrderBean orderBean = new OrderBean();
        orderBean.setIdSetPlant(setPlantBean.getIdSet());
        orderBean.setEmail("bianchi.mimmo@gmail.com");
        orderBean.setTelephone("+393899903040");
        orderBean.setFirstName("Mimmo");
        orderBean.setLastName("Bianchi");
        orderBean.setCity("Bologna");
        orderBean.setAddress("Via Nazionale, 89");
        orderBean.setOnlinePayment(false);
        createOrderController.sendOrder(setPlantBean, orderBean);
        assertNotNull(orderBean.getCodeOrder());
    }
}