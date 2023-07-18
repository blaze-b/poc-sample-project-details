package com.mclebtec.dto.records;

public record EmployeeAuditDetails(long id,
                                   String employee_name,
                                   long employee_salary,
                                   String profile_image,
                                   long employee_age) {
}
