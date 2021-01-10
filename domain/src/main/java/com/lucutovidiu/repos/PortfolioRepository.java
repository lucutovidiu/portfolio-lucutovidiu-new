package com.lucutovidiu.repos;

import com.lucutovidiu.models.Portfolio;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PortfolioRepository extends MongoRepository<Portfolio, String> {
}
