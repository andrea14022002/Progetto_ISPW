package com.plantnursery.payment;

public abstract class PaymentAPI extends Subject {

    protected boolean paymentResponse;

    public abstract void processPayment(PaymentRequest request);

    public boolean getResponse() {
        return paymentResponse;
    }
}
