package nl.lucas.letscookapi.service;

import nl.lucas.letscookapi.exception.ForbiddenException;
import nl.lucas.letscookapi.exception.RecordNotFoundException;
import nl.lucas.letscookapi.model.Recipe;
import nl.lucas.letscookapi.model.User;
import nl.lucas.letscookapi.repository.RecipeRepository;
import nl.lucas.letscookapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;
    private final UserRepository userRepository;


    @Autowired
    public RecipeServiceImpl(RecipeRepository recipeRepository, UserRepository userRepository) {
        this.recipeRepository = recipeRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<Recipe> findAllRecipes() {
        return recipeRepository.findAll();
    }

    @Override
    @PostAuthorize("returnObject.owner.username == authentication.name")
    public Recipe findRecipeById(Long id) {
        Optional<Recipe> optionalRecipe = recipeRepository.findById(id);
        if (optionalRecipe.isPresent()) {
            Recipe recipe = optionalRecipe.get();
            if (!recipe.getOwner().getUsername().equalsIgnoreCase(getAuthenticatedUser().getUsername())) throw new ForbiddenException();
            return recipe;
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
        recipe.setOwner(getAuthenticatedUser());
        recipeRepository.save(recipe);
    }

    @Override
    //POST AUTHORIZE IS HIER NIET NODIG
    public void deleteRecipe(Long id) {
        Optional<Recipe> optionalRecipe = recipeRepository.findById(id);

        if (optionalRecipe.isPresent()) {
            Recipe recipe = optionalRecipe.get();
            if (!recipe.getOwner().getUsername().equalsIgnoreCase(getAuthenticatedUser().getUsername())) throw new ForbiddenException();
            recipeRepository.deleteById(id);
        } else {
            throw new RecordNotFoundException();
        }

    }

    @Override
    //POSTAUTHORIRZE IS HIER NIET NODIG
    public void updateRecipe(Long id, Recipe updatedRecipe) {
        Optional<Recipe> optionalRecipe = recipeRepository.findById(id);

        if (optionalRecipe.isPresent()) {
            Recipe recipe = optionalRecipe.get();
            if(!recipe.getOwner().getUsername().equalsIgnoreCase(getAuthenticatedUser().getUsername())) throw new ForbiddenException();

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
    public void uploadImage(Long id, MultipartFile file) throws IOException {
        Optional<Recipe> optionalRecipe = recipeRepository.findById(id);

        if (optionalRecipe.isPresent()) {
            Recipe recipe = optionalRecipe.get();
            recipe.setRecipeImage(file.getBytes());
            recipeRepository.save(recipe);
        } else {
            throw new RecordNotFoundException();
        }
    }

    private User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            UserDetails userPrincipal = (UserDetails)authentication.getPrincipal();
            String username = userPrincipal.getUsername();
            return userRepository.findById(username).orElse(null);
        } else {
            return null;
        }
    }
}
