/**********************************************************************************
 * Project: < comp3095_assignment1 >
 * Assignment: < assignment 1 >
 * Author(s): < Calvin Pierce, Ikechukwu Emmanuel Okonkwo, Shiming Ye>
 * Student Number: < 101253832, 101277584, 101274045 >
 * Date: November 25th 2021
 * Description: This java file is used to control all pages available to registered users.
 **********************************************************************************/
package ca.gbc.comp3095.recipe.controllers;

import ca.gbc.comp3095.recipe.model.Event;
import ca.gbc.comp3095.recipe.model.Meal;
import ca.gbc.comp3095.recipe.model.Recipe;
import ca.gbc.comp3095.recipe.model.User;
import ca.gbc.comp3095.recipe.repositories.*;
import ca.gbc.comp3095.recipe.services.MealService;
import ca.gbc.comp3095.recipe.services.RecipeService;
import ca.gbc.comp3095.recipe.services.SearchService;
import ca.gbc.comp3095.recipe.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;

@RequestMapping("/registered")
@Controller
public class RegisteredController {

    @Autowired
    private UserService userService;

    @Autowired
    private RecipeService recipeService;

    @Autowired
    private SearchService searchService;

    @Autowired
    private MealService mealService;

    @Autowired
    RecipeRepository recipeRepository;

    @Autowired
    MealRepository mealRepository;

    @Autowired
    SearchRepository searchRepository;

    @Autowired
    EventRepository eventRepository;

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
        recipeService.save(recipe);
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
        List<Recipe> listRecipes = searchService.listAll("");
        model.addAttribute("recipes", listRecipes);
        return "registered/create-meal";
    }

    @PostMapping(value = "/saveMeal")
    public String saveMeal(Meal meal, Authentication authentication, Model model) {
        mealService.save(meal);
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
        List<Recipe> resp = searchService.listAll(searchName);
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
        model.addAttribute("user", userService.getUserByUsername(authentication.getName()));
        model.addAttribute("userRecipes", searchRepository.findByUsername(authentication.getName()));
        return "registered/view-profile";
    }

    @RequestMapping({"/edit-profile/{id}"})
    public String viewProfile(Model model, @PathVariable Long id) {
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        return "registered/edit-profile";
    }

    @RequestMapping(value = "/saveUser")
    public String saveUser(User user, Model model, Authentication authentication) {
        if (userService.getUserByUsername(user.getUsername()) != null) {
            if (!Objects.equals(authentication.getName(), user.getUsername())){
                model.addAttribute("message", "Invalid User! " + user.getUsername() + " Username Already Taken!");
                return "/registered/edit-profile";
            }
        }
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        user.setPassword(((UserDetails)principal).getPassword());
        userService.updateUser(user);
        model.addAttribute("message", "Profile updated, please login again to see all changes.");
        return "registered/view-profile";
    }

    @RequestMapping(value = {"/change", "/change-password", "change-password.html"}, method = RequestMethod.GET)
    public String change(Model model) {
        return "/registered/change-password";
    }

    @RequestMapping(value = {"/change", "/change-password", "change-password.html"}, method = RequestMethod.POST)
    public String change(HttpServletRequest request, Model model, Authentication authentication) {
        if (!request.getParameter("password").equals(request.getParameter("confirmPassword"))) {
            model.addAttribute("badMatch", "Error! Passwords do not match!");
            return "/registered/change-password";
        }
        User user = userService.getUserByUsername(authentication.getName());
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(request.getParameter("password"));
        user.setPassword(encodedPassword);
        userService.saveNewPassword(user);
        model.addAttribute("message", "You have successfully changed your password.");
        return "/registered/change-success";
    }

    @RequestMapping({"/view-recipe", "view-recipe.html"})
    public String viewRecipe(Model model) {
        List<Recipe> listRecipes = searchService.listAll("");
        model.addAttribute("recipes", listRecipes);
        return "registered/view-recipe";
    }

    @RequestMapping({"/edit-recipe/{id}"})
    public String editRecipe(Model model, @PathVariable Long id) {
        Recipe recipe = recipeService.getRecipeById(id);
        model.addAttribute("recipe", recipe);
        return "registered/edit-recipe";
    }

    @PostMapping(value = "/updateRecipe")
    public String updateRecipe(Recipe recipe, Model model) {
        recipeService.save(recipe);
        List<Recipe> listRecipes = searchService.listAll("");
        model.addAttribute("recipes", listRecipes);
        return "/registered/view-recipe";
    }

    @RequestMapping({"/view-steps/{id}"})
    public String viewSteps(Model model, @PathVariable Long id) {
        Recipe recipe = recipeService.getRecipeById(id);
        model.addAttribute("recipe", recipe);
        return "registered/view-steps";
    }

    @RequestMapping({"/view-events", "view-events.html"})
    public String viewEvents(Model model, Authentication authentication) {
        List<Event> listEvents = eventRepository.findEventByUser(authentication.getName());//service.listAllEvents("");
        model.addAttribute("user", userService.getUserByUsername(authentication.getName()));
        model.addAttribute("events", listEvents);
        return "registered/view-events";
    }

    @RequestMapping({"/update-event/{id}", "update-event.html"})
    public String updateView(Model model, Authentication authentication, @PathVariable("id") String id) {
        System.out.println(id);
        Long id_ = Long.valueOf(id);
        Event event = searchRepository.findEventById(id_);
        model.addAttribute("event", event);
        return "registered/update-event";
    }

    @PostMapping(value = "/updateEvent")
    public String updateEvent(HttpServletRequest request, Event event, Authentication authentication, Model model) {
        event.setUser(userService.getUserByUsername(authentication.getName()));
        String newName = request.getParameter("name");
        event.setName(newName);
        eventRepository.updateEvent(event.getId(), newName);
        List<Event> listEvents = eventRepository.findEventByUser(authentication.getName());//service.listAllEvents("");
        model.addAttribute("user", userService.getUserByUsername(authentication.getName()));
        model.addAttribute("events", listEvents);
        return "registered/view-events";
    }

    @RequestMapping({"/delete-event/{id}", "view-events.html"})
    public String deleteEvent(Model model, Authentication authentication, @PathVariable("id") String id) {

        System.out.println(id);
        Long id_ = Long.valueOf(id);
        eventRepository.deleteById(id_);
        List<Event> listEvents = searchService.listAllEvents("");
        model.addAttribute("events", listEvents);
        return "registered/view-events";
    }
}