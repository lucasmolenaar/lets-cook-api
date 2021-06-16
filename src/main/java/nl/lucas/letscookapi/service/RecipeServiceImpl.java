package nl.lucas.letscookapi.service;

import nl.lucas.letscookapi.exception.RecordNotFoundException;
import nl.lucas.letscookapi.model.Recipe;
import nl.lucas.letscookapi.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;

    @Autowired
    public RecipeServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public List<Recipe> findAllRecipes() {
        return recipeRepository.findAll();
    }

    @Override
    public Recipe findRecipeById(Long id) {
        Optional<Recipe> optionalRecipe = recipeRepository.findById(id);

        if (optionalRecipe.isPresent()) {
            return optionalRecipe.get();
        } else {
            throw new RecordNotFoundException();
        }
    }

    @Override
    public Recipe findRecipeByName(String name) {
        Recipe recipe = recipeRepository.findByNameIgnoreCase(name);

        if (recipe == null) {
            throw new RecordNotFoundException();
        } else {
            return recipe;
        }
    }

    @Override
    public void createRecipe(Recipe recipe) {
        recipeRepository.save(recipe);
    }

    @Override
    public void deleteRecipe(Long id) {
        recipeRepository.deleteById(id);
    }

    @Override
    public void updateRecipe(Long id, Recipe updatedRecipe) {
        Optional<Recipe> optionalRecipe = recipeRepository.findById(id);
        if (optionalRecipe.isPresent()) {
            Recipe recipe = optionalRecipe.get();
            recipe.setName(updatedRecipe.getName());
            recipe.setCalories(updatedRecipe.getCalories());
            recipe.setTimeInMinutes(updatedRecipe.getTimeInMinutes());
            recipe.setIngredients(updatedRecipe.getIngredients());
            recipe.setSteps(updatedRecipe.getSteps());
            recipe.setEquipment(updatedRecipe.getEquipment());
            recipeRepository.save(recipe);
        } else {
            throw new RecordNotFoundException();
        }
    }

    @Override
    public void uploadPicture(Long id, MultipartFile file) throws IOException {
        Optional<Recipe> optionalRecipe = recipeRepository.findById(id);

        if (optionalRecipe.isPresent()) {
            Recipe recipe = optionalRecipe.get();
            recipe.setRecipePicture(file.getBytes());
            recipeRepository.save(recipe);
        } else {
            throw new RecordNotFoundException();
        }
    }
}
