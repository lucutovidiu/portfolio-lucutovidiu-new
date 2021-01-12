package com.lucutovidiu.pojo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UpcomingPortfolioBasic {
    private String title;
    private String shortDescription;
    private String technologiesUsed;
    private String httpAccessLink;
    private String repoLink;
}
