package ca.gbc.comp3095.recipe.services;

import ca.gbc.comp3095.recipe.model.Recipe;
import ca.gbc.comp3095.recipe.repositories.RecipeRepository;
import ca.gbc.comp3095.recipe.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class RecipeService {

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private UserRepository userRepository;

    public void save(Recipe recipe){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        recipe.setAuthor(userRepository.getUserByUsername(authentication.getName()));
        recipe.setDateAdded(new Date());
        recipe.setTotalTime(recipe.getPrepTime() + recipe.getCookTime());
        recipeRepository.save(recipe);
    }
}
