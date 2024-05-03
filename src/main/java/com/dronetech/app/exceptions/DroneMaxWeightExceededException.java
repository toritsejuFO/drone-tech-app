package com.dronetech.app.exceptions;

public class DroneMaxWeightExceededException extends BaseException {

    public DroneMaxWeightExceededException(String message) {
        super(message, "DRONE_MAX_WEIGHT_EXCEEDED");
    }
}
