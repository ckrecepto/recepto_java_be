package com.getrecepto.receptoparking.entities.mongo;

import com.getrecepto.receptoparking.constants.VehicleType;
import com.getrecepto.receptoparking.entities.qrCode.UpiData;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Slf4j
@Data
@SuperBuilder
@Document(collection = "QrCodes")
public class QrCodeDocument {
    @Id
    private String id;
    private Boolean used = false;
    private String supplier = "RAZORPAY";
    private VehicleType vehicleType;
    private float amount;
    private LocalDateTime expiry;
    private UpiData upiData;
}
