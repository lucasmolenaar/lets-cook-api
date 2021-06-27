package nl.lucas.letscookapi.service;

import nl.lucas.letscookapi.model.Comment;

import java.util.List;

public interface CommentService {

    List<Comment> findAllCommentsForRecipe(Long id);
    Comment findCommentById(Long recipeId, Long commentId);
    void createComment(Long recipeId, Comment comment);
    void deleteComment(Long recipeId, Long commentId);
}
