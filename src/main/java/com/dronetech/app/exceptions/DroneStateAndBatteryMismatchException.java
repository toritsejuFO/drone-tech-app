package com.dronetech.app.exceptions;

public class DroneStateAndBatteryMismatchException extends BaseException {

    public DroneStateAndBatteryMismatchException(String message) {
        super(message, "DRONE_STATE_BATTERY_MISMATCH");
    }
}
