package com.lucutovidiu.pages;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/news")
public interface News {

    @GetMapping
    String getNews(Model model);

    @GetMapping("/bank-holiday/expire-cache")
    @ResponseBody
    void expireBankHolidays();
}
