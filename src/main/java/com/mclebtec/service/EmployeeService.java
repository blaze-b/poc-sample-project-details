package com.mclebtec.service;

import com.mclebtec.dto.EmployeeDto;
import com.mclebtec.model.Employee;
import com.mclebtec.repository.EmployeeRepository;
import com.mclebtec.util.EmployeeAuditUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public interface EmployeeService {
    List<EmployeeDto> fetchAllEmployeeDetails();

    EmployeeDto createEmployeeDetail(EmployeeDto employeeDto);

}

@Slf4j
@Service
class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    private final EmployeeAuditUtil employeeAuditUtil;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository,
                               EmployeeAuditUtil employeeAuditUtil) {
        this.employeeRepository = employeeRepository;
        this.employeeAuditUtil = employeeAuditUtil;
    }

    @Override
    public List<EmployeeDto> fetchAllEmployeeDetails() {
        log.info("fetchAllEmployeeDetails::entering....");
        List<Employee> employeeDetails = employeeRepository.getAllEmployeeDetails("_employee_service");
        return employeeDetails.stream().map(employee -> {
            EmployeeDto employeeDto = new EmployeeDto();
            BeanUtils.copyProperties(employee, employeeDto);
            employeeDto.setEmployeeId(employee.getEmployeeId().toString());
            return employeeDto;
        }).collect(Collectors.toList());
    }

    @Override
    public EmployeeDto createEmployeeDetail(EmployeeDto employeeDto) {
        log.info("createEmployee::entering....");
        Employee employee = new Employee();
        employee.setEmail(employeeDto.getEmail());
        employee.setFirstName(employeeDto.getFirstName());
        employee.setLastName(employeeDto.getLastName());
        employee.setGender(employeeDto.getGender());
        employee.setCompany(employeeDto.getCompany());
        employee.setDateOfBirth(employeeDto.getDateOfBirth());
        employee.setCreatedDate(new Date());
        employee.setRole(employeeDto.getRole());
        log.info("createEmployee::employee-details-before-saving::{}", employee);
        employee = employeeRepository.save("_employee_service", employee);
        employeeDto = new EmployeeDto();
        employeeDto.setEmployeeId(employee.getEmployeeId().toString());
        return employeeDto;
    }
}

