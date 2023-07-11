package com.mclebtec.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Common {
    EMPLOYEE_ERROR_CODE("LUM_EMP_ERR_"),
    BEARER("Bearer "),
    TOKEN("token"),
    AZURE_API_KEY("api-key"),
    ;

    private String key;

}
