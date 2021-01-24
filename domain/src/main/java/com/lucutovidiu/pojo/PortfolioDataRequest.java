package com.lucutovidiu.pojo;

import com.lucutovidiu.models.PortfolioDataRequestStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PortfolioDataRequest {
    private String requestId;
    private PortfolioDataRequestStatus requestStatus;
    private String zipName;
    private String rootDir;
    private boolean requestFailed;
    private String failMessage;
}
