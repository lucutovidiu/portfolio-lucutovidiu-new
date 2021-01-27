package com.lucutovidiu.portfolio;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lucutovidiu.conversions.util.ZipUtil;
import com.lucutovidiu.portfolio.dto.PortfolioDto;
import com.lucutovidiu.mongo.PortfolioDataRequestService;
import com.lucutovidiu.pojo.ImageDescription;
import com.lucutovidiu.pojo.Portfolio;
import com.lucutovidiu.util.SlugUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.FileSystemUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.lucutovidiu.models.PortfolioDataRequestStatus.DONE;
import static com.lucutovidiu.portfolio.FileUploadTypes.THUMBNAIL;

@Component
@Slf4j
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class PortfolioPhotoStorageServiceImpl implements PortfolioPhotoStorageService {
    public static final String PORTFOLIO_IMAGES = "portfolioImages";
    public static final String JSON_DIR = "json";
    private static final String OUTPUT_DIR_BASE = "api-impl/src/main/resources/static".replaceAll("/", File.separator);
    private static final String WEB_BROWSER_PATH_BASE = "/img/portfolios".replaceAll("/", File.separator);
    private static final String PORTFOLIO_IMAGES_BASE = OUTPUT_DIR_BASE + WEB_BROWSER_PATH_BASE;
    private static final String TMP_DIR = OUTPUT_DIR_BASE + File.separator + "tmp";
    private final PortfolioDataRequestService portfolioDataRequestService;

    @Override
    public String createPortfolioPhotoBaseDir(String portfolioTitle, String portfolioId) {
        try {
            String slug = generatePortfolioBaseDirName(portfolioTitle, portfolioId);
            String baseDir = PORTFOLIO_IMAGES_BASE + File.separator + slug;
            createDirectory(Paths.get(baseDir));
            String thumbImgDir = baseDir + File.separator + THUMBNAIL;
            createDirectory(Paths.get(thumbImgDir));
            return slug;
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new BaseStructureCreationException(e.getMessage());
        }
    }

    @Override
    public boolean removePortfolioPhotoBaseDir(String rootDir) {
        return removeFileRecursively(combinePaths(PORTFOLIO_IMAGES_BASE, rootDir));
    }

    @Override
    public String saveThumbImage(byte[] image, String rootDir) {
        String generatedImageName = generateImageName();
        String thumbNailBaseDir = combinePaths(PORTFOLIO_IMAGES_BASE, rootDir, THUMBNAIL.toString());
        String getThumbPath = combinePaths(thumbNailBaseDir, generatedImageName);
        cleanDirectory(thumbNailBaseDir);
        writeFileToDisk(getThumbPath, image);
        return generatedImageName;
    }

    @Override
    public String savePortfolioImage(byte[] image, String rootDir) {
        String generatedImageName = generateImageName();
        String getThumbPath = combinePaths(PORTFOLIO_IMAGES_BASE, rootDir, generatedImageName);
        writeFileToDisk(getThumbPath, image);
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
                imagePath = Paths.get(combinePaths(PORTFOLIO_IMAGES_BASE, rootDir, THUMBNAIL.toString(), imageName));
            } else {
                imagePath = Paths.get(combinePaths(PORTFOLIO_IMAGES_BASE, rootDir, imageName));
            }
            return Files.readAllBytes(imagePath);
        } catch (IOException e) {
            return new byte[0];
        }
    }

    @Override
