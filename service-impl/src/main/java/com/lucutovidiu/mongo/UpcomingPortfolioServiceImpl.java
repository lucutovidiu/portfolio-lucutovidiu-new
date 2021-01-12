package com.lucutovidiu.mongo;

import com.lucutovidiu.models.UpcomingPortfolioEntity;
import com.lucutovidiu.pojo.UpcomingPortfolioBasic;
import com.lucutovidiu.repos.UpcomingPortfolioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class UpcomingPortfolioServiceImpl implements UpcomingPortfolioService {

    private final UpcomingPortfolioRepository upcomingPortfolioRepository;

    @Override
    public List<UpcomingPortfolioBasic> getUpcomingPortfolios() {
        return upcomingPortfolioRepository.findAll().stream()
                .map(UpcomingPortfolioEntity::toUpcomingPortfolio)
                .collect(Collectors.toList());
    }
}
