/**********************************************************************************
 * Project: < comp3095_assignment1 >
 * Assignment: < assignment 1 >
 * Author(s): < Calvin Pierce>
 * Student Number: < 101253832 >
 * Date: November 7th 2021
 * Description: This java file is used to set the recipe entity in our h2 database.
 **********************************************************************************/
package ca.gbc.comp3095.recipe.services;

import ca.gbc.comp3095.recipe.model.Event;
import ca.gbc.comp3095.recipe.repositories.EventRepository;
import ca.gbc.comp3095.recipe.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private UserRepository userRepository;

    public Event getEventById(Long id){
        return eventRepository.getEventById(id);
    }

    public void deleteById(Long id){
        eventRepository.deleteById(id);
    }

    public Iterable<Event> findAll(){
        return eventRepository.findAll();
    }

    public void save(Event event){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        event.setUser(userRepository.getUserByUsername(authentication.getName()));
        eventRepository.save(event);
    }
}
