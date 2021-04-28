package com.lucutovidiu.pojo;

import com.lucutovidiu.news.bankholiday.dto.UkBankHolidayDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
public class UkBankHoliday {
    @NotBlank
    private String title;
    private List<UkBankHolidayBody> holiday;

    public UkBankHolidayDto toDto() {
        UkBankHolidayDto dto = new UkBankHolidayDto();
        dto.setTitle(title);
        dto.setHolidays(holiday.stream().map(UkBankHolidayBody::toDto).collect(Collectors.toList()));
        return dto;
    }
}
