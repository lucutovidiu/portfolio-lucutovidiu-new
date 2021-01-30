package com.lucutovidiu.pages;

import com.lucutovidiu.models.UserRole;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import static com.lucutovidiu.util.PageAttributesUtil.UserRoles;

@Component
public class UsersImpl implements Users {

    @Override
    public String addUser(Model model) {
        model.addAttribute(UserRoles, UserRole.getUserRolesAsList());
        return "users/user-management";
    }
}
