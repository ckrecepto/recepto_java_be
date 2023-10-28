package com.getrecepto.receptoparking.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.getrecepto.receptoparking.constants.PaymentMode;
import com.getrecepto.receptoparking.constants.VehicleType;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class NewParkingReq {
    @JsonProperty("payment_mode")
    private PaymentMode paymentMode;

    @JsonProperty("vehicle_type")
    private VehicleType vehicleType;

    @JsonProperty("vehicle_number")
    private Integer vehicleNumber;
}
