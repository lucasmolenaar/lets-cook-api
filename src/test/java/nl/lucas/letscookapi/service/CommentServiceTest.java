package nl.lucas.letscookapi.service;

import nl.lucas.letscookapi.exception.RecordNotFoundException;
import nl.lucas.letscookapi.model.*;
import nl.lucas.letscookapi.repository.CommentRepository;
import nl.lucas.letscookapi.repository.RecipeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class CommentServiceTest {

    @Mock
    private RecipeRepository recipeRepository;

    @Mock
    private CommentRepository commentRepository;

    @InjectMocks
    private CommentServiceImpl commentService;

    @Test
    public void shouldReturnAllCommentsForRecipe() throws UnsupportedEncodingException {
        Recipe recipe = new Recipe(1L, "Name", 1, 1);
        ArrayList<Comment> commentList = new ArrayList<Comment>();
        recipe.setComments(commentList);
        Optional<Recipe> ofResult = Optional.<Recipe>of(recipe);

        when(this.recipeRepository.findById((Long) any())).thenReturn(ofResult);
        List<Comment> actualFindAllCommentsForRecipeResult = this.commentService.findAllCommentsForRecipe(1L);
        assertSame(commentList, actualFindAllCommentsForRecipeResult);
        assertTrue(actualFindAllCommentsForRecipeResult.isEmpty());
        verify(this.recipeRepository).findById((Long) any());
    }

    @Test
    public void shouldThrowExceptionWhenRecipeIsNotFound() {
        when(this.recipeRepository.findById((Long) any())).thenReturn(Optional.<Recipe>empty());
        assertThrows(RecordNotFoundException.class, () -> this.commentService.findAllCommentsForRecipe(1L));
        verify(this.recipeRepository).findById((Long) any());
    }

    @Test
    public void shouldFindCommentById() {
        Recipe recipe = new Recipe(1L, "Name", 1, 1);
        recipe.setComments(new ArrayList<Comment>());
        Optional<Recipe> ofResult = Optional.<Recipe>of(recipe);
        when(this.recipeRepository.findById((Long) any())).thenReturn(ofResult);

        Recipe recipe1 = new Recipe(1L, "Name", 1, 1);
        recipe1.setComments(new ArrayList<Comment>());

        Comment comment = new Comment();
        comment.setRecipe(recipe1);
        comment.setId(1L);
        comment.setTitle("Test");
        comment.setContent("Test message");
        Optional<Comment> ofResult1 = Optional.<Comment>of(comment);

        when(this.commentRepository.findById((Long) any())).thenReturn(ofResult1);
        assertSame(comment, this.commentService.findCommentById(1L, 1L));
        verify(this.recipeRepository).findById((Long) any());
        verify(this.commentRepository).findById((Long) any());
    }

    @Test
    public void shouldThrowExceptionWhenRecipeIdNotFoundWhileSearchingComments() {
        when(this.recipeRepository.findById((Long) any())).thenReturn(Optional.<Recipe>empty());

        Recipe recipe = new Recipe(1L, "Name", 1, 1);
        recipe.setComments(new ArrayList<Comment>());


        Comment comment = new Comment();
        comment.setRecipe(recipe);
        comment.setId(1L);
        comment.setTitle("Test");
        comment.setContent("Test content");
        Optional<Comment> ofResult = Optional.<Comment>of(comment);

        when(this.commentRepository.findById((Long) any())).thenReturn(ofResult);
        assertThrows(RecordNotFoundException.class, () -> this.commentService.findCommentById(1L, 1L));
        verify(this.recipeRepository).findById((Long) any());
        verify(this.commentRepository).findById((Long) any());
    }

    @Test
    public void shouldThrowExceptionWhenCommentIdNotFoundWhileSearchingComments() {
        Recipe recipe = new Recipe(1L, "name", 1, 1);
        recipe.setComments(new ArrayList<Comment>());
        Optional<Recipe> ofResult = Optional.<Recipe>of(recipe);

        when(this.recipeRepository.findById((Long) any())).thenReturn(ofResult);
        when(this.commentRepository.findById((Long) any())).thenReturn(Optional.<Comment>empty());

        assertThrows(RecordNotFoundException.class, () -> this.commentService.findCommentById(1L, 1L));
        verify(this.recipeRepository).findById((Long) any());
        verify(this.commentRepository).findById((Long) any());
    }

    @Test
    public void shouldCreateComment() {
        Recipe recipe = new Recipe(1L, "Name", 1,1);
        recipe.setComments(new ArrayList<Comment>());
        Optional<Recipe> ofResult = Optional.<Recipe>of(recipe);

        Recipe recipe1 = new Recipe(1L, "Name", 1, 1);
        recipe1.setComments(new ArrayList<Comment>());

        when(this.recipeRepository.save((Recipe) any())).thenReturn(recipe1);
        when(this.recipeRepository.findById((Long) any())).thenReturn(ofResult);

        Comment comment = new Comment();
        this.commentService.createComment(123L, comment);

        verify(this.recipeRepository).findById((Long) any());
        verify(this.recipeRepository).save((Recipe) any());

        Recipe recipe2 = comment.getRecipe();
        assertSame(recipe, recipe2);
        assertEquals(1, recipe2.getComments().size());
    }

    @Test
    public void shouldThrowErrorWhenRecipeIdNotFoundWhileCreatingComment() throws UnsupportedEncodingException {
        Recipe recipe = new Recipe(1L, "Name", 1, 1);
        recipe.setComments(new ArrayList<Comment>());

        when(this.recipeRepository.save((Recipe) any())).thenReturn(recipe);
        when(this.recipeRepository.findById((Long) any())).thenReturn(Optional.<Recipe>empty());

        assertThrows(RecordNotFoundException.class, () -> this.commentService.createComment(123L, new Comment()));
        verify(this.recipeRepository).findById((Long) any());
    }

    @Test
    public void shouldThrowExceptionWhenRecipeIdNotFoundWhileDeletingComment() {
        when(this.recipeRepository.findById((Long) any())).thenReturn(Optional.<Recipe>empty());

        Recipe recipe = new Recipe(1L, "name", 1, 1);
        recipe.setComments(new ArrayList<Comment>());

        Comment comment = new Comment();
        comment.setRecipe(recipe);
        comment.setId(1L);
        comment.setTitle("Test");
        comment.setContent("Test content");
        Optional<Comment> ofResult = Optional.<Comment>of(comment);

        doNothing().when(this.commentRepository).deleteById((Long) any());
        when(this.commentRepository.findById((Long) any())).thenReturn(ofResult);

        assertThrows(RecordNotFoundException.class, () -> this.commentService.deleteComment(1L, 1L));
        verify(this.recipeRepository).findById((Long) any());
        verify(this.commentRepository).findById((Long) any());
    }

    @Test
    public void shouldThrowExceptionWhenRCommentIdNotFoundWhileDeletingComment() {
        Recipe recipe = new Recipe(1L, "Name", 1,1);
        recipe.setComments(new ArrayList<Comment>());
        Optional<Recipe> ofResult = Optional.<Recipe>of(recipe);

        when(this.recipeRepository.findById((Long) any())).thenReturn(ofResult);
        doNothing().when(this.commentRepository).deleteById((Long) any());
        when(this.commentRepository.findById((Long) any())).thenReturn(Optional.<Comment>empty());

        assertThrows(RecordNotFoundException.class, () -> this.commentService.deleteComment(1L, 1L));
        verify(this.recipeRepository).findById((Long) any());
        verify(this.commentRepository).findById((Long) any());
    }
}
