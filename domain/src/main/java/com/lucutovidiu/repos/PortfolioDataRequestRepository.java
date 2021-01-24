package com.lucutovidiu.repos;

import com.lucutovidiu.models.PortfolioDataRequestEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface PortfolioDataRequestRepository extends MongoRepository<PortfolioDataRequestEntity, String> {

    Optional<PortfolioDataRequestEntity> findByRequestId(String requestId);
}
