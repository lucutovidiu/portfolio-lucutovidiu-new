package com.lucutovidiu.api.household;

import com.lucutovidiu.household.dto.HouseholdGroupOnlyDto;
import com.lucutovidiu.household.dto.HouseholdGroupPostResponseDto;
import com.lucutovidiu.household.dto.HouseholdGroupRequestDto;
import com.lucutovidiu.household.dto.HouseholdItemRequestDto;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/household")
public interface HouseholdApi {

    @PostMapping("/group")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('PRIVATE')")
    HouseholdGroupPostResponseDto postHouseholdGroup(HouseholdGroupRequestDto householdGroupRequestDto);

    @PostMapping("/item")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('PRIVATE')")
    boolean postHouseholdItem(HouseholdItemRequestDto householdItemRequestDto);

    @GetMapping("/groups")
    @PreAuthorize("hasRole('PRIVATE')")
    List<HouseholdGroupOnlyDto> getHouseholdGroups();

    @DeleteMapping("/group/{groupId}")
    @PreAuthorize("hasRole('PRIVATE')")
    boolean deleteGroup(@PathVariable String groupId);

    @DeleteMapping("/group/{groupId}/item/{itemId}")
    @PreAuthorize("hasRole('PRIVATE')")
    boolean deleteGroupItem(@PathVariable String groupId, @PathVariable String itemId);

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/sync-holiday")
    @PreAuthorize("hasRole('PRIVATE')")
    void syncHolidays();
}
