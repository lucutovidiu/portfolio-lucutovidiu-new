package com.lucutovidiu.api.email;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/email")
public interface ContactApi {

    @PostMapping("/contact")
    boolean sendContactEmail(@RequestBody @Valid ContactRequest request);
}
