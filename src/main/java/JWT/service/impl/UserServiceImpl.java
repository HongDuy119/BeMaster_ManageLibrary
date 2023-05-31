package JWT.service.impl;

import JWT.enity.User;
import JWT.repository.IBookRespository;
import JWT.repository.IUserRepository;
import JWT.security.jwt.JwtProvider;
import JWT.service.IBookService;
import JWT.service.IUserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
@Service
@Component
@Transactional
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {
    private final JwtProvider jwtProvider;

    @Autowired  // inject UserService
    IUserRepository iUserRepository;

    @Autowired
    IBookService bookService;

    @Override
    public Optional<User> findByUsername(String name) {
        return iUserRepository.findByUsername(name);
    }

    @Override
    public boolean existsByUsername(String username) {
        return iUserRepository.existsByUsername(username);
    }

    @Override
    public boolean existsByEmail(String email) {
        return iUserRepository.existsByEmail(email);
    }

    @Override
    public User save(User user) {
        return iUserRepository.save(user);
    }

    @Override
    public List<User> findAll() {
        return iUserRepository.findAll();
    }

    @Override
    public User getInfor(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        String token = header.substring(7);
        String username = jwtProvider.getUserNameFromToken(token);
        User user = iUserRepository.getUserByUsername(username);
        return user;
    }

    @Override
    public String updateProfile(User user,HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        String token = header.substring(7);
        String username = jwtProvider.getUserNameFromToken(token);
        User user1 = iUserRepository.getUserByUsername(username);
        user1.setName(user.getName());
        user1.setEmail(user.getEmail());
        user1.setPhone(user.getPhone());
        user1.setAddress(user.getAddress());
        iUserRepository.save(user1);
        return "ok";
    }

    @Override
    public String updateAvatar(HttpServletRequest request, MultipartFile multipartFile) throws IOException {
        String header = request.getHeader("Authorization");
        String token = header.substring(7);
        String username = jwtProvider.getUserNameFromToken(token);
        User user = iUserRepository.getUserByUsername(username);
        user.setAvatar(bookService.convertFile_to_String(multipartFile));
        iUserRepository.save(user);
        return "oce";
    }
}
