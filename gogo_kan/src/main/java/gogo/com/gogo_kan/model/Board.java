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
    private int  boardId;

    @Column(name = "board_name")
    private String name;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "project_id")
    private Product boardProject;


    @OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE)
    private List<Task> tasks;

    @Column(name = "board_index")
    private int boardIndex;

    @Column(name = "project_index")
    private int projectIndex;

    @Column(name = "created_at", updatable = false, nullable = false)
    @CreationTimestamp
    private Instant createdAt;


    @Column(name = "updatedAt", nullable = false)
    @UpdateTimestamp
    private Instant updatedAt;

    @Column(name = "complete")
    private boolean complete = false;

}
