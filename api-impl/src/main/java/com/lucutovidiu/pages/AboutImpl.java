package com.lucutovidiu.pages;

import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import static com.lucutovidiu.util.PageAttributesUtil.ActivePage;
import static com.lucutovidiu.util.Pages.ABOUT;

@Component
public class AboutImpl implements About {
    @Override
    public String getAboutPage(Model model) {
        model.addAttribute(ActivePage, ABOUT);
        return "about/about";
    }
}
