package com.lucutovidiu.pages;

import com.lucutovidiu.mongo.PortfolioDbService;
import com.lucutovidiu.dto.PortfolioDto;
import com.lucutovidiu.pages.exceptions.PortfolioException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import static com.lucutovidiu.util.PageAttributesUtil.ActivePage;
import static com.lucutovidiu.util.PageAttributesUtil.EditPortfolioMode;
import static com.lucutovidiu.util.PageAttributesUtil.Portfolio;
import static com.lucutovidiu.util.Pages.ADDNEWPORTFOLIO;

@Component
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class EditPortfolioImpl implements EditPortfolio {

    private final PortfolioDbService portfolioDbService;

    @Override
    public String editPortfolio(String portfolioId, Model model) {
        PortfolioDto portfolio = portfolioDbService.getPortfolioById(portfolioId)
                .map(PortfolioDto::toPortfolioDto)
                .orElseThrow(() -> new PortfolioException(String.format("Portfolio id: %s not found", portfolioId)));
        model.addAttribute(Portfolio, portfolio);
        model.addAttribute(EditPortfolioMode, true);
        model.addAttribute(ActivePage, ADDNEWPORTFOLIO);
        return "portfolios/new-portfolio/create-new-portfolio";
    }
}
