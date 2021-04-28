package com.lucutovidiu.pojo;

import com.lucutovidiu.news.bankholiday.dto.UkBankHolidayBodyDto;
import com.lucutovidiu.news.bankholiday.dto.UkBankHolidayDto;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Getter
@Setter
public class UkBankHolidayBody {
    @NotBlank
    private String shortDate;
    @NotBlank
    private String dayOfWeek;
    @NotBlank
    private String fullDate;
    @NotBlank
    private String holidayName;
    
    public UkBankHolidayBodyDto toDto(){
        UkBankHolidayBodyDto dto = new UkBankHolidayBodyDto();
        dto.setHolidayName(holidayName);
        dto.setDayOfWeek(dayOfWeek);
        dto.setShortDate(shortDate);
        return dto;
    }
}
