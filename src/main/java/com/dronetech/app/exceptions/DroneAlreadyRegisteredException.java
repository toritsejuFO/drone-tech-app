package com.dronetech.app.exceptions;

public class DroneAlreadyRegisteredException extends BaseException {

    public DroneAlreadyRegisteredException(String message) {
        super(message, "DRONE_ALREADY_REGISTERED");
    }
}
