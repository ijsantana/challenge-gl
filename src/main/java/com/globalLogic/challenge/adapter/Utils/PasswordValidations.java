package com.globalLogic.challenge.adapter.Utils;

public class PasswordValidations {

    public static boolean validateMail(String mail){
        return mail.contains("@") && mail.contains(".");
    }

    public static boolean validatePassword(String password){
        boolean isUppercase = false;
        int digitCounter = 0;

        for(char ch : password.toCharArray()) {
            if (Character.isUpperCase(ch)) {
                isUppercase = true;
            }
            if (Character.isDigit(ch)) {
                digitCounter++;
            }
        }

        return isUppercase && digitCounter >= 2;
    }

}
