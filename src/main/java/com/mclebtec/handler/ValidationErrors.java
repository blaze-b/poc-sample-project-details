package com.mclebtec.handler;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ValidationErrors {

    BAD_REQUEST_DETAILS("001", "Bad request"),
    INVALID_EMAIL("002", "Enter the email"),
    INVALID_FIRST_NAME("002", "Enter the first name"),
    INVALID_LAST_NAME("003", "Enter the last name"),
    INVALID_GENDER("004", "Enter your gender"),
    INVALID_EMAIL_FORMAT("005", "Invalid email format"),
    MONGO_CONNECTIVITY_ISSUE("006", "Mongo connectivity failure"),
    EMPLOYEE_EMAIL_ALREADY_EXISTS("007", "Email already exists"),
    CONNECTIVITY_ERROR("008", "Rest Connection Issue"),
    EMPLOYEE_AUDIT_DETAILS_PARSING_ERROR("009", "Could not parse the employee audit details"),
    ;

    private final String errorCode;
    private final String statusText;

}
