package com.lucutovidiu.pages;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
public interface Users {

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    String addUser(Model model);
}
