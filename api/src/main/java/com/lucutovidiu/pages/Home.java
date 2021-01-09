package com.lucutovidiu.pages;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Controller
@RequestMapping("/")
public interface Home {

    @GetMapping("/")
    String getIndex(Model model, HttpServletRequest request);

    @GetMapping(value = "/favicon.ico", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @ResponseBody
    byte[] getFavicon() throws IOException;
}
