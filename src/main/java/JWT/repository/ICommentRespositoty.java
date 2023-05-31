package JWT.repository;

import JWT.enity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ICommentRespositoty extends JpaRepository<Comment,Long> {
    @Query("select c from Comment as c where c.book.bookId = :bookId")
    List<Comment> findByBookId(Long bookId);



}
