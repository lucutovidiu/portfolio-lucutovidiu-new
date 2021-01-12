package com.lucutovidiu.repos;

import com.lucutovidiu.models.PortfolioEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PortfolioRepository extends MongoRepository<PortfolioEntity, String> {
}
