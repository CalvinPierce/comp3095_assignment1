package ca.gbc.comp3095.recipe.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/registered")
@Controller
public class RegisteredController {

    @RequestMapping({"", "/", "index.html"})
    public String index() {
        return "registered/index";
    }

    @RequestMapping({"/create", "create-recipe.html"})
    public String create() {
        return "registered/create-recipe";
    }

    @RequestMapping({"/logout", "logout.html"})
    public String logout() {
        return "registered/logout";
    }

    @RequestMapping({"/plan", "plan-meal.html"})
    public String plan() {
        return "registered/plan-meal";
    }

    @RequestMapping({"/search", "search.recipe.html"})
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