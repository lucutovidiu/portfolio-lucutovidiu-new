package com.lucutovidiu.pages;

import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import static com.lucutovidiu.util.PageAttributesUtil.ActivePage;
import static com.lucutovidiu.util.Pages.LOGIN;

@Component
public class LoginImpl implements Login {
    @Override
    public String getLoginPage(Model model) {
        model.addAttribute(ActivePage, LOGIN);
        return "login/login";
    }
}
