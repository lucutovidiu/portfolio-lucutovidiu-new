package com.lucutovidiu.mongo;

import com.lucutovidiu.pojo.PortfolioBasic;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PortfolioService {

    List<PortfolioBasic> getAllPortfolios();
}
