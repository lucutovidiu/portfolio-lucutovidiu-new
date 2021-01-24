package com.lucutovidiu.models;

import com.lucutovidiu.pojo.PortfolioDataRequest;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;

import static com.lucutovidiu.models.PortfolioDataRequestStatus.PROCESSING;

@Getter
@Setter
@Document(collection = "portfolio_data_request")
public class PortfolioDataRequestEntity extends BaseEntity {
    @NotBlank
    @Indexed(unique = true)
    private String requestId;
    @NotBlank
    private PortfolioDataRequestStatus requestStatus;
    @NotBlank
    private String rootDir;
    private String zipName;
    @NotBlank
    private boolean hasRequestFailed = false;
    private String failMessage;

    public static PortfolioDataRequestEntity createRequest(String requestId) {
        PortfolioDataRequestEntity entity = new PortfolioDataRequestEntity();
        entity.setRequestId(requestId);
        entity.setRequestStatus(PROCESSING);
        return entity;
    }

    public PortfolioDataRequest toPortfolioDataRequest() {
        return PortfolioDataRequest.builder()
                .requestId(requestId)
                .requestStatus(requestStatus)
                .zipName(zipName)
                .rootDir(rootDir)
                .requestFailed(hasRequestFailed)
                .failMessage(failMessage)
                .build();
    }
}
