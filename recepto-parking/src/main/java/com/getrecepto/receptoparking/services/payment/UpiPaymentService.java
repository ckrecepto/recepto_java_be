package com.getrecepto.receptoparking.services.payment;

import com.getrecepto.receptoparking.entities.TransactionDetail;
import com.getrecepto.receptoparking.entities.VehicleBill;
import com.getrecepto.receptoparking.entities.mongo.QrCodeDocument;
import com.getrecepto.receptoparking.entities.qrCode.UpiData;
import com.getrecepto.receptoparking.repos.mongo.QrCodeMongoRepo;
import com.getrecepto.receptoparking.services.razorPay.RazorpayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service("UpiPaymentService")
public class UpiPaymentService implements PaymentService {
    private final Integer BUFFER_SIZE = 10;

    @Autowired
    private RazorpayService razorpayService;

    @Autowired
    private QrCodeMongoRepo qrCodeMongoRepo;

    @Autowired
    @Qualifier("ReceptoThreadPool")
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Override
    public TransactionDetail getPaymentDetail(VehicleBill vehicleBill) {
        UpiData upiData = this.getAvailableUpiData(vehicleBill);
        //TODO @Lakshmana create adaptor to transform upiData to transactionDetail
        return null;
    }

    private UpiData getAvailableUpiData(VehicleBill vehicleBill) {
        QrCodeDocument qrCodeDocument = this.qrCodeMongoRepo.getAvailableQrCodeDocument(vehicleBill.getVehicleType(), vehicleBill.getAmount());
        this.threadPoolTaskExecutor.submit(() -> {
            this.checkAndGenerateQrCodes(vehicleBill);
        });
        return qrCodeDocument.getUpiData();
    }

    private void checkAndGenerateQrCodes(VehicleBill vehicleBill) {
        final int AVAILABLE_QR_CODES = this.qrCodeMongoRepo.getAvailableQrCodes(vehicleBill.getVehicleType(), vehicleBill.getAmount());
        if (BUFFER_SIZE < AVAILABLE_QR_CODES) {
            log.info(String.format("%s available qr codes for vehicleType: %s", AVAILABLE_QR_CODES, vehicleBill.getVehicleType()));
            return;
        }
        log.info(String.format("less than bufferSize: %s, only %s available qr codes for vehicleType: %s", this.BUFFER_SIZE, AVAILABLE_QR_CODES, vehicleBill.getVehicleType()));
        List<QrCodeDocument> qrCodeDocumentList = new LinkedList<>();
        List<UpiData> razorPayQrCodes = this.razorpayService.getQrCodes(vehicleBill, this.BUFFER_SIZE);
        if (razorPayQrCodes != null && razorPayQrCodes.size() > 0) {
            for (UpiData upiDataItr : razorPayQrCodes) {
                QrCodeDocument qrCodeDocument = QrCodeDocument.builder().id(UUID.randomUUID().toString()).upiData(upiDataItr)
                        //.expiry()//TODO @Lakshmana fetch upiData expiryTime and subtract 10 minutes from there
                        .build();
                qrCodeDocumentList.add(qrCodeDocument);
            }
        }

        this.qrCodeMongoRepo.insertQrCodes(qrCodeDocumentList);
    }
}
