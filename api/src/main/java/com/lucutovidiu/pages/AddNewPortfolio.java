package com.lucutovidiu.pages;

import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("/add-new-portfolio")
public interface AddNewPortfolio {

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    String getCreateNewPortfolio(Model model);

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    String loadFromJson(@RequestParam("jsonProject") MultipartFile jsonProject, Model model) throws IOException;
}
