package com.lucutovidiu.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Getter
@Setter
@Document(collection = "upcomingportfolios")
public class UpcomingPortfolio extends BaseEntity {
    @Indexed(unique = true)
    private String title;
    @NotBlank
    private String shortDescription;
    @NotBlank
    private String technologiesUsed;
    private String httpAccessLink;
    private LocalDate projectStartDate;
    private String repoLink;
}
