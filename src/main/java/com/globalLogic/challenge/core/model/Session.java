package com.globalLogic.challenge.core.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "session")
@Getter
@Setter
@RequiredArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Session {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String sessionToken;

    @Column(nullable = false)
    private LocalDateTime created;

    @Column(nullable = false)
    private LocalDateTime dueDate;

    @Column(nullable = false)
    private boolean isActive;

    @ManyToOne()
    @JoinColumn(name="id_user")
    @JsonIgnore
    private User user;

    @Override
    public String toString() {
        return "Session{" +
                "id=" + id +
                ", sessionToken='" + sessionToken + '\'' +
                ", created=" + created +
                ", dueDate=" + dueDate +
                ", isActive=" + isActive +
                '}';
    }
}
