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
//@RequestMapping(value = "/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @GetMapping("/admin/users")
    public String showAdminPage(Model model, Principal principal) {
        String email = principal.getName();
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("user", userService.getUserByEmail(email));
        model.addAttribute("newUser", new User());
        return "/adminpage";
    }

    @PostMapping("/admin/edit")
    public String edit(User user,
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

    @GetMapping("/admin/delete")
    public String deleteUser(Long id) {
        userService.delete(id);
        return "redirect:/admin/users";
    }

    @GetMapping("/admin/findOneUser")
    @ResponseBody
    public User findOneUser(Long id) {
        return userService.show(id);
    }

    @PostMapping("/admin/users/create")
    public String addNewUser(@ModelAttribute("newUser") User user,
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
}
