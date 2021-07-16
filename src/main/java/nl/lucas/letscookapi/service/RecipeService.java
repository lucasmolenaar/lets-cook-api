package nl.lucas.letscookapi.service;

import nl.lucas.letscookapi.model.Recipe;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface RecipeService {

    List<Recipe> getAllRecipes();
    Recipe getRecipeById(Long id);
    Recipe getRecipeByName(String name);
    Page<Recipe> getRecipesPerPage(Pageable pageable);
    void createRecipe(Recipe recipe);
    void deleteRecipe(Long id);
    void uploadImage(Long id, MultipartFile file) throws IOException;

}
