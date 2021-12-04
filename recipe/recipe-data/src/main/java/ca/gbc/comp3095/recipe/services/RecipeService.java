/**********************************************************************************
 * Project: < comp3095_assignment1 >
 * Assignment: < assignment 1 >
 * Author(s): < Calvin Pierce>
 * Student Number: < 101253832 >
 * Date: November 7th 2021
 * Description: This java file is used to set the recipe entity in our h2 database.
 **********************************************************************************/
package ca.gbc.comp3095.recipe.services;

import ca.gbc.comp3095.recipe.model.Recipe;
import ca.gbc.comp3095.recipe.repositories.RecipeRepository;
import ca.gbc.comp3095.recipe.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;

@Service
public class RecipeService {

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private UserRepository userRepository;

    public Recipe getRecipeById(Long id){
        return recipeRepository.getRecipeById(id);
    }

    public void save(Recipe recipe){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        recipe.setAuthor(userRepository.getUserByUsername(authentication.getName()));
        recipe.setDateAdded(LocalDate.now());
        recipe.setTotalTime(recipe.getPrepTime() + recipe.getCookTime());
        recipeRepository.save(recipe);
    }

    public void saveImage(Long id, MultipartFile file) throws IOException {
        Recipe recipe = recipeRepository.getRecipeById(id);
        Byte[] bytes = new Byte[file.getBytes().length];
        int i = 0;
        for (Byte b : file.getBytes()){
            bytes[i++] = b;
        }
        recipe.setImage(bytes);

        recipeRepository.save(recipe);
    }
}
