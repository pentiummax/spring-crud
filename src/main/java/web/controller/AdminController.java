package web.controller;

import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import web.model.Role;
import web.model.User;
import web.service.UserService;

import javax.persistence.EntityNotFoundException;
import java.util.HashSet;
import java.util.Set;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public AdminController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("")
    public String admin() {
        return "redirect:/admin/users";
    }

    @GetMapping("/users")
    public String getUsers(Model model) {
        model.addAttribute("users", userService.getUsersList());
        return "users";
    }

    @GetMapping(value = "/users/{id}")
    public String show(@PathVariable("id") long id, Model model) {
        model.addAttribute("user", userService.getUser(id));
        return "show";
    }

    @GetMapping(value = "/users/new")
    public String newUser(@ModelAttribute("user") User user, Model model) {
        model.addAttribute("all_roles", userService.getRoles());
        return "new";
    }

    @PostMapping("/users")
    public String create(@ModelAttribute("user") User user) {
        Set<Role> allRoles = user.getRoles();

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.saveUser(user);
        return "redirect:/admin/users";
    }

    @GetMapping(value = {"/users/{id}/edit"})
    public String edit(Model model, @PathVariable long id) {
        User user = null;
        try {
            user = userService.getUser(id);
        } catch (EntityNotFoundException e) {
            model.addAttribute("errorMessage", "User not found");
        }
        model.addAttribute("user", user);
        return "edit";
    }

    @PatchMapping(value = {"/users/{id}"})
    public String updateUser(@PathVariable long id,
                             @ModelAttribute("user") User user) {
        user.setId(id);
        userService.updateUser(user);
        return "redirect:/admin/users";
    }

    @DeleteMapping(value = {"/users/{id}"})
    public String deleteUserById(@PathVariable long id) {
        userService.deleteUser(id);
        return "redirect:/admin/users";
    }
}
