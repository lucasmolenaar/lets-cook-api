package nl.lucas.letscookapi.repository;

import nl.lucas.letscookapi.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    Recipe findByNameIgnoreCase(String name);

}