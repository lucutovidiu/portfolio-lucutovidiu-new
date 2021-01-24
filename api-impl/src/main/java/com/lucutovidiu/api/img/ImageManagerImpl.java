package com.lucutovidiu.api.img;

import com.lucutovidiu.portfolio.PortfolioPhotoStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ImageManagerImpl implements ImageManager {

    private final PortfolioPhotoStorageService portfolioPhotoStorageService;

    @Override
    public byte[] getImage(String rootDir, String imgType, String imageName) {
        return portfolioPhotoStorageService.getImageFromLocalDir(rootDir, imgType, imageName);
    }
}
