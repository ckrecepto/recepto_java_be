package com.getrecepto.receptoparking.services.parking;

import com.getrecepto.receptoparking.constants.PaymentMode;
import com.getrecepto.receptoparking.constants.VehicleType;
import com.getrecepto.receptoparking.entities.NewParkingReq;
import com.getrecepto.receptoparking.entities.NewParkingRes;
import com.getrecepto.receptoparking.entities.VehicleBill;
import com.getrecepto.receptoparking.services.payment.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Slf4j
@Service("CarParkingService")
public class CarParkingService implements ParkingService {
    @Autowired
    @Qualifier("ReceptoThreadPool")
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Autowired
    @Qualifier("PaymentServiceFactory")
    private PaymentService paymentServiceFactory;

    private final List<VehicleBill> BILL_LIST = new LinkedList<VehicleBill>() {{
        add(VehicleBill.builder().vehicleType(VehicleType.Car).amount(100).paymentMode(PaymentMode.Upi).build());
        add(VehicleBill.builder().vehicleType(VehicleType.Car).amount(100).paymentMode(PaymentMode.Cash).build());
        add(VehicleBill.builder().vehicleType(VehicleType.Car).amount(0).paymentMode(PaymentMode.Cancelled).build());
        add(VehicleBill.builder().vehicleType(VehicleType.Car).amount(0).paymentMode(PaymentMode.UTurn).build());
    }};

    @Override
    public NewParkingRes newParking(NewParkingReq newParkingReq) throws Exception {
        return ParkingService.util(threadPoolTaskExecutor, paymentServiceFactory, this.BILL_LIST, newParkingReq);
    }
}
