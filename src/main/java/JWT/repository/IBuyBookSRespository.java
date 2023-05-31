package JWT.repository;

import JWT.enity.BuyBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface IBuyBookSRespository extends JpaRepository<BuyBook,Long> {
    BuyBook findBuyBookById(Long id);
    @Query("select b from BuyBook b where b.user.username = :username")
    List<BuyBook> findByUserName(String username);
    @Transactional
    void deleteById(Long bookId);
    List<BuyBook> findAll();

}
