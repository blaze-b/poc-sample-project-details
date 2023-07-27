package com.mclebtec.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Success<T> {

  @JsonProperty(value = "status_code")
  private Integer statusCode;
  @JsonProperty(value = "status_txt")
  private String statusText;
  @JsonProperty(value = "data")
  private T data;

  public Success() {
    this.setStatusCode(HttpStatus.OK.value());
    this.setStatusText(HttpStatus.OK.getReasonPhrase());
  }

  public Success(HttpStatus status) {
    this.setStatusCode(status.value());
    this.setStatusText(status.getReasonPhrase());
  }

}
