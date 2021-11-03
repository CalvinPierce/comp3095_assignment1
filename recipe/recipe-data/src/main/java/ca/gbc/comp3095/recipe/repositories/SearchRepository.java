/**********************************************************************************
 * Project: < comp3095_assignment1 >
 * Assignment: < assignment 1 >
 * Author(s): < Calvin Pierce>
 * Student Number: < 101253832 >
 * Date: November 3rd 2021
 * Description: This java file is used to search recipes in our app.
 **********************************************************************************/
package ca.gbc.comp3095.recipe.repositories;

import ca.gbc.comp3095.recipe.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SearchRepository extends JpaRepository<Recipe, Long> {
    @Query("SELECT r FROM Recipe r WHERE CONCAT(r.name, r.ingredients, r.instructions) LIKE %?1%")
    List<Recipe> search(String keyword);
}
