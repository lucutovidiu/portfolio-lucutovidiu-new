package com.lucutovidiu.pages;

import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import static com.lucutovidiu.util.PageAttributesUtil.ActivePage;
import static com.lucutovidiu.util.Pages.ADDNEWPORTFOLIO;

@Component
public class AddNewPortfolioImpl implements AddNewPortfolio {

    @Override
    public String getCreateNewPortfolio(Model model) {
        model.addAttribute(ActivePage, ADDNEWPORTFOLIO);
        return "portfolios/new-portfolio/create-new-portfolio";
    }
}
