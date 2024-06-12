package gogo.com.gogo_kan.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.List;


@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "board")
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "board_name")
    private String boardName;

    @JsonBackReference
    @ManyToOne
    private Product boardProject;


    @OneToMany(mappedBy = "board")
    private List<Task> boardTask;

    private int index;
    private int projectIndex;

    @Column(name = "created_at", updatable = false, nullable = false)
    @CreationTimestamp
    private Instant createdAt;


    @Column(name = "updatedAt", nullable = false)
    @UpdateTimestamp
    private Instant updatedAt;

}
