package gogo.com.gogo_kan.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    @ManyToOne
    private Project boardProject;

    @OneToMany(mappedBy = "board")
    private List<Task> boardTask;

}
