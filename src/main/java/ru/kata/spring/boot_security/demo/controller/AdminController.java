package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {

    @Autowired
    UserService userService;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AdminController(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping(value = "")
    public String idnk() {
        return "redirect:/admin/";
    }

    @GetMapping(value = "/")
    public String mainPage(ModelMap modelMap) {
        List<User> users = userService.listUsers();
        modelMap.addAttribute("users", users);
        return "admin";
    }

    @GetMapping(value = "new")
    public String writeNewUser(Model model) {
        User user = new User();
        Set<Role> allRoles = new HashSet<>();
        allRoles.add(new Role("USER"));
        allRoles.add(new Role("ADMIN"));
        model.addAttribute("user", user);
        model.addAttribute("allRoles", allRoles);

        return "secondPage";
    }


    @GetMapping(value = "/saveUser")
    public String saveUser(@ModelAttribute User user) {
        if (!StringUtils.isEmpty(user.getPassword())) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        userService.add(user);
        return "redirect:/admin/";
    }


    @GetMapping(value = "/delete")
    public String deleteUser(@RequestParam("userId") long id) {
        userService.delete(id);
        return "redirect:/admin/";
    }


    @GetMapping(value = "/update")
    public String updateUser(@RequestParam("userId") long id, Model model) {
        Set<Role> allRoles = new HashSet<>();
        allRoles.add(new Role("USER"));
        allRoles.add(new Role("ADMIN"));
        model.addAttribute(userService.getUser(id));
        model.addAttribute("allRoles", allRoles);
        return "secondPage";
    }
}
