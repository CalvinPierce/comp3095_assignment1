/**********************************************************************************
 * Project: < comp3095_assignment1 >
 * Assignment: < assignment 1 >
 * Author(s): < Calvin Pierce>
 * Student Number: < 101253832 >
 * Date: November 1st 2021
 * Description: This java file is used to find users in our application.
 **********************************************************************************/
package ca.gbc.comp3095.recipe.repositories;

import ca.gbc.comp3095.recipe.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    List<User> findByUsername(String username);
  
    User getUserByUsername(String username);
}
