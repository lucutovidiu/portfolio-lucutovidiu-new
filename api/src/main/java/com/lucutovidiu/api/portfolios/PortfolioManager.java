package com.lucutovidiu.api.portfolios;

import com.lucutovidiu.portfolio.NewPortfolioRequestDto;
import com.lucutovidiu.portfolio.NewPortfolioResponseDto;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping("/api/portfolio")
public interface PortfolioManager {

    @PostMapping("/upload-data")
    @PreAuthorize("hasRole('ADMIN')")
    NewPortfolioResponseDto postPortfolioData(@RequestBody @Valid NewPortfolioRequestDto newPortfolioRequest);

    @PostMapping("/edit-portfolio")
    @PreAuthorize("hasRole('ADMIN')")
    NewPortfolioResponseDto editPortfolio(@RequestBody @Valid NewPortfolioRequestDto newPortfolioRequest);

    @PostMapping(value = "/upload-file/type/{type}/portfolio-id/{portfolioId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    NewPortfolioResponseDto postFile(@RequestParam("file") MultipartFile file, @PathVariable String type, @PathVariable String portfolioId) throws IOException;

    @DeleteMapping("/delete/{portfolioId}")
    @PreAuthorize("hasRole('ADMIN')")
    boolean deletePortfolio(@PathVariable String portfolioId);
}