//    @Retryable
    public void createPortfolioRequestBaseDir(String requestId, List<Portfolio> portfolios) {
        String tempBaseFolderStructure = null;
        String zipFolderPath = null;
        try {
            tempBaseFolderStructure = createFolderStructure(requestId);
            copyPortfolioDataToTempDir(tempBaseFolderStructure, portfolios);
            zipFolderPath = tempBaseFolderStructure + ".zip";
            ZipUtil.zipFolder(tempBaseFolderStructure, zipFolderPath);
            FileUtils.copyFileToDirectory(new File(zipFolderPath), new File(tempBaseFolderStructure));
            String zipName = requestId + ".zip";
            portfolioDataRequestService.updateRequest(requestId, tempBaseFolderStructure, zipName, DONE);
        } catch (IOException e) {
            e.printStackTrace();
            portfolioDataRequestService.updateFailedRequest(requestId, e.getMessage());
            if (tempBaseFolderStructure != null) removeFileRecursively(tempBaseFolderStructure);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (zipFolderPath != null) removeFileRecursively(zipFolderPath);
        }
    }

    @Override
    public byte[] getZipFile(String rootDir, String zipName) {
        try {
            return Files.readAllBytes(Paths.get(combinePaths(TMP_DIR, rootDir, zipName)));
        } catch (IOException e) {
            return new byte[0];
        }
    }

    @Override
    public boolean clearTempDir() {
        for (File file : Objects.requireNonNull(new File(TMP_DIR).listFiles())) {
            if (file.isDirectory()) {
                removeFileRecursively(file.toString());
            }
        }
        return true;
    }

    private void cleanDirectory(String dirPath) {
        for (File file : Objects.requireNonNull(new File(dirPath).listFiles())) {
            removeFileRecursively(file.toString());
        }
    }

    private String combinePaths(String... paths) {
        return Arrays.stream(paths).collect(Collectors.joining(File.separator));
    }

    private String createFolderStructure(String requestId) throws IOException {
        return Files.createDirectory(Paths.get(combinePaths(TMP_DIR, requestId))).toString();
    }

    private void copyPortfolioDataToTempDir(String tempDir, List<Portfolio> portfolios) throws IOException {
        for (Portfolio portfolio : portfolios) {
            String portfolioDir = tempDir + File.separator + portfolio.getRootDirectory();
            createDirectory(Paths.get(portfolioDir));
            String destThumbnailImage = combinePaths(portfolioDir, THUMBNAIL.toString());
            createDirectory(Paths.get(destThumbnailImage));
            String sourceThumbnailImage = combinePaths(PORTFOLIO_IMAGES_BASE, portfolio.getRootDirectory(), THUMBNAIL.toString(), portfolio.getThumbImage());
            FileUtils.copyFileToDirectory(new File(sourceThumbnailImage), new File(destThumbnailImage));
            for (ImageDescription image : portfolio.getMoreImages()) {
                String destMorePhotos = combinePaths(portfolioDir, PORTFOLIO_IMAGES);
                createDirectory(Paths.get(destMorePhotos));
                String sourcePortfolioBaseImage = combinePaths(PORTFOLIO_IMAGES_BASE, portfolio.getRootDirectory(), image.getImageSrc());
                FileUtils.copyFileToDirectory(new File(sourcePortfolioBaseImage), new File(destMorePhotos));
            }
            createJsonFileInRootTempDir(portfolio, portfolioDir);
        }
    }

    private void createJsonFileInRootTempDir(Portfolio portfolio, String tempProjectDir) throws IOException {
        String destJsonDir = combinePaths(tempProjectDir, JSON_DIR);
        createDirectory(Paths.get(destJsonDir));
        ObjectMapper mapper = new ObjectMapper();
        String jsonFilePath = combinePaths(destJsonDir, portfolio.slugifyTitle() + ".json");
        String portfolioJsonData = mapper
                .writerWithDefaultPrettyPrinter()
                .writeValueAsString(PortfolioDto.toPortfolioDto(portfolio));
        writeFileToDisk(jsonFilePath, portfolioJsonData.getBytes());
    }

    private boolean removeFileRecursively(String dir) {
        try {
            return FileSystemUtils.deleteRecursively(Paths.get(dir));
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new BaseStructureCreationException(String.format("Couldn't remove file path: %s", dir));
        }
    }

    private void writeFileToDisk(String filePath, byte[] bytes) {
        try {
            Path thumbnailPath = Paths.get(filePath);
            createIfNotExistsFile(thumbnailPath);
            Files.write(thumbnailPath, bytes);
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new BaseStructureCreationException(String.format("Couldn't save file path: %s", filePath));
        }
    }

    private String generatePortfolioBaseDirName(String portfolioTitle, String portfolioId) {
        String slug = SlugUtil.generateSlug(portfolioTitle + "-" + portfolioId);
        return slug.length() > 55 ? slug.substring(0, 55) : slug;
    }

    private void createDirectory(Path filePath) throws IOException {
        if (!filePath.toFile().exists()) Files.createDirectory(filePath);
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
