package nl.lucas.letscookapi.service;

import nl.lucas.letscookapi.exception.RecordNotFoundException;
import nl.lucas.letscookapi.model.Comment;
import nl.lucas.letscookapi.model.Recipe;
import nl.lucas.letscookapi.repository.CommentRepository;
import nl.lucas.letscookapi.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final RecipeRepository recipeRepository;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository, RecipeRepository recipeRepository) {
        this.commentRepository = commentRepository;
        this.recipeRepository = recipeRepository;
    }

    @Override
    public List<Comment> findAllCommentsForRecipe(Long id) {
        Optional<Recipe> optionalRecipe = recipeRepository.findById(id);
        if (optionalRecipe.isPresent()) {
            return optionalRecipe.get().getComments();
        } else {
            throw new RecordNotFoundException();
        }
    }

    @Override
    public Comment findCommentById(Long recipeId, Long commentId) {
        Optional<Recipe> optionalRecipe = recipeRepository.findById(recipeId);
        Optional<Comment> optionalComment = commentRepository.findById(commentId);

        if(optionalRecipe.isEmpty()) {
            throw new RecordNotFoundException();
        } else if (optionalComment.isEmpty()) {
            throw new RecordNotFoundException();
        }

        return optionalComment.get();
    }


    @Override
    public void createComment(Long recipeId, Comment comment) {
        Optional<Recipe> optionalRecipe = recipeRepository.findById(recipeId);
        if (optionalRecipe.isPresent()) {
            Recipe recipe = optionalRecipe.get();
            comment.setRecipe(recipe);
            recipe.getComments().add(comment);
            recipeRepository.save(recipe);
        } else {
            throw new RecordNotFoundException();
        }
    }

    @Override
    public void deleteComment(Long recipeId, Long commentId) {
        Optional<Recipe> optionalRecipe = recipeRepository.findById(recipeId);
        Optional<Comment> optionalComment = commentRepository.findById(commentId);

        if (optionalRecipe.isEmpty()) {
            throw new RecordNotFoundException();
        } else if (optionalComment.isEmpty()) {
            throw new RecordNotFoundException();
        }

        commentRepository.deleteById(commentId);
    }
}
