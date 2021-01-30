package com.lucutovidiu.mongo;

import com.lucutovidiu.pojo.Portfolio;
import com.lucutovidiu.pojo.PortfolioBasic;
import com.lucutovidiu.portfolio.NewPortfolioRequestDto;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface PortfolioDbService {

    List<PortfolioBasic> getAllPortfolios();

    List<Portfolio> getAllPortfoliosFull();

    Optional<Portfolio> getPortfolioById(String id);

    String createPortfolioStructure(NewPortfolioRequestDto newPortfolioRequest);

    boolean deletePortfolioStructure(String portfolioId);

    void updatePortfolioThumbnailImage(String portfolioId, String thumbnailImage);

    void addPortfolioMoreImages(String portfolioId, String imageSrc);

    void editExistingPortfolio(NewPortfolioRequestDto newPortfolioRequest);
}
