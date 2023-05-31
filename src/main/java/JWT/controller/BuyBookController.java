package JWT.controller;

import JWT.enity.BuyBook;
import JWT.service.IBuyBookService;
import jakarta.servlet.http.HttpServletRequest;
import org.hibernate.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RequestMapping("/api/buybook")
@CrossOrigin
@RestController
public class BuyBookController {
    @Autowired
    IBuyBookService iBuyBookService;

    @GetMapping("/get")
    public ResponseEntity<List<BuyBook>> getBuyBook(HttpServletRequest request)
    {
        return ResponseEntity.ok(iBuyBookService.getByBook(request));
    }

    @PostMapping("/addbuy/{bookId}")
    public ResponseEntity<String> addBuyBook(@PathVariable Long bookId, @RequestBody BuyBook buybook, HttpServletRequest request) throws ParseException {
        return ResponseEntity.ok(iBuyBookService.addBuyBook(bookId,buybook,request));
    }

    @DeleteMapping("/delete/{id}")
    public void deleteBuyBook(@PathVariable Long id)
    {
        iBuyBookService.deleteBuyBook(id);
    }

    //Admin moi truy cap duoc tat cac don hang
    @GetMapping("/getAll")
    public ResponseEntity<List<BuyBook>> getAll()
    {
        return ResponseEntity.ok(iBuyBookService.findAll());
    }

    @PutMapping("/editStatus/{id}")
    public ResponseEntity<BuyBook> editStatus(@PathVariable Long id,@RequestParam int status){
        return ResponseEntity.ok(iBuyBookService.EditStatus(id,status));
    }
}
