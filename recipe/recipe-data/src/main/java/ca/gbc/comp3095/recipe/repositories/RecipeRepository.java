package ca.gbc.comp3095.recipe.repositories;

import ca.gbc.comp3095.recipe.model.Recipe;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface RecipeRepository extends CrudRepository<Recipe, Long> {
    Set<Recipe> findByName(String name);
}
