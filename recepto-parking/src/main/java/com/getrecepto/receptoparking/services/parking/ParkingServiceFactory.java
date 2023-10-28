package com.getrecepto.receptoparking.services.parking;

import com.getrecepto.receptoparking.constants.VehicleType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class ParkingServiceFactory {
    @Autowired
    @Qualifier("BikeParkingService")
    private ParkingService bikeParkingService;
    @Autowired
    @Qualifier("BusParkingService")
    private ParkingService busParkingService;
    @Autowired
    @Qualifier("CarParkingService")
    private ParkingService carParkingService;
    @Autowired
    @Qualifier("VanParkingService")
    private ParkingService vanParkingService;

    private final Map<VehicleType, ParkingService> factoryMap = new HashMap<VehicleType, ParkingService>() {{
        put(VehicleType.Bike, bikeParkingService);
        put(VehicleType.Bus, busParkingService);
        put(VehicleType.Car, carParkingService);
        put(VehicleType.Van, vanParkingService);
    }};

    public ParkingService getParkingService(VehicleType vehicleType) {
        if (!this.factoryMap.containsKey(vehicleType)) {
            log.warn(String.format("%s is not supported", vehicleType));
            return null;
        }
        return this.factoryMap.get(vehicleType);
    }
}
