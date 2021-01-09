package com.lucutovidiu.pages;

import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import static com.lucutovidiu.util.PageAttributesUtil.ActivePage;
import static com.lucutovidiu.util.Pages.PORTFOLIOS;

@Component
public class PortfoliosImpl implements Portfolios {

    @Override
    public String getPortfolios(Model model) {
        model.addAttribute(ActivePage, PORTFOLIOS);
        return "portfolios/portfolios";
    }
}
