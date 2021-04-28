package com.lucutovidiu.pojo;

import com.lucutovidiu.news.bankholiday.dto.BankHolidayDto;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class UkBankHoliday {
    private String id;
    @NotBlank
    private String title;
    private String country;
    private Integer year;
    private List<UkBankHolidayBody> holiday;

    public BankHolidayDto toDto() {
        BankHolidayDto dto = new BankHolidayDto();
        dto.setTitle(title);
        dto.setCountry(country);
        dto.setHolidays(holiday.stream().map(UkBankHolidayBody::toDto).collect(Collectors.toList()));
        return dto;
    }

    public Integer getYear() {
        return year;
    }
}
