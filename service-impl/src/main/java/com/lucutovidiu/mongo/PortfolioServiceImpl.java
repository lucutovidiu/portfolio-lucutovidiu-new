package com.lucutovidiu.mongo;

import com.lucutovidiu.models.PortfolioEntity;
import com.lucutovidiu.pojo.ImageDescription;
import com.lucutovidiu.pojo.Portfolio;
import com.lucutovidiu.pojo.PortfolioBasic;
import com.lucutovidiu.portfolio.NewPortfolioRequestDto;
import com.lucutovidiu.repos.PortfolioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class PortfolioServiceImpl implements PortfolioService {

    private final PortfolioRepository portfolioRepository;

    @Override
    public List<PortfolioBasic> getAllPortfolios() {
        return portfolioRepository.findAll().stream()
                .map(PortfolioEntity::toPortfolioBasic)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Portfolio> getPortfolioById(String id) {
        return portfolioRepository.findById(id).map(PortfolioEntity::toPortfolio);
    }

    @Override
    public String createPortfolioStructure(NewPortfolioRequestDto newPortfolioRequest) {
        return portfolioRepository.save(PortfolioEntity.create(newPortfolioRequest)).getId();
    }

    @Override
    public boolean deletePortfolioStructure(String portfolioId) {
        return portfolioRepository.findById(portfolioId).map(entity -> {
            portfolioRepository.delete(entity);
            return true;
        }).orElse(false);
    }

    @Override
    public void updatePortfolioThumbnailImage(String portfolioId, String thumbnailImage) {
        portfolioRepository.findById(portfolioId)
                .map(entity -> {
                    entity.setThumbImage(thumbnailImage);
                    return entity;
                }).ifPresent(portfolioRepository::save);
    }

    @Override
    public void addPortfolioMoreImages(String portfolioId, String imageSrc) {
        portfolioRepository.findById(portfolioId)
                .map(entity -> {
                    entity.getMoreImages().add(new ImageDescription(imageSrc, portfolioId));
                    return entity;
                }).ifPresent(portfolioRepository::save);
    }

}
