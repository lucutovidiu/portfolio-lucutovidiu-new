package com.lucutovidiu.mongo;

import com.lucutovidiu.household.dto.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface HouseholdService {
    HouseholdGroupPostResponseDto addGroup(HouseholdGroupRequestDto householdGroupRequestDto, String loggerUser);

    List<HouseholdGroupOnlyDto> getGroupsOnly(String loggerUser);

    List<HouseholdGroupDto> getGroups(String loggerUser);

    Optional<HouseholdGroupDto> getGroup(String groupName, String loggerUser);

    List<HouseholdGroupDto> getAllGroups();

    boolean addItem(HouseholdItemRequestDto householdItemRequestDto);

    boolean deleteGroup(String groupId, String loggerUser);

    boolean deleteItemFromGroup(String itemId, String groupId);

    void updateLastNotification(String groupId, String itemId);
}
