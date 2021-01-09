package com.lucutovidiu.pages;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/Contact")
public interface Contact {

    @GetMapping
    String getContactPage(Model model);
}
