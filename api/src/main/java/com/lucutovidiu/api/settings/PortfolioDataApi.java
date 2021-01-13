package com.lucutovidiu.api.settings;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/get-portfolio-data")
public interface PortfolioDataApi {

    @GetMapping("/get-portfolio-data")
    @PreAuthorize("hasRole('ADMIN')")
    PortfoliosDataDto getPortfoliosData();

}
