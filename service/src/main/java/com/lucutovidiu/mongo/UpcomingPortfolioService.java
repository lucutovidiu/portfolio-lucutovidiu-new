package com.lucutovidiu.mongo;

import com.lucutovidiu.models.UpcomingPortfolio;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UpcomingPortfolioService {

    List<UpcomingPortfolio> getUpcomingPortfolios();
}
