package nl.lucas.letscookapi.service;

import nl.lucas.letscookapi.exception.FileStorageException;
import nl.lucas.letscookapi.exception.RecordNotFoundException;
import nl.lucas.letscookapi.model.Recipe;
import nl.lucas.letscookapi.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;

@Service
public class RecipeServiceImpl implements RecipeService {

//    @Value("${app.upload.dir:${user.home}}")
//    public String uploadDir;

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
        Optional<Recipe> optionalRecipe = recipeRepository.findById(id);
        if (optionalRecipe.isPresent()) {
            recipeRepository.deleteById(id);
        } else {
            throw new RecordNotFoundException();
        }

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

    /*
    Hieronder andere manier van fileupload, lukt nog niet
     */
//
//    public void uploadPicture(MultipartFile file) {
//        try {
//            Path copyLocation = Paths.get(uploadDir + File.separator + StringUtils.cleanPath(file.getOriginalFilename()));
//            Files.copy(file.getInputStream(), copyLocation, StandardCopyOption.REPLACE_EXISTING);
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new FileStorageException("Could not store file " + file.getOriginalFilename() + ". Please try again.");
//        }
//    }

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
