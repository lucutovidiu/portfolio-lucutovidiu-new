package com.lucutovidiu.pojo;

import com.lucutovidiu.news.bankholiday.dto.UkBankHolidayBodyDto;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class UkBankHolidayBody {
    @NotBlank
    private String shortDate;
    @NotBlank
    private String dayOfWeek;
    private String fullDate;
    @NotBlank
    private String holidayName;

    public UkBankHolidayBodyDto toDto() {
        UkBankHolidayBodyDto dto = new UkBankHolidayBodyDto();
        dto.setHolidayName(holidayName);
        dto.setDayOfWeek(dayOfWeek);
        dto.setShortDate(shortDate);
        dto.setFullDate(fullDate);
        return dto;
    }
}
