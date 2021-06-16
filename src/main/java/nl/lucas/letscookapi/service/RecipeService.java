package nl.lucas.letscookapi.service;

import nl.lucas.letscookapi.model.Recipe;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface RecipeService {

    List<Recipe> findAllRecipes();
    Recipe findRecipeById(Long id);
    Recipe findRecipeByName(String name);
    void createRecipe(Recipe recipe);
    void deleteRecipe(Long id);
    void updateRecipe(Long id, Recipe updatedRecipe);
    void uploadPicture(Long id, MultipartFile file) throws IOException;
}
