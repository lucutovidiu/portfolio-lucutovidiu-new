package com.lucutovidiu.mongo;

import com.lucutovidiu.models.PortfolioDataRequestStatus;
import com.lucutovidiu.pojo.PortfolioDataRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface PortfolioDataRequestService {

    Optional<PortfolioDataRequest> getPortfolioDataRequestByRequestId(String findByRequestId);

    PortfolioDataRequest createPortfolioDataRequest(String requestId);

    void updateRequest(String requestId, String rootDir, String zipName, PortfolioDataRequestStatus requestStatus);

    void updateFailedRequest(String requestId, String failMessage);

    boolean clearTempRequests();
}
