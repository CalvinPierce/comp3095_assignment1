/**********************************************************************************
 * Project: < comp3095_assignment1 >
 * Assignment: < assignment 1 >
 * Author(s): < Calvin Pierce>
 * Student Number: < 101253832 >
 * Date: November 1st 2021
 * Description: This java file is used to find recipes in our application.
 **********************************************************************************/
package ca.gbc.comp3095.recipe.repositories;

import ca.gbc.comp3095.recipe.model.Recipe;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Set;

public interface RecipeRepository extends CrudRepository<Recipe, Long> {
    Set<Recipe> findByName(String name);
    
    List<Recipe> findByNameIgnoreCase(String name);
}
