package JWT.enity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter@Setter
@NoArgsConstructor@AllArgsConstructor
@Builder
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String comment;
    private int star;

    @ManyToOne
    @JoinColumn
    @JsonIncludeProperties(value = {"name","avatar"})
    private User user;

    @ManyToOne
    @JoinColumn
    private Book book;

}
