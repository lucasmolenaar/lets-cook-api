package nl.lucas.letscookapi.controller;

import nl.lucas.letscookapi.model.Comment;
import nl.lucas.letscookapi.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/api/recipes")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping("/{recipeId}/comments")
    public ResponseEntity<Object> getComments(@PathVariable("recipeId") Long id) {
        return ResponseEntity.ok().body(commentService.findAllCommentsForRecipe(id));
    }

    @GetMapping("/{recipeId}/comments/{commentId}")
    public ResponseEntity<Object> getCommentById(@PathVariable("recipeId") Long recipeId, @PathVariable("commentId") Long commentId) {
        return ResponseEntity.ok().body(commentService.findCommentById(recipeId, commentId));
    }

    @PostMapping("/{recipeId}/comments")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<Object> createComment(@PathVariable("recipeId") Long id, @RequestBody Comment comment) {
        commentService.createComment(id, comment);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{recipeId}/comments/{commentId}")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<Object> deleteComment(@PathVariable("recipeId") Long recipeId, @PathVariable("commentId") Long commentId) {
        commentService.deleteComment(recipeId, commentId);
        return ResponseEntity.noContent().build();
    }
}
