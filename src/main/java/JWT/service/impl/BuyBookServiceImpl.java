package JWT.service.impl;

import JWT.enity.Book;
import JWT.enity.BuyBook;
import JWT.enity.User;
import JWT.repository.IBookRespository;
import JWT.repository.IBuyBookSRespository;
import JWT.repository.IUserRepository;
import JWT.security.jwt.JwtProvider;
import JWT.service.IBuyBookService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class BuyBookServiceImpl implements IBuyBookService {
    @Autowired
    IBookRespository iBookRespository;

    @Autowired
    IUserRepository iUserRepository;

    private final JwtProvider jwtProvider;
    @Autowired
    private IBuyBookSRespository iBuyBookSRespository;
    @Override
    public List<BuyBook> getByBook(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        String token = header.substring(7);
        String username = jwtProvider.getUserNameFromToken(token);
        return iBuyBookSRespository.findByUserName(username);
    }

    @Override
    public String addBuyBook(Long id, BuyBook buyBook, HttpServletRequest request) throws ParseException {
        if(buyBook.getQuantity()<=0)
        {
            return "falseQuantity";
        }
        String header = request.getHeader("Authorization");
        String token = header.substring(7);
        String username = jwtProvider.getUserNameFromToken(token);
        User user = iUserRepository.getUserByUsername(username);
        Book book = iBookRespository.getBookByBookId(id);
        if(user != null && book != null)
        {
            LocalDateTime now = LocalDateTime.now();
            Date date = new SimpleDateFormat("yyyy-MM-dd").parse(String.valueOf(now).substring(0,10));
            buyBook.setDateOrder(date);
            BuyBook b = BuyBook.builder().user(user)
                            .book(book).quantity(buyBook.getQuantity()).dateOrder(buyBook.getDateOrder()).status(buyBook.getStatus()).
                    build();
            iBuyBookSRespository.save(b);
            return "trueBuy";
        }
        return "false";
    }

    @Override
    public void deleteBuyBook(Long idByBook) {
        iBuyBookSRespository.deleteById(idByBook);
    }

    @Override
    public List<BuyBook> findAll() {
        return iBuyBookSRespository.findAll();
    }

    @Override
    public BuyBook EditStatus(Long id, int status) {
        BuyBook book = iBuyBookSRespository.findBuyBookById(id);
        book.setStatus(status);
        return iBuyBookSRespository.save(book);
    }
}
