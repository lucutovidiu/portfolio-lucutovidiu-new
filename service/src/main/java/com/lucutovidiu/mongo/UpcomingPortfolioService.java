package com.lucutovidiu.mongo;

import com.lucutovidiu.pojo.UpcomingPortfolioBasic;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UpcomingPortfolioService {

    List<UpcomingPortfolioBasic> getUpcomingPortfolios();
}
