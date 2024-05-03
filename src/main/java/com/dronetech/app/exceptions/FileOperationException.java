package com.dronetech.app.exceptions;

public class FileOperationException extends BaseException {

    public FileOperationException(String message) {
        super(message, "FILE_OPERATION_ERROR");
    }
}
