package nl.lucas.letscookapi.controller;

import nl.lucas.letscookapi.model.Comment;
import nl.lucas.letscookapi.model.Recipe;
import nl.lucas.letscookapi.service.CommentService;
import nl.lucas.letscookapi.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/recipes")
public class CommentController {

    private final RecipeService recipeService;
    private final CommentService commentService;

    @Autowired
    public CommentController(RecipeService recipeService, CommentService commentService) {
        this.recipeService = recipeService;
        this.commentService = commentService;
    }

    @GetMapping("/{recipeId}/comments")
    public ResponseEntity<Object> getComments(@PathVariable("recipeId") Long id) {
        return ResponseEntity.ok().body(commentService.findAllCommentsForRecipe(id));
    }

    /*
    Onderstaande functie krijg ik nog niet werkend
     */

//    @GetMapping("/{recipeId}/comments/{commentId}")
//    public ResponseEntity<Object> getCommentById(@PathVariable("recipeId") Long recipeId, @PathVariable("commentId") Long commentId) {
//       return ResponseEntity.ok().body(commentService.findCommentById(recipeId, commentId));
//    }

    @PostMapping("{recipeId}/comment")
    public ResponseEntity<Object> createComment(@PathVariable("recipeId") Long id, @RequestBody Comment comment) {
        commentService.createComment(id, comment);
        return ResponseEntity.noContent().build();
    }

}
