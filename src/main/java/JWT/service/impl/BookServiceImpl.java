package JWT.service.impl;

import JWT.dto.request.BookDTO;
import JWT.enity.Book;
import JWT.repository.IBookRespository;
import JWT.service.IBookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Component
@Service
@Transactional
@Slf4j
public class BookServiceImpl implements IBookService {
    private static final Path CURRENT_FOLDER = Paths.get(System.getProperty("user.dir"));
    @Autowired(required = true)
    private IBookRespository bookReps;

    //Get AllBook
    @Override
    public List<Book> findAll() {
        List<Book> list = bookReps.findAll();
        return list;
    }

    // add Book
    @Override
    public String addBook(BookDTO bookDTO) throws IOException {
        Book book1 = bookReps.findBookByBookAuthor(bookDTO.getBookAuthor());
        if(book1!=null && book1.getBookTitle().equals(bookDTO.getBookTitle()))
        {
            System.out.println(book1.getBookTitle());
            return "FalseAdd";
        }
        Book book = new Book(bookDTO.getBookId(),bookDTO.getBookTitle(),bookDTO.getBookDescription(),
                bookDTO.getBookNumberPage(),bookDTO.getBookAuthor(),bookDTO.getBookCategory(),
                bookDTO.getImage(), bookDTO.getPrice(),bookDTO.getDate());
        bookReps.save(book);
        return "TrueAdd";
    }

    @Override
    public Book findbyId(Long id) {
        return bookReps.findByBookId(id);
    }

    @Override
    public Book getBookByBookId(Long id) {
        return bookReps.getBookByBookId(id);
    }

    @Override
    public List<Book> getBooksByBookTitle(String s) {
        return bookReps.findByBookTitleContaining(s);
    }

    @Override
    public List<Book> findByBookDescriptionContaining(String s) {
        return bookReps.findByBookDescriptionContaining(s);
    }

    @Override
    public List<Book> findByBookCategoryContaining(String s) {
        return bookReps.findByBookCategoryContaining(s);
    }

    @Override
    public List<Book> findByBookAuthorContaining(String s) {
        return bookReps.findByBookAuthorContaining(s);
    }


    //edit book
    @Override
    public String editBook(BookDTO bookDTO) throws IOException, ParseException {
//        Date date = new SimpleDateFormat("dd/MM/yyyy").parse(bookDTO.getDate().toString());
//        bookDTO.setDate(date);
        Book book = new Book(bookDTO.getBookId(),bookDTO.getBookTitle(),bookDTO.getBookDescription(),
                bookDTO.getBookNumberPage(),bookDTO.getBookAuthor(),bookDTO.getBookCategory(),
                bookDTO.getImage(), bookDTO.getPrice(),bookDTO.getDate());
        bookReps.save(book);

        return "ok";
    }

    //DeleteBook by id
    @Override
    public int deleteBook(Long id) {
        int id1 = bookReps.deleteByBookId(id);
        return id1;
    }
    @Override
    public String convertFile_to_String(MultipartFile images) throws IOException {
        Path staticPath = Paths.get("static");

        Path imagePath = Paths.get("images");
        if (!Files.exists(CURRENT_FOLDER.resolve(staticPath).resolve(imagePath))) {
            Files.createDirectories(CURRENT_FOLDER.resolve(staticPath).resolve(imagePath));
        }
        Path file = CURRENT_FOLDER.resolve(staticPath)
                .resolve(imagePath).resolve(images.getOriginalFilename());
        if (Files.exists(file)) {
            // Xử lý tệp tin đã tồn tại ở đây
            return "images\\"+file.toString().substring(file.toString().lastIndexOf("\\")+1);
        } else {
            // Ghi dữ liệu tệp tin mới
            try (OutputStream os = Files.newOutputStream(file)) {
                os.write(images.getBytes());
            }
        }
        return imagePath.resolve(images.getOriginalFilename()).toString();
    }
}

