package com.mclebtec.props;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Slf4j
@Component
@Getter
public class MongoEndpoints {

  @Value("${spring_data_mongodb_host:localhost}")
  private String host;
  @Value("${spring_data_mongodb_port:27017}")
  private Integer port;
  @Value("${spring_data_mongodb_url:}")
  private String dbUrl;
  @Value("${spring_data_mongodb_username:}")
  private String username;
  @Value("${spring_data_mongodb_password:}")
  private String password;

  @PostConstruct
  public void init() {
    if (!StringUtils.hasText(dbUrl))
      this.dbUrl = "mongodb://%s:%d".formatted(host, port);
    log.info("mongo-url::{}", dbUrl);
  }


}
