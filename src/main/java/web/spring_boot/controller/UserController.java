package web.spring_boot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import web.spring_boot.models.Role;
import web.spring_boot.models.User;
import web.spring_boot.service.RoleService;
import web.spring_boot.service.UserService;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class UserController {


    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @GetMapping(value = "login")
    public String loginPage() {
        return "login";
    }
    @GetMapping(value = "/")
    public String printWelcome(ModelMap model) {
        List<String> messages = new ArrayList<>();
        messages.add("3.1.1");
        messages.add("Spring-Boot application");
        model.addAttribute("messages", messages);
        return "index";
    }

    @GetMapping(value = "admin/users/{id}")
    public String allUsers(@PathVariable("id") long id,Model model) {
        List<User> users = userService.getAll();
        User user = userService.getOne(id);
        Set<Role> role =user.getRoles();
        StringBuilder roles = new StringBuilder();
        for(Role r :role){
            roles.append(r.getName());
            roles.append(" ");
        }
        model.addAttribute("user", userService.getOne(id));
        model.addAttribute("users", users);
        model.addAttribute("role", roles.toString());
        return "users";
    }

    @GetMapping(value = "/user/{id}")
    public String infoUser(@PathVariable("id") long id, Model model) {
        model.addAttribute("user", userService.getOne(id));
        return "user";
    }

    @GetMapping(value = "admin/new")
    public String newUser(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("role", roleService.getAll());
        return "new";
    }

    @PatchMapping(value = "admin/newuser")
    public String create(@ModelAttribute("user") User user, @RequestParam(name = "roleId") long[] roleId) {
        Set<Role> role = new HashSet<>();
        for (Long id : roleId) {
            role.add(roleService.getOne(id));
        }
        user.setRoles(role);
        userService.add(user);
        return "redirect:users";
    }


    @DeleteMapping(value = "admin/delete/{id}")
    public String delete(@PathVariable("id") long id) {
        userService.remove(id);
        return "redirect:/admin/users";
    }

    @GetMapping(value = "admin/edit/{id}")
    public String edit(Model model, @PathVariable("id") long id) {
        model.addAttribute("user", userService.getOne(id));
        model.addAttribute("role", roleService.getAll());

        return "edit";
    }

    @PatchMapping(value = "admin/update/{id}")
    public String update(@ModelAttribute("user") User user, @PathVariable("id") long id, @RequestParam(name = "roleId") long[] roleId) {
        Set<Role> role = new HashSet<>();
        for (Long idRole : roleId) {
            role.add(roleService.getOne(idRole));
        }
        user.setRoles(role);
        userService.update(id, user);
        return "redirect:/admin/users";
    }


}

