package org.study;

import org.apache.bval.jsr.ApacheValidationProvider;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.util.CollectionUtils;
import org.study.controller.request.User;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static java.lang.System.exit;


/**
 * @author dizhang
 * @date 2021-02-07
 */
@SpringBootApplication
public class Application implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        ValidatorFactory validatorFactory = Validation.byProvider(ApacheValidationProvider.class)
                .configure()
                .buildValidatorFactory();
        Validator validator = validatorFactory.getValidator();
        User user = new User("zhangsan", "123@11111", null, null);

        Set<ConstraintViolation<User>> violations = validator.validateProperty(user, "password");
        System.out.println(!CollectionUtils.isEmpty(violations) ? violations.iterator().next().getMessage() : "violations1 pass");

        Set<ConstraintViolation<User>> violations2 = validator.validate(user);
        System.out.println(!CollectionUtils.isEmpty(violations2) ? violations2.iterator().next().getMessage() : "violations2 pass");


        exit(0);

    }
}
