package com.lucutovidiu.portfolio;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class NewPortfolioResponseDto {
    private String portfolioId;
    private boolean succeed;
}
