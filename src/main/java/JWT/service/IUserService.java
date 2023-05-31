package JWT.service;

import JWT.enity.User;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface IUserService {
    Optional<User> findByUsername(String name);

    boolean existsByUsername(String username);  //user da co trong db chua, khi tao du lieu

    boolean existsByEmail(String email); // email da co trong db chua

    User save(User user);

    List<User> findAll();

    User getInfor(HttpServletRequest request);

    String updateProfile(User user,HttpServletRequest request);

    String updateAvatar(HttpServletRequest request, MultipartFile multipartFile) throws IOException;

}
