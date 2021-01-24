package com.lucutovidiu.api.portfolios;

import com.lucutovidiu.conversions.PhotoConversion;
import com.lucutovidiu.conversions.pojo.PhotoSize;
import com.lucutovidiu.mongo.PortfolioDbService;
import com.lucutovidiu.pojo.Portfolio;
import com.lucutovidiu.portfolio.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.UUID;

@Component
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class PortfolioManagerImpl implements PortfolioManager {

    private final PortfolioDbService portfolioDbService;
    private final PortfolioPhotoStorageService portfolioPhotoStorageService;
    private final PhotoConversion photoConversion;

    @Override
    public NewPortfolioResponseDto postPortfolioData(NewPortfolioRequestDto newPortfolioRequest) {
        if (newPortfolioRequest.getProjectStartDate() == null || newPortfolioRequest.getProjectEndDate() == null)
            throw new DateIncorrectException("Project data incorrect!!");
        String generatedId = generateDBId();
        String rootDirectory = portfolioPhotoStorageService.createPortfolioPhotoBaseDir(newPortfolioRequest.getTitle(), generatedId);
        newPortfolioRequest.setId(generatedId);
        newPortfolioRequest.setRootDirectory(rootDirectory);
        portfolioDbService.createPortfolioStructure(newPortfolioRequest);
        return new NewPortfolioResponseDto(generatedId, true);
    }

    @Override
    public NewPortfolioResponseDto editPortfolio(NewPortfolioRequestDto newPortfolioRequest) {
        return editExistingPortfolio(newPortfolioRequest);
    }

    private NewPortfolioResponseDto editExistingPortfolio(NewPortfolioRequestDto newPortfolioRequest) {
        portfolioDbService.editExistingPortfolio(newPortfolioRequest);
        return new NewPortfolioResponseDto(newPortfolioRequest.getId(), true);
    }

    @Override
    public NewPortfolioResponseDto postFile(MultipartFile file, String type, String portfolioId) throws IOException {
        Portfolio portfolio = portfolioDbService.getPortfolioById(portfolioId)
                .orElseThrow(() -> new BaseStructureCreationException("Portfolio id: " + portfolioId + " not found!"));
        switch (FileUploadTypes.valueOf(type)) {
            case THUMBNAIL:
                byte[] imageToJpeg = photoConversion.convertAndScaleImageToJpeg(file.getBytes(), PhotoSize.getIdealThumbnailWidth());
                String thumbImage = portfolioPhotoStorageService.saveThumbImage(imageToJpeg, portfolio.getRootDirectory());
                portfolioDbService.updatePortfolioThumbnailImage(portfolioId, thumbImage);
                break;
            case MORE_IMAGES:
                imageToJpeg = photoConversion.convertAndScaleImageToJpeg(file.getBytes(), PhotoSize.getIdealMediumWidth());
                String imageSrc = portfolioPhotoStorageService.savePortfolioImage(imageToJpeg, portfolio.getRootDirectory());
                portfolioDbService.addPortfolioMoreImages(portfolioId, imageSrc);
                break;
            default:
                throw new RuntimeException("Invalid type entered");
        }
        return new NewPortfolioResponseDto(portfolioId, true);
    }

    @Override
    public boolean deletePortfolio(String portfolioId) {
        Portfolio portfolio = portfolioDbService.getPortfolioById(portfolioId)
                .orElseThrow(() -> new BaseStructureCreationException("Portfolio id: " + portfolioId + " not found!"));
        boolean hasPortfolioBeenDeleted = portfolioDbService.deletePortfolioStructure(portfolioId);
        boolean pathDeleted = portfolioPhotoStorageService.removePortfolioPhotoBaseDir(portfolio.getRootDirectory());
        return hasPortfolioBeenDeleted || pathDeleted;
    }

    private String generateDBId() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
