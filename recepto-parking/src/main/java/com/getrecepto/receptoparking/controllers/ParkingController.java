package com.getrecepto.receptoparking.controllers;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.TextNode;
import com.getrecepto.receptoparking.constants.VehicleType;
import com.getrecepto.receptoparking.entities.NewParkingReq;
import com.getrecepto.receptoparking.entities.ParkingRes;
import com.getrecepto.receptoparking.exception.ReceptoRunTimeException;
import com.getrecepto.receptoparking.services.parking.ParkingService;
import com.getrecepto.receptoparking.services.parking.ParkingServiceFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController("/parking")
public class ParkingController {
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ParkingServiceFactory parkingServiceFactory;

    @PostMapping("/{vehicleType}/check-in")
    public ResponseEntity<ParkingRes> checkIn(@PathVariable("vehicleType") VehicleType vehicleType, @RequestBody TextNode reqBody) {
        ParkingRes parkingRes = null;
        try {
            NewParkingReq parkingReq = this.objectMapper.readValue(reqBody.asText(), NewParkingReq.class);
            ParkingService parkingService = this.parkingServiceFactory.getParkingService(parkingReq.getVehicleType());
            if (parkingService == null) {
                throw new ReceptoRunTimeException(HttpStatus.BAD_REQUEST.value(), "vehicle type is not supported");
            }
            parkingRes = parkingService.newParking(parkingReq);
            if (parkingRes == null) {
                log.error("checkIn: parking response is null from service");
                parkingRes = ParkingRes.builder().statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value()).msg("something went wrong!!").build();
            }
        } catch (JacksonException e) {
            log.error("checkIn: req body is not valid");
            parkingRes = ParkingRes.builder().statusCode(HttpStatus.BAD_REQUEST.value()).msg("req body is invalid").build();
        } catch (ReceptoRunTimeException e) {
            log.error(String.format("checkIn: runTimeexception thrown: %s", e));
            parkingRes = ParkingRes.builder().statusCode(e.getStatusCode()).msg(e.getMsg()).build();
        } catch (Exception e) {
            log.error(String.format("checkIn: exception thrown: %s", e));
            parkingRes = ParkingRes.builder().statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value()).msg("something went wrong!!").build();
        }
        return new ResponseEntity<ParkingRes>(parkingRes, null, parkingRes.getStatusCode());
    }
}
