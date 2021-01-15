package com.lucutovidiu.portfolio;

import com.lucutovidiu.util.SlugUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.FileSystemUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import static com.lucutovidiu.portfolio.FileUploadTypes.THUMBNAIL;

@Component
@Slf4j
public class PortfolioPhotoManagerImpl implements PortfolioPhotoManager {
    private static final String OUTPUT_DIR_BASE = "api-impl/src/main/resources/static".replaceAll("/", File.separator);
    private static final String WEB_BROWSER_PATH_BASE = "/img/portfolios".replaceAll("/", File.separator);
    private static final String PORTFOLIO_IMAGES_BASE = OUTPUT_DIR_BASE + WEB_BROWSER_PATH_BASE;


    @Override
    public String createPortfolioPhotoBaseDir(String portfolioTitle, String portfolioId) {
        String slug = generatePortfolioBaseDirName(portfolioTitle, portfolioId);
        String baseDir = PORTFOLIO_IMAGES_BASE + File.separator + slug;
        createDirectory(Paths.get(baseDir));
        String thumbImgDir = baseDir + File.separator + THUMBNAIL;
        createDirectory(Paths.get(thumbImgDir));
        return slug;
    }

    @Override
    public boolean removePortfolioPhotoBaseDir(String rootDir) {
        return removeFileRecursively(PORTFOLIO_IMAGES_BASE + File.separator + rootDir);
    }

    @Override
    public String saveThumbImage(byte[] image, String rootDir) {
        String generatedImageName = generateImageName();
        String getThumbPath = PORTFOLIO_IMAGES_BASE + File.separator + rootDir + File.separator + THUMBNAIL + File.separator + generatedImageName;
        writeImageToDisk(getThumbPath, image);
        return generatedImageName;
    }

    @Override
    public String savePortfolioImage(byte[] image, String rootDir) {
        String generatedImageName = generateImageName();
        String getThumbPath = PORTFOLIO_IMAGES_BASE + File.separator + rootDir + File.separator + generatedImageName;
        writeImageToDisk(getThumbPath, image);
        return generatedImageName;
    }

    private String generateImageName() {
        return UUID.randomUUID().toString().replaceAll("-", "") + ".jpeg";
    }

    @Override
    public byte[] getImageFromLocalDir(String rootDir, String imgType, String imageName) {
        try {
            Path imagePath;
            if (FileUploadTypes.valueOf(imgType) == THUMBNAIL) {
                imagePath = Paths.get(PORTFOLIO_IMAGES_BASE + File.separator + rootDir + File.separator + THUMBNAIL.toString() + File.separator + imageName);
            } else {
                imagePath = Paths.get(PORTFOLIO_IMAGES_BASE + File.separator + rootDir + File.separator + imageName);
            }
            return Files.readAllBytes(imagePath);
        } catch (IOException e) {
            return new byte[0];
        }
    }

    private boolean removeFileRecursively(String fileName) {
        try {
            return FileSystemUtils.deleteRecursively(Paths.get(fileName));
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new BaseStructureCreationException(String.format("Couldn't remove file path: %s", fileName));
        }
    }

    private void writeImageToDisk(String imagePath, byte[] image) {
        try {
            Path thumbnailPath = Paths.get(imagePath);
            createIfNotExistsFile(thumbnailPath);
            Files.write(thumbnailPath, image);
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new BaseStructureCreationException(String.format("Couldn't save file path: %s", imagePath));
        }
    }

    private String generatePortfolioBaseDirName(String portfolioTitle, String portfolioId) {
        String slug = SlugUtil.generateSlug(portfolioTitle + "-" + portfolioId);
        return slug.length() > 55 ? slug.substring(0, 55) : slug;
    }

    private void createDirectory(Path filePath) {
        try {
            Files.createDirectory(filePath);
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new BaseStructureCreationException(e.getMessage());
        }
    }

    private void createIfNotExistsFile(Path thumbnailPath) {
        try {
            if (!thumbnailPath.toFile().exists()) {
                Files.createFile(thumbnailPath);
            }
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new BaseStructureCreationException(e.getMessage());
        }
    }
}
