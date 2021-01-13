package com.lucutovidiu.pojo;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class PortfolioBasic {
    private String id;
    private String title;
    private String thumbImage;
    private String shortDescription;
    private String technologiesUsed;
}
