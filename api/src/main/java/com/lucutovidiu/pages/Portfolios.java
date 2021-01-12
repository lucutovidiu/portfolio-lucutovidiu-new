package com.lucutovidiu.pages;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/portfolio")
public interface Portfolios {

    @GetMapping("/all")
    String getPortfolios(Model model);

    @GetMapping("/{id}")
    String getPortfolioById(Model model);
}
