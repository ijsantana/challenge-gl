package com.globalLogic.challenge.model;

import com.globalLogic.challenge.exception.AttributeException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "user")
@Getter
@Setter
@RequiredArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(nullable = false)
    private String mail;
    @Column(nullable = false)
    private String password;

    @OneToMany(mappedBy="user")
    private List<Phone> phones;

    @Column(name = "user_token", nullable = false, unique = true)
    private String userToken;

    @OneToMany(mappedBy="user")
    private List<Session> sessions;

    public User withName(String name){
        this.name = name;
        return this;
    }

    public User withEmail(String mail){
        this.mail = mail;
        return this;
    }


    public User withUserToken(String userToken){
        this.userToken = userToken;
        return this;
    }

    public User withPhones(List<Phone> phones){
        this.phones = new ArrayList<>();
        this.phones.addAll(phones);
        return this;
    }

    private User withPassword(String password){
        //TODO: ENCRIPTAR LA CONTRASEÑA EN LA BD
        this.password = password;
        return this;
    }

    public boolean validateMail(String mail){
        return mail.contains("@") && mail.contains(".");
    }

    public boolean validatePassword(String password){
        boolean isUppercase = false;
        int digitCounter = 0;

        for(char ch : password.toCharArray()){
            if(Character.isUpperCase(ch)){
                isUppercase = true;
            } if(Character.isDigit(ch)){
                digitCounter++;
        }

        if(isUppercase && digitCounter>=2){
            return true;
        } else {
            return false;
        }
    }

}
