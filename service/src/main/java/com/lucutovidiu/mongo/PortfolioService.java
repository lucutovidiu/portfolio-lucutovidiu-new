package com.lucutovidiu.mongo;

import com.lucutovidiu.pojo.Portfolio;
import com.lucutovidiu.pojo.PortfolioBasic;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface PortfolioService {

    List<PortfolioBasic> getAllPortfolios();

    Optional<Portfolio> getPortfolioById(String id);
}
