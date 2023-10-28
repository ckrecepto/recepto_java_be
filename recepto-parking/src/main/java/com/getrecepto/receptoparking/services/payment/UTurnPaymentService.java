package com.getrecepto.receptoparking.services.payment;

import com.getrecepto.receptoparking.constants.PaymentMode;
import com.getrecepto.receptoparking.entities.TransactionDetail;
import com.getrecepto.receptoparking.entities.VehicleBill;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service("UTurnPaymentService")
public class UTurnPaymentService implements PaymentService {
    @Override
    public TransactionDetail getPaymentDetail(VehicleBill vehicleBill) {
        return TransactionDetail.builder()
                .paymentMode(PaymentMode.UTurn)
                .amount(0)
                .build();
    }
}
