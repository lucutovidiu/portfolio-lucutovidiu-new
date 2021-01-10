package com.lucutovidiu.mongo;

import com.lucutovidiu.models.Portfolio;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PortfolioService {

    List<Portfolio> getAllPortfolios();
}
