package JWT.dto.request;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BookDTO {
    private Long bookId;

    private String bookTitle;
    private String bookDescription;
    private int bookNumberPage;
    private String bookAuthor;
    private String bookCategory;
    private String image;
    private Long price;
    private Date date;
}

