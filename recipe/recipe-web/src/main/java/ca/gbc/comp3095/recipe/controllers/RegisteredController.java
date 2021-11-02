/**********************************************************************************
 * Project: < comp3095_assignment1 >
 * Assignment: < assignment 1 >
 * Author(s): < Calvin Pierce>
 * Student Number: < 101253832 >
 * Date: November 1st 2021
 * Description: This java file is used to control all pages available to registered users.
 **********************************************************************************/
package ca.gbc.comp3095.recipe.controllers;

import ca.gbc.comp3095.recipe.model.Recipe;
import ca.gbc.comp3095.recipe.repositories.RecipeRepository;
import ca.gbc.comp3095.recipe.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.http.HttpRequest;
import org.springframework.web.HttpRequestHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import java.util.Date;
import java.util.HashSet;

@RequestMapping("/registered")
@Controller
public class RegisteredController {

    @Autowired
    RecipeRepository recipeRepository;

    @Autowired
    UserRepository userRepository;

    @RequestMapping({"", "/", "index", "index.html"})
    public String index() {
        return "registered/index";
    }

    @RequestMapping({"/create", "/create-recipe", "create-recipe.html"})
    public String create(Model model) {
        Recipe recipe = new Recipe();
        model.addAttribute("recipe", recipe);
        return "registered/create-recipe";
    }

    @PostMapping(value = "/save")
    public String save(Recipe recipe, Authentication authentication) {
        recipe.setAuthor(new HashSet<>(userRepository.findByUsername(authentication.getName())));
        recipe.setDateAdded(new Date());
        recipe.setTotalTime(recipe.getPrepTime() + recipe.getCookTime());
        recipeRepository.save(recipe);
        return "/registered/index";
    }

    @RequestMapping({"/plan", "/plan-meal", "plan-meal.html"})
    public String plan() {
        return "registered/plan-meal";
    }

    @RequestMapping({"/search", "/search-recipe", "search-recipe.html"})
    public String search() {
        return "registered/search-recipe";
    }

    @PostMapping(value = "/name")
    public String name(HttpServletRequest request, Model model){
        String searchName = request.getParameter("name");
        model.addAttribute("searchString", "You searched for " + searchName);
        model.addAttribute("recipes", recipeRepository.findByNameIgnoreCase(searchName));
        List<Recipe> resp = recipeRepository.findByNameIgnoreCase(searchName);
        model.addAttribute("nameCount", -1);
        model.addAttribute("count", resp.size());
        if(resp.size() >0)
        {
            model.addAttribute("recipes", resp);
            return "/registered/index";
        }
        else {
            model.addAttribute("message", "No record Found");
            return "/registered/index";
        }
    }


    @RequestMapping({"/view-profile", "view-profile.html"})
    public String viewProfile() {
        return "registered/view-profile";
    }

    @RequestMapping({"/view-recipe", "view-recipe.html"})
    public String viewRecipe() {
        return "registered/view-recipe";
    }
}