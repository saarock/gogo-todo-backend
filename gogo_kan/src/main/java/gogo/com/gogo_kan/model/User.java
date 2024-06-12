package gogo.com.gogo_kan.model;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import gogo.com.gogo_kan.enums.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

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
    @Column(name = "role", columnDefinition = "String default 'USER'")
    private Role role;

    @Column(name = "isActive", columnDefinition = "boolean default true")
    private boolean isActive;

    @CreationTimestamp
    @Column(name = "created_date", updatable = false, nullable = false)
    private Instant createdDate;

    @UpdateTimestamp
    @Column(name = "last_modified_date", nullable = false)
    private Instant lastModifiedDate;

    @JsonManagedReference
    @OneToMany(mappedBy = "productUser", cascade = CascadeType.ALL)
    private List<Product> products = new ArrayList<>();



}
