package com.lucutovidiu.portfolio;

import org.springframework.stereotype.Service;

@Service
public interface PortfolioPhotoManager {

    String createPortfolioPhotoBaseDir(String portfolioTitle, String portfolioId);

    boolean removePortfolioPhotoBaseDir(String rootDir);

    String saveThumbImage(byte[] image, String fileName, String rootDir);

    String savePortfolioImage(byte[] image, String contentType, String rootDir);
}
