package com.getrecepto.receptoparking.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class NewParkingRes extends ParkingRes {
    @JsonProperty("req")
    private NewParkingReq req;

    @JsonProperty("transaction_details")
    private List<TransactionDetail> transactionDetailList;
}
