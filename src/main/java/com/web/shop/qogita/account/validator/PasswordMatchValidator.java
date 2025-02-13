package com.web.shop.qogita.account.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.BeanWrapperImpl;

import java.util.Objects;
import java.util.Optional;

public class PasswordMatchValidator implements ConstraintValidator<PasswordMatch, Object> {
    private String passwordConfirm;
    private String password;

    @Override
    public void initialize(PasswordMatch constraintAnnotation) {
        this.password = constraintAnnotation.password();
        this.passwordConfirm = constraintAnnotation.passwordConfirm();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {
        String password = Optional.of(Objects.requireNonNull(new BeanWrapperImpl(value)
                                .getPropertyValue(this.password))
                        .toString().toLowerCase())
                .orElseThrow();
        String passedPassword = Optional.of(Objects.requireNonNull(new BeanWrapperImpl(value)
                                .getPropertyValue(passwordConfirm))
                        .toString().toLowerCase())
                .orElseThrow();
        return password.equals(passedPassword);
    }
}