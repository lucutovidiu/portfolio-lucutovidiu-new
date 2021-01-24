package com.lucutovidiu.api.settings;

import com.lucutovidiu.models.PortfolioDataRequestStatus;
import com.lucutovidiu.pojo.PortfolioDataRequest;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PortfoliosDataDto {
    private PortfolioDataRequestStatus requestStatus;
    private String requestId;
    private String zipName;
    private boolean hasRequestFailed;
    private String failMessage;

    public static PortfoliosDataDto convert(PortfolioDataRequest request) {
        return PortfoliosDataDto.builder()
                .requestStatus(request.getRequestStatus())
                .requestId(request.getRequestId())
                .zipName(request.getZipName())
                .hasRequestFailed(request.isRequestFailed())
                .failMessage(request.getFailMessage())
                .build();
    }
}
