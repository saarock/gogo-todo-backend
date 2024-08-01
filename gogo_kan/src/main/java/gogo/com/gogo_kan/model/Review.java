package gogo.com.gogo_kan.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table
@Getter
@Setter
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    public Review(String fullName, String email, String message) {
        this.fullName = fullName;
        this.email = email;
        this.message = message;
    }

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String message;

    public Review() {

    }
}
