package com.lucutovidiu.portfolio;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Getter
@Setter
@ToString
public class NewPortfolioRequestDto {
    @JsonIgnore
    private String id;
    @JsonIgnore
    private String rootDirectory;
    @NotBlank
    private String title;
    @NotBlank
    private String shortDescription;
    @NotBlank
    private String technologiesUsed;
    @NotBlank
    private String fullDescription;
    private LocalDate projectStartDate;
    private LocalDate projectEndDate;
    private String httpAccessLink;
    private String repoLink;
}
