package com.mclebtec.handler.exception;


import com.mclebtec.handler.ValidationErrors;
import lombok.Getter;

@Getter
public class GenericException extends RuntimeException {

    private final String errorText;
    private final String errorCode;

    public GenericException(ValidationErrors error) {
        super(error.getStatusText());
        this.errorText = error.getStatusText();
        this.errorCode = error.getErrorCode();
    }

    public GenericException(ValidationErrors error, String message) {
        super(message);
        this.errorText = error.getStatusText();
        this.errorCode = error.getErrorCode();
    }

    public GenericException(ValidationErrors error, String message, Throwable ex) {
        super(message, ex);
        this.errorText = error.getStatusText();
        this.errorCode = error.getErrorCode();
    }

}
