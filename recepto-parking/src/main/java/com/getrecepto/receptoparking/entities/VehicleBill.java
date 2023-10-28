package com.getrecepto.receptoparking.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.getrecepto.receptoparking.constants.PaymentMode;
import com.getrecepto.receptoparking.constants.VehicleType;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class VehicleBill {
    private VehicleType vehicleType;
    private PaymentMode paymentMode;
    private float amount;
}
