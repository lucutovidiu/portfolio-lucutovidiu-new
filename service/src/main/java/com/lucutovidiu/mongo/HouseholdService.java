package com.lucutovidiu.mongo;

import com.lucutovidiu.household.dto.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface HouseholdService {
    HouseholdGroupPostResponseDto addGroup(HouseholdGroupRequestDto householdGroupRequestDto, String loggerUser);

    List<HouseholdGroupOnlyDto> getGroupsOnly(String loggerUser);

    List<HouseholdGroupDto> getGroups(String loggerUser);

    List<HouseholdGroupDto> getAllGroups();

    boolean addItem(HouseholdItemRequestDto householdItemRequestDto);

    boolean deleteGroup(String groupId, String loggerUser);

    boolean deleteItemFromGroup(String itemId, String groupId);

    void updateLastNotification(String groupId, String itemId);
}
