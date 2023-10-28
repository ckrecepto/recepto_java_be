package com.getrecepto.receptoparking.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ParkingRes {
    @JsonProperty("status_code")
    private int statusCode = 200;

    @JsonProperty("msg")
    private String msg;
}
