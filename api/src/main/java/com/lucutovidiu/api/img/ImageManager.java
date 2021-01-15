package com.lucutovidiu.api.img;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/img")
public interface ImageManager {

    @GetMapping("/{rootDir}/{imgType}/{imageName}")
    byte[] getImage(@PathVariable String rootDir, @PathVariable String imgType, @PathVariable String imageName);
}
