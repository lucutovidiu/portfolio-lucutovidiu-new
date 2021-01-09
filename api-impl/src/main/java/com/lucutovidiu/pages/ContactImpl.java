package com.lucutovidiu.pages;

import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import static com.lucutovidiu.util.PageAttributesUtil.ActivePage;
import static com.lucutovidiu.util.Pages.CONTACT;

@Component
public class ContactImpl implements Contact {

    @Override
    public String getContactPage(Model model) {
        model.addAttribute(ActivePage, CONTACT);
        return "contact/contact";
    }
}
