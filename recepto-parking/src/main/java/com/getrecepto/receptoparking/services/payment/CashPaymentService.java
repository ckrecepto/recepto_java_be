package com.getrecepto.receptoparking.services.payment;

import com.getrecepto.receptoparking.constants.PaymentMode;
import com.getrecepto.receptoparking.entities.TransactionDetail;
import com.getrecepto.receptoparking.entities.VehicleBill;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service("CashPaymentService")
public class CashPaymentService implements PaymentService {
    @Override
    public TransactionDetail getPaymentDetail(VehicleBill vehicleBill) {
        return TransactionDetail.builder()
                .paymentMode(PaymentMode.Cash)
                .amount(vehicleBill.getAmount())
                .build();
    }
}
