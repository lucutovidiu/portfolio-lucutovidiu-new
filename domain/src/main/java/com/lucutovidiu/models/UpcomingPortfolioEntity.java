package com.lucutovidiu.models;

import com.lucutovidiu.pojo.UpcomingPortfolioBasic;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Getter
@Setter
@Document(collection = "upcomingportfolios")
public class UpcomingPortfolioEntity extends BaseEntity {
    @Indexed(unique = true)
    private String title;
    @NotBlank
    private String shortDescription;
    @NotBlank
    private String technologiesUsed;
    private String httpAccessLink;
    private LocalDate projectStartDate;
    private String repoLink;

    public UpcomingPortfolioBasic toUpcomingPortfolio() {
        return UpcomingPortfolioBasic.builder()
                .title(title)
                .shortDescription(shortDescription)
                .technologiesUsed(technologiesUsed)
                .httpAccessLink(httpAccessLink)
                .repoLink(repoLink)
                .build();
    }
}
