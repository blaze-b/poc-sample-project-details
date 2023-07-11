package com.mclebtec.controller;

import com.mclebtec.dto.EmployeeDto;
import com.mclebtec.dto.SuccessDto;
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
    public SuccessDto<List<EmployeeDto>> fetchAllEmployeeDetails() {
        log.info("fetchAllEmployeeDetails::entering.....");
        SuccessDto<List<EmployeeDto>> successDto = new SuccessDto<>();
        final List<EmployeeDto> employeeDetails = employeeService.fetchAllEmployeeDetails();
        log.info("fetchAllEmployeeDetails::employee-details::{}", employeeDetails);
        successDto.setData(employeeDetails);
        return successDto;
    }

    @PostMapping(value = "create")
    @ResponseStatus(HttpStatus.CREATED)
    public SuccessDto<EmployeeDto> createEmployeeDetails(@Valid @RequestBody EmployeeDto employeeDto) {
        log.info("createEmployeeDetails::entering::input::{}", employeeDto);
        EmployeeDto employDetailsAfterSaving = employeeService.createEmployeeDetail(employeeDto);
        SuccessDto<EmployeeDto> successDto = new SuccessDto<>(HttpStatus.CREATED);
        successDto.setData(employDetailsAfterSaving);
        return successDto;
    }

}
