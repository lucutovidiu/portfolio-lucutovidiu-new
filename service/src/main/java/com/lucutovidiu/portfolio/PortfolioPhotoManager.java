package com.lucutovidiu.portfolio;

import org.springframework.stereotype.Service;

@Service
public interface PortfolioPhotoManager {

    String createPortfolioPhotoBaseDir(String portfolioTitle, String portfolioId);

    boolean removePortfolioPhotoBaseDir(String rootDir);

    String saveThumbImage(byte[] image, String rootDir);

    String savePortfolioImage(byte[] image, String rootDir);

    byte[] getImageFromLocalDir(String rootDir, String imgType, String imageName);
}
