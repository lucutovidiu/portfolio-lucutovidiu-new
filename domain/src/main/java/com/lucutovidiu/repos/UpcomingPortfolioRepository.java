package com.lucutovidiu.repos;

import com.lucutovidiu.models.UpcomingPortfolioEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UpcomingPortfolioRepository extends MongoRepository<UpcomingPortfolioEntity, String> {
}
