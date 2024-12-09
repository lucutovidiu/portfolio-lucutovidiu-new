package com.lucutovidiu.pages;

import com.lucutovidiu.mongo.PortfolioDbService;
import com.lucutovidiu.mongo.UpcomingPortfolioService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import static com.lucutovidiu.util.PageAttributesUtil.Portfolios;
import static com.lucutovidiu.util.PageAttributesUtil.*;
import static com.lucutovidiu.util.Pages.PORTFOLIOS;

@Component
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class PortfoliosImpl implements Portfolios {

    private final PortfolioDbService portfolioDbService;
    private final UpcomingPortfolioService upcomingPortfolioService;

    @Override
    public String getPortfolios(Model model) {
        model.addAttribute(ActivePage, PORTFOLIOS);
        model.addAttribute(Portfolios, portfolioDbService.getAllPortfolios());
        model.addAttribute(UpcomingPortfolios, upcomingPortfolioService.getUpcomingPortfolios());
        return "portfolios/portfolios";
    }

    @Override
    public String getPortfolioById(String id, Model model) {
        model.addAttribute(PortfolioData, portfolioDbService.getPortfolioById(id).orElse(null));
        return "portfolios/fetch-portfolio-by-id";
    }
}
