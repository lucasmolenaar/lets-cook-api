package nl.lucas.letscookapi.service;

import nl.lucas.letscookapi.exception.RecordNotFoundException;
import nl.lucas.letscookapi.model.*;
import nl.lucas.letscookapi.repository.RecipeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
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

    @Captor
    ArgumentCaptor<Recipe> recipeCaptor;

    @Test
    public void shouldReturnAllRecipes() {
        //Mock data hardcoden
        List<Recipe> recipes = new ArrayList<>();
        Recipe recipe1 = new Recipe("Test1", 500, 60);
        Recipe recipe2 = new Recipe("Test2", 500, 60);
        Recipe recipe3 = new Recipe("Test3", 500, 60);
        //Mock data in List toevoegen
        recipes.add(recipe1);
        recipes.add(recipe2);
        recipes.add(recipe3);

        //Wanneer findAll uit repo wordt aangeroepen, hardcoded list returnen, niet de echte data uit de DB
        when(recipeRepository.findAll()).thenReturn(recipes);

        //Via de service de repository aanroepen, maar deze geeft nu hardcoded data terug (zie hierboven)
        List<Recipe> recipesList = recipeService.findAllRecipes();

        //Checken of de aangevraagde lijst klopt met de data hierboven geschreven
        assertEquals(3, recipesList.size());
        verify(recipeRepository, times(1)).findAll();
    }

    @Test
    public void testFindRecipeById() {
        Recipe recipe = new Recipe(1L, "Name", 1, 1);
        Optional<Recipe> ofResult = Optional.of(recipe);
        when(this.recipeRepository.findById((Long) any())).thenReturn(ofResult);
        assertSame(recipe, this.recipeService.findRecipeById(1L));
        verify(this.recipeRepository).findById((Long) any());
        assertTrue(this.recipeService.findAllRecipes().isEmpty());
    }

    @Test
    public void shouldThrowExceptionWhenIdNotFound() {
        when(this.recipeRepository.findById((Long) any())).thenReturn(Optional.<Recipe>empty());
        assertThrows(RecordNotFoundException.class, () -> this.recipeService.findRecipeById(1L));
        verify(this.recipeRepository).findById((Long) any());
    }

//    @Test
//    public void shouldCreateRecipe() {
//        //given
//        Recipe recipe = new Recipe("Pizza", 950, 45);
//        //when
//        recipeService.createRecipe(recipe);
//        //then
//        recipeCaptor = ArgumentCaptor.forClass(Recipe.class);
//        verify(recipeRepository).save(recipeCaptor.capture());
//        Recipe capturedRecipe = recipeCaptor.getValue();
//        assertThat(capturedRecipe).isEqualTo(recipe);
//    }

    @Test
    public void shouldFindRecipeByName() {
        Recipe recipe = new Recipe(1L, "Name", 1, 1);
        when(this.recipeRepository.findByNameIgnoreCase(anyString())).thenReturn(recipe);
        assertSame(recipe, this.recipeService.findRecipeByName("Name"));
        verify(this.recipeRepository).findByNameIgnoreCase(anyString());
        assertTrue(this.recipeService.findAllRecipes().isEmpty());
    }

//    @Test
//    public void shouldDeleteRecipeById() {
//        Recipe recipe = new Recipe(1L, "Name", 1, 1);
//        Optional<Recipe> ofResult = Optional.<Recipe>of(recipe);
//        doNothing().when(this.recipeRepository).deleteById((Long) any());
//        when(this.recipeRepository.findById((Long) any())).thenReturn(ofResult);
//        this.recipeService.deleteRecipe(1L);
//        verify(this.recipeRepository).deleteById((Long) any());
//        verify(this.recipeRepository).findById((Long) any());
//        assertTrue(this.recipeService.findAllRecipes().isEmpty());
//    }

    @Test
    public void shouldThrowErrorWhenIdToDeleteNotFound() {
        doNothing().when(this.recipeRepository).deleteById((Long) any());
        when(this.recipeRepository.findById((Long) any())).thenReturn(Optional.<Recipe>empty());
        assertThrows(RecordNotFoundException.class, () -> this.recipeService.deleteRecipe(1L));
        verify(this.recipeRepository).findById((Long) any());
    }

//    @Test
//    public void shouldUpdateRecipe() {
//        Recipe recipe = new Recipe(1L, "Name", 1, 1);
//        Optional<Recipe> ofResult = Optional.<Recipe>of(recipe);
//
//        Recipe newRecipe = new Recipe();
//        newRecipe.setCalories(100);
//        newRecipe.setId(1L);
//        newRecipe.setName("Test");
//        newRecipe.setTimeInMinutes(5);
//
//        when(this.recipeRepository.save((Recipe) any())).thenReturn(newRecipe);
//        when(this.recipeRepository.findById((Long) any())).thenReturn(ofResult);
//        this.recipeService.updateRecipe(1L, new Recipe("Name", 1, 1));
//        verify(this.recipeRepository).findById((Long) any());
//        verify(this.recipeRepository).save((Recipe) any());
//        assertTrue(this.recipeService.findAllRecipes().isEmpty());
//    }

    @Test
    public void shouldThrowExceptionWhenIdToUpdateIsNotFound() {
        Recipe recipe = new Recipe(1L, "Name", 1, 1);

        when(this.recipeRepository.save((Recipe) any())).thenReturn(recipe);
        when(this.recipeRepository.findById((Long) any())).thenReturn(Optional.<Recipe>empty());
        assertThrows(RecordNotFoundException.class,
                () -> this.recipeService.updateRecipe(1L, new Recipe("Name", 1, 1)));
        verify(this.recipeRepository).findById((Long) any());
    }

//    @Test
//    public void shouldUploadRecipePicture() throws IOException {
//        Recipe recipe = new Recipe(1L, "Name", 1, 1);
//        recipe.setComments(new ArrayList<Comment>());
//        recipe.setIngredients(new ArrayList<Ingredient>());
//        recipe.setSteps(new ArrayList<Step>());
//        recipe.setRecipeImage("AAAAAAAA".getBytes("UTF-8"));
//        recipe.setEquipment(new ArrayList<Equipment>());
//        Optional<Recipe> ofResult = Optional.<Recipe>of(recipe);
//
//        Recipe recipe1 = new Recipe(1L, "Name", 1, 1);
//        recipe1.setComments(new ArrayList<Comment>());
//        recipe1.setIngredients(new ArrayList<Ingredient>());
//        recipe1.setSteps(new ArrayList<Step>());
//        recipe1.setRecipeImage("AAAAAAAA".getBytes("UTF-8"));
//        recipe1.setEquipment(new ArrayList<Equipment>());
//
//        when(this.recipeRepository.save((Recipe) any())).thenReturn(recipe1);
//        when(this.recipeRepository.findById((Long) any())).thenReturn(ofResult);
//        this.recipeService.uploadImage(123L,
//                new MockMultipartFile("Name", "AAAAAAAAAAAAAAAAAAAAAAAA".getBytes("UTF-8")));
//        verify(this.recipeRepository).findById((Long) any());
//        verify(this.recipeRepository).save((Recipe) any());
//        assertTrue(this.recipeService.findAllRecipes().isEmpty());
//    }

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




















