package com.mclebtec;

import com.mclebtec.dto.records.EmployeeAuditDetails;
import com.mclebtec.util.EmployeeAuditUtil;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

@SpringBootTest
@ActiveProfiles(value = "test")
public class EmployeeAuditUtilTest {

  @Autowired
  private EmployeeAuditUtil employeeAuditUtil;

  @BeforeEach
  public void init() {

  }

  @Test
  public void getEmployeeFromOtherCountries_Test() {

    List<EmployeeAuditDetails> employeeAuditDetails = employeeAuditUtil.getEmployeeFromOtherCountries(null);

    Assertions.assertThat(employeeAuditDetails).isNotEmpty();
  }


}
