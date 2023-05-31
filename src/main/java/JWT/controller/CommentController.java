package JWT.controller;

import JWT.enity.Comment;
import JWT.service.ICommentService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/comment")
public class CommentController {
    @Autowired
    ICommentService iCommentService;

    @GetMapping("/get/{bookId}")
    public ResponseEntity<List<Comment>> getComment(@PathVariable Long bookId)
    {
        return ResponseEntity.ok(iCommentService.getComment(bookId));
    }

    @PostMapping("/add/{bookId}")
    public ResponseEntity<String> addComment(@PathVariable Long bookId, @RequestBody Comment comment, HttpServletRequest request)
    {
        return ResponseEntity.ok(iCommentService.addComment(bookId,comment,request));
    }
}
