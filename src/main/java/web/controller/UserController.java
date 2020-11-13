package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import web.model.User;
import web.service.UserService;

import javax.persistence.EntityNotFoundException;

@Controller
@RequestMapping
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/admin/users")
    public String getUsers(Model model) {
        model.addAttribute("users", userService.getUsersList());
        return "users";
    }

    @GetMapping("/user")
    public String getUser(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("user", user);
        return "user";
    }

    @GetMapping(value = "/admin/user/{id}")
    public String show(@PathVariable("id") long id, Model model) {
        model.addAttribute("user", userService.getUser(id));
        return "show";
    }

    @GetMapping(value = "/admin/user/new")
    public String newUser(@ModelAttribute("user") User user) {
        return "new";
    }

    @PostMapping("/admin")
    public String create(@ModelAttribute("user") User user) {
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @GetMapping(value = {"admin/user/{id}/edit"})
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

    @PatchMapping(value = {"admin/user/{id}"})
    public String updateUser(@PathVariable long id,
                             @ModelAttribute("user") User user) {
        user.setId(id);
        userService.updateUser(user);
        return "redirect:/admin";
    }

    @DeleteMapping(value = {"admin/user/{id}"})
    public String deleteUserById(@PathVariable long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }

}
