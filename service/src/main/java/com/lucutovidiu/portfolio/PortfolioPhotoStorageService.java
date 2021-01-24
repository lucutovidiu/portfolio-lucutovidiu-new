package com.lucutovidiu.portfolio;

import com.lucutovidiu.pojo.Portfolio;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PortfolioPhotoStorageService {

    String createPortfolioPhotoBaseDir(String portfolioTitle, String portfolioId);

    boolean removePortfolioPhotoBaseDir(String rootDir);

    String saveThumbImage(byte[] image, String rootDir);

    String savePortfolioImage(byte[] image, String rootDir);

    byte[] getImageFromLocalDir(String rootDir, String imgType, String imageName);

    @Async
    void createPortfolioRequestBaseDir(String requestId, List<Portfolio> portfolios);

    byte[] getZipFile(String rootDir, String zipName);

    boolean clearTempDir();
}
