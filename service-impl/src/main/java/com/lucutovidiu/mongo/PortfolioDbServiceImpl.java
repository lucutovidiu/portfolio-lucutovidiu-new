package com.lucutovidiu.mongo;

import com.lucutovidiu.models.PortfolioEntity;
import com.lucutovidiu.pojo.ImageDescription;
import com.lucutovidiu.pojo.Portfolio;
import com.lucutovidiu.pojo.PortfolioBasic;
import com.lucutovidiu.portfolio.NewPortfolioRequestDto;
import com.lucutovidiu.repos.PortfolioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.lucutovidiu.cache.CacheNames.*;

@Component
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class PortfolioDbServiceImpl implements PortfolioDbService {

    private final PortfolioRepository portfolioRepository;

    @Override
    @Cacheable(value = PORTFOLIOS_BASIC)
    public List<PortfolioBasic> getAllPortfolios() {
        return portfolioRepository.findAll().stream()
                .sorted(Comparator.comparing(PortfolioEntity::getProjectStartDate).reversed())
                .map(PortfolioEntity::toPortfolioBasic)
                .collect(Collectors.toList());
    }

    @Override
    @Cacheable(value = PORTFOLIOS_FULL)
    public List<Portfolio> getAllPortfoliosFull() {
        return portfolioRepository.findAll().stream()
                .map(PortfolioEntity::toPortfolio)
                .collect(Collectors.toList());
    }

    @Override
    @Cacheable(value = PORTFOLIO_BY_ID, key = "#id")
    public Optional<Portfolio> getPortfolioById(String id) {
        return portfolioRepository.findById(id).map(PortfolioEntity::toPortfolio);
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = PORTFOLIOS_BASIC, allEntries = true),
            @CacheEvict(value = PORTFOLIOS_FULL, allEntries = true)
    })
    public String createPortfolioStructure(NewPortfolioRequestDto newPortfolioRequest) {
        return portfolioRepository.save(PortfolioEntity.create(newPortfolioRequest)).getId();
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = PORTFOLIOS_BASIC, allEntries = true),
            @CacheEvict(value = PORTFOLIOS_FULL, allEntries = true),
            @CacheEvict(value = PORTFOLIO_BY_ID, allEntries = true)
    })
    public boolean deletePortfolioStructure(String portfolioId) {
        return portfolioRepository.findById(portfolioId).map(entity -> {
            portfolioRepository.delete(entity);
            return true;
        }).orElse(false);
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = PORTFOLIOS_BASIC, allEntries = true),
            @CacheEvict(value = PORTFOLIOS_FULL, allEntries = true),
            @CacheEvict(value = PORTFOLIO_BY_ID, allEntries = true)
    })
    public void updatePortfolioThumbnailImage(String portfolioId, String thumbnailImage) {
        portfolioRepository.findById(portfolioId)
                .map(entity -> {
                    entity.setThumbImage(thumbnailImage);
                    return entity;
                }).ifPresent(portfolioRepository::save);
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = PORTFOLIOS_BASIC, allEntries = true),
            @CacheEvict(value = PORTFOLIOS_FULL, allEntries = true),
            @CacheEvict(value = PORTFOLIO_BY_ID, key = "#portfolioId")
    })
    public void addPortfolioMoreImages(String portfolioId, String imageSrc) {
        portfolioRepository.findById(portfolioId)
                .map(entity -> {
                    entity.getMoreImages().add(new ImageDescription(imageSrc, portfolioId));
                    return entity;
                }).ifPresent(portfolioRepository::save);
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = PORTFOLIOS_BASIC, allEntries = true),
            @CacheEvict(value = PORTFOLIOS_FULL, allEntries = true),
            @CacheEvict(value = PORTFOLIO_BY_ID, key = "#newPortfolioRequest.getId()")
    })
    public void editExistingPortfolio(NewPortfolioRequestDto newPortfolioRequest) {
        portfolioRepository.findById(newPortfolioRequest.getId())
                .map(entity -> {
                    entity.update(newPortfolioRequest);
                    return entity;
                }).ifPresent(portfolioRepository::save);
    }
}
