package com.mclebtec.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mclebtec.components.Connect;
import com.mclebtec.props.Connectors;
import com.mclebtec.util.EmployeeAuditUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Configuration

public class RestConfig {

    @Bean(name = "objectMapperForRest")
    public ObjectMapper objectMapperForRest() {
        return new ObjectMapper();
    }

    @Bean(name = "restTemplate")
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean(name = "retryTemplate")
    public RetryTemplate retryTemplate() {
        RetryTemplate retryTemplate = new RetryTemplate();
        FixedBackOffPolicy fixedBackOffPolicy = new FixedBackOffPolicy();
        fixedBackOffPolicy.setBackOffPeriod(3000L);
        retryTemplate.setBackOffPolicy(fixedBackOffPolicy);
        SimpleRetryPolicy retryPolicy = new SimpleRetryPolicy();
        retryPolicy.setMaxAttempts(3);
        retryTemplate.setRetryPolicy(retryPolicy);
        return retryTemplate;
    }

    @Bean
    public EmployeeAuditUtil employeeAuditUtil(Connect connect, Connectors connectors) {
        return new EmployeeAuditUtil(connect, connectors);
    }

}
