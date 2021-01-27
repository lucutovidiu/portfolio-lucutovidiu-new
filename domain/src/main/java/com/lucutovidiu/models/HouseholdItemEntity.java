package com.lucutovidiu.models;

import com.lucutovidiu.household.dto.HouseholdItemDto;
import com.lucutovidiu.household.dto.HouseholdItemRequestDto;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Getter
@Setter
@Document(collection = "household_item")
public class HouseholdItemEntity extends BaseEntity {

    @NotBlank
    private String itemName;
    private LocalDate itemStartFromDate;
    @NotBlank
    private LocalDate itemExpirationDate;
    private String moreInfo;

    public static HouseholdItemEntity convert(HouseholdItemRequestDto dto) {
        HouseholdItemEntity entity = new HouseholdItemEntity();
        entity.setItemName(dto.getItemName());
        entity.setItemStartFromDate(LocalDate.parse(dto.getItemStartFromDate()));
        entity.setItemExpirationDate(LocalDate.parse(dto.getItemExpirationDate()));
        entity.setMoreInfo(dto.getMoreInfo());
        return entity;
    }

    public HouseholdItemDto getHouseholdItemDto() {
        HouseholdItemDto dto = new HouseholdItemDto();
        dto.setId(getId());
        dto.setItemName(itemName);
        dto.setItemStartFromDate(itemStartFromDate);
        dto.setItemExpirationDate(itemExpirationDate);
        dto.setMoreInfo(moreInfo);
        return dto;
    }


}
