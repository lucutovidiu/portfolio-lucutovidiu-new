package com.lucutovidiu.api.settings;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PortfolioDataRequestDto {
    private PortfolioDataRequestType requestType;
    private String requestId;
}
