package JWT.service;

import JWT.enity.BuyBook;
import jakarta.servlet.http.HttpServletRequest;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;

public interface IBuyBookService {
    List<BuyBook> getByBook(HttpServletRequest request);
    String addBuyBook(Long id, BuyBook buyBook, HttpServletRequest request) throws ParseException;

    void deleteBuyBook(Long idByBook);
    List<BuyBook> findAll();

    BuyBook EditStatus(Long id, int status);
}
