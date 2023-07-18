package com.mclebtec.service.converter;

import com.mclebtec.dto.EmployeeDetails;
import com.mclebtec.model.Employee;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import java.util.Date;

@Slf4j
public class EmployeeConverter {

    private EmployeeConverter() {}

    public static EmployeeDetails setEmployeeDetailsForView(Employee employee) {
        EmployeeDetails employeeDetails = new EmployeeDetails();
        BeanUtils.copyProperties(employee, employeeDetails);
        employeeDetails.setEmployeeId(employee.getEmployeeId().toString());
        return employeeDetails;
    }

    public static Employee setEmployeeDetailsToDao(EmployeeDetails employeeDetails) {
        Employee employee = new Employee();
        employee.setEmail(employeeDetails.getEmail());
        employee.setFirstName(employeeDetails.getFirstName());
        employee.setLastName(employeeDetails.getLastName());
        employee.setGender(employeeDetails.getGender());
        employee.setCompany(employeeDetails.getCompany());
        employee.setDateOfBirth(employeeDetails.getDateOfBirth());
        employee.setCreatedDate(new Date());
        employee.setRole(employeeDetails.getRole());
        return employee;
    }

    public static EmployeeDetails setEmployeeIdDetails(Employee employee) {
        EmployeeDetails employeeDetails = new EmployeeDetails();
        employeeDetails.setEmployeeId(employee.getEmployeeId().toString());
        return employeeDetails;
    }
}
