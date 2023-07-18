package com.mclebtec.props;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Getter
public class Connectors {

    @Value("${employee_audit_url:null}")
    private String employeeAuditService;

    @PostConstruct
    public void init() {
        log.info("Connectors::initializer.....");
    }


}
