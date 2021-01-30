package com.lucutovidiu.household.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class HouseholdGroupDto {
    private String id;
    private String groupName;
    private String createdBy;
    private List<HouseholdItemDto> householdItems;
}
