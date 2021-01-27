package com.lucutovidiu.pages;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/household")
public interface Household {

    @GetMapping
    @PreAuthorize("hasRole('PRIVATE')")
    String getHouseholdPage();

    @GetMapping("/view_page")
    @PreAuthorize("hasRole('PRIVATE')")
    String getHouseholdViewPage(Model model);
}
