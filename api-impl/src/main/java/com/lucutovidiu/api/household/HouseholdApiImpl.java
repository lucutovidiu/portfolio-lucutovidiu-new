package com.lucutovidiu.api.household;

import com.lucutovidiu.household.dto.*;
import com.lucutovidiu.mongo.HouseholdService;
import com.lucutovidiu.mongo.UkBankHolidayService;
import com.lucutovidiu.news.BankHolidays;
import com.lucutovidiu.news.bankholiday.dto.BankHolidayDto;
import com.lucutovidiu.news.bankholiday.dto.UkBankHolidayBodyDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class HouseholdApiImpl implements HouseholdApi {

    private final HouseholdService householdService;
    private final BankHolidays bankHolidays;

    @Override
    public HouseholdGroupPostResponseDto postHouseholdGroup(HouseholdGroupRequestDto householdGroupRequestDto) {
        SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return householdService.addGroup(householdGroupRequestDto, getLoggedUser());
    }

    @Override
    public boolean postHouseholdItem(HouseholdItemRequestDto householdItemRequestDto) {
        return householdService.addItem(householdItemRequestDto);
    }

    @Override
    public List<HouseholdGroupOnlyDto> getHouseholdGroups() {
        return householdService.getGroupsOnly(getLoggedUser());
    }

    @Override
    public boolean deleteGroup(String groupId) {
        return householdService.deleteGroup(groupId, getLoggedUser());
    }

    @Override
    public boolean deleteGroupItem(String groupId, String itemId) {
        return householdService.deleteItemFromGroup(itemId, groupId);
    }

    @Override
    public void syncHolidays() {
        Optional<HouseholdGroupDto> ukHolidayGroup = householdService.getGroup(UkBankHolidayService.groupName(), getLoggedUser());
        ukHolidayGroup.ifPresentOrElse(this::syncPresentGroup, this::createAndSyncPresentGroup);
    }

    private void createAndSyncPresentGroup() {
        HouseholdGroupRequestDto groupRequest = new HouseholdGroupRequestDto(UkBankHolidayService.groupName());
        HouseholdGroupPostResponseDto groupResponse = householdService.addGroup(groupRequest, getLoggedUser());
        HouseholdGroupDto group = new HouseholdGroupDto();
        group.setId(groupResponse.getId());
        group.setHouseholdItems(new ArrayList<>());
        syncPresentGroup(group);
    }

    private void syncPresentGroup(HouseholdGroupDto group) {
        int thisYear = LocalDate.now().getYear();
        List<BankHolidayDto> ukHolidays = bankHolidays.getUkBankHolidaysByYears(List.of(thisYear, thisYear + 1));
        ukHolidays.forEach(holiday -> syncHolidayDays(holiday, group));
    }

    private void syncHolidayDays(BankHolidayDto holidayItems, HouseholdGroupDto group) {
        holidayItems.getHolidays().stream()
                .filter(holiday -> LocalDate.parse(holiday.getFullDate()).isAfter(LocalDate.now()))
                .map(holiday -> {
                    if (checkIfItemExists(group.getHouseholdItems(), holiday.getHolidayName())) {
                        return Optional.empty();
                    }
                    return Optional.of(convertToHouseHoldItem(group.getId(), holidayItems.getTitle(), holiday));
                })
                .filter(Optional::isPresent)
                .map(Optional::get)
                .forEach(dto -> householdService.addItem((HouseholdItemRequestDto) dto));
    }

    private boolean checkIfItemExists(List<HouseholdItemDto> getHouseholdItems, String holidayName) {
        return getHouseholdItems.stream().anyMatch(item -> item.getItemName().equals(holidayName));
    }

    private HouseholdItemRequestDto convertToHouseHoldItem(String id, String title, UkBankHolidayBodyDto holiday) {
        HouseholdItemRequestDto item = new HouseholdItemRequestDto();
        item.setHouseholdGroupId(id);
        item.setItemName(holiday.getHolidayName());
        item.setMoreInfo(title);
        item.setItemStartFromDate(LocalDate.now().toString());
        item.setItemExpirationDate(holiday.getFullDate());
        return item;
    }

    private String getLoggedUser() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
