package gogo.com.gogo_kan.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.Instant;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int taskId;

    @Column(name = "name")
    private String name;

    @Column(name = "content")
    private String content;

    @JsonBackReference
    @ManyToOne
    private Board board;

    @Column(name = "board_index")
    private int boardIndex;

    @Column(name = "task_index")
    private int index;

    @Column(name = "created_at", updatable = false, nullable = false)
    @CreationTimestamp
    private Instant createdAt;


    @Column(name = "updatedAt", nullable = false)
    @UpdateTimestamp
    private Instant updatedAt;

    @Column(name = "complete")
    private boolean complete = false;
}
