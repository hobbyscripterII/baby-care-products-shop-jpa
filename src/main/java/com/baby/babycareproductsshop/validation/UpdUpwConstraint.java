package com.baby.babycareproductsshop.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UpdUpwConstraintValidator.class)
public @interface UpdUpwConstraint {
    String message() default "비밀번호는 공백을 제외한 영어와 숫자, 특수문자를 하나 이상 포함한 8~16자리이어야 합니다.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
