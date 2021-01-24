package com.lucutovidiu.api.settings;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/get-portfolio-data")
public interface PortfolioDataApi {

    @PostMapping(value = "/request-zip", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    PortfoliosDataDto getPortfoliosData(@RequestBody PortfolioDataRequestDto portfolioDataRequestDto);

    @GetMapping(value = "/{requestId}/{zipName}", produces = "application/zip")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<byte[]> getPortfolioZip(@PathVariable String requestId, @PathVariable String zipName);

    @DeleteMapping("/clear-temp-dir")
    @PreAuthorize("hasRole('ADMIN')")
    boolean clearTempDir();
}
