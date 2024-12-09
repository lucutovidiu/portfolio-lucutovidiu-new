package com.lucutovidiu.models;

import com.lucutovidiu.pojo.ImageDescription;
import com.lucutovidiu.pojo.Portfolio;
import com.lucutovidiu.pojo.PortfolioBasic;
import com.lucutovidiu.portfolio.NewPortfolioRequestDto;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Document(collection = "portfolios")
public class PortfolioEntity extends BaseEntity {
    @Indexed(unique = true)
    @NotBlank
    private String title;
    @NotBlank
    private String shortDescription;
    @NotBlank
    private String technologiesUsed;
    private String thumbImage;
    private List<ImageDescription> moreImages = new ArrayList<>();
    @NotBlank
    private String fullDescription;
    private LocalDate projectStartDate;
    private LocalDate projectEndDate;
    private String httpAccessLink;
    private String repoLink;
    private String rootDirectory;

    public static PortfolioEntity create(NewPortfolioRequestDto dto) {
        PortfolioEntity entity = new PortfolioEntity();
        entity.setId(dto.getId());
        entity.setTitle(dto.getTitle());
        entity.setShortDescription(dto.getShortDescription());
        entity.setTechnologiesUsed(dto.getTechnologiesUsed());
        entity.setFullDescription(dto.getFullDescription());
        entity.setProjectStartDate(dto.getProjectStartDate());
        entity.setProjectEndDate(dto.getProjectEndDate());
        entity.setHttpAccessLink(dto.getHttpAccessLink());
        entity.setRepoLink(dto.getRepoLink());
        entity.setRootDirectory(dto.getRootDirectory());
        return entity;
    }

    public PortfolioEntity update(NewPortfolioRequestDto dto) {
        title = dto.getTitle();
        shortDescription = dto.getShortDescription();
        technologiesUsed = dto.getTechnologiesUsed();
        fullDescription = dto.getFullDescription();
        projectStartDate = dto.getProjectStartDate();
        projectEndDate = dto.getProjectEndDate();
        httpAccessLink = dto.getHttpAccessLink();
        repoLink = dto.getRepoLink();
        return this;
    }

    public PortfolioBasic toPortfolioBasic() {
        return PortfolioBasic.builder()
                .id(getId())
                .title(title)
                .thumbImage(thumbImage)
                .shortDescription(shortDescription)
                .technologiesUsed(technologiesUsed)
                .rootDirectory(rootDirectory)
                .httpAccessLink(httpAccessLink)
                .repoLink(repoLink)
                .build();
    }

    public Portfolio toPortfolio() {
        return Portfolio.builder()
                .id(getId())
                .title(title)
                .thumbImage(thumbImage)
                .shortDescription(shortDescription)
                .technologiesUsed(technologiesUsed)
                .moreImages(moreImages)
                .fullDescription(fullDescription)
                .projectStartDate(projectStartDate)
                .projectEndDate(projectEndDate)
                .httpAccessLink(httpAccessLink)
                .repoLink(repoLink)
                .rootDirectory(rootDirectory)
                .build();
    }
}
