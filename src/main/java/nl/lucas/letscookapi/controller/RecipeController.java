package nl.lucas.letscookapi.controller;

import nl.lucas.letscookapi.exception.BadRequestException;
import nl.lucas.letscookapi.model.Recipe;
import nl.lucas.letscookapi.service.RecipeService;
import nl.lucas.letscookapi.utils.RecipePdfExporter;
import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("/recipes")
public class RecipeController {

    @Autowired
    private RecipeService recipeService;

    @GetMapping
    public ResponseEntity<Object> getRecipes() {
        return ResponseEntity.ok().body(recipeService.findAllRecipes());
    }

    @GetMapping("/{recipeId}")
    public ResponseEntity<Object> getRecipeById(@PathVariable("recipeId") Long id) {
        return ResponseEntity.ok().body(recipeService.findRecipeById(id));
    }

    @GetMapping("/search")
    public ResponseEntity<Object> getRecipeByName(@RequestParam(value = "name", required = false, defaultValue = "") String recipeName) {
        return ResponseEntity.ok().body(recipeService.findRecipeByName(recipeName));
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<Object> createRecipe(@RequestBody Recipe recipe) {
        recipeService.createRecipe(recipe);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("{recipeId}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<Object> deleteRecipe(@PathVariable("recipeId") Long id) {
        recipeService.deleteRecipe(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("{recipeId}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<Object> updateRecipe(@PathVariable("recipeId") Long id, @RequestBody Recipe updatedRecipe) {
        recipeService.updateRecipe(id, updatedRecipe);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("{recipeId}/image")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<Object> uploadImage(@PathVariable("recipeId") Long id, @RequestParam("file") MultipartFile file) throws IOException {
        if (file.getContentType() == null || !file.getContentType().equals("image/jpeg")) {
            throw new BadRequestException();
        } else {
            recipeService.uploadImage(id, file);
        }

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{recipeId}/export")
    public void exportToPdf(@PathVariable("recipeId") Long recipeId, HttpServletResponse response) throws DocumentException, IOException {
        response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=recipe_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);

        Recipe recipe = recipeService.findRecipeById(recipeId);

        RecipePdfExporter exporter = new RecipePdfExporter(recipe);
        exporter.export(response);
    }
}
