package ca.gbc.comp3095.recipe.controllers;

import ca.gbc.comp3095.recipe.model.User;
import ca.gbc.comp3095.recipe.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@RequestMapping("/unregistered")
@Controller
public class UnregisteredController {

    @Autowired
    private UserRepository userRepository;

    @RequestMapping({"", "/", "register.html"})
    public String index(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "unregistered/register";
    }

    @PostMapping(value = "/save")
    public String save(User user, Model model) {
        userRepository.save(user);
        return "redirect:/";
    }

}
