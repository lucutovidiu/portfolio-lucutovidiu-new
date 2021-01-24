package com.lucutovidiu.pages;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/edit-portfolio")
public interface EditPortfolio {

    @GetMapping("/{portfolioId}")
    @PreAuthorize("hasRole('ADMIN')")
    String editPortfolio(@PathVariable String portfolioId, Model model);
}
