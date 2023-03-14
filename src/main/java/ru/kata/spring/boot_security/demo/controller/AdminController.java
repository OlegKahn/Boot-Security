package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.model.UsersCreationDto;
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
    public String idk() {
        return "redirect:/admin/";
    }

    @GetMapping(value = "/")
    public String mainPage(ModelMap modelMap) {
        List<User> users = userService.listUsers();
        User user = new User();
        UsersCreationDto usersCreationDto = new UsersCreationDto();
        usersCreationDto.setUsers(users);
        Set<Role> allRoles = new HashSet<>();
        allRoles.add(new Role("USER"));
        allRoles.add(new Role("ADMIN"));
        modelMap.addAttribute("allRoles", allRoles);
        modelMap.addAttribute("uCD", usersCreationDto);
        modelMap.addAttribute("userin", user);
        return "admin_panel";
    }



    @PostMapping(value = "/delete/{deleteId}")
    public String deleteUser(@PathVariable("deleteId") long id) {
        userService.delete(id);
        return "redirect:/admin/";
    }
    @PostMapping(value = "/add/")
    public String add(@ModelAttribute User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setIsActive(true);
        userService.add(user);
        return "redirect:/admin/";
    }

    @PostMapping("/update/")
    public String update(@ModelAttribute User user) {
        if (user.getPassword().isEmpty()) {
            user.setPassword(userService.getUser(user.getId()).getPassword());
        } else {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        if (user.getRoles().isEmpty()) {
            user.setRoles(userService.getUser(user.getId()).getRoles());
        }
        user.setIsActive(true);
                userService.add(user);
        return "redirect:/admin/";
    }

}
