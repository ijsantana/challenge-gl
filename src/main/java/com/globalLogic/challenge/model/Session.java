package com.globalLogic.challenge.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "session")
@Getter
@Setter
@RequiredArgsConstructor
public class Session {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String sessionToken;
    private String dueDate;
    private boolean isActive;

    @ManyToOne()
    @JoinColumn(name="id_user")
    private User user;

}
