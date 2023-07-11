package com.mclebtec.repository;

import com.mclebtec.components.Mongo;
import com.mclebtec.handler.ValidationErrors;
import com.mclebtec.handler.exception.GenericException;
import com.mclebtec.model.Employee;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

public interface EmployeeRepository {

    List<Employee> getAllEmployeeDetails(String database);

    Employee save(String database, Employee employee);

}

@Slf4j
@Repository
class EmployeeRepositoryImpl implements EmployeeRepository {
    private final Mongo mongo;

    public EmployeeRepositoryImpl(Mongo mongo) {this.mongo = mongo;}

    @Override
    public List<Employee> getAllEmployeeDetails(String database) {
        return this.mongo.getTemplate(database).findAll(Employee.class);
    }

    @Override
    public Employee save(String database, Employee employee) {
        final MongoTemplate template = this.mongo.getTemplate(database);
        Query query = new Query();
        Criteria criteria = Criteria.where(Employee.EMP_EMAIL).is(employee.getEmail());
        query.addCriteria(criteria);
        if (Objects.nonNull(template.findOne(query, Employee.class)))
            throw new GenericException(ValidationErrors   .EMPLOYEE_EMAIL_ALREADY_EXISTS);
        return template.insert(employee, Employee.DOCUMENT_NAME);
    }
}

