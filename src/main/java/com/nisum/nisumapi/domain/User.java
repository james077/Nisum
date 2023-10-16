package com.nisum.nisumapi.domain;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@Setter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table
public class User {

    @Id
    @Column(name = "user_id")
    private String id;

    @Column(nullable = false)
    private String name;

    private String email;

    private String password;

    @OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL, mappedBy = "user")
    private List<Phone> phones;

    @Column(nullable = false, updatable = false)
    @CreatedDate
    private LocalDateTime created;

    @Column(nullable = false)
    @LastModifiedDate
    private LocalDateTime modified;

    @CreatedDate
    private LocalDateTime lastLogin;

    private boolean isActive;

    public void setAllJoins() {
        if (phones != null) {
            phones.forEach(p -> {
                p.setUser(this);
            });
        }
    }

}
