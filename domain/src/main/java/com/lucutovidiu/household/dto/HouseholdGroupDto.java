package com.lucutovidiu.household.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class HouseholdGroupDto {
    private String id;
    private String groupName;
    private List<HouseholdItemDto> householdItems;
}
