package com.lucutovidiu.pages;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lucutovidiu.dto.PortfolioDto;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static com.lucutovidiu.util.PageAttributesUtil.ActivePage;
import static com.lucutovidiu.util.PageAttributesUtil.Portfolio;
import static com.lucutovidiu.util.Pages.ADDNEWPORTFOLIO;

@Component
public class AddNewPortfolioImpl implements AddNewPortfolio {

    @Override
    public String getCreateNewPortfolio(Model model) {
        model.addAttribute(ActivePage, ADDNEWPORTFOLIO);
        return "portfolios/new-portfolio/create-new-portfolio";
    }

    @Override
    public String loadFromJson(MultipartFile jsonProject, Model model) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        PortfolioDto portfolio = objectMapper.readValue(jsonProject.getBytes(), PortfolioDto.class);
        model.addAttribute(Portfolio, portfolio);
        return "portfolios/new-portfolio/create-new-portfolio";
    }
}
