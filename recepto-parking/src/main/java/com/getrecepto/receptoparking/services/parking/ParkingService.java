package com.getrecepto.receptoparking.services.parking;

import com.getrecepto.receptoparking.constants.PaymentMode;
import com.getrecepto.receptoparking.entities.NewParkingReq;
import com.getrecepto.receptoparking.entities.NewParkingRes;
import com.getrecepto.receptoparking.entities.TransactionDetail;
import com.getrecepto.receptoparking.entities.VehicleBill;
import com.getrecepto.receptoparking.services.payment.PaymentService;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

/***
 * entry ->
 *  bike only
 *  no cars data
 *
 * payment:
 *  online
 *  cash
 *
 * journey:
 *  u-turn
 *  cancellation
 */
public interface ParkingService {
    public NewParkingRes newParking(NewParkingReq newParkingReq) throws Exception;

    static public NewParkingRes util(ThreadPoolTaskExecutor threadPoolTaskExecutor, PaymentService paymentServiceFactory, List<VehicleBill> billList, NewParkingReq newParkingReq) throws Exception {
        List<TransactionDetail> transactionDetailList = new LinkedList<>();
        Map<PaymentMode, Future<TransactionDetail>> futureMap = new HashMap<>();
        for (VehicleBill vehicleBill : billList) {
            Future<TransactionDetail> fItr = threadPoolTaskExecutor.submit(() -> {
                return paymentServiceFactory.getPaymentDetail(vehicleBill);
            });
            futureMap.put(vehicleBill.getPaymentMode(), fItr);
        }

        for (PaymentMode pMode : futureMap.keySet()) {
            TransactionDetail transactionDetail = futureMap.get(pMode).get();
            transactionDetailList.add(transactionDetail);
        }
        return NewParkingRes.builder().req(newParkingReq).transactionDetailList(transactionDetailList).build();
    }
}
