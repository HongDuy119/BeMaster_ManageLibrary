package JWT.controller;

import JWT.dto.request.BookDTO;
import JWT.enity.Book;
import JWT.service.IBookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;



@RestController
@CrossOrigin
@RequestMapping("/api/book")
public class BookController {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private IBookService bookService;

    // get AllBook
    @GetMapping("/getAll")
    public ResponseEntity<List<Book>> getAllBook(){
        return ResponseEntity.ok(bookService.findAll());
    }

    // Quyền Admin truy cập
    @GetMapping("/getAllbyAdmin")
    public ResponseEntity<List<Book>> getAllBookbyAdmin(){
        return ResponseEntity.ok(bookService.findAll());
    }

    //Search book by....
    @GetMapping("/getbytitle")
    public ResponseEntity<List<Book>> getByTitle(@RequestParam String s, @RequestParam String s1){
        List<Book> books = null;
        if(s1.contains("title")) books = bookService.getBooksByBookTitle(s);
        else if(s1.contains("content")) books = bookService.findByBookDescriptionContaining(s);
        else if(s1.contains("author")) books = bookService.findByBookAuthorContaining(s);
        else if(s1.contains("category")) books = bookService.findByBookCategoryContaining(s);
        else books = bookService.getBooksByBookTitle(s);
        return ResponseEntity.ok(books);
    }
    @PostMapping("/addBook")
    public String addBook(@RequestParam("book") String book,@RequestParam("images") MultipartFile images) throws IOException {
        BookDTO bookDTO = objectMapper.readValue(book, BookDTO.class);
        bookDTO.setImage(bookService.convertFile_to_String(images));
        return bookService.addBook(bookDTO);
    }
    @PutMapping("/editBook/{id}")
    public ResponseEntity<String> editBook(@PathVariable Long id,@RequestParam("book") String book,@RequestParam("images") MultipartFile images) throws IOException, ParseException {
        BookDTO bookDTO = objectMapper.readValue(book, BookDTO.class);
//        System.out.println(bookDTO.getDate().toString());
        bookDTO.setImage(bookService.convertFile_to_String(images));
        bookDTO.setBookId(id);
        return ResponseEntity.ok(bookService.editBook(bookDTO));
    }

    // Get book by ID
    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookbyId(@PathVariable Long id){
        return ResponseEntity.ok(bookService.findbyId(id));
    }
    // Get book by ID by Admin
    @GetMapping("/getAdmin/{bookId}")
    public ResponseEntity<Book> getBookbyId1(@PathVariable Long bookId){
        return ResponseEntity.ok(bookService.findbyId(bookId));
    }
    @DeleteMapping ("/deleteBook/{id}")
    public int deleteBook(@PathVariable Long id){;
        return bookService.deleteBook(id);
    }
}
