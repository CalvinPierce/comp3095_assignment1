/**********************************************************************************
 * Project: < comp3095_assignment1 >
 * Assignment: < assignment 1 >
 * Author(s): < Calvin Pierce>
 * Student Number: < 101253832 >
 * Date: November 11th 2021
 * Description: This java file is used to control our pages for non-registered users.
 **********************************************************************************/
package ca.gbc.comp3095.recipe.controllers;

import ca.gbc.comp3095.recipe.model.User;
import ca.gbc.comp3095.recipe.repositories.RoleRepository;
import ca.gbc.comp3095.recipe.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;

@Controller
public class IndexController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @RequestMapping({"/register", "register.html"})
    public String index(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "/register";
    }

    @PostMapping(value = "/save")
    public String save(User user, Model model) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        user.setRoles(new HashSet<>(roleRepository.findByName("user")));
        user.setEnabled(true);
        userRepository.save(user);
        return "/login";
    }

    @RequestMapping(value = {"", "/", "/login", "/login.html"}, method = RequestMethod.GET)
    public String showLoginPage() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "/login";
        }
        return "redirect:/registered/";
    }

    @RequestMapping({"/logout", "logout.html"})
    public String logout() {
        return "/logout";
    }

    @RequestMapping(value = {"/reset", "reset-password.html"}, method = RequestMethod.GET)
    public String reset(Model model) {
        return "/reset-password";
    }

    @RequestMapping(value = {"/reset", "reset-password.html"}, method = RequestMethod.POST)
    public String reset(HttpServletRequest request, Model model) {
        User user = userRepository.getUserByUsername(request.getParameter("username"));
        if (user == null) {
            model.addAttribute("message", "Invalid User! " + request.getParameter("username") + " Not Found!");
            return "/reset-password";
        } else {
            if (!request.getParameter("password").equals(request.getParameter("confirmPassword"))) {
                model.addAttribute("badMatch", "Error! Passwords do not match!");
                return "/reset-password";
            }
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String encodedPassword = passwordEncoder.encode(request.getParameter("password"));
            user.setPassword(encodedPassword);
            userRepository.save(user);
            model.addAttribute("message", "You have successfully changed your password.");
        }
        return "/reset-success";
    }

}
