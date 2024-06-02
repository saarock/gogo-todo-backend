package gogo.com.gogo_kan.models;


import gogo.com.gogo_kan.enums.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "user")
public class User {

    public User(String email, String fullName, String password) {
        this.email = email;
        this.fullName = fullName;
        this.password = password;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;


    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;

    @Column(name = "isActive", columnDefinition = "boolean default true")
    private boolean isActive;

    @CreatedDate
    @Column(name = "created_date", updatable = false, nullable = false)
    private Date createdDate;

    @LastModifiedDate
    @Column(name = "last_modified_date", nullable = false)
    private Date lastModifiedDate;






}
