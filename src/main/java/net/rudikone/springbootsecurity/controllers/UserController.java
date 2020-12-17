package net.rudikone.springbootsecurity.controllers;

import net.rudikone.springbootsecurity.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("")
    public String getUserPage(Model model, Principal principal) {
        String email = principal.getName();
        model.addAttribute("user", userService.getUserByEmail(email));
        return "/userpage";
    }
}
