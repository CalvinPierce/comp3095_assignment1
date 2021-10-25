package ca.gbc.comp3095.recipe.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/unregistered")
@Controller
public class UnregisteredController {

    @RequestMapping({"", "/", "register.html"})
    public String index() {
        return "unregistered/register";
    }

}
