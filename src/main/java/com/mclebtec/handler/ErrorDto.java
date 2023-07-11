package com.mclebtec.handler;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mclebtec.constant.Common;
import com.mclebtec.dto.SuccessDto;
import com.mclebtec.handler.exception.GenericException;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;


@Data
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorDto<T> extends SuccessDto<T> {

    @JsonProperty(value = "error_code")
    private String errorCode;

    public ErrorDto(HttpStatus status) {
        super(status);
        this.setErrorCode("%s%s".formatted(Common.EMPLOYEE_ERROR_CODE.getKey(),
                ValidationErrors.BAD_REQUEST_DETAILS.getErrorCode()));
    }

    public ErrorDto(HttpStatus status, GenericException ex) {
        super(status);
        super.setStatusText(ex.getErrorText());
        this.setErrorCode("%s%s".formatted(Common.EMPLOYEE_ERROR_CODE.getKey(), ex.getErrorCode()));
    }
}

