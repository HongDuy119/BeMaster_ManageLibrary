package JWT.service;

import JWT.dto.request.BookDTO;
import JWT.enity.Book;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

public interface IBookService {
    public List<Book> findAll();
    public String addBook(BookDTO bookDTO) throws IOException;

    Book findbyId(Long id);
    Book getBookByBookId(Long id);

    List<Book> getBooksByBookTitle(String s);

    List<Book> findByBookDescriptionContaining(String s);

    List<Book> findByBookCategoryContaining(String s);

    //    List<Book>
    List<Book> findByBookAuthorContaining(String s);

    public String editBook(BookDTO bookDTO) throws IOException, ParseException;

    public int deleteBook(Long id);

    String convertFile_to_String(MultipartFile multipartFile) throws IOException;
}
