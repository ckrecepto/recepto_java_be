package com.getrecepto.receptoparking.services.razorPay;

import com.getrecepto.receptoparking.entities.VehicleBill;
import com.getrecepto.receptoparking.entities.qrCode.UpiData;
import com.razorpay.RazorpayClient;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service("RazorpayService")
public class RazorpayService {
    @Autowired
    @Qualifier("RazorpayClient")
    private RazorpayClient razorpayClient;

    @PostConstruct
    private void warmUpQrCodes() {
        //TODO check in mongo for available UPI qr codes
        //TODO if count is less than create qr codes and store in mongo with TTL of 24 hours
    }

    public List<UpiData> getQrCodes(VehicleBill vehicleBill, int size) {
        //TODO @Lakshmana QR generation changes
        return null;
    }
}

/***
 * qr code
 * print code
 *
 * payment confirmation
 *
 * transaction id
 *  10 each
 *      car
 *      bike
 *      bus
 *      van
 *
 *   when threshold hits
 *      6
 *      generate 10+ for each vehicle type
 *
 *  db:
 *      fetch from mongo where transaction_status is available
 */