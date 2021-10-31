package ca.gbc.comp3095.recipe.controllers;

import ca.gbc.comp3095.recipe.model.Recipe;
import ca.gbc.comp3095.recipe.model.User;
import ca.gbc.comp3095.recipe.repositories.RecipeRepository;
import ca.gbc.comp3095.recipe.repositories.UserRepository;
import org.hibernate.engine.jdbc.ClobProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
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

    @RequestMapping({"/view-profile", "view-profile.html"})
    public String viewProfile() {
        return "registered/view-profile";
    }

    @RequestMapping({"/view-recipe", "view-recipe.html"})
    public String viewRecipe() {
        return "registered/view-recipe";
    }
}