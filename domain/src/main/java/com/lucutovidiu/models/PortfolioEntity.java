package com.lucutovidiu.models;

import com.lucutovidiu.pojo.ImageDescription;
import com.lucutovidiu.pojo.PortfolioBasic;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
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
    @NotBlank
    private String thumbImage;
    @NotEmpty
    private List<ImageDescription> moreImages;
    @NotBlank
    private String fullDescription;
    private LocalDate projectStartDate;
    private LocalDate projectEndDate;
    private String httpAccessLink;
    private String repoLink;
    private String rootDirectory;

    public PortfolioBasic toPortfolio() {
        return PortfolioBasic.builder()
                .id(getId())
                .title(title)
                .thumbImage(thumbImage)
                .shortDescription(shortDescription)
                .technologiesUsed(technologiesUsed)
                .build();
    }
}
