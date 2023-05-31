package JWT.service;

import JWT.enity.Comment;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.Optional;

public interface ICommentService {
    public List<Comment> getComment(Long id);

    public String addComment(Long id, Comment comment, HttpServletRequest request);
}
