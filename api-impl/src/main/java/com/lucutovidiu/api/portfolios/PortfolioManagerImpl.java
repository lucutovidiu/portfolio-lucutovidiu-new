package com.lucutovidiu.api.portfolios;

import com.lucutovidiu.conversions.PhotoConversion;
import com.lucutovidiu.conversions.pojo.PhotoSize;
import com.lucutovidiu.mongo.PortfolioService;
import com.lucutovidiu.pojo.Portfolio;
import com.lucutovidiu.portfolio.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Component
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class PortfolioManagerImpl implements PortfolioManager {

    private final PortfolioService portfolioService;
    private final PortfolioPhotoManager portfolioPhotoManager;
    private final PhotoConversion photoConversion;

    @Override
    public NewPortfolioResponseDto postPortfolioData(NewPortfolioRequestDto newPortfolioRequest) {
        if (newPortfolioRequest.getProjectStartDate() == null || newPortfolioRequest.getProjectEndDate() == null)
            throw new DateIncorrectException("Project data incorrect!!");
        String generatedId = generateDBId();
        String rootDirectory = portfolioPhotoManager.createPortfolioPhotoBaseDir(newPortfolioRequest.getTitle(), generatedId);
        newPortfolioRequest.setId(generatedId);
        newPortfolioRequest.setRootDirectory(rootDirectory);
        portfolioService.createPortfolioStructure(newPortfolioRequest);
        return new NewPortfolioResponseDto(generatedId, true);
    }

    @Override
    public NewPortfolioResponseDto postFile(MultipartFile file, String type, String portfolioId) throws IOException {
        Portfolio portfolio = portfolioService.getPortfolioById(portfolioId)
                .orElseThrow(() -> new BaseStructureCreationException("Portfolio id: " + portfolioId + " not found!"));
        switch (FileUploadTypes.valueOf(type)) {
            case THUMBNAIL:
                byte[] imageToJpeg = photoConversion.convertAndScaleImageToJpeg(file.getBytes(), PhotoSize.getIdealThumbnailWidth());
                String thumbImage = portfolioPhotoManager.saveThumbImage(imageToJpeg, portfolio.getRootDirectory());
                portfolioService.updatePortfolioThumbnailImage(portfolioId, thumbImage);
                break;
            case MORE_IMAGES:
                imageToJpeg = photoConversion.convertAndScaleImageToJpeg(file.getBytes(), PhotoSize.getIdealMediumWidth());
                String imageSrc = portfolioPhotoManager.savePortfolioImage(imageToJpeg, portfolio.getRootDirectory());
                portfolioService.addPortfolioMoreImages(portfolioId, imageSrc);
                break;
            default:
                throw new RuntimeException("Invalid type entered");
        }
        return new NewPortfolioResponseDto(portfolioId, true);
    }

    @Override
    public boolean deletePortfolio(String portfolioId) {
        Portfolio portfolio = portfolioService.getPortfolioById(portfolioId)
                .orElseThrow(() -> new BaseStructureCreationException("Portfolio id: " + portfolioId + " not found!"));
        boolean hasPortfolioBeenDeleted = portfolioService.deletePortfolioStructure(portfolioId);
        boolean pathDeleted = portfolioPhotoManager.removePortfolioPhotoBaseDir(portfolio.getRootDirectory());
        return hasPortfolioBeenDeleted || pathDeleted;
    }

    private String generateDBId() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
