package com.mclebtec.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mclebtec.components.Connect;
import com.mclebtec.dto.records.EmployeeAuditDetails;
import com.mclebtec.handler.ValidationErrors;
import com.mclebtec.handler.exception.GenericException;
import com.mclebtec.props.Connectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class EmployeeAuditUtil {

    private final Connect connect;
    private final Connectors connectors;
    private final ObjectMapper objectMapperForRest;

    public static final String AUDIT_URL = "/api/v1/employees";

    public EmployeeAuditUtil(Connect connect, Connectors connectors, ObjectMapper objectMapperForRest) {
        this.connect = connect;
        this.connectors = connectors;
        this.objectMapperForRest = objectMapperForRest;
    }

    public List<EmployeeAuditDetails> getEmployeeFromOtherCountries(String token) {
        try {
            final String employee = "%s%s".formatted(connectors.getEmployeeAuditService(), AUDIT_URL);
            String responseDetails = connect.rest(employee, HttpMethod.GET, token, null);
            if (StringUtils.hasText(responseDetails)) {
                List<EmployeeAuditDetails> employeeDetails = objectMapperForRest.readValue(responseDetails,
                        new TypeReference<>() {
                        });
                log.info("getEmployeeFromOtherCountries::employee-details::{}", employeeDetails);
                return employeeDetails;
            }
            return new ArrayList<>();
        } catch (JsonProcessingException e) {
            throw new GenericException(ValidationErrors.EMPLOYEE_AUDIT_DETAILS_PARSING_ERROR, e.getLocalizedMessage(),
                    e.getCause());
        }
    }


}
