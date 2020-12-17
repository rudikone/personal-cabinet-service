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
@RequestMapping(value="/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @RequestMapping(value = "/users",
            //produces = "application/json",
            method=RequestMethod.GET)
    public String getAllUsers(Model model, Principal principal) {
        String email = principal.getName();
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("user", userService.getUserByEmail(email));

        return "/adminpage";
    }

    @RequestMapping(value = "/users/{id}",
            //produces = "application/json",
            method=RequestMethod.GET)
    public String show(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", userService.show(id));
        return "/show";
    }

    @RequestMapping(value = "/users/new",
            //produces = "application/json",
            method=RequestMethod.GET)
    public String newUser(Model model) {
        model.addAttribute("user", new User());
        return "/new";
    }

    @RequestMapping(value = "/users",
            //produces = "application/json",
            method=RequestMethod.POST)
    public String create(@ModelAttribute("user") User user,
                         @RequestParam(value = "ADMIN", required = false) boolean isAdmin,
                         @RequestParam(value = "USER", required = false) boolean isUser,
                         BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "admin/users/new";
        }

        Set<Role> roles = new HashSet<>();

        if (isAdmin) {
            roles.add(roleService.findRoleById(1));
        }
        if (isUser) {
            roles.add(roleService.findRoleById(2));
        }

        user.setRoles(roles);
        userService.save(user);
        return "redirect:/admin/users";
    }

    @RequestMapping(value = "/users/{id}/edit",
            //produces = "application/json",
            method=RequestMethod.GET)
    public String edit(Model model, @PathVariable("id") Long id) {
        model.addAttribute("user", userService.show(id));
        return "/edit";
    }

    @RequestMapping(value = "/users/update/{id}",
            //produces = "application/json",
            method=RequestMethod.POST)
    public String update(@ModelAttribute("user") User user,
                         @PathVariable("id") Long id,
                         @RequestParam(value = "ADMIN", required = false) boolean isAdmin,
                         @RequestParam(value = "USER", required = false) boolean isUser,
                         BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "admin/users/edit";
        }

        Set<Role> roles = new HashSet<>();

        if (isAdmin) {
            roles.add(roleService.findRoleById(1));
        }
        if (isUser) {
            roles.add(roleService.findRoleById(2));
        }

        user.setRoles(roles);
        userService.save(user);
        return "redirect:/admin/users";
    }

    @RequestMapping(value = "/users/{id}",
            //produces = "application/json",
            method=RequestMethod.POST)
    public String delete(@PathVariable("id") Long id) {
        userService.delete(id);
        return "redirect:/admin/users";
    }
}
