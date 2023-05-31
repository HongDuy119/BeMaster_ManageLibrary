package JWT.repository;

import JWT.enity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@EnableJpaRepositories
public interface IBookRespository extends JpaRepository<Book,Long> {
    List<Book> findAll();

    Book findByBookId(Long id);
    Book getBookByBookId(Long id);


    List<Book> findByBookTitleContaining(String s);

    List<Book> findByBookDescriptionContaining(String s);
    List<Book> findByBookCategoryContaining(String s);
    List<Book> findByBookAuthorContaining(String s);
    @Transactional
    public int deleteByBookId(Long bookId);
    Book findBookByBookAuthor(String s);
}
