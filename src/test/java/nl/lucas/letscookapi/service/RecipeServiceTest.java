package nl.lucas.letscookapi.service;

import nl.lucas.letscookapi.exception.RecordNotFoundException;
import nl.lucas.letscookapi.model.*;
import nl.lucas.letscookapi.repository.RecipeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class RecipeServiceTest {

    @Mock
    private RecipeRepository recipeRepository;

    @InjectMocks
    private RecipeServiceImpl recipeService;

    @Test
    public void shouldReturnAllRecipes() {
        List<Recipe> recipes = new ArrayList<>();
        Recipe recipe1 = new Recipe("Test1", 500, 60);
        Recipe recipe2 = new Recipe("Test2", 500, 60);
        Recipe recipe3 = new Recipe("Test3", 500, 60);
        recipes.add(recipe1);
        recipes.add(recipe2);
        recipes.add(recipe3);

        when(recipeRepository.findAll()).thenReturn(recipes);
        List<Recipe> recipesList = recipeService.getAllRecipes();

        assertEquals(3, recipesList.size());
        verify(recipeRepository, times(1)).findAll();
    }

    @Test
    public void shouldFindRecipeById() {
        Recipe recipe = new Recipe(1L, "Name", 1, 1);
        Optional<Recipe> ofResult = Optional.of(recipe);
        when(this.recipeRepository.findById((Long) any())).thenReturn(ofResult);
        assertSame(recipe, this.recipeService.getRecipeById(1L));
        verify(this.recipeRepository).findById((Long) any());
        assertTrue(this.recipeService.getAllRecipes().isEmpty());
    }

    @Test
    public void shouldThrowExceptionWhenIdNotFound() {
        when(this.recipeRepository.findById((Long) any())).thenReturn(Optional.<Recipe>empty());
        assertThrows(RecordNotFoundException.class, () -> this.recipeService.getRecipeById(1L));
        verify(this.recipeRepository).findById((Long) any());
    }

    @Test
    public void shouldFindRecipeByName() {
        Recipe recipe = new Recipe(1L, "Name", 1, 1);
        when(this.recipeRepository.findByNameIgnoreCase(anyString())).thenReturn(recipe);
        assertSame(recipe, this.recipeService.getRecipeByName("Name"));
        verify(this.recipeRepository).findByNameIgnoreCase(anyString());
        assertTrue(this.recipeService.getAllRecipes().isEmpty());
    }

    @Test
    public void shouldFindRecipesPerPage() {
        PageImpl<Recipe> pageImpl = new PageImpl<Recipe>(new ArrayList<Recipe>());
        when(this.recipeRepository.findAll((Pageable) any())).thenReturn(pageImpl);
        Page<Recipe> actualFindRecipesPerPageResult = this.recipeService.getRecipesPerPage(null);
        assertSame(pageImpl, actualFindRecipesPerPageResult);
        assertTrue(actualFindRecipesPerPageResult.toList().isEmpty());
        verify(this.recipeRepository).findAll((Pageable) any());
        assertTrue(this.recipeService.getAllRecipes().isEmpty());
    }

    @Test
    public void shouldThrowErrorWhenNameToFindNotFound() {
        when(this.recipeRepository.findByNameIgnoreCase(anyString())).thenReturn(null);
        assertThrows(RecordNotFoundException.class, () -> this.recipeService.getRecipeByName("TestRecipe"));
        verify(this.recipeRepository).findByNameIgnoreCase(anyString());
    }

    @Test
    public void shouldThrowErrorWhenIdToDeleteNotFound() {
        doNothing().when(this.recipeRepository).deleteById((Long) any());
        when(this.recipeRepository.findById((Long) any())).thenReturn(Optional.<Recipe>empty());
        assertThrows(RecordNotFoundException.class, () -> this.recipeService.deleteRecipe(1L));
        verify(this.recipeRepository).findById((Long) any());
    }

    @Test
    public void shouldThrowErrorWhenRecipeNotFoundWhileUploadingImage() throws IOException {
        Recipe recipe = new Recipe(1L, "Name", 1, 1);
        recipe.setComments(new ArrayList<Comment>());
        recipe.setIngredients(new ArrayList<Ingredient>());
        recipe.setSteps(new ArrayList<Step>());
        recipe.setRecipeImage("AAAAAAAA".getBytes("UTF-8"));
        recipe.setEquipment(new ArrayList<Equipment>());

        when(this.recipeRepository.save((Recipe) any())).thenReturn(recipe);
        when(this.recipeRepository.findById((Long) any())).thenReturn(Optional.<Recipe>empty());
        assertThrows(RecordNotFoundException.class, () -> this.recipeService.uploadImage(1L,
                new MockMultipartFile("Name", "AAAAAAAAAAAAAAAAAAAAAAAA".getBytes("UTF-8"))));
        verify(this.recipeRepository).findById((Long) any());
    }

}




















