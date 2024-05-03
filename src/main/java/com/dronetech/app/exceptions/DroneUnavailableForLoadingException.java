package com.dronetech.app.exceptions;

public class DroneUnavailableForLoadingException extends BaseException {

    public DroneUnavailableForLoadingException(String message) {
        super(message, "DRONE_UNAVAILABLE_FOR_LOADING");
    }
}
