/**********************************************************************************
 * Project: < comp3095_assignment1 >
 * Assignment: < assignment 1 >
 * Author(s): < Calvin Pierce>
 * Student Number: < 101253832 >
 * Date: November 7th 2021
 * Description: This java file is used to save meals in our application.
 **********************************************************************************/
package ca.gbc.comp3095.recipe.repositories;

import ca.gbc.comp3095.recipe.model.Meal;
import org.springframework.data.repository.CrudRepository;

public interface MealRepository extends CrudRepository<Meal, Long> {
}