package com.qiuguan.boot.custom;


import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;

/**
 * @author qiuguan
 */
public class GenderChoiceValidator implements ConstraintValidator<GenderChoice, String> {

    private List<String> genderList;

    @Override
    public void initialize(GenderChoice constraintAnnotation) {
        this.genderList = Arrays.asList(constraintAnnotation.value());
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
       return this.genderList.contains(s);
    }
}
