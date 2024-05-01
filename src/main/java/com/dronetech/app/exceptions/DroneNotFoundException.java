package com.dronetech.app.exceptions;

public class DroneNotFoundException extends BaseException {

    public DroneNotFoundException(String message) {
        super(message, "DRONE_NOT_FOUND");
    }
}
