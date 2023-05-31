package JWT.repository;

import JWT.enity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<User,Long> {
    //Tim kiem User co ton tai trong db k
    List<User> findAll();
    Optional<User> findByUsername(String name);

    User getUserByUsername(String name);

    boolean existsByUsername(String username);  //user da co trong db chua, khi tao du lieu

    boolean existsByEmail(String email); // email da co trong db chua
}
