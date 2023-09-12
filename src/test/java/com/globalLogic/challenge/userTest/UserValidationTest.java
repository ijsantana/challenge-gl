package com.globalLogic.challenge.userTest;


import com.globalLogic.challenge.adapter.Utils.PasswordValidations;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


@Slf4j
public class UserValidationTest {

    //TODO: FALTA BAJAR LAS DEPENDENCIAS DE MOCK Y JUNIT PARA REALIZAR TEST UNITARIOS
    @Test
    void passwordValidationTest(){
        System.out.println("Testing Password Validation");
        String password = "A1234adwdaw";
        Assertions.assertTrue(PasswordValidations.validatePassword(password));
    }

    @Test
    void passwordValidationTestUpperCase(){
        System.out.println("Testing Password Validation");
        String password = "a1234adwdaw";
        Assertions.assertFalse(PasswordValidations.validatePassword(password));
    }

    @Test
    void passwordValidationTestNumberCondition(){
        System.out.println("Testing Password Validation");
        String password = "a4adwdaw";
        Assertions.assertFalse(PasswordValidations.validatePassword(password));
    }

    @Test
    void mailValidationTest(){
        System.out.println("Testing Password Validation");
        String mail = "mail@dom.com";
        Assertions.assertTrue(PasswordValidations.validateMail(mail));
    }

    @Test
    void wrongMailValidationTest(){
        System.out.println("Testing Password Validation");
        String mail = "maildom.com";
        Assertions.assertFalse(PasswordValidations.validateMail(mail));
    }

}
