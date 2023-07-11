package com.mclebtec.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mclebtec.constant.Gender;
import com.mclebtec.constant.Role;
import com.mclebtec.controller.validator.ValidEmployee;
import lombok.Data;

import java.util.Date;

@Data
@ValidEmployee
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EmployeeDto {

    @JsonProperty(value = "employee_id")
    private String employeeId;

    private String email;

    @JsonProperty(value = "first_name")
    private String firstName;

    @JsonProperty(value = "last_name")
    private String lastName;

    @JsonProperty(value = "company")
    private String company;

    @JsonProperty(value = "date_of_birth")
    @JsonFormat(pattern = "MM/dd/yyyy")
    private Date dateOfBirth;

    private Gender gender;

    private Role role;

}
