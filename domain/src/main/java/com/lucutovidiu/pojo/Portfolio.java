package com.lucutovidiu.pojo;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Getter
@Setter
@SuperBuilder
public class Portfolio extends PortfolioBasic {
    private List<ImageDescription> moreImages;
    private String fullDescription;
    private LocalDate projectStartDate;
    private LocalDate projectEndDate;
    private String httpAccessLink;
    private String repoLink;
    private String rootDirectory;

    public long getProjectDuration() {
        return ChronoUnit.WEEKS.between(projectStartDate, projectEndDate);
    }

    public String getProjectStartDate() {
        return projectStartDate.format(DateTimeFormatter.ofPattern("dd-MMM-yy"));
    }

    public String getProjectEndDate() {
        return projectEndDate.format(DateTimeFormatter.ofPattern("dd-MMM-yy"));
    }
}
