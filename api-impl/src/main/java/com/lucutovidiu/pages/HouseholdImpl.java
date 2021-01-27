package com.lucutovidiu.pages;

import com.lucutovidiu.mongo.HouseholdService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import static com.lucutovidiu.util.PageAttributesUtil.HouseholdGroups;

@Component
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class HouseholdImpl implements Household {

    private final HouseholdService householdService;

    @Override
    public String getHouseholdPage() {
        return "household/household";
    }

    @Override
    public String getHouseholdViewPage(Model model) {
        model.addAttribute(HouseholdGroups, householdService.getGroups(getLoggedUser()));
        return "household/household_view_page";
    }

    private String getLoggedUser() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
