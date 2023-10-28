package com.getrecepto.receptoparking.services.payment;

import com.getrecepto.receptoparking.constants.PaymentMode;
import com.getrecepto.receptoparking.entities.TransactionDetail;
import com.getrecepto.receptoparking.entities.VehicleBill;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service("PaymentServiceFactory")
public class PaymentServiceFactory implements PaymentService {
    @Autowired
    @Qualifier("CashPaymentService")
    private PaymentService CashPaymentService;

    @Autowired
    @Qualifier("CancelPaymentService")
    private PaymentService CancelPaymentService;
    @Autowired
    @Qualifier("UTurnPaymentService")
    private PaymentService UTurnPaymentService;
    @Autowired
    @Qualifier("UpiPaymentService")
    private PaymentService UpiPaymentService;

    private final Map<PaymentMode, PaymentService> factoryMap = new HashMap<PaymentMode, PaymentService>() {{
        put(PaymentMode.Cash, CashPaymentService);
        put(PaymentMode.Cancelled, CancelPaymentService);
        put(PaymentMode.Upi, UpiPaymentService);
        put(PaymentMode.UTurn, UTurnPaymentService);
    }};


    @Override
    public TransactionDetail getPaymentDetail(VehicleBill vehicleBill) {
        return this.factoryMap.get(vehicleBill.getPaymentMode()).getPaymentDetail(vehicleBill);
    }
}
