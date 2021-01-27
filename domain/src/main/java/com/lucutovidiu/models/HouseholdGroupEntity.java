package com.lucutovidiu.models;

import com.lucutovidiu.household.dto.HouseholdGroupDto;
import com.lucutovidiu.household.dto.HouseholdGroupOnlyDto;
import com.lucutovidiu.household.dto.HouseholdGroupRequestDto;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.util.stream.Collectors.toList;

@Getter
@Setter
@Document(collection = "household_group")
public class HouseholdGroupEntity extends BaseEntity {

    @NotBlank
    @Indexed(unique = true)
    private String groupName;
    @NotBlank
    private String createdBy;
    @DBRef(lazy = true)
    private List<HouseholdItemEntity> householdItems = new ArrayList<>();

    public static HouseholdGroupEntity convertToGroupOnly(HouseholdGroupRequestDto householdGroupRequestDto, String loggerUser) {
        HouseholdGroupEntity entity = new HouseholdGroupEntity();
        entity.setGroupName(householdGroupRequestDto.getGroupName());
        entity.setCreatedBy(loggerUser);
        return entity;
    }

    public HouseholdGroupOnlyDto toHouseholdGroupOnlyDto() {
        HouseholdGroupOnlyDto dto = new HouseholdGroupOnlyDto();
        dto.setId(getId());
        dto.setGroupName(groupName);
        return dto;
    }

    public HouseholdGroupDto toHouseholdGroupDto() {
        HouseholdGroupDto dto = new HouseholdGroupDto();
        dto.setId(getId());
        dto.setGroupName(groupName);
        dto.setHouseholdItems(householdItems.stream()
                .filter(householdItemEntity -> !Objects.isNull(householdItemEntity))
                .map(HouseholdItemEntity::getHouseholdItemDto)
                .collect(toList()));
        return dto;
    }
}
