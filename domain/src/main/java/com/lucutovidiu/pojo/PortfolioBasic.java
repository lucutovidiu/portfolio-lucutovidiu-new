package com.lucutovidiu.pojo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PortfolioBasic {
    private String id;
    private String title;
    private String thumbImage;
    private String shortDescription;
    private String technologiesUsed;
}
