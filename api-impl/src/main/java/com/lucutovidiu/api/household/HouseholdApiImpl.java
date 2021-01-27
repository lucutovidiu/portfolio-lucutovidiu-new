package com.lucutovidiu.api.household;

import com.lucutovidiu.household.dto.HouseholdGroupOnlyDto;
import com.lucutovidiu.household.dto.HouseholdGroupPostResponseDto;
import com.lucutovidiu.household.dto.HouseholdGroupRequestDto;
import com.lucutovidiu.household.dto.HouseholdItemRequestDto;
import com.lucutovidiu.mongo.HouseholdService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class HouseholdApiImpl implements HouseholdApi {

    private final HouseholdService householdService;

    @Override
    public HouseholdGroupPostResponseDto postHouseholdGroup(HouseholdGroupRequestDto householdGroupRequestDto) {
        SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return householdService.addGroup(householdGroupRequestDto, getLoggedUser());
    }

    @Override
    public boolean postHouseholdItem(HouseholdItemRequestDto householdItemRequestDto) {
        return householdService.addItem(householdItemRequestDto);
    }

    @Override
    public List<HouseholdGroupOnlyDto> getHouseholdGroups() {
        return householdService.getGroupsOnly(getLoggedUser());
    }

    @Override
    public boolean deleteGroup(String groupId) {
        return householdService.deleteGroup(groupId, getLoggedUser());
    }

    @Override
    public boolean deleteGroupItem(String groupId, String itemId) {
        return householdService.deleteItemFromGroup(itemId, groupId);
    }

    private String getLoggedUser() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
