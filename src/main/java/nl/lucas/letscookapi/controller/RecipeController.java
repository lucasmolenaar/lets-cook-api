package nl.lucas.letscookapi.controller;

import nl.lucas.letscookapi.exception.BadRequestException;
import nl.lucas.letscookapi.model.Recipe;
import nl.lucas.letscookapi.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/recipes")
public class RecipeController {

    private final RecipeService recipeService;

    @Autowired
    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping
    public ResponseEntity<Object> getRecipes() {
        return ResponseEntity.ok().body(recipeService.findAllRecipes());
    }

    @GetMapping("/{recipeId}")
    public ResponseEntity<Object> getRecipeById(@PathVariable("recipeId") Long id) {
        return ResponseEntity.ok().body(recipeService.findRecipeById(id));
    }

    @GetMapping("/search")
    public ResponseEntity<Object> getRecipeByName(@RequestParam("name") String name) {
        return ResponseEntity.ok().body(recipeService.findRecipeByName(name));
    }

    @PostMapping
    public ResponseEntity<Object> createRecipe(@RequestBody Recipe recipe) {
        recipeService.createRecipe(recipe);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("{recipeId}")
    public ResponseEntity<Object> deleteRecipe(@PathVariable("recipeId") Long id) {
        recipeService.deleteRecipe(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("{recipeId}")
    public ResponseEntity<Object> updateRecipe(@PathVariable("recipeId") Long id, @RequestBody Recipe updatedRecipe) {
        recipeService.updateRecipe(id, updatedRecipe);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("{recipeId}/image")
    public ResponseEntity<Object> uploadPicture(@PathVariable("recipeId") Long id, @RequestParam("file") MultipartFile file) throws IOException {
        if (file.getContentType() == null || !file.getContentType().equals("image/jpeg")) {
            throw new BadRequestException();
        } else {
            recipeService.uploadPicture(id, file);
        }

        return ResponseEntity.noContent().build();
    }
}
