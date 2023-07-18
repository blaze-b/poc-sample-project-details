package com.mclebtec.components;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mclebtec.constant.Common;
import com.mclebtec.handler.ValidationErrors;
import com.mclebtec.handler.exception.GenericException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON;


@Slf4j
@Component
public class Connect {

    private final RestTemplate restTemplate;
    private final RetryTemplate retryTemplate;
    private final ObjectMapper objectMapperForRest;

    public Connect(RestTemplate restTemplate, RetryTemplate retryTemplate, ObjectMapper objectMapperForRest) {
        this.restTemplate = restTemplate;
        this.retryTemplate = retryTemplate;
        this.objectMapperForRest = objectMapperForRest;
    }

    public String rest(String url, HttpMethod method, String token, Object body) {
        try {
            log.info("rest-connector::method::{}::url-details::{}::body-details::{}::auth-token::{}", method,
                    url, body, token);
            AtomicReference<String> finalResponse = new AtomicReference<>("");
            retryTemplate.execute((RetryCallback<Boolean, Exception>) context -> {
                final HttpHeaders requestHeaders = new HttpHeaders();
                if (Objects.nonNull(token)) {
                    requestHeaders.add(AUTHORIZATION, "%s%s".formatted(Common.BEARER.getKey(), token));
                    requestHeaders.add(Common.TOKEN.getKey(), token);
                    requestHeaders.add(Common.AZURE_API_KEY.getKey(), token);
                }
                requestHeaders.setContentType(APPLICATION_JSON);
                requestHeaders.setAccept(Collections.singletonList(APPLICATION_JSON));
                final HttpEntity<Object> httpEntity =
                        (Objects.nonNull(body)) ? new HttpEntity<>(body, requestHeaders)
                                : new HttpEntity<>(requestHeaders);
                log.info("rest-connector::start-time::{}", Instant.now().toEpochMilli());
                final ResponseEntity<String> response = restTemplate.exchange(url, method, httpEntity, String.class);
                log.info("rest-connector::end-time::{}", Instant.now().toEpochMilli());
                final Map<String, Object> responseDetails = objectMapperForRest.readValue(response.getBody(),
                        new TypeReference<HashMap<String, Object>>() {
                        });
                log.debug("initial-response::{}", responseDetails);
                if (checkStatus(responseDetails)) {
                    final String data = objectMapperForRest.writeValueAsString(responseDetails.get("data"));
                    log.debug("success-response::{}", data);
                    finalResponse.set(data);
                }
                return true;
            });
            return finalResponse.get();
        } catch (Exception ex) {
            throw new GenericException(ValidationErrors.CONNECTIVITY_ERROR, ex.getLocalizedMessage(),
                    ex.getCause());
        }
    }

    private static boolean checkStatus(Map<String, Object> responseDetails) {
        return Objects.nonNull(responseDetails)
                && responseDetails.containsKey("status") && responseDetails.get("status").equals("success")
                && responseDetails.containsKey("data");
    }

}
