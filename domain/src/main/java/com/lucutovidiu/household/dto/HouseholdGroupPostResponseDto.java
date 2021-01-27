package com.lucutovidiu.household.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HouseholdGroupPostResponseDto {
    private String id;
    private String groupName;
    private boolean succeeded;
    private String errorMsg;
}
