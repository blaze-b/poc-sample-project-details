package com.mclebtec.model;

import com.mclebtec.constant.EmployeeStatus;
import com.mclebtec.constant.Gender;
import com.mclebtec.constant.Role;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Persistable;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Data
@Document(collection = "_employee")
public class Employee implements Serializable, Persistable<UUID> {

  @Serial
  private static final long serialVersionUID = -5007604671399318852L;

  public static final String DOCUMENT_NAME = "_employee";
  public static final String EMP_EMAIL = "email";
  public static final String EMP_FIRST_NAME = "first_name";
  public static final String EMP_LAST_NAME = "last_name";
  public static final String EMP_COMPANY = "company";
  public static final String EMP_DATE_OF_BIRTH = "date_of_birth";
  public static final String EMP_GENDER = "gender";
  public static final String EMP_ROLE = "role";
  public static final String EMP_CREATED_DATE = "created_date";
  public static final String EMP_UPDATED_DATE = "updated_date";
  public static final String EMP_STATUS = "status";


  @Id
  private UUID employeeId;
  @Field(EMP_EMAIL)
  private String email;
  @Field(value = EMP_FIRST_NAME)
  private String firstName;
  @Field(value = EMP_LAST_NAME)
  private String lastName;
  @Field(value = EMP_COMPANY)
  private String company;
  @Field(value = EMP_DATE_OF_BIRTH)
  private Date dateOfBirth;
  @Field(value = EMP_GENDER)
  private Gender gender;
  @Field(value = EMP_ROLE)
  private Role role;
  @Field(value = EMP_CREATED_DATE)
  private Date createdDate;
  @Field(value = EMP_UPDATED_DATE)
  private Date lastUpdatedDate;
  @Field(value = EMP_STATUS)
  private EmployeeStatus status;

  public Employee() {
    this.setEmployeeId(UUID.randomUUID());
    this.setStatus(EmployeeStatus.ACTIVE);
  }

  @Override
  public UUID getId() {
    return this.getEmployeeId();
  }

  @Override
  public boolean isNew() {
    return Objects.isNull(this.employeeId);
  }

}
