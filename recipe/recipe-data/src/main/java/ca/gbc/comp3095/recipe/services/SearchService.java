/**********************************************************************************
 * Project: < comp3095_assignment1 >
 * Assignment: < assignment 1 >
 * Author(s): < Calvin Pierce, Shiming Ye >
 * Student Number: < 101253832, 101274045 >
 * Date: November 1st 2021
 * Description: This java file is used to return our searches for our app.
 **********************************************************************************/
package ca.gbc.comp3095.recipe.services;

import ca.gbc.comp3095.recipe.model.Event;
import ca.gbc.comp3095.recipe.model.Meal;
import ca.gbc.comp3095.recipe.model.Recipe;
import ca.gbc.comp3095.recipe.repositories.SearchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchService {

    @Autowired
    private SearchRepository searchRepository;

    public List<Recipe> listAll(String keyword){
        if(keyword != null){
            return searchRepository.search(keyword);
        }
        return searchRepository.findAll();
    }

//    public List<Event> listAllEvents() {
//        return searchRepository.findEventByUser();
//    }
}
