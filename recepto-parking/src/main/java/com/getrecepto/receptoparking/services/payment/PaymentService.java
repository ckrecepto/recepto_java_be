package com.getrecepto.receptoparking.services.payment;

import com.getrecepto.receptoparking.entities.TransactionDetail;
import com.getrecepto.receptoparking.entities.VehicleBill;

public interface PaymentService {
    public TransactionDetail getPaymentDetail(VehicleBill vehicleBill);
}
