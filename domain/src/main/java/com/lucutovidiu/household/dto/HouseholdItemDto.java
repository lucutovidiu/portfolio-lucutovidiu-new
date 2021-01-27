package com.lucutovidiu.household.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class HouseholdItemDto {
    private String id;
    private String itemName;
    private LocalDate itemStartFromDate;
    private LocalDate itemExpirationDate;
    private String moreInfo;

    public boolean isExpirationDateDue(){
        return itemExpirationDate.minusMonths(1).isBefore(LocalDate.now());
    }
}
