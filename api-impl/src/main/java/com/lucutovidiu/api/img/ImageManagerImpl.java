package com.lucutovidiu.api.img;

import com.lucutovidiu.portfolio.PortfolioPhotoManager;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ImageManagerImpl implements ImageManager {

    private final PortfolioPhotoManager portfolioPhotoManager;

    @Override
    public byte[] getImage(String rootDir, String imgType, String imageName) {
        return portfolioPhotoManager.getImageFromLocalDir(rootDir, imgType, imageName);
    }
}
