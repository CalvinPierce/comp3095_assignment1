/**********************************************************************************
 * Project: < comp3095_assignment1 >
 * Assignment: < assignment 1 >
 * Author(s): < Calvin Pierce, Shiming Ye >
 * Student Number: < 101253832, 101274045 >
 * Date: November 7th 2021
 * Description: This java file is used to search recipes in our app.
 **********************************************************************************/
package ca.gbc.comp3095.recipe.repositories;

import ca.gbc.comp3095.recipe.model.Event;
import ca.gbc.comp3095.recipe.model.Meal;
import ca.gbc.comp3095.recipe.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SearchRepository extends JpaRepository<Recipe, Long> {
    @Query("SELECT r FROM Recipe r WHERE LOWER(CONCAT(r.name, r.ingredients)) LIKE LOWER(concat('%', concat(:keyword, '%')))")
    List<Recipe> search(String keyword);

    @Query("SELECT r FROM Recipe r WHERE r.author.username LIKE %?1%")
    List<Recipe> findByUsername(String userName);

    @Query("SELECT m FROM Meal m WHERE m.user.username LIKE %?1%")
    List<Meal> findByUser(String userName);

}


