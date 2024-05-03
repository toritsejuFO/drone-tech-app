package com.dronetech.app.services;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UtilityService {

    public static void verifyRegex(String value, String regex, String errorMessage) {
        if (!value.matches(regex)) {
            throw new IllegalArgumentException(errorMessage);
        }
    }
}
