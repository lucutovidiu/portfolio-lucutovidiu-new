package com.lucutovidiu.pages;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/settings")
public interface Settings {

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'PRIVATE')")
    String getSettingsPage(Model model);
}
