package com.lucutovidiu.repos;

import com.lucutovidiu.models.UpcomingPortfolio;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UpcomingPortfolioRepository extends MongoRepository<UpcomingPortfolio, String> {
}
