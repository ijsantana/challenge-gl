package com.globalLogic.challenge.core.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "phone")
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Phone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long number;
    private Integer cityCode;
    private String countryCode;

    @ManyToOne()
    @JoinColumn(name="id_user")
    @JsonIgnore
    private User user;

    public Phone withNumber(Long number){
        this.number = number;
        return this;
    }

    public Phone withCityCode(Integer cityCode){
        this.cityCode = cityCode;
        return this;
    }

    public Phone withCountryCode(String countryCode){
        this.countryCode = countryCode;
        return this;
    }

    public Phone withUser(User user){
        this.user = user;
        return this;
    }

    @Override
    public String toString() {
        return "Phone{" +
                "id=" + id +
                ", number=" + number +
                ", cityCode=" + cityCode +
                ", countryCode='" + countryCode + '\'' +
                '}';
    }

}
