package com.lucutovidiu.portfolio.dto;

import com.lucutovidiu.pojo.ImageDescription;
import com.lucutovidiu.pojo.Portfolio;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PortfolioDto {
    private String id;
    private String title;
    private String thumbImage;
    private String shortDescription;
    private String technologiesUsed;
    private List<ImageDescription> moreImages;
    private String fullDescription;
    private String projectStartDate;
    private String projectEndDate;
    private String httpAccessLink;
    private String repoLink;
    private String rootDirectory;
    private long projectDuration;

    public static PortfolioDto toPortfolioDto(Portfolio portfolio) {
        PortfolioDto dto = new PortfolioDto();
        dto.setId(portfolio.getId());
        dto.setTitle(portfolio.getTitle());
        dto.setThumbImage(portfolio.getThumbImage());
        dto.setShortDescription(portfolio.getShortDescription());
        dto.setTechnologiesUsed(portfolio.getTechnologiesUsed());
        dto.setFullDescription(portfolio.getFullDescription());
        dto.setProjectStartDate(portfolio.getProjectStartDate().toString());
        dto.setProjectEndDate(portfolio.getProjectEndDate().toString());
        dto.setHttpAccessLink(portfolio.getHttpAccessLink());
        dto.setRepoLink(portfolio.getRepoLink());
        dto.setProjectDuration(portfolio.getProjectDuration());
        return dto;
    }
}
