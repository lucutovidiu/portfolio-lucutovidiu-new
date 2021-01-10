package com.lucutovidiu.mongo;

import com.lucutovidiu.models.Portfolio;
import com.lucutovidiu.repos.PortfolioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class PortfolioServiceImpl implements PortfolioService {

    private final PortfolioRepository portfolioRepository;

    @Override
    public List<Portfolio> getAllPortfolios() {
        return portfolioRepository.findAll();
    }
}
