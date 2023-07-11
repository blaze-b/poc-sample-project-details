package com.mclebtec.util;

import com.mclebtec.components.Connect;
import com.mclebtec.props.Connectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;

import java.util.Map;
import java.util.Objects;

@Slf4j
public class EmployeeAuditUtil {

    private final Connect connect;
    private final Connectors connectors;

    public static final String AUDIT_URL = "/api/v1/employees";

    public EmployeeAuditUtil(Connect connect, Connectors connectors) {
        this.connect = connect;
        this.connectors = connectors;
    }

    public EmployeeDetailsFromAudit getEmployeeFromOtherCountries(String token) {
        final String employee = "%s%s".formatted(connectors.getEmployeeAuditService(), AUDIT_URL);
        Map<String, Object> responseDetails = connect.rest(employee, HttpMethod.GET, token, null);
        log.info("getEmployeeFromOtherCountries::details::{}", responseDetails);
        if (Objects.nonNull(responseDetails)  &&
                Objects.deepEquals(responseDetails.get("status"), "success")) {
        }
        return null;
    }

    public  record EmployeeDetailsFromAudit(String id,
                                            String employee_name,
                                            String employee_salary,
                                            long age,
                                            String profile_image) {}


}
