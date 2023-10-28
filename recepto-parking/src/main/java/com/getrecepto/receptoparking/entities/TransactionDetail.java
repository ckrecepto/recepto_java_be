package com.getrecepto.receptoparking.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.getrecepto.receptoparking.constants.PaymentMode;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class TransactionDetail {
    @JsonProperty("payment_mode")
    private PaymentMode paymentMode;

    @JsonProperty("amount")
    private float amount;

    @JsonProperty("transaction_id")
    private String transactionId;

    @JsonProperty("qr_code_url")
    private String qrCodeUrl;
}
