package com.lucutovidiu.household.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HouseholdItemRequestDto {
    private String householdGroupId;
    private String itemName;
    private String itemStartFromDate;
    private String itemExpirationDate;
    private String moreInfo;
}
