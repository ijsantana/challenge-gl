package com.globalLogic.challenge.core.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user")
@Getter
@Setter
@RequiredArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(nullable = false)
    private String mail;

    @Column(nullable = false, unique = true)
    private String password;

    @OneToMany(mappedBy="user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Phone> phones;

    @Column(name = "user_token", nullable = false, unique = true)
    private String userToken;

    @OneToMany(mappedBy="user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
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

    public User withPassword(String password){
        //TODO: ENCRIPTAR LA CONTRASEÑA EN LA BD
        this.password = password;
        return this;
    }

    public String getPassword() {
        return "xxxxxxxx";
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", mail='" + mail + '\'' +
                ", password='" + "xxxxxxxxx" + '\'' +
                ", phones=" + phones +
                ", userToken='" + userToken + '\'' +
                '}';
    }
}
