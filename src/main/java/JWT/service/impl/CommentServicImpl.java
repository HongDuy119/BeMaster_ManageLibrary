package JWT.service.impl;

import JWT.enity.Book;
import JWT.enity.Comment;
import JWT.enity.User;
import JWT.repository.IBookRespository;
import JWT.repository.ICommentRespositoty;
import JWT.repository.IUserRepository;
import JWT.security.jwt.JwtProvider;
import JWT.service.ICommentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentServicImpl implements ICommentService {
    @Autowired
    IBookRespository iBookRespository;

    @Autowired
    IUserRepository iUserRepository;

    private final JwtProvider jwtProvider;
    @Autowired
    private ICommentRespositoty ICommentRespositoty;

    @Override
    public List<Comment> getComment(Long id) {
        return ICommentRespositoty.findByBookId(id);
    }

    @Override
    public String addComment(Long id, Comment comment, HttpServletRequest request) {
        if(comment.getStar()<=0 || comment.getStar()>5)
        {
            return "falseStar";
        }
        if(comment.getComment().equals(""))
        {
            return "falseComment";
        }
        String header = request.getHeader("Authorization");
        String token = header.substring(7);
        String username = jwtProvider.getUserNameFromToken(token);
        User user = iUserRepository.getUserByUsername(username);
        Book book = iBookRespository.findByBookId(id);
        if(user!=null && book!=null){
            Comment c = Comment.builder().user(user).book(book).comment(comment.getComment()).star(comment.getStar()).build();
            ICommentRespositoty.save(c);
            return "Truemess";
        }
        return "false";
    }
}
