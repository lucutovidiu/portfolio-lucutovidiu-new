package com.lucutovidiu.api.settings;

import com.lucutovidiu.mongo.PortfolioDataRequestService;
import com.lucutovidiu.mongo.PortfolioDbService;
import com.lucutovidiu.portfolio.PortfolioPhotoStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.UUID;

import static com.lucutovidiu.models.PortfolioDataRequestStatus.PROCESSING;

@Component
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class PortfolioDataApiImpl implements PortfolioDataApi {

    private final PortfolioDbService portfolioDbService;
    private final PortfolioPhotoStorageService portfolioPhotoStorageService;
    private final PortfolioDataRequestService portfolioDataRequestService;

    @Override
    public PortfoliosDataDto getPortfoliosData(PortfolioDataRequestDto portfolioDataRequestDto) {
        switch (portfolioDataRequestDto.getRequestType()) {
            case CREATE_PORTFOLIO_DATA_REQUEST:
                String requestId = requestPortfolioData();
                return PortfoliosDataDto.builder()
                        .requestStatus(PROCESSING)
                        .requestId(requestId)
                        .build();
            case REQUEST_STATUS:
                return portfolioDataRequestService.getPortfolioDataRequestByRequestId(portfolioDataRequestDto.getRequestId())
                        .map(PortfoliosDataDto::convert)
                        .orElseThrow(() -> new PortfolioRequestTypeInvalidException("Invalid Request id!!!"));
            default:
                throw new PortfolioRequestTypeInvalidException("Invalid Request Type!!!");
        }
    }

    @Override
    public ResponseEntity<byte[]> getPortfolioZip(String requestId, String zipName) {
        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=\"" + requestId + ".zip\"")
                .body(portfolioPhotoStorageService.getZipFile(requestId, zipName));
    }

    @Override
    public boolean clearTempDir() {
        return portfolioPhotoStorageService.clearTempDir() && portfolioDataRequestService.clearTempRequests();
    }

    private String requestPortfolioData() {
        String requestId = UUID.randomUUID().toString();
        portfolioDataRequestService.createPortfolioDataRequest(requestId);
        portfolioPhotoStorageService.createPortfolioRequestBaseDir(requestId, portfolioDbService.getAllPortfoliosFull());
        return requestId;
    }
}
