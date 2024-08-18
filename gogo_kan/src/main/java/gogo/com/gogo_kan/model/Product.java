package gogo.com.gogo_kan.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "product")
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "product_name")
    private String name;

    @Column(name = "product_index")
    private int index;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User productUser;

    @OneToMany(mappedBy = "boardProject", cascade = CascadeType.REMOVE)
    private List<Board> boards;

    @CreationTimestamp
    @Column(name = "created_date", updatable = false, nullable = false)
    private Instant createdDate;

//    private int index;

    @UpdateTimestamp
    @Column(name = "last_modified_date", nullable = false)
    private Instant updatedAt;



}
