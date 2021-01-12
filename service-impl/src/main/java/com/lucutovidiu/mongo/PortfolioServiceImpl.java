package com.lucutovidiu.mongo;

import com.lucutovidiu.models.PortfolioEntity;
import com.lucutovidiu.pojo.PortfolioBasic;
import com.lucutovidiu.repos.PortfolioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class PortfolioServiceImpl implements PortfolioService {

    private final PortfolioRepository portfolioRepository;

    @Override
    public List<PortfolioBasic> getAllPortfolios() {
        return portfolioRepository.findAll().stream()
                .map(PortfolioEntity::toPortfolio)
                .collect(Collectors.toList());
    }
}
