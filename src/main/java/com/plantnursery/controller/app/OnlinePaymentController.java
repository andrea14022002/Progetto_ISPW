package com.plantnursery.controller.app;

import com.plantnursery.payment.Observer;
import com.plantnursery.payment.PayPalAPI;
import com.plantnursery.payment.PaymentAPI;
import com.plantnursery.payment.PaymentRequest;
import com.plantnursery.model.Seller;

public class OnlinePaymentController extends Observer {

    private PaymentAPI paymentAPI;

    private Boolean response;

    protected boolean payPayPal(Seller seller, Double amount, String reason) {
        PaymentRequest request = new PaymentRequest(seller.getInfoPayPal(), amount, reason);
        paymentAPI = new PayPalAPI();
        paymentAPI.registerObserver(this);
        long startTime = System.currentTimeMillis();
        long timeout = 60000L;

        paymentAPI.processPayment(request);
        while(response == null) {
            if(System.currentTimeMillis() - startTime > timeout) {
                return false;
            }
        }
        return response;
    }

    @Override
    protected void update(){
        if (paymentAPI != null){
            response = paymentAPI.getResponse();
        }
    }
}


