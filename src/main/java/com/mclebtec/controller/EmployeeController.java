package com.mclebtec.controller;

import com.mclebtec.dto.EmployeeDetails;
import com.mclebtec.dto.Success;
import com.mclebtec.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "v1/employee")
@Validated
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Success<List<EmployeeDetails>> fetchAllEmployeeDetails() {
        log.info("fetchAllEmployeeDetails::entering.....");
        Success<List<EmployeeDetails>> success = new Success<>();
        final List<EmployeeDetails> employeeDetails = employeeService.fetchAllEmployeeDetails();
        log.info("fetchAllEmployeeDetails::employee-details::{}", employeeDetails);
        success.setData(employeeDetails);
        return success;
    }

    @PostMapping(value = "create")
    @ResponseStatus(HttpStatus.CREATED)
    public Success<EmployeeDetails> createEmployeeDetails(@Valid @RequestBody EmployeeDetails employeeDetails) {
        log.info("createEmployeeDetails::entering::input::{}", employeeDetails);
        final EmployeeDetails employDetailsAfterSaving = employeeService.createEmployeeDetail(employeeDetails);
        Success<EmployeeDetails> success = new Success<>(HttpStatus.CREATED);
        success.setData(employDetailsAfterSaving);
        return success;
    }

}
