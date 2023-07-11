package com.mclebtec.controller.validator;


import com.mclebtec.dto.EmployeeDto;
import com.mclebtec.handler.ValidationErrors;
import com.mclebtec.handler.exception.GenericException;
import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.lang.annotation.*;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Documented
@Constraint(validatedBy = EmployeeValidator.class)
@Target({ElementType.PARAMETER, ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidEmployee {
    String message() default "Invalid Employee Details";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}

@Slf4j
class EmployeeValidator implements ConstraintValidator<ValidEmployee, EmployeeDto> {

    private static final String EMAIL_REGEX;

    static {
        EMAIL_REGEX = "^\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*(\\.\\w{2,3})+$";
    }

    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    @Override
    public boolean isValid(EmployeeDto employee, ConstraintValidatorContext context) {
        log.debug("Inside the employee input details validator....");
        if (!StringUtils.hasText(employee.getEmail()))
            throw new GenericException(ValidationErrors.INVALID_EMAIL);
        else if (!isValidEmail(employee.getEmail()))
            throw new GenericException(ValidationErrors.INVALID_EMAIL_FORMAT);
        if (!StringUtils.hasText(employee.getFirstName()))
            throw new GenericException(ValidationErrors.INVALID_FIRST_NAME);
        if (!StringUtils.hasText(employee.getLastName()))
            throw new GenericException(ValidationErrors.INVALID_LAST_NAME);
        if (Objects.isNull(employee.getGender()))
            throw new GenericException(ValidationErrors.INVALID_GENDER);
        return true;
    }

    public boolean isValidEmail(String email) {
        Matcher matcher = EMAIL_PATTERN.matcher(email);
        return matcher.matches();
    }
}

