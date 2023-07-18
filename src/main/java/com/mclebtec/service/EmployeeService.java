package com.mclebtec.service;

import com.mclebtec.dto.EmployeeDetails;
import com.mclebtec.model.Employee;
import com.mclebtec.repository.EmployeeRepository;
import com.mclebtec.service.converter.EmployeeConverter;
import com.mclebtec.util.EmployeeAuditUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

public interface EmployeeService {

    List<EmployeeDetails> fetchAllEmployeeDetails();

    EmployeeDetails createEmployeeDetail(EmployeeDetails employeeDetails);

}

@Slf4j
@Service
class EmployeeServiceImpl implements EmployeeService {

    private static final String EMPLOYEE_SERVICE = "_employee_service";
    private final EmployeeRepository employeeRepository;
    private final EmployeeAuditUtil employeeAuditUtil;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository,
                               EmployeeAuditUtil employeeAuditUtil) {
        this.employeeRepository = employeeRepository;
        this.employeeAuditUtil = employeeAuditUtil;
    }

    @Override
    public List<EmployeeDetails> fetchAllEmployeeDetails() {
        log.info("fetchAllEmployeeDetails::entering....");
        List<Employee> employeeDetails = employeeRepository.getAllEmployeeDetails(EMPLOYEE_SERVICE);
        return employeeDetails.stream().map(EmployeeConverter::setEmployeeDetailsForView)
                .collect(Collectors.toList());
    }


    @Override
    public EmployeeDetails createEmployeeDetail(EmployeeDetails employeeDetails) {
        log.info("createEmployee::entering....");
        Employee employee = EmployeeConverter.setEmployeeDetailsToDao(employeeDetails);
        log.info("createEmployee::employee-details-before-saving::{}", employee);
        employee = employeeRepository.save(EMPLOYEE_SERVICE, employee);
        return EmployeeConverter.setEmployeeIdDetails(employee);
    }

}

