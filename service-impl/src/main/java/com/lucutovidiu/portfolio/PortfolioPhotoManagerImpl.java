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

@Component
@Slf4j
public class PortfolioPhotoManagerImpl implements PortfolioPhotoManager {
    public static final String THUMB_IMG = "thumb-img";
    private static final String OUTPUT_DIR_BASE = "api-impl/src/main/resources/static".replaceAll("/", File.separator);
    private static final String WEB_BROWSER_PATH_BASE = "/img/portfolios".replaceAll("/", File.separator);
    private static final String PORTFOLIO_IMAGES_BASE = OUTPUT_DIR_BASE + WEB_BROWSER_PATH_BASE;


    @Override
    public String createPortfolioPhotoBaseDir(String portfolioTitle, String portfolioId) {
        String slug = generatePortfolioBaseDirName(portfolioTitle, portfolioId);
        String baseDir = PORTFOLIO_IMAGES_BASE + File.separator + slug;
        createDirectory(Paths.get(baseDir));
        String thumbImgDir = baseDir + File.separator + THUMB_IMG;
        createDirectory(Paths.get(thumbImgDir));
        return baseDir;
    }

    @Override
    public boolean removePortfolioPhotoBaseDir(String rootDir) {
        return removeFileRecursively(rootDir);
    }

    @Override
    public String saveThumbImage(byte[] image, String fileName, String rootDir) {
        String getThumbPath = rootDir + File.separator + THUMB_IMG + File.separator + fileName;
        writeImageToDisk(getThumbPath, image);
        return getThumbPath.replace(OUTPUT_DIR_BASE, "");
    }

    @Override
    public String savePortfolioImage(byte[] image, String contentType, String rootDir) {
        String generatedImageName = UUID.randomUUID().toString().replaceAll("-", "");
        String getThumbPath = rootDir + File.separator + generatedImageName + "." + tryGetContentType(contentType);
        writeImageToDisk(getThumbPath, image);
        return getThumbPath.replace(OUTPUT_DIR_BASE, "");
    }

    private boolean removeFileRecursively(String fileName) {
        try {
            return FileSystemUtils.deleteRecursively(Paths.get(fileName));
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new BaseStructureCreationException(String.format("Couldn't remove file path: %s", fileName));
        }
    }

    private String tryGetContentType(String contentType) {
        String[] typeContent = contentType.split("/");
        if (typeContent.length > 0) return typeContent[1];
        throw new BaseStructureCreationException(String.format("Image Format Error: %s", contentType));
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
