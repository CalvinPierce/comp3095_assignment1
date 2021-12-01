/**********************************************************************************
 * Project: < comp3095_assignment1 >
 * Assignment: < assignment 1 >
 * Author(s): < Calvin Pierce, Shiming Ye >
 * Student Number: < 101253832, 101274045 >
 * Date: November 25th 2021
 * Description: This java file is used for events in our app.
 **********************************************************************************/
 
package ca.gbc.comp3095.recipe.repositories;

import ca.gbc.comp3095.recipe.model.Event;
import org.springframework.data.repository.CrudRepository;

public interface EventRepository extends CrudRepository<Event, Long> {
    Event getEventById(Long id);
}
