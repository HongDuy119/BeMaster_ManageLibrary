package JWT.enity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "books")
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookId;

    private String bookTitle;
    @Lob
    @Column(nullable = false)
    private String bookDescription;
    private int bookNumberPage;
    private String bookAuthor;
    private String bookCategory;

    private String bookImage;
    private Long price;
    private Date bookDate;
    @OneToMany(mappedBy = "book", cascade = CascadeType.REMOVE)
    @JsonIgnore
    private List<BuyBook> buyBooks;

    @JsonIgnore
    @OneToMany(mappedBy = "book", cascade = CascadeType.REMOVE)
    private List<Comment> comments;


    public Book(Long bookId, String bookTitle, String bookDescription, int bookNumberPage, String bookAuthor, String bookCategory, String bookImage,Long price,Date date) {
        this.bookId = bookId;
        this.bookTitle = bookTitle;
        this.bookDescription = bookDescription;
        this.bookNumberPage = bookNumberPage;
        this.bookAuthor = bookAuthor;
        this.bookCategory = bookCategory;
        this.bookImage = bookImage;
        this.price = price;
        this.bookDate = date;
    }
}
