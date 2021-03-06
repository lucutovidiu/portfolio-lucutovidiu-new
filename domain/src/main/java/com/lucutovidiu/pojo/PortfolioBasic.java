package com.lucutovidiu.pojo;

import com.lucutovidiu.util.SlugUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Getter
@Setter
@SuperBuilder
public class PortfolioBasic {
    private String id;
    private String title;
    private String thumbImage;
    private String shortDescription;
    private String technologiesUsed;
    private String rootDirectory;

    public PortfolioBasic() {
    }

    public String slugifyTitle() {
        return SlugUtil.generateSlug(title);
    }
}
