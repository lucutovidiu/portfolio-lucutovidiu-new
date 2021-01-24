package com.lucutovidiu.mongo;

import com.lucutovidiu.models.PortfolioDataRequestEntity;
import com.lucutovidiu.models.PortfolioDataRequestStatus;
import com.lucutovidiu.pojo.PortfolioDataRequest;
import com.lucutovidiu.repos.PortfolioDataRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class PortfolioDataRequestServiceImpl implements PortfolioDataRequestService {

    private final PortfolioDataRequestRepository portfolioDataRequestRepository;

    @Override
    public Optional<PortfolioDataRequest> getPortfolioDataRequestByRequestId(String findByRequestId) {
        return portfolioDataRequestRepository.findByRequestId(findByRequestId).map(PortfolioDataRequestEntity::toPortfolioDataRequest);
    }

    @Override
    public PortfolioDataRequest createPortfolioDataRequest(String requestId) {
        return portfolioDataRequestRepository.save(PortfolioDataRequestEntity.createRequest(requestId)).toPortfolioDataRequest();
    }

    @Override
    public void updateRequest(String requestId, String rootDir, String zipName, PortfolioDataRequestStatus requestStatus) {
        portfolioDataRequestRepository.findByRequestId(requestId)
                .map(entity -> {
                    entity.setRootDir(rootDir);
                    entity.setZipName(zipName);
                    entity.setRequestStatus(requestStatus);
                    return entity;
                }).ifPresent(portfolioDataRequestRepository::save);
    }

    @Override
    public void updateFailedRequest(String requestId, String failMessage) {
        portfolioDataRequestRepository.findByRequestId(requestId)
                .map(entity -> {
                    entity.setHasRequestFailed(true);
                    entity.setFailMessage(failMessage);
                    return entity;
                }).ifPresent(portfolioDataRequestRepository::save);
    }

    @Override
    public boolean clearTempRequests() {
        portfolioDataRequestRepository.findAll()
                .forEach(portfolioDataRequestRepository::delete);
        return true;
    }
}
