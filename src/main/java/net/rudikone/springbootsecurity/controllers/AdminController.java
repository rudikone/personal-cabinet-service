package net.rudikone.springbootsecurity.controllers;

import net.rudikone.springbootsecurity.model.Role;
import net.rudikone.springbootsecurity.model.User;
import net.rudikone.springbootsecurity.service.RoleService;
import net.rudikone.springbootsecurity.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashSet;
import java.util.Set;

@Controller
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @GetMapping("/admin/users")
    public String showAdminPage(Model model, Principal principal) {
        String email = principal.getName();
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("roleSet", roleService.findAll());

        model.addAttribute("admin", userService.getUserByEmail(email));

        model.addAttribute("newUser", new User());
        return "/adminpage";
    }


    @PostMapping("/admin/edit/{id}")
    public String edit(@ModelAttribute("user") User user,
                       @PathVariable("id") Long id,
                       BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "admin/users/new";
        }

        userService.save(user);
        return "redirect:/admin/users";
    }

    @PostMapping("/admin/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.delete(id);
        return "redirect:/admin/users";
    }


    @PostMapping("/admin/users/create")
    public String addNewUser(@ModelAttribute("newUser") User user,
                             BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "/adminpage";
        }
        userService.save(user);
        return "redirect:/admin/users";
    }
}
