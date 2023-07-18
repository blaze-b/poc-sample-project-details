package com.mclebtec.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mclebtec.constant.Common;
import com.mclebtec.dto.Success;
import com.mclebtec.handler.ValidationErrors;
import com.mclebtec.handler.exception.GenericException;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;


@Data
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Error<T> extends Success<T> {

    @JsonProperty(value = "error_code")
    private String errorCode;

    public Error(HttpStatus status) {
        super(status);
        this.setErrorCode("%s%s".formatted(Common.EMPLOYEE_ERROR_CODE.getKey(),
                ValidationErrors.BAD_REQUEST_DETAILS.getErrorCode()));
    }

    public Error(HttpStatus status, GenericException ex) {
        super(status);
        super.setStatusText(ex.getErrorText());
        this.setErrorCode("%s%s".formatted(Common.EMPLOYEE_ERROR_CODE.getKey(), ex.getErrorCode()));
    }
}

