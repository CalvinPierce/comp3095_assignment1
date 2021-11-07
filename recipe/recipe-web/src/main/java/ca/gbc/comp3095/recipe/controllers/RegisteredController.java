/**********************************************************************************
 * Project: < comp3095_assignment1 >
 * Assignment: < assignment 1 >
 * Author(s): < Calvin Pierce, Ikechukwu Emmanuel Okonkwo>
 * Student Number: < 101253832, 101277584 >
 * Date: November 7th 2021
 * Description: This java file is used to control all pages available to registered users.
 **********************************************************************************/
package ca.gbc.comp3095.recipe.controllers;

import ca.gbc.comp3095.recipe.model.Meal;
import ca.gbc.comp3095.recipe.model.Recipe;
import ca.gbc.comp3095.recipe.repositories.MealRepository;
import ca.gbc.comp3095.recipe.repositories.RecipeRepository;
import ca.gbc.comp3095.recipe.repositories.SearchRepository;
import ca.gbc.comp3095.recipe.repositories.UserRepository;
import ca.gbc.comp3095.recipe.services.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Date;

@RequestMapping("/registered")
@Controller
public class RegisteredController {

    @Autowired
    RecipeRepository recipeRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    MealRepository mealRepository;

    @Autowired
    SearchRepository searchRepository;

    @Autowired
    SearchService service;

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

    @PostMapping(value = "/saveRecipe")
    public String saveRecipe(Recipe recipe, Authentication authentication) {
        recipe.setAuthor(userRepository.getUserByUsername(authentication.getName()));
        recipe.setDateAdded(new Date());
        recipe.setTotalTime(recipe.getPrepTime() + recipe.getCookTime());
        recipeRepository.save(recipe);
        return "/registered/index";
    }

    @RequestMapping({"/plan", "/plan-meal", "plan-meal.html"})
    public String plan(Model model, Authentication authentication) {
        model.addAttribute("userMeals", searchRepository.findByUser(authentication.getName()));
        return "registered/plan-meal";
    }

    @RequestMapping({"/create-meal", "create-meal.html"})
    public String createMeal(Model model) {
        Meal meal = new Meal();
        model.addAttribute("meal", meal);
        List<Recipe> listRecipes = service.listAll("");
        model.addAttribute("recipes", listRecipes);
        return "registered/create-meal";
    }

    @PostMapping(value = "/saveMeal")
    public String saveMeal(Meal meal, Authentication authentication, Model model) {
        meal.setUser(userRepository.getUserByUsername(authentication.getName()));
        mealRepository.save(meal);
        model.addAttribute("userMeals", searchRepository.findByUser(authentication.getName()));
        return "/registered/plan-meal";
    }

    @RequestMapping(value = {"search", "/search-recipe", "/search-recipe.html"}, method = RequestMethod.GET)
    public String search(Model model) {
        model.addAttribute("recipe", new Recipe());
        return "registered/search-recipe";
    }

    @RequestMapping(value = {"search", "/search-recipe", "/search-recipe.html"}, method = RequestMethod.POST)
    public String search(HttpServletRequest request, Model model) {
        String searchName = request.getParameter("name");
        model.addAttribute("searchString", "You searched for " + searchName);
        List<Recipe> resp = service.listAll(searchName);
        model.addAttribute("nameCount", -1);
        model.addAttribute("count", resp.size());
        if (resp.size() > 0) {
            model.addAttribute("recipes", resp);
        } else {
            model.addAttribute("message", "No record Found");
        }
        return "/registered/search-recipe";
    }

    @RequestMapping({"/view-profile", "view-profile.html"})
    public String viewProfile(Model model, Authentication authentication) {
        model.addAttribute("user", userRepository.getUserByUsername(authentication.getName()));
        model.addAttribute("userRecipes", searchRepository.findByUsername(authentication.getName()));
        return "registered/view-profile";
    }

    @RequestMapping({"/view-recipe", "view-recipe.html"})
    public String viewRecipe(Model model) {
        List<Recipe> listRecipes = service.listAll("");
        model.addAttribute("recipes", listRecipes);
        return "registered/view-recipe";
    }
}